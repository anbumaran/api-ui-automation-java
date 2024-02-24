package com.asapp.api.live;

import com.asapp.api.model.EmployeeDTO;
import com.asapp.api.util.ServiceUtil;
import com.asapp.common.model.ServiceObject;
import com.asapp.common.validations.Assertions;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.asapp.api.Constants.DEFAULT_PORT_NUMBER;
import static com.asapp.api.Constants.END_POINT_BASE;
import static com.asapp.api.Constants.END_POINT_FILE;
import static com.asapp.api.Constants.EVN_PROPERTY;
import static com.asapp.api.Constants.REQUEST_FILE_PATH;
import static com.asapp.api.Constants.RESPONSE_FILE_PATH;
import static com.asapp.api.Constants.TEST_DATA;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeLiveTest extends Assertions {

    private final int portNumber = DEFAULT_PORT_NUMBER;

    @Mock
    ServiceObject serviceObject;

    private static final Logger LOGGER = LogManager.getLogger(EmployeeLiveTest.class);

    private static final String ADD_EMP_SERVICE_NAME = "Add Employee";
    private static final String ADD_EMP_MODULE_NAME = "Employee";
    private static final String ADD_EMP_REQUEST_TYPE = "Post";

    private static final String GET_EMP_SERVICE_NAME = "Get Employee";
    private static final String GET_EMP_MODULE_NAME = "Employee";
    private static final String GET_EMP_REQUEST_TYPE = "Get";

    private static final String UPDATE_EMP_SERVICE_NAME = "Update Employee";
    private static final String UPDATE_EMP_MODULE_NAME = "Employee";
    private static final String UPDATE_EMP_REQUEST_TYPE = "Put";

    private static final String DELETE_EMP_SERVICE_NAME = "Delete Employee";
    private static final String DELETE_EMP_MODULE_NAME = "Employee";
    private static final String DELETE_EMP_REQUEST_TYPE = "Delete";

    private static final String EMP_NAME = "empName";
    private static final String EMP_ID = "empId";
    private static final String DEPT = "dept";

    @Order(1)
    @ParameterizedTest(name = "Test - " + ADD_EMP_SERVICE_NAME + " Service in - " + ADD_EMP_MODULE_NAME +
            " Module - Valid service response - Positive scenario - {0}")
    @ValueSource(ints = {1, 2})
    @Tag("live")
    @Tag("dev")
    public void addEmployee(int testInput) {

        setInputServiceAndModule(serviceObject, testInput, ADD_EMP_SERVICE_NAME, ADD_EMP_MODULE_NAME,
                portNumber, ADD_EMP_REQUEST_TYPE);

        ServiceUtil.setEndPointParameter(serviceObject, EMP_NAME, EMP_ID, DEPT);

        verifyServiceResponseString(serviceObject);

    }

    @Order(2)
    @ParameterizedTest(name = "Test - " + GET_EMP_SERVICE_NAME + " Service in - " + GET_EMP_MODULE_NAME +
            " Module - Valid service response - Positive scenario - {0}")
    @ValueSource(ints = {1, 2})
    @Tag("live")
    @Tag("dev")
    public void getEmployee(int testInput) {

        setInputServiceAndModule(serviceObject, testInput, GET_EMP_SERVICE_NAME, GET_EMP_MODULE_NAME,
                portNumber, GET_EMP_REQUEST_TYPE);

        ServiceUtil.setEndPointParameter(serviceObject, EMP_ID);

        verifyServiceResponseBody(serviceObject);

    }

    @Order(3)
    @ParameterizedTest(name = "Test - " + UPDATE_EMP_SERVICE_NAME + " Service in - " + UPDATE_EMP_MODULE_NAME +
            " Module - Valid service response - Positive scenario - {0}")
    @ValueSource(ints = {1, 2})
    @Tag("live")
    @Tag("dev")
    public void updateEmployee(int testInput) {

        setInputServiceAndModule(serviceObject, testInput, UPDATE_EMP_SERVICE_NAME, UPDATE_EMP_MODULE_NAME,
                portNumber, UPDATE_EMP_REQUEST_TYPE);

        ServiceUtil.setEndPointParameter(serviceObject, EMP_ID);
        ServiceUtil.setRequestBody(serviceObject);

        verifyServiceResponseBody(serviceObject);

    }

    @Order(4)
    @ParameterizedTest(name = "Test - " + DELETE_EMP_SERVICE_NAME + " Service in - " + DELETE_EMP_MODULE_NAME +
            " Module - Valid service response - Positive scenario - {0}")
    @ValueSource(ints = {1, 2})
    @Tag("live")
    @Tag("dev")
    public void deleteEmployee(int testInput) {

        setInputServiceAndModule(serviceObject, testInput, DELETE_EMP_SERVICE_NAME, DELETE_EMP_MODULE_NAME,
                portNumber, DELETE_EMP_REQUEST_TYPE);

        ServiceUtil.setEndPointParameter(serviceObject, EMP_ID);

        verifyServiceResponseString(serviceObject);

    }

    public void setInputServiceAndModule(ServiceObject serviceObject, int testDataInput, String serviceName,
                                         String moduleName, int portNumber, String requestType) {

        LOGGER.info("Then Verify valid service response for the service - '{}' in the module - '{}'",
                serviceName, moduleName);

        String testData = TEST_DATA + testDataInput;

        serviceObject.env = System.getProperty(EVN_PROPERTY);

        ServiceUtil.setServiceAndModule(serviceObject, serviceName, moduleName);

        String baseEndPoint = String.format(END_POINT_BASE, portNumber);

        ServiceUtil.setEndPoint(serviceObject, END_POINT_FILE, baseEndPoint);

        ServiceUtil.setRequestData(serviceObject, REQUEST_FILE_PATH, testData);

        ServiceUtil.setRequestType(serviceObject, requestType);

    }

    public void verifyServiceResponseString(ServiceObject serviceObject) {

/*        hitServiceAndVerifyStatusSuccess(serviceObject);

        String actualResponse = serviceObject.response.getBody().asString();

        ServiceUtil.setExpectedNode(serviceObject, RESPONSE_FILE_PATH);

        String expectedResponse = serviceObject.expectedRespData.asText();

        assertEqual(actualResponse, expectedResponse);*/

    }

    public void verifyServiceResponseBody(ServiceObject serviceObject) {

/*        hitServiceAndVerifyStatusSuccess(serviceObject);

        EmployeeDTO employeeDTOActual = serviceObject.response.as(EmployeeDTO.class);

        ServiceUtil.setExpectedNode(serviceObject, RESPONSE_FILE_PATH);
        EmployeeDTO employeeDTOExpected = new ObjectMapper().convertValue(
                serviceObject.expectedRespData, new TypeReference<>() {
                });

        assertEqual(employeeDTOActual, employeeDTOExpected);*/

    }

    public void hitServiceAndVerifyStatusSuccess(ServiceObject serviceObject) {

        ServiceUtil.hitService(serviceObject);

        //assertStatusCodeSuccess(serviceObject.response);

    }


}