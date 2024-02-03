package com.asapp.common.utils;

import com.asapp.common.model.ServiceObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;

public class JsonUtil {

    private static final Logger LOGGER = LogManager.getLogger(JsonUtil.class);

    private JsonUtil() {
        //Empty Constructor
    }

    public static JsonNode getJsonNode(FileReader fileReader) {
        try {
            return new ObjectMapper().readTree(fileReader);
        } catch (IOException e) {
            throw new IllegalStateException("Conversion from FileReader to Json Node Failed with Error : " + e);
        }
    }

    public static String getJsonNodeStringAt(JsonNode jsonNode, Object... xPaths) {
        return getJsonNodeAt(jsonNode, xPaths).asText();
    }

    public static JsonNode getJsonNodeAt(JsonNode jsonNode, Object... xPaths) {
        return jsonNode.at(StringUtil.getXpath(xPaths));
    }

    public static String getJsonNodeStringAt(FileReader fileReader, Object... xPaths) {
        return getJsonNodeAt(fileReader, xPaths).asText();
    }

    public static JsonNode getJsonNodeAt(FileReader fileReader, Object... xPaths) {
        return getJsonNodeAt(getJsonNode(fileReader), xPaths);
    }

    public static JsonNode setJsonNode(JsonNode jsonNode, String nodeName, String inputValue) {
        JsonNode updateJsonNode = ((ObjectNode) jsonNode).put(nodeName, inputValue);
        LOGGER.info("Updated the Node - \"{}\" with Value - {}", nodeName, updateJsonNode);
        return updateJsonNode;
    }

    public static JsonNode getJsonNodeData(ServiceObject serviceObject, String filePath) {
        return getJsonNodeData(serviceObject, filePath, serviceObject.dataId);
    }

    public static JsonNode getJsonNodeData(ServiceObject serviceObject, String filePath, String nodeName) {
        String fileLocation = String.format(filePath, serviceObject.env, serviceObject.moduleNode,
                serviceObject.serviceNode);
        FileReader fileReader = new FileReaderUtil().getFileReader(fileLocation);
        return JsonUtil.getJsonNodeAt(fileReader, nodeName);
    }

    public static JsonNode getJsonNode(String fileLocation){
        FileReader fileReader = new FileReaderUtil().getFileReader(fileLocation);
        try {
            return new ObjectMapper().readTree(fileReader);
        } catch (IOException e) {
            throw new IllegalStateException("Conversion from FileReader to Json Node Failed with Error : " + e);
        }
    }

}
