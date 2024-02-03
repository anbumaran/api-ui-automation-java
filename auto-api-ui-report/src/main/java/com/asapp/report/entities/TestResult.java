package com.asapp.report.entities;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TestResult implements Serializable {

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
