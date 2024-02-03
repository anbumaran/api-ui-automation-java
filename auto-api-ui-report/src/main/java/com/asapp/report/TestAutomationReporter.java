package com.asapp.report;

import com.asapp.report.dto.MappingDTO;
import com.asapp.report.service.ReportService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.Map;

import static com.asapp.report.Constants.BASE_PACKAGE;
import static com.asapp.report.Constants.ENTITIES;
import static com.asapp.report.Constants.REPORT_SERVICE;

@EntityScan(ENTITIES)
@SpringBootApplication(scanBasePackages = BASE_PACKAGE)
public class TestAutomationReporter {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(TestAutomationReporter.class, args);
        ReportService reportService = (ReportService) context.getBean(REPORT_SERVICE);
        Map<String, MappingDTO> testMappings = reportService.readTestMappingXML();
        try {
            reportService.processReports(testMappings);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
