
package com.asapp.coding;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Streams {

    @Test
    public void filterEmployee() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee("Anbu", 20, "Automation"));
        employeeList.add(new Employee("Anitha", 40, "Automation"));
        //Using default Setter as Builder Chain
        employeeList.add(new Employee().setEmpName("Magi").setEmpId(10).setDept("Testing"));
        //Using Builder without Department
        employeeList.add(new Employee.Builder().setEmpName("Shirly").setEmpId(50).build());
        //Without using Builder without Department
        employeeList.add(new Employee("Ravi", 60, ""));

        System.out.println("List of Employees : ");
        employeeList.forEach(System.out::println);
        System.out.println("List of Employees whose Department number is more than 30 ");
        employeeList.stream().filter(a -> a.getEmpId() > 30).map(Employee::getEmpName).forEach(System.out::println);
    }

    @Test
    public void testChartCount(){
        String word = "anbumaranchandrasekaran";
        Map<String, Long> charCount = word.codePoints().mapToObj(Character::toString)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        charCount.forEach((key, value) -> System.out.println(key + " ==> " + value));
    }

    @Test
    public void testFibonacci(){
        Stream.iterate(new long[] { 1, 1 }, p -> new long[] { p[1], p[0] + p[1] })
                .limit(92)
                .forEach(p -> System.out.println(p[0]));
    }

    @Test
    public void testStreams() {

        List<Integer> integerList = Arrays.asList(500, 2, 625, 225, 105, -6, 2, 625, 31, 31, 1, 6, 4, 625, 4, 31, 2, 1);

        System.out.println("Numbers Start with 6, -6 and Ends with 5:");

        integerList.stream().map(String::valueOf).filter(a -> (a.startsWith("6") || a.startsWith("-6") || a.endsWith("5"))).map(Integer::valueOf).forEach(System.out::println);

        System.out.println("Second Low in Integer List : " +
                integerList.stream().sorted(Comparator.reverseOrder()).distinct().skip(1).findFirst().get());

        System.out.println("Second Min to Fifth Max (2nd to 5th) : ");

        int sum = integerList.stream().sorted().skip(1).limit(5).peek(System.out::println).reduce(Integer::sum).get();

        System.out.println("Sum Second Min to Fifth Max (2nd to 5th) :" + sum);

        System.out.println("Collection Sorted : ");
        integerList.stream().sorted().forEach(System.out::println);

        System.out.println("Collection Sorted Reverse : ");
        integerList.stream().sorted(Collections.reverseOrder()).forEach(System.out::println);

        System.out.println("Max in Integer List : " + integerList.stream().mapToInt(a -> a).max().getAsInt());

        System.out.println("Min in Integer List : " + integerList.stream().min(Comparator.naturalOrder()).get());

        HashMap<Integer, Integer> hashMap = new HashMap<>();

        //Save Number of Time a number appears in the Integer List

        integerList
                .forEach(e -> {
                    if (hashMap.containsKey(e)) {
                        hashMap.put(e, hashMap.get(e) + 1);
                    } else {
                        hashMap.put(e, 1);
                    }
                });

        System.out.println("Hash Map - Integer and its Occurrence : " + hashMap);


        System.out.println("Max Repeated Integer is Key : " +
                Collections.max(hashMap.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey());

        System.out.println("Max Repeated Integer is Value : " +
                hashMap.get((Collections.max(hashMap.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey())));

        System.out.println("Max Repeated Integer is Key - Value : " +
                hashMap.entrySet().stream().filter(a ->
                        Objects.equals(a.getValue(), hashMap.get((Collections.max(hashMap.entrySet(),
                                Comparator.comparingInt(Map.Entry::getValue)).getKey())))).collect(Collectors.toList()));

        System.out.println("Min Repeated Integer is Key - Value : " +
                hashMap.entrySet().stream().filter(a ->
                        Objects.equals(a.getValue(), hashMap.get((Collections.min(hashMap.entrySet(),
                                Comparator.comparingInt(Map.Entry::getValue)).getKey())))).collect(Collectors.toList()));

        System.out.println("Range of Values from 500 to 505 : ");
        IntStream.range(500, 505).forEach(System.out::println);

        List<Integer> evenIntList = integerList.stream().filter(a -> a % 2 == 0).collect(Collectors.toList());

        System.out.println("Event Numbers List : " + evenIntList);

        System.out.println("Int List - Square : ");
        System.out.println("Int List - Square Sum : " + IntStream.range(0, 10).filter(a -> a < 6).filter(a -> a > 1)
                .map(a -> a * a).peek(a -> System.out.println(a + "")).sum());

        System.out.println("Int List - Sum : " + integerList.stream().mapToInt(a -> a).sum());

        System.out.println("Int List - Min : " + integerList.stream().reduce(Integer::min).get());

        List<Double> doubleList = Arrays.asList(5.2, 6.25, 4.56, 3.56, 2.01, 1.10);

        System.out.println("Double List - Min : " + doubleList.stream().reduce(Double::min).get());

        List<String> stringList = Arrays.asList("Anbu", "Maran", "Umesh", "Kalai");

        stringList.stream().filter(s -> s.startsWith("A")).map(String::toUpperCase).forEach(System.out::println);

        System.out.println(integerList.stream().sorted(Comparator.reverseOrder()).skip(0).limit(1).findAny().get());

    }

    @Test
    public void testTreeSet() {
        Set<Integer> integerSet = new TreeSet<>();
        integerSet.add(15);
        integerSet.add(5);
        integerSet.add(3);
        integerSet.add(9);
        integerSet.add(5);
        integerSet.add(0);
        integerSet.forEach(System.out::println);
    }

}