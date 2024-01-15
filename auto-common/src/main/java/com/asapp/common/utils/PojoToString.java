package com.asapp.common.utils;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;

import static io.restassured.http.ContentType.JSON;

public class PojoToString {

    private PojoToString() {
        //Empty Constructor
    }

    public static String getPOJOString(Object object, ContentType contentType) {
        if (contentType == JSON) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            StringWriter stringWriter = new StringWriter();
            try {
                objectMapper.writeValue(stringWriter, object);
            } catch (IOException e) {
                throw new IllegalArgumentException("JSON to Sting Failed", e);
            }
            return stringWriter.toString();
        } else {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
                Marshaller marshaller = jaxbContext.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                StringWriter stringWriter = new StringWriter();
                marshaller.marshal(object, stringWriter);
                return stringWriter.toString();
            } catch (JAXBException e) {
                throw new IllegalArgumentException("XML to String Failed", e);
            }
        }
    }

    public static String getPOJOString(Object object){
        return getPOJOString(object, JSON);
    }
}
