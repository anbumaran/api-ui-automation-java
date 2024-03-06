
package com.asapp.coding.inheritance;

import com.asapp.coding.Employee;
import com.asapp.coding.InterviewPrograms;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class IndependentTest {

    @Test
    public void testIndependent() {

        String palindrome = "malayalam";
        String input1 = "anbuma";
        String input2 = "umabna";

        //Method Reference - to implement interface method
        IFaceTwo iFaceTwo = InterviewPrograms::printStrings;
        iFaceTwo.compareStrings(palindrome, InterviewPrograms.getRevStrRecursion(palindrome));

        //Lambda Expression - to implement interface method
        IFaceOne iFaceOne = (key, value) -> {
            if (String.valueOf(InterviewPrograms.getStringSorted(key))
                    .equals(String.valueOf(InterviewPrograms.getStringSorted(value)))) {
                System.out.println("Both Strings '" + key + "' & '" + value + "' - same Chars ");
            } else {
                System.out.println("Both Strings '" + key + "' & '" + value + "' - NOT same Chars ");
            }
        };
        iFaceOne.compareStrings(input1, input2);

        System.out.println("Initialize class with argument.....");
        IFaceImplParent iFaceImplParent1 = new IFaceImplChild(input1);
        System.out.println("Initialize class without argument.....");
        IFaceImplParent iFaceImplParent2 = new IFaceImplChild();

        IFaceImplChild iFaceImplChild = new IFaceImplChild();
        iFaceImplChild.show();
        iFaceImplChild.myPrint("Normal Method Call via Instance");

    }

    @Test
    public void testCompareBy() {

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee().setEmpName("Nithya").setEmpId(40).setDept("Dev"));
        employees.add(new Employee().setEmpName("Rebecca").setEmpId(20).setDept("Automation"));
        employees.add(new Employee().setEmpName("Praveen").setEmpId(10).setDept("Testing"));
        employees.add(new Employee().setEmpName("Anbu").setEmpId(50).setDept("Automation"));
        employees.add(new Employee().setEmpName("Maran").setEmpId(30).setDept("Testing"));

        System.out.println("Emp List - Order as Added......................\n");
        employees.forEach(System.out::println);

        System.out.println("\nEmp List - Order By Emp Name.................\n");
        employees.sort(Employee::compareByEmpName);
        employees.forEach(System.out::println);

        System.out.println("\nEmp List - Order By Emp Id...................\n");
        employees.sort(Employee::compareByEmpId);
        employees.forEach(System.out::println);

        System.out.println("\nEmp List - Order By Department...............\n");
        employees.sort(Employee::compareByDept);
        employees.forEach(System.out::println);

    }

    @Test
    public void testLambda() {

        ArrayList<Integer> integers = new ArrayList<>();

        //Add using IntStream and Method
        IntStream.range(1, 11).forEach(integers::add);

        //Consumer - Function takes in one argument and produces a result (don’t return any value)
        Consumer<Integer> consumerSquare = (x) -> {
            System.out.println("Square of - " + x + " is - " + x * x);
        };

        Consumer<Integer> consumerEvenOdd = (x) -> {
            if (x % 2 == 0) {
                System.out.println(x + " - Even Number");
            } else {
                System.out.println(x + " - Odd Number");
            }
        };

        integers.parallelStream().forEach(consumerSquare);
        System.out.println("\n...........................\n");
        integers.forEach(consumerEvenOdd);

    }

}
