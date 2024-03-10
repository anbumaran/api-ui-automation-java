package com.asapp.api;

import com.asapp.api.model.EmployeeDTO;
import com.asapp.api.util.ServiceUtil;
import com.asapp.common.model.ServiceObject;
import com.asapp.common.validations.Assertions;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Comparator;
import java.util.List;

import static com.asapp.TestConstants.END_POINT_BASE;
import static com.asapp.TestConstants.END_POINT_FILE;
import static com.asapp.TestConstants.EVN_PROPERTY;
import static com.asapp.TestConstants.REQUEST_FILE_PATH;
import static com.asapp.TestConstants.RESPONSE_FILE_PATH;
import static com.asapp.TestConstants.TEST_DATA;

public class BaseTestApi extends Assertions {

    protected static final String EMP_MODULE_NAME = "Employee";


    public void setInputServiceAndModule(ServiceObject serviceObject, int testDataInput, String serviceName,
                                         String moduleName, int portNumber, String requestType) {

        setInputServiceAndModule(serviceObject, serviceName, moduleName, portNumber, requestType);

        String testData = TEST_DATA + testDataInput;

        ServiceUtil.setRequestData(serviceObject, REQUEST_FILE_PATH, testData);

    }

    public void setInputServiceAndModule(ServiceObject serviceObject, String serviceName, String moduleName,
                                         int portNumber, String requestType) {

        serviceObject.env = System.getProperty(EVN_PROPERTY);

        ServiceUtil.setServiceAndModule(serviceObject, serviceName, moduleName);

        String baseEndPoint = String.format(END_POINT_BASE, portNumber);

        ServiceUtil.setEndPoint(serviceObject, END_POINT_FILE, baseEndPoint);

        ServiceUtil.setRequestType(serviceObject, requestType);

    }


    public void verifyServiceResponseString(ServiceObject serviceObject) {

        hitServiceAndVerifyStatusSuccess(serviceObject);

        String actualResponse = serviceObject.response.getBody().asString();

        ServiceUtil.setExpectedNode(serviceObject, RESPONSE_FILE_PATH);

        String expectedResponse = serviceObject.expectedRespData.asText();

        assertEqual(actualResponse, expectedResponse);

    }

    public void verifyServiceResponseBody(ServiceObject serviceObject) {

        hitServiceAndVerifyStatusSuccess(serviceObject);

        EmployeeDTO employeeDTOActual = serviceObject.response.as(EmployeeDTO.class);

        ServiceUtil.setExpectedNode(serviceObject, RESPONSE_FILE_PATH);
        EmployeeDTO employeeDTOExpected = new ObjectMapper().convertValue(
                serviceObject.expectedRespData, new TypeReference<>() {
                });

        assertEqual(employeeDTOActual, employeeDTOExpected);

    }

    public void verifyServiceResponseBodyList(ServiceObject serviceObject) {

        hitServiceAndVerifyStatusSuccess(serviceObject);

        List<EmployeeDTO> employeeDTOActual = new ObjectMapper().convertValue(
                serviceObject.response.as(JsonNode.class), new TypeReference<>() {
                });

        ServiceUtil.setExpectedNode(serviceObject, RESPONSE_FILE_PATH);
        List<EmployeeDTO> employeeDTOExpected = new ObjectMapper().convertValue(
                serviceObject.expectedRespData, new TypeReference<>() {
                });

        employeeDTOActual.sort(Comparator.comparing(EmployeeDTO::getEmpId));
        employeeDTOExpected.sort(Comparator.comparing(EmployeeDTO::getEmpId));

        assertEqual(employeeDTOActual, employeeDTOExpected);

    }

    public void hitServiceAndVerifyStatusSuccess(ServiceObject serviceObject) {

        ServiceUtil.hitService(serviceObject);

        assertStatusCodeSuccess(serviceObject.response);

    }


}
