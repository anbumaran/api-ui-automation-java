package com.asapp.common.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Objects;

public class FileReaderUtil {

    public FileReader getFileReader(String file) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            return new FileReader((Objects.requireNonNull(classLoader.getResource(file)).getFile()));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("File Read Failed with Error : " + e);
        }
    }

}
