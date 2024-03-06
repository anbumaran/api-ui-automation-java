package com.asapp.coding.inheritance;

import com.asapp.coding.Employee;

public interface IFaceTwo {

    Employee employee = new Employee("Suresh", 80, "Manager");

    default void myPrint(String input) {
        System.out.println("Interface - IFace Two - Print :" + input);
    }

    default void show() {
        System.out.println("Interface - IFace Two - Show - Employee Object : " + employee);
    }

    void compareStrings(String input1, String input2);


}
