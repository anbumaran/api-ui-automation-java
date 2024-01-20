package com.asapp.common.validations;

import com.asapp.common.utils.StringUtil;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.assertj.core.api.Assertions.assertThat;

public class Assertions {

    private static final Logger LOGGER = LogManager.getLogger(Assertions.class);

    public void assertEqual(Object actual, Object expected) {

        LOGGER.info("Actual Object : ");
        StringUtil.printObject(actual);

        LOGGER.info("Expected Object : ");
        StringUtil.printObject(expected);

        assertThat(actual).isEqualTo(expected);
        LOGGER.info("Actual - Object matches Expected - Object");

    }

    public void assetStatusCodeSuccess(Response response) {

        org.assertj.core.api.Assertions.assertThat(response.getStatusCode()).isEqualTo(200);
        LOGGER.info("Actual - Response Code - '{}' matches Expected - Response Code is 200 ", response.getStatusCode());

    }

    public void assetStatusCodeFail(Response response) {

        org.assertj.core.api.Assertions.assertThat(response.getStatusCode()).isNotEqualTo(200);
        LOGGER.info("Actual - Response Code - '{}' matches Expected - Response Code is NOT 200", response.getStatusCode());

    }

}
