package com.asapp.common.utils;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.text.WordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.stream.IntStream;

public class StringUtil {

    private static final Logger LOGGER = LogManager.getLogger(StringUtil.class);

    private StringUtil() {
        //Empty Constructor
    }

    public static String getCapitalizeNoSpace(String inputString) {
        return WordUtils.capitalizeFully(inputString).replaceAll("\\s+", "");
    }

    public static String getSmallNoSpace(String inputString) {
        return inputString.replaceAll("\\s+", "").toLowerCase();
    }

    public static String getXpath(Object... xPaths) {

        StringBuilder dummyPath = new StringBuilder();

        IntStream.range(0, xPaths.length).forEach(i -> dummyPath.append("/%s"));

        return String.format(dummyPath.toString(), xPaths);

    }

    public static void printObject(Object object) {
        if (object instanceof Collection<?>) {
            ((Collection<?>) object).forEach(each -> LOGGER.info(PojoToString.getPOJOString(each)));
        } else if (object instanceof JsonNode) {
            LOGGER.info(((JsonNode) object).toPrettyString());
        } else {
            if (object instanceof String) {
                LOGGER.info(object);
            } else {
                LOGGER.info(PojoToString.getPOJOString(object));
            }
        }
    }

}
