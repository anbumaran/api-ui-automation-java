package com.asapp.report.service;


import com.asapp.common.utils.DateTimeUtil;
import com.asapp.common.utils.MathUtil;
import com.asapp.report.dto.MappingDTO;
import com.asapp.report.entities.Case;
import com.asapp.report.entities.Suite;
import com.asapp.report.entities.TestResult;
import com.asapp.report.repository.ReportRepository;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.asapp.report.Constants.REPORT_SERVICE;
import static com.asapp.report.Constants.TEMPLATE_FILE;

@Service(REPORT_SERVICE)
public class ReportServiceImpl implements ReportService {

    @Autowired
    private Configuration configuration;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ReportRepository reportRepository;

    @Value("${paths.integration}")
    private String integrationTestPath;

    @Value("${paths.integrationJunit}")
    private String integrationJunitTestPath;

    @Value("${application.name}")
    private String appName;

    @Value("${spring.profiles.active}")
    private String env;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Override
    public TestResult processReports(Map<String, MappingDTO> testMappings) throws IOException {

        LOGGER.info("Processing Test Report");

        File canonicalFile = new File("../").getCanonicalFile();
        File integrationFilePath;
        if (env.contains("Junit")) {
            integrationFilePath = new File(integrationJunitTestPath + env);
        } else {
            integrationFilePath = new File(integrationTestPath + env);
        }
        File folder = new File(canonicalFile + integrationFilePath.getPath());
        File[] listOfFiles = folder.listFiles();

        List<Suite> suites = new ArrayList<>();
        TestResult testResult = new TestResult();

        if (listOfFiles != null) {
            int lastNumber = listOfFiles.length - 1;
            for (File filename : listOfFiles) {
                try {
                    if (filename.getName().endsWith(".xml") || filename.getName().endsWith(".XML")) {
                        Suite suite = parseXmlByXmlEventReader(Paths.get(filename.toString()));
                        MappingDTO mappingDTO = testMappings.get(suite.getName());
                        if (mappingDTO != null) {
                            suite.setDisplayName(mappingDTO.getDisplayName());
                            suite.setOrder(mappingDTO.getOrder());
                        } else {
                            suite.setDisplayName(suite.getName());
                            suite.setOrder(lastNumber);
                        }
                        suites.add(suite);
                    }
                } catch (FileNotFoundException | XMLStreamException e) {
                    e.printStackTrace();
                }
            }
        }

        int failCount = 0, passCount = 0, skipCount = 0, tests = 0;
        double duration = 0d;
        List<Double> epochTimeBeforeExe = new ArrayList<>();
        List<Double> epochTimeAfterExe = new ArrayList<>();

        for (Suite suite : suites) {
            passCount += suite.getPassed();
            failCount += suite.getFailed();
            skipCount += suite.getIgnored();
            tests += suite.getTests();
            duration += suite.getDuration();
            epochTimeBeforeExe.add(DateTimeUtil.convertToEpoch(suite.getTimeStamp()));
            epochTimeAfterExe.add(DateTimeUtil.convertToEpoch(suite.getTimeStamp()) + suite.getDuration());
        }

        testResult.setAppName(appName);
        testResult.setEnv(env.toUpperCase());
        testResult.setPassCount(passCount);
        testResult.setFailCount(failCount);
        testResult.setSkipCount(skipCount);
        testResult.setDuration(duration);
        testResult.setTimestamp(MathUtil.diffBtwMinOfMinListMaxOfMaxList(epochTimeBeforeExe, epochTimeAfterExe));
        testResult.setReadableDuration(DateTimeUtil.getReadableTime(duration));
        testResult.setSuites(suites);
        testResult.setTests(tests);
        if (testResult.getSuites() != null && testResult.getSuites().size() > 0) {
            getHTMLReport(testResult);
            //saveTestResult(testResult);
        }

        return testResult;

    }

    @Override
    public String getHTMLReport(TestResult testResult) {
        String html = null;
        Map<String, Object> model = new HashMap<>();
        try {

            Template template = configuration.getTemplate(TEMPLATE_FILE);
            model.put("appName", testResult.getAppName());
            model.put("env", testResult.getEnv());
            model.put("totalTestCases", testResult.getTests());
            model.put("passedTestCases", testResult.getPassCount());
            model.put("failedTestCases", testResult.getFailCount());
            model.put("testDuration", DateTimeUtil.getReadableTime(testResult.getTimestamp()));

            List<Suite> suites = testResult.getSuites();
            suites = suites.stream().sorted(Comparator.comparing(Suite::getOrder)).collect(Collectors.toList());
            long totalNoOfTests = suites.size();
            long noOfTestPassed = suites.stream().filter(suite -> suite.getFailed() == 0).count();
            long noOfTestFailed = totalNoOfTests - noOfTestPassed;

            model.put("testResultsList", suites);
            model.put("totalEndPoints", totalNoOfTests);
            model.put("passedEndPoints", noOfTestPassed);
            model.put("failedEndPoints", noOfTestFailed);

            List<Case> failedTestCaseList = new ArrayList<>();
            suites.forEach(suite -> {
                List<Case> failedList = suite.getCases().stream().filter(
                        aCase -> aCase.getStatus().equals("FAILED")).collect(Collectors.toList());
                failedTestCaseList.addAll(failedList);
            });

            model.put("failedTestCaseList", failedTestCaseList);

            html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

        emailService.sendMail(model);

        return html;
    }

    @Override
    public Map<String, MappingDTO> readTestMappingXML() {
        Map<String, MappingDTO> mappings = new HashMap<>();
        XMLEventReader reader = null;
        try {
            File testMappingXml = new ClassPathResource("testmappings.xml").getFile();
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            reader = xmlInputFactory.createXMLEventReader(new FileInputStream(testMappingXml));
            XMLEvent event;
            while (reader.hasNext()) {
                event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement element = event.asStartElement();
                    if ("mapping".equals(element.getName().getLocalPart())) {
                        MappingDTO mappingDTO = new MappingDTO();
                        mappingDTO.setClassName(getAttributeValue(element, "classname"));
                        mappingDTO.setDisplayName(getAttributeValue(element, "displayname"));
                        mappingDTO.setOrder(Integer.parseInt(getAttributeValue(element, "order")));

                        mappings.put(mappingDTO.getClassName(), mappingDTO);

                    }
                }
            }
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (XMLStreamException e) {
                    e.printStackTrace();
                }
            }
        }
        return mappings;
    }

    private Suite parseXmlByXmlEventReader(Path path) throws FileNotFoundException, XMLStreamException {

        XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(path.toFile()));

        Set<Case> cases = new HashSet<>();
        XMLEvent event;
        Case aCase = null;
        boolean isCaseTraversed = false;
        Suite suite = new Suite();
        int ignored = 0, test = 0, failed = 0;

        while (reader.hasNext()) {
            event = reader.nextEvent();
            if (event.isStartElement()) {

                StartElement element = event.asStartElement();

                switch (element.getName().getLocalPart()) {

                    case "testsuite":
                        suite.setName(getAttributeValue(element, "name"));
                        suite.setDuration(Double.parseDouble(getAttributeValue(element, "time")));
                        suite.setTimeStamp(getAttributeValue(element, "timestamp"));
                        suite.setReadableDuration(DateTimeUtil.getReadableTime(
                                Double.parseDouble(getAttributeValue(element, "time"))));
                        break;

                    case "testcase":
                        aCase = new Case();
                        aCase.setClassName(getAttributeValue(element, "classname"));
                        aCase.setName(getAttributeValue(element, "name"));
                        aCase.setStatus("PASSED");
                        isCaseTraversed = true;
                        test++;
                        break;

                    case "skipped":
                        if (isCaseTraversed) {
                            aCase.setSkipped(true);
                            aCase.setStatus("SKIPPED");
                            ignored++;
                        }
                        break;

                    case "failure":
                        if (isCaseTraversed) {
                            aCase.setStatus("FAILED");
                            aCase.setFailedMessage(getAttributeValue(element, "message"));
                            aCase.setFailedType(getAttributeValue(element, "type"));
                            event = reader.nextEvent();
                            if (event.isCharacters()) {
                                aCase.setFailedShortMessage(event.asCharacters().getData());
                            }
                            failed++;
                        }
                        break;

                }
            }

            if (event.isEndElement()) {
                EndElement endElement = event.asEndElement();
                if (endElement.getName().getLocalPart().equals("testcase")) {
                    cases.add(aCase);
                    aCase = null;
                    isCaseTraversed = false;
                }
            }
        }

        suite.setCases(cases);
        suite.setIgnored(ignored);
        suite.setFailed(failed);
        suite.setTests(test - ignored);
        suite.setPassed(test - (failed + ignored));

        return suite;

    }

    private static String getAttributeValue(StartElement startElement, String attributeName) {
        return startElement.getAttributeByName(new QName(attributeName)).getValue();
    }

    private void saveTestResult(TestResult testResult) {
        String testResultName = testResult.getAppName() + " - " + testResult.getEnv();
        LOGGER.info("Saving Test Result for " + testResultName);
        LOGGER.info("Saving the Test Data : \n " + testResult);
        reportRepository.saveTestResult(testResult);
        LOGGER.info("Test Result Saved Successfully for " + testResultName);
    }

}
