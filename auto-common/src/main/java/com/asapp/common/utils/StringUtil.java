package com.asapp.common.utils;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.text.WordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.IntStream;

import static com.asapp.Constants.IGNORE_STACK_TRACE;

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

    public static String getStackTraceTill(Exception e) {
        return getStackTraceTill(e, IGNORE_STACK_TRACE);
    }

    public static String getStackTraceTill(Exception e, String ignoreStackTrack) {
        return Arrays.stream(e.getStackTrace())
                .takeWhile(element -> !element.getClassName().startsWith(ignoreStackTrack))
                .map(StackTraceElement::toString)
                .reduce((line1, line2) -> line1 + "\n" + line2)
                .orElse("");
    }

}
