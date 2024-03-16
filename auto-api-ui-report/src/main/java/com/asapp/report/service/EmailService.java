package com.asapp.report.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static com.asapp.report.Constants.EMAIL_LIST;
import static com.asapp.report.Constants.EMAIL_SERVICE;
import static com.asapp.report.Constants.INT;
import static com.asapp.report.Constants.LIVE;
import static com.asapp.report.Constants.TEMPLATE_FILE;

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

    @Value("${spring.profiles.active}")
    private String env;

    @Value("${paths.extentReport}")
    private String extentReportPath;

    @Value("${paths.extentReportJunit}")
    private String extentReportJunit;

    @Value("${paths.emailReport}")
    private String emailReportPath;

    @Value("${paths.emailReportJunit}")
    private String emailReportPathJunit;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    public void sendMail(Map<String, Object> model) {

        try {

            LOGGER.info("Sending Test Report Email");
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Template template = configuration.getTemplate(TEMPLATE_FILE);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            String emailFolderPath = "../" + emailReportPath + env;
            File emailFolder = new File(emailFolderPath);
            if (!emailFolder.exists()) {
                emailFolder.mkdirs();
            }
            File emailFile = new File(emailFolderPath + "/" + env + ".html");
            try (PrintWriter emailReport = new PrintWriter(emailFile)) {
                emailReport.print(html);
            }
            LOGGER.info("email html content:\n" + html);

            helper.setText(html, true);
            String testEnv = env.toUpperCase().contains(LIVE) ? LIVE :
                    (env.toUpperCase().contains(INT) ? INT : env);
            helper.setSubject(appName + " - Automation Report - " + testEnv.toUpperCase());
            helper.setFrom(emailFrom);
            File canonicalFile = new File("../").getCanonicalFile();
            File integrationFilePath = new File(extentReportPath + env + "/" + env + ".html");
            File extentReport = new File(canonicalFile + integrationFilePath.getPath());
            helper.addAttachment(env + "Saving the Test Data : .html", extentReport);

            String emailList = System.getProperty(EMAIL_LIST);
            if (emailList.equalsIgnoreCase("QATeam")) {
                helper.setTo(emailToQa.split((",")));
            } else {
                helper.setTo(emailTo.split((",")));
            }

            if (!emailList.isEmpty()) {
                javaMailSender.send(message);
            }

        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }

    }

}
