package com.asapp.jbasics;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SerializationDeSerial {

    @Test
    public void testSerialDeSerial() {

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee().setEmpName("Anbu").setEmpId(10).setDept("Automation"));
        employees.add(new Employee().setEmpName("Maran").setEmpId(20).setDept("Testing"));
        employees.add(new Employee().setEmpName("Rebecca").setEmpId(30).setDept("Automation"));
        employees.add(new Employee().setEmpName("Nithya").setEmpId(40).setDept("Dev"));
        employees.add(new Employee().setEmpName("Praveen").setEmpId(50).setDept("Testing"));

        employees.forEach(System.out::println);

        String file = Objects.requireNonNull(
                getClass().getClassLoader().getResource("dummyStream")).getPath();

        try {

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            // Method for Serialization of object
            objectOutputStream.writeObject(employees);

            objectOutputStream.close();
            fileOutputStream.close();

            System.out.println("Object has been serialized");

        } catch (IOException ex) {
            System.out.println("IOException is caught");
        }

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            // Method for deserialization of object
            List<Employee> employeesDeSerial = (List<Employee>) objectInputStream.readObject();

            fileInputStream.close();
            objectInputStream.close();

            System.out.println("Object has been deserialized ");
            System.out.println(employeesDeSerial);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testJson() {

        FileReader fileReader;

        try {

            fileReader = new FileReader(Objects.requireNonNull(
                    getClass().getClassLoader().getResource("employees.json")).getFile());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {

            JsonNode jsonNode = new ObjectMapper().readTree(fileReader);
            JsonNode jsonNodeEmp = jsonNode.at("/emp");
            System.out.println("Emp Name : " + jsonNodeEmp.get("empName").asText());
            System.out.println("Emp : " + jsonNodeEmp.toPrettyString());

            ((ObjectNode) jsonNodeEmp).put("empName", "Anitha");
            ((ObjectNode) jsonNodeEmp).put("empId", 100);
            ((ObjectNode) jsonNodeEmp).put("dept", "QA");
            System.out.println("Updated Emp : " + jsonNodeEmp.toPrettyString());

            Employee employee = new ObjectMapper().treeToValue(jsonNodeEmp, Employee.class);
            System.out.println("Emp Object : " + employee);

            JsonNode jsonNodeAutoEmpIds = jsonNode.at("/Automation/empIds");
            System.out.println("Automation Emp Ids : " + jsonNodeAutoEmpIds.toPrettyString());
            List<Integer> empIds = new ObjectMapper().treeToValue(jsonNodeAutoEmpIds, new TypeReference<>() {
            });

            System.out.println("Emp Ids Object : " + empIds);

        } catch (IOException e) {
            throw new IllegalStateException("Conversion from FileReader to Json Node Failed with  Error : " + e);
        }

    }

    @Test
    public void testJaxbMap() {

        FileReader fileReader;

        try {

            fileReader = new FileReader(Objects.requireNonNull(
                    getClass().getClassLoader().getResource("employees.xml")).getFile());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Employees employees = null;

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Employees.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            employees = (Employees) unmarshaller.unmarshal(fileReader);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        System.out.println("Employee from XML : " + employees.toString());

    }


    @Test
    public void testXmlDocBuilder() {

        String file = Objects.requireNonNull(
                getClass().getClassLoader().getResource("data.xml")).getPath();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());

            NodeList staffList = doc.getElementsByTagName("staff");

            for (int i = 0; i < staffList.getLength(); i++) {

                Node node = staffList.item(i);
                System.out.println("Current Element : " + node.getNodeName());

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

                    // Get Attribute Id - <staff id="1001">
                    String id = element.getAttribute("id");
                    System.out.println("Staff Id : " + id);

                    // Get Element By Tag Name - <firstname>low</firstname>
                    String firstname = element.getElementsByTagName("firstname").item(0).getTextContent();
                    System.out.println("First Name : " + firstname);

                    //Get Salary Node - <salary currency="INR">200000</salary>
                    NodeList salaryNodeList = element.getElementsByTagName("salary");
                    // Get Salary Text - 200000
                    String salary = salaryNodeList.item(0).getTextContent();
                    // Get salary's Attribute - INR
                    String currency = salaryNodeList.item(0).getAttributes().getNamedItem("currency").getTextContent();

                    System.out.printf("Salary [Currency] : %,.2f [%s]%n%n", Float.parseFloat(salary), currency);

                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

}
