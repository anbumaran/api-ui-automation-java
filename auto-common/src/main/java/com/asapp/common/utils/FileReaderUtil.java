package com.asapp.common.utils;

import com.asapp.common.model.ServiceObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReaderUtil {

    public FileReader getFileReader(String file) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            return new FileReader((Objects.requireNonNull(classLoader.getResource(file)).getFile()));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("File Read Failed with Error : " + e);
        }
    }

    /**
     * Get the List of Files from the input Directory Location and File Extension
     *
     * @param directoryLocation - Directory Location
     * @param fileExtension     - File Extension - Example: ".json"
     * @return - List of Files as String List
     */
    public static List<String> getListOfFiles(String directoryLocation, String fileExtension) {
        List<String> listOfFiles;
        try (Stream<Path> pathStream = Files.walk(Paths.get(directoryLocation))) {
            listOfFiles = pathStream
                    .map(x -> x.toString())
                    .filter(f -> f.endsWith(fileExtension))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Fetching list of File from the input Directory Location : " + directoryLocation +
                            " Failed with Error : " + e);
        }
        return listOfFiles;
    }

    /**
     * Get File Input Steam for the given inputs
     *
     * @param serviceObject - Service Object
     * @param filePath      - File Path
     * @param fileExtension - File Extension
     * @return - File Input Stream
     */
    public static FileInputStream getFileInputStream(ServiceObject serviceObject, String filePath, String fileExtension) {

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(getFileLocation(serviceObject, filePath, fileExtension));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileInputStream;

    }

    public static String getFileLocation(ServiceObject serviceObject, String filePath, String fileExtension) {
        return String.format(filePath, serviceObject.env, serviceObject.moduleNode, serviceObject.serviceNode,
                fileExtension);
    }

}
