package com.asapp.api.dev;

import com.asapp.api.BaseTestApi;
import com.asapp.api.MyApiApp;
import com.asapp.api.controller.EmployeeController;
import com.asapp.api.util.ServiceUtil;
import com.asapp.common.model.ServiceObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static com.asapp.api.Constants.DEPT;
import static com.asapp.api.Constants.EMP_ID;
import static com.asapp.api.Constants.EMP_NAME;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = MyApiApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("${testautomation.env}")
@ContextConfiguration(classes = {EmployeeController.class})
class EmployeeDevTestApi extends BaseTestApi {

    @Value("${local.server.port}")
    private int portNumber;

    @Mock
    ServiceObject serviceObject;

    private static final String ADD_EMP_SERVICE_NAME = "Add Employee";
    private static final String ADD_EMP_REQUEST_TYPE = "Post";

    private static final String GET_EMP_SERVICE_NAME = "Get Employee";
    private static final String GET_EMP_REQUEST_TYPE = "Get";

    private static final String UPDATE_EMP_SERVICE_NAME = "Update Employee";
    private static final String UPDATE_EMP_REQUEST_TYPE = "Put";

    private static final String DELETE_EMP_SERVICE_NAME = "Delete Employee";
    private static final String DELETE_EMP_REQUEST_TYPE = "Delete";

    @Order(1)
    @ParameterizedTest(name = "Test - " + ADD_EMP_SERVICE_NAME + " Service in - " + EMP_MODULE_NAME +
            " Module - Valid service response - Positive scenario - {0}")
    @ValueSource(ints = {1, 2})
    @Tag("live")
    @Tag("int")
    public void addEmployee(int testInput) {

        setInputServiceAndModule(serviceObject, testInput, ADD_EMP_SERVICE_NAME, EMP_MODULE_NAME,
                portNumber, ADD_EMP_REQUEST_TYPE);

        ServiceUtil.setEndPointParameter(serviceObject, EMP_NAME, EMP_ID, DEPT);

        verifyServiceResponseString(serviceObject);

    }

    @Order(2)
    @ParameterizedTest(name = "Test - " + GET_EMP_SERVICE_NAME + " Service in - " + EMP_MODULE_NAME +
            " Module - Valid service response - Positive scenario - {0}")
    @ValueSource(ints = {1, 2})
    @Tag("live")
    @Tag("int")
    public void getEmployee(int testInput) {

        setInputServiceAndModule(serviceObject, testInput, GET_EMP_SERVICE_NAME, EMP_MODULE_NAME,
                portNumber, GET_EMP_REQUEST_TYPE);

        ServiceUtil.setEndPointParameter(serviceObject, EMP_ID);

        verifyServiceResponseBody(serviceObject);

    }

    @Order(3)
    @ParameterizedTest(name = "Test - " + UPDATE_EMP_SERVICE_NAME + " Service in - " + EMP_MODULE_NAME +
            " Module - Valid service response - Positive scenario - {0}")
    @ValueSource(ints = {1, 2})
    @Tag("live")
    @Tag("int")
    public void updateEmployee(int testInput) {

        setInputServiceAndModule(serviceObject, testInput, UPDATE_EMP_SERVICE_NAME, EMP_MODULE_NAME,
                portNumber, UPDATE_EMP_REQUEST_TYPE);

        ServiceUtil.setEndPointParameter(serviceObject, EMP_ID);
        ServiceUtil.setRequestBody(serviceObject);

        verifyServiceResponseBody(serviceObject);

    }

    @Order(4)
    @ParameterizedTest(name = "Test - " + DELETE_EMP_SERVICE_NAME + " Service in - " + EMP_MODULE_NAME +
            " Module - Valid service response - Positive scenario - {0}")
    @ValueSource(ints = {1, 2})
    @Tag("live")
    @Tag("int")
    public void deleteEmployee(int testInput) {

        setInputServiceAndModule(serviceObject, testInput, DELETE_EMP_SERVICE_NAME, EMP_MODULE_NAME,
                portNumber, DELETE_EMP_REQUEST_TYPE);

        ServiceUtil.setEndPointParameter(serviceObject, EMP_ID);

        verifyServiceResponseString(serviceObject);

    }

}