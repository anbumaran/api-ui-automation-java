package com.asapp.report.entities;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class Suite implements Serializable {

    private String id;
    private Set<Case> cases;
    private String name;
    private double duration;
    private String timeStamp;
    private String readableDuration;
    private int tests;
    private int passed;
    private int failed;
    private int ignored;
    private String displayName;
    private int order;

}
