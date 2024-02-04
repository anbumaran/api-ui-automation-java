package com.asapp.report.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import static com.asapp.report.Constants.TEMPLATE_PATH;

@Configuration
@EnableScheduling
@EnableAsync
public class AppConfig {

    @Primary
    @Bean
    public FreeMarkerConfigurationFactoryBean factoryBean(){
        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath(TEMPLATE_PATH);
        return bean;
    }

}
