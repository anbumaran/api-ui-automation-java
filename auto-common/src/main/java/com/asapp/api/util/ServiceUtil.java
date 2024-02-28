package com.asapp.api.util;


import com.asapp.api.service.Service;
import com.asapp.common.model.ServiceObject;
import com.asapp.common.utils.FileReaderUtil;
import com.asapp.common.utils.JsonUtil;
import com.asapp.common.utils.StringUtil;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import static com.asapp.common.Constants.CONTENT_TYPE_JSON;
import static com.asapp.common.Constants.REQUEST_BODY;
import static com.asapp.common.utils.JsonUtil.getJsonNodeData;
import static com.asapp.common.utils.StringUtil.printObject;
import static io.restassured.RestAssured.given;

public class ServiceUtil {

    private ServiceUtil() {
        //Empty Constructor
    }

    private static final Logger LOGGER = LogManager.getLogger(ServiceUtil.class);

    public static void setServiceAndModule(ServiceObject serviceObject, String serviceName, String moduleName) {

        LOGGER.info("Input - Test Name : {} ", serviceName);
        LOGGER.info("Input - Module Name : {} ", moduleName);

        serviceObject.serviceNode = StringUtil.getCapitalizeNoSpace(serviceName);
        serviceObject.moduleNode = StringUtil.getSmallNoSpace(moduleName);

    }

    public static void setEndPoint(ServiceObject serviceObject, String endPointFile, String baseEndPoint) {

        serviceObject.baseEndPoint = baseEndPoint;
        LOGGER.info("Base - End Point : {} ", serviceObject.baseEndPoint);
        FileReader fileReader = new FileReaderUtil().getFileReader(endPointFile);

        serviceObject.endPointParameters = JsonUtil.getJsonNodeStringAt(fileReader, serviceObject.moduleNode,
                serviceObject.serviceNode);

    }

    public static void setRequestType(ServiceObject serviceObject, String requestType) {

        serviceObject.requestType = requestType.toUpperCase();
        LOGGER.info("Input - Request Type : {} ", serviceObject.requestType);

    }

    public static void setRequestData(ServiceObject serviceObject, String requestFilePath, String testData) {

        serviceObject.requestData = getJsonNodeData(serviceObject, requestFilePath, testData);
        serviceObject.dataId = testData;
        LOGGER.info("Input - Request Data Id : {}", serviceObject.dataId);
        LOGGER.info("Input - Request Data : {}", printObject(serviceObject.requestData));

    }

    public static void setRequestBody(ServiceObject serviceObject) {
        setRequestBody(serviceObject, REQUEST_BODY);
    }

    public static void setEndPointParameter(ServiceObject serviceObject, String... parameterNameList) {

        Object[] parameterValues = new String[parameterNameList.length];

        IntStream.range(0, parameterNameList.length).forEach(i ->
                parameterValues[i] = getParameter(serviceObject, parameterNameList[i]));

        String endPointParameters = serviceObject.endPointParameters;
        serviceObject.endPointParameters = String.format(endPointParameters, parameterValues);
        LOGGER.info("End Point Parameter: {} ", serviceObject.endPointParameters);

    }

    private static String getParameter(ServiceObject serviceObject, String parameterName) {

        String parameter = JsonUtil.getJsonNodeStringAt(serviceObject.requestData, parameterName);
        LOGGER.info("Input - Parameter - Name - {} & Parameter Value - {}", parameterName, parameter);
        return parameter;

    }

    public static void setRequestBody(ServiceObject serviceObject, String requestBodyNode) {

        serviceObject.requestBody = JsonUtil.getJsonNodeAt(serviceObject.requestData, requestBodyNode);
        LOGGER.info("Input - Request Body : {}", printObject(serviceObject.requestBody));

    }

    public static void setExpectedNode(ServiceObject serviceObject, String responseFilePath) {

        serviceObject.expectedRespData = getJsonNodeData(serviceObject, responseFilePath);
        LOGGER.info("Expected - Response Data : {}", printObject(serviceObject.expectedRespData));

    }

    public static RequestSpecification getRequestSpecification(ServiceObject serviceObject) {

        String[] splitQueryParam = serviceObject.endPointParameters.split("\\?(?!\\?)");

        RequestSpecification requestSpecification;

        if (splitQueryParam.length > 1) {

            serviceObject.endPointParameters = splitQueryParam[0];

            Map<String, Object> queryParam = getQueryParam(splitQueryParam[1]);

            if (serviceObject.requestType.equalsIgnoreCase("GET") ||
                    serviceObject.requestType.equalsIgnoreCase("DELETE")) {

                requestSpecification = given().queryParams(queryParam);

            } else if (serviceObject.requestBody == null) {

                requestSpecification = given()
                        .contentType(CONTENT_TYPE_JSON)
                        .queryParams(queryParam)
                        .baseUri(serviceObject.baseEndPoint + serviceObject.endPointParameters);

            } else {

                requestSpecification = given()
                        .contentType(CONTENT_TYPE_JSON)
                        .queryParams(queryParam)
                        .baseUri(serviceObject.baseEndPoint + serviceObject.endPointParameters)
                        .body(serviceObject.requestBody);

            }
        } else {

            requestSpecification = getRequestSpecificationNoQueryParam(serviceObject);

        }

        return requestSpecification;

    }

    public static RequestSpecification getRequestSpecificationNoQueryParam(ServiceObject serviceObject) {

        RequestSpecification requestSpecification;


        if (serviceObject.requestType.equalsIgnoreCase("GET") ||
                serviceObject.requestType.equalsIgnoreCase("DELETE")) {

            requestSpecification = given();

        } else if (serviceObject.requestBody == null) {

            requestSpecification = given()
                    .contentType(CONTENT_TYPE_JSON)
                    .baseUri(serviceObject.baseEndPoint + serviceObject.endPointParameters);

        } else {

            requestSpecification = given()
                    .contentType(CONTENT_TYPE_JSON)
                    .baseUri(serviceObject.baseEndPoint + serviceObject.endPointParameters)
                    .body(serviceObject.requestBody);

        }

        return requestSpecification;

    }


    public static Map<String, Object> getQueryParam(String inputString) {

        String[] queryParamPairs = inputString.split("&");
        Map<String, Object> queryParam = new HashMap<>();

        for (String queryParamPair : queryParamPairs) {
            String[] queryParamKeyVal = queryParamPair.split("=");
            queryParam.put(queryParamKeyVal[0], queryParamKeyVal[1]);
            LOGGER.info("Query Param Key - {}, Value - {}", queryParamKeyVal[0], queryParamKeyVal[0]);
        }

        return queryParam;

    }

    public static void hitService(ServiceObject serviceObject) {

        serviceObject.requestSpecification = getRequestSpecification(serviceObject);

        serviceObject.response = new Service().getResponse(serviceObject);

    }

}
