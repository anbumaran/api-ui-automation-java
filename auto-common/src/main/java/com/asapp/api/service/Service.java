package com.asapp.api.service;

import com.asapp.common.model.ServiceObject;
import com.asapp.common.utils.DateTimeUtil;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.asapp.common.Constants.CONTENT_TYPE_JSON;
import static com.asapp.common.utils.PojoToString.getPOJOString;

public class Service {

    private static final Logger LOGGER = LogManager.getLogger(Service.class);


    /**
     * Get Service Response for the given input Service Object
     *
     * @param serviceObject - Service Object
     * @return - Rest Assured Service Response
     */
    public Response getResponse(ServiceObject serviceObject) {
        return getResponse(serviceObject.requestSpecification, serviceObject.requestType,
                serviceObject.baseEndPoint + serviceObject.endPointParameters, serviceObject.requestBody);
    }

    /**
     * Get Service Response for the given input Request Specification, Request Type, End Point, Request Body
     *
     * @param requestSpecification - Request Specification
     * @param requestType          - Request Type
     * @param endPoint             - End Point
     * @param requestBody          - Request Body
     * @return - Rest Assured Service Response
     */
    public Response getResponse(RequestSpecification requestSpecification, String requestType, String endPoint,
                                Object requestBody) {

        printEndPoint(endPoint);

        printRequestBody(requestBody);

        LOGGER.info("Service Request Type : {}", requestType.toUpperCase());

        Response response;

        switch (requestType.toUpperCase()) {
            case "GET":
                response = requestSpecification.get(endPoint);
                break;
            case "POST":
                response = requestSpecification.post();
                break;
            case "PUT":
                response = requestSpecification.put();
                break;
            case "DELETE":
                response = requestSpecification.delete(endPoint);
                break;
            default:
                throw new IllegalArgumentException("Invalid Input - Request Type : {}" + requestType);
        }

        printResponse(response);

        return response;

    }

    public void printEndPoint(String endPoint) {
        LOGGER.info("Service Endpoint : " + endPoint);
    }

    public void printRequestBody(Object request) {

        if (request instanceof Class) {
            LOGGER.info("Service Request Body :\n " + getPOJOString(request, CONTENT_TYPE_JSON));
        } else {
            LOGGER.info("Service Request Body :\n " + request);
        }

    }

    public void printResponse(Response response) {

        String responseString = response.getBody().asString();

        LOGGER.info("Service Response Time : {} (hh:mm:ss)", DateTimeUtil.getReadableTime(response.getTime()));

        LOGGER.info("Service Response Body : \n" + responseString);

    }

}
