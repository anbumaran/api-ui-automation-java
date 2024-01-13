package com.asapp.common.model;

import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.stereotype.Component;

@Component
public class ServiceObject {
    public String baseEndPoint;
    public String env;
    public String moduleNode;
    public String serviceNode;
    public String dataId;
    public JsonNode requestData;
    public String endPointParameters;
    public JsonNode requestBody;
    public String requestType;
    public Response response;
    public JsonNode expectedRespData;
    public RequestSpecification requestSpecification;
    public String username;
    public String password;

    public ServiceObject() {
        requestBody = null;
    }
}
