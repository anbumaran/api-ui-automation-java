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
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Value("${application.name}")
    private String appName;

    @Value("${sprint.profile.active}")
    private String env;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Override
    public TestResult processReports(Map<String, MappingDTO> mappingDTOMap) throws IOException {

        LOGGER.info("Processing Test Report");

        File canonicalFile = new File("../").getCanonicalFile();
        File integrationFilePath = new File(integrationTestPath + env);
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
                        MappingDTO mappingDTO = mappingDTOMap.get(suite.getName());
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
            epochTimeBeforeExe.add((double) DateTimeUtil.convertToEpoch(suite.getTimeStamp()));
            epochTimeAfterExe.add((double) DateTimeUtil.convertToEpoch(suite.getTimeStamp()) + suite.getDuration());
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
            saveTestResult(testResult);
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
            model.put("totalEndPoint", totalNoOfTests);
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
        return html;
    }

    @Override
    public Map<String, MappingDTO> readTestMappingXML() {
        return null;
    }

    private Suite parseXmlByXmlEventReader(Path path) throws FileNotFoundException, XMLStreamException {
        return null;
    }

    private void saveTestResult(TestResult testResult) {
        String testResultName = testResult.getAppName() + " - " + testResult.getEnv();
        LOGGER.info("Saving Test Result for " + testResultName);
        reportRepository.save(testResult);
        LOGGER.info("Test Result Saved Successfully for " + testResultName);
    }

}
