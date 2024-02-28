package com.asapp.api.dev;

import com.asapp.api.BaseTest;
import com.asapp.api.MyApiApp;
import com.asapp.api.controller.EmployeeController;
import com.asapp.api.util.ServiceUtil;
import com.asapp.common.model.ServiceObject;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static com.asapp.api.Constants.DEPT;
import static com.asapp.api.Constants.EMP_ID;
import static com.asapp.api.Constants.EMP_NAME;
import static com.asapp.api.Constants.TEST_DATA;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = MyApiApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("${testautomation.env}")
@ContextConfiguration(classes = {EmployeeController.class})
class MultiEmployeesDevTest extends BaseTest {

    @Value("${local.server.port}")
    private int portNumber;

    @Mock
    ServiceObject serviceObject;

    private static final Logger LOGGER = LogManager.getLogger(MultiEmployeesDevTest.class);

    private static final String ADD_EMP_SERVICE_NAME = "Add Employees";
    private static final String ADD_EMP_REQUEST_TYPE = "Post";

    private static final String GET_EMP_SERVICE_NAME = "Get Filter Employees";
    private static final String GET_EMP_REQUEST_TYPE = "Get";

    private static final String GET_EMPS_SERVICE_NAME = "Get All Employees";
    private static final String GET_EMPS_REQUEST_TYPE = "Get";

    private static final String DELETE_EMP_SERVICE_NAME = "Delete All Employees";
    private static final String DELETE_EMP_REQUEST_TYPE = "Delete";

    private static final String EQUAL = "=";
    private static final String AND = "&";

    @Order(1)
    @ParameterizedTest(name = "Test - " + DELETE_EMP_SERVICE_NAME + " Service in - " + EMP_MODULE_NAME +
            " Module - Valid service response - Positive scenario - {0}")
    @ValueSource(ints = {1})
    @Tag("live")
    @Tag("dev")
    public void deleteAllEmployeesStart(int testInput) {

        setInputServiceAndModule(serviceObject, DELETE_EMP_SERVICE_NAME, EMP_MODULE_NAME,
                portNumber, DELETE_EMP_REQUEST_TYPE);

        ServiceUtil.setEndPointParameter(serviceObject);

        serviceObject.dataId = TEST_DATA + testInput;

        verifyServiceResponseString(serviceObject);

    }

    @Order(2)
    @ParameterizedTest(name = "Test - " + ADD_EMP_SERVICE_NAME + " Service in - " + EMP_MODULE_NAME +
            " Module - Valid service response - Positive scenario - {0}")
    @ValueSource(ints = {1})
    @Tag("live")
    @Tag("dev")
    public void addEmployees(int testInput) {

        setInputServiceAndModule(serviceObject, testInput, ADD_EMP_SERVICE_NAME, EMP_MODULE_NAME,
                portNumber, ADD_EMP_REQUEST_TYPE);

        ServiceUtil.setEndPointParameter(serviceObject);

        ServiceUtil.setRequestBody(serviceObject);

        verifyServiceResponseString(serviceObject);

    }

    @Order(3)
    @ParameterizedTest(name = "Test - " + GET_EMP_SERVICE_NAME + " Service in - " + EMP_MODULE_NAME +
            " Module - Valid service response - Positive scenario - {0}")
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8})
    @Tag("live")
    @Tag("int")
    @Tag("dev")
    public void getFilterEmployee(int testInput) {

        setInputServiceAndModule(serviceObject, testInput, GET_EMP_SERVICE_NAME, EMP_MODULE_NAME,
                portNumber, GET_EMP_REQUEST_TYPE);

        ServiceUtil.setEndPointParameter(serviceObject);

        String inputEmpName = serviceObject.requestData.get(EMP_NAME).asText();
        String inputEmpId = serviceObject.requestData.get(EMP_ID).asText();
        String inputDept = serviceObject.requestData.get(DEPT).asText();

        String queryParam = inputEmpId.isBlank() ?
                (inputEmpName.isBlank() ?
                        (inputDept.isBlank() ? "" :
                                DEPT + EQUAL + inputDept) :
                        (inputDept.isBlank() ?
                                EMP_NAME + EQUAL + inputEmpName :
                                DEPT + EQUAL + inputDept + AND + EMP_NAME + EQUAL + inputEmpName)) :
                inputEmpName.isBlank() ?
                        (inputDept.isBlank() ? EMP_ID + EQUAL + inputEmpId :
                                EMP_ID + EQUAL + inputEmpId + AND + DEPT + EQUAL + inputDept) :
                        (inputDept.isBlank() ?
                                EMP_ID + EQUAL + inputEmpId + AND + EMP_NAME + EQUAL + inputEmpName :
                                EMP_ID + EQUAL + inputEmpId + AND + DEPT + EQUAL + inputDept + AND + EMP_NAME + EQUAL + inputEmpName);

        if (!queryParam.isBlank()) {
            serviceObject.endPointParameters += "?" + queryParam;
        }

        LOGGER.info("End Point Parameter: {} ", serviceObject.endPointParameters);

        verifyServiceResponseBodyList(serviceObject);

    }

    @Order(4)
    @ParameterizedTest(name = "Test - " + GET_EMPS_SERVICE_NAME + " Service in - " + EMP_MODULE_NAME +
            " Module - Valid service response - Positive scenario - {0}")
    @ValueSource(ints = {1})
    @Tag("live")
    @Tag("dev")
    public void getAllEmployee(int testInput) {

        setInputServiceAndModule(serviceObject, GET_EMPS_SERVICE_NAME, EMP_MODULE_NAME,
                portNumber, GET_EMPS_REQUEST_TYPE);

        ServiceUtil.setEndPointParameter(serviceObject);

        serviceObject.dataId = TEST_DATA + testInput;

        verifyServiceResponseBodyList(serviceObject);

    }

    @Order(5)
    @ParameterizedTest(name = "Test - " + DELETE_EMP_SERVICE_NAME + " Service in - " + EMP_MODULE_NAME +
            " Module - Valid service response - Positive scenario - {0}")
    @ValueSource(ints = {1})
    @Tag("live")
    @Tag("dev")
    public void deleteAllEmployeesEnd(int testInput) {

        setInputServiceAndModule(serviceObject, DELETE_EMP_SERVICE_NAME, EMP_MODULE_NAME,
                portNumber, DELETE_EMP_REQUEST_TYPE);

        ServiceUtil.setEndPointParameter(serviceObject);

        serviceObject.dataId = TEST_DATA + testInput;

        verifyServiceResponseString(serviceObject);

    }
    
}