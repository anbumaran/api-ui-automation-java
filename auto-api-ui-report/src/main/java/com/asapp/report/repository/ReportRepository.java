package com.asapp.report.repository;

import com.asapp.report.entities.TestResult;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportRepository extends MongoRepository<TestResult, String> {
}
