package com.asapp.report.service;

import com.asapp.report.dto.MappingDTO;
import com.asapp.report.entities.TestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.module.Configuration;
import java.util.Map;

import static com.asapp.report.Constants.REPORT_SERVICE;

@Service(REPORT_SERVICE)
public class ReportServiceImpl implements ReportService {

    @Autowired
    private Configuration configuration;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ReportService reportService;

    @Value("${paths.integration}")
    private String integrationTestPath;

    @Value("${application.name}")
    private String appName;

    @Value("${sprint.profile.active}")
    private String env;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Override
    public TestResult processReports(Map<String, MappingDTO> mappingDTOMap) throws IOException {
        return null;
    }

    @Override
    public String getHTMLReport(TestResult testResult) {
        return null;
    }

    @Override
    public Map<String, MappingDTO> readTestMappingXML() {
        return null;
    }

}
