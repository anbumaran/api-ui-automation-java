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
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static com.asapp.report.Constants.EMAIL_LIST;
import static com.asapp.report.Constants.EMAIL_SERVICE;
import static com.asapp.report.Constants.EMAIL_SUBJECT;
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

    @Value("${sprint.profile.active}")
    private String env;

    @Value("${paths.extentReport}")
    private String extentReportPath;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    public void sendMail(Map<String, Object> model) {

        try {

            LOGGER.info("Sending The Report Email");
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Template template = configuration.getTemplate(TEMPLATE_FILE);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            LOGGER.info("email html content:\n" + html);
            String emailList = System.getProperty(EMAIL_LIST);
            if (emailList.equalsIgnoreCase("QATeam")) {
                helper.setTo(emailToQa.split((",")));
            } else {
                helper.setTo(emailTo.split((",")));
            }
            helper.setText(html, true);
            helper.setSubject(String.format(EMAIL_SUBJECT, appName, env.toUpperCase()));
            helper.setFrom(emailFrom);
            File canonicalFile = new File("../").getCanonicalFile();
            File integrationFilePath = new File(extentReportPath + env + "/" + env + ".html");
            File extentReport = new File(canonicalFile + integrationFilePath.getPath());
            helper.addAttachment(env + ".html", extentReport);
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }

    }

}
