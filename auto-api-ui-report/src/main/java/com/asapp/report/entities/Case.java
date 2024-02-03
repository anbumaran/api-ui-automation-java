package com.asapp.report.entities;

import lombok.Data;

import java.io.Serializable;

@Data
public class Case implements Serializable {

    private String className;
    private String name;
    private boolean skipped;
    private String skippedMessage;
    private String status;
    private String failedMessage;
    private String failedType;
    private String failedShortMessage;
    private double duration;

}
