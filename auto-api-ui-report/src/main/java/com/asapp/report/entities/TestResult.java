package com.asapp.report.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@Document(collection = "testReports")
public class TestResult implements Serializable {

    @Id
    private String id;
    private int buildNumber;
    private double duration;
    private double timestamp;
    private String readableDuration;
    private int failCount;
    private int passCount;
    private int skipCount;
    private int tests;
    private double success;
    private String appName;
    private List<Suite> suites;
    private String env;

}
