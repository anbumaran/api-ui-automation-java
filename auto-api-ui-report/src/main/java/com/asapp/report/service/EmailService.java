package com.asapp.report.service;

import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.asapp.report.Constants.EMAIL_SERVICE;

@Service(EMAIL_SERVICE)
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Configuration configuration;

    @Value("${email.from}")
    private String emailFrom;

    @Value("${email.to}")
    private String emailTo;

    @Value("${email.toQa}")
    private String emailToQa;

    @Value("${application.name}")
    private String appName;

    @Value("${sprint.profile.active}")
    private String env;

    @Value("${paths.extentReport}")
    private String extentReportPath;

}
