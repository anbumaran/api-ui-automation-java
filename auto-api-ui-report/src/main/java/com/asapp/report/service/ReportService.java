package com.asapp.report.service;

import com.asapp.report.dto.MappingDTO;
import com.asapp.report.entities.TestResult;

import java.io.IOException;
import java.util.Map;

public interface ReportService {

    TestResult processReports(Map<String, MappingDTO> mappingDTOMap) throws IOException;

    String getHTMLReport(TestResult testResult);

    Map<String,MappingDTO> readTestMappingXML();

}
