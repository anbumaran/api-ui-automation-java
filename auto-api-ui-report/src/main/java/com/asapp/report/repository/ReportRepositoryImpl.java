package com.asapp.report.repository;

import com.asapp.report.entities.TestResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReportRepositoryImpl {

    @Autowired
    private ReportRepository reportRepository;

    public void saveTestResult(TestResult testResult) {
        reportRepository.save(testResult);
    }

    public TestResult getTestResult(String id) {
        return reportRepository.findById(id).orElse(null);
    }

    public List<TestResult> getAllTestResults() {
        return reportRepository.findAll();
    }

    public void deleteTestResult(String id) {
        reportRepository.deleteById(id);
    }

}
