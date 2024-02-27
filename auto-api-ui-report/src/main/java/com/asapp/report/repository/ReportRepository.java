package com.asapp.report.repository;

import com.asapp.report.entities.TestResult;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReportRepository extends MongoRepository<TestResult, String> {

    public void saveTestResult(TestResult testResult);

    public TestResult getTestResult(String id);

    public List<TestResult> getAllTestResults();

    public void deleteTestResult(String id);

}
