package com.asapp.coding;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;

public class InterviewPrograms {

    @Test
    public void testBubbleSort() {
        int arr0[] = {320, 60, 35, 5, 3, 2, -45, -70};
        int arr1[] = {3, 60, 320, 35, 5, 2, -45, -70};
        System.out.println("Bubble Sort..........");
        bubbleShortRev(arr0);
        System.out.println("Bubble Sort..........");
        bubbleShortRev(arr1);
    }

    public static void bubbleShortRev(int[] array) {
        printArray(array);
        int n = array.length;
        for (int i = 0; i < n; i++) {
            boolean isSwapped = false;
            for (int j = 0; j < n - 1; j++) {
                //Next Element is Greater than swap to make it ascending order
                if (array[j] > array[j + 1]) {
                    //Swap without using 3rd variable
                    array[j] = array[j] + array[j + 1];
                    array[j + 1] = array[j] - array[j + 1];
                    array[j] = array[j] - array[j + 1];
                    isSwapped = true;
                }
            }
            if (!isSwapped) break;
            printArray(array);
        }
    }

    public static void printArray(int[] array) {
        System.out.println(getListFromArray(array));
    }

    public static List<?> getListFromArray(int[] array) {
        List<Object> objectList = new ArrayList<>();
        IntStream.range(0, array.length).forEach(i -> {
            objectList.add(array[i]);
        });
        return objectList;
    }

    @Test
    public void findPrimeNumbers() {
        int N = 100;
        //check for every number from 1 to N
        System.out.print("Prime Numbers : ");
        for (int i = 1; i <= N; i++) {
            //check if current number is prime
            if (isPrime(i)) {
                System.out.print(i + " ");
            }
        }
    }

    static boolean isPrime(int n) {
        //since 0 and 1 is not prime return false.
        if (n <= 1)
            return false;

        // Check from 2 to n/2
        for (int i = 2; i <= n / 2; i++)
            if (n % i == 0) return false;

        return true;
    }

    @Test
    public void testFactorial() {
        System.out.println("Factorial : " + factorial(5));
    }

    public static long factorial(long input) {
        if (input <= 1) return 1;
        return input * factorial(input - 1);
    }

    @Test
    public void testFibonacci() {
        long input = 12;
        System.out.println("Fibonacci : ");
        for (long i = 0; i <= input; i++) {
            System.out.print(fibonacci(i) + " ");
        }
    }

    public static long fibonacci(long input) {
        if (input == 0) {
            return 0;
        } else if (input == 1) {
            return 1;
        } else {
            return fibonacci(input - 2) + fibonacci(input - 1);
        }

    }

    @Test
    public void testRevString() {

        List<String> stringList = asList("malayalam", "tattarrattat", "anbu", "rebecca");

        stringList.forEach(s -> printStrings(s, getRevStrRecursion(s)));
        stringList.forEach(s -> printStrings(s, getRevStrCharArray(s)));

    }

    public static void printStrings(String inputString, String reverseString) {
        System.out.print("'" + inputString + "' - & Its Reverse - '" + reverseString + "' are - ");
        if (reverseString.equals(inputString)) {
            System.out.println("equal");
        } else {
            System.out.println("not equal");
        }
    }

    public static String getRevStrRecursion(String input) {
        if (input.isEmpty()) return input;
        return getRevStrRecursion(input.substring(1)) + input.charAt(0);
    }


    public String getRevStrCharArray(String input) {
        char[] charArray = input.toCharArray();
        char[] charArrayRev = new char[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            charArrayRev[i] = charArray[charArray.length - i - 1];
        }
        return new String(charArrayRev);
    }

    @Test
    public void testRevWords() {
        String input = "anbu maran chandra sekaran";
        String[] words = input.split("\\s");
        StringBuffer sb = new StringBuffer();
        for (int i = words.length - 1; i >= 0; i--) {
            sb.append(words[i]);
            sb.append(' ');
        }

        System.out.println(sb.toString());
    }

    @Test
    public void testCountAndUniqueCharHashMap() {
        String input = "anbumaranchandrasekaran";
        StringBuilder uniqueCharString = new StringBuilder();

        HashMap<Character, Integer> keyVal = new HashMap();
        for (int i = 0; i < input.length(); i++) {
            Character chr = input.charAt(i);
            if (keyVal.containsKey(chr)) {
                keyVal.put(chr, keyVal.get(chr) + 1);
            } else {
                keyVal.put(chr, 1);
                uniqueCharString.append(chr);
            }
        }

        System.out.println("Character & Count : " + keyVal);

        List<Character> moreThanOne =
                keyVal.keySet().stream().filter(i -> keyVal.get(i) > 1).collect(Collectors.toList());
        System.out.println("Character occur more than once : " + moreThanOne);

        Character firstNonRepeat = keyVal.keySet().stream().filter(i -> keyVal.get(i) == 1).findFirst().get();
        System.out.println("First Non Repeated Character : " + firstNonRepeat);

        System.out.println("Unique Characters : " + uniqueCharString);

    }

    @Test
    public void testCountStringList() {

        List<String> stringList = asList("Anbu", "Maran", "Anbu", "Rebecca");

        HashMap<String, Integer> keyVal = new HashMap();
        stringList.forEach(i -> {
            if (keyVal.containsKey(i)) {
                keyVal.put(i, keyVal.get(i) + 1);
            } else {
                keyVal.put(i, 1);
            }
        });

        System.out.println("String & Count : " + keyVal);

    }

    @Test
    public void testRemoveDuplicateInt() {

        int[] input = new int[]{1, 4, 2, 2, 1, 5};
        List<Integer> integerList = new ArrayList<>();

        for (Integer i : stream(input).toArray()) {
            integerList.add(i);
        }

        List<Integer> uniqueIntList = new ArrayList<>();

        TreeMap<Integer, Integer> keyVal = new TreeMap<>();

        integerList.forEach(c -> {
                    if (keyVal.containsKey(c)) {
                        keyVal.put(c, keyVal.get(c) + 1);
                    } else {
                        keyVal.put(c, 1);
                        uniqueIntList.add(c);
                    }
                }
        );
        System.out.println("Integer & Count : " + keyVal);

    }


    @Test
    public void myString() {

        HashMap<String, String> compareStringPair = new HashMap<>();
        compareStringPair.put("Anbuma", "maanbu");
        compareStringPair.put("anbuma", "maanbu");
        compareStringPair.put("anbu", "maran");

        compareStringPair.forEach((key, value) -> {
            if (String.valueOf(getStringSorted(key))
                    .equals(String.valueOf(getStringSorted(value)))) {
                System.out.println("Both Strings '" + key + "' & '" + value + "' same Chars ");
            } else {
                System.out.println("Both Strings '" + key + "' & '" + value + "' NOT same Chars ");
            }
        });

    }


    public static char[] getStringSorted(String input) {

        char[] charArray = input.toCharArray();

        int n = charArray.length;
        for (int i = 0; i < n - 1; i++) {
            boolean isSwapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (charArray[j] > charArray[j + 1]) {
                    char temp = charArray[j];
                    charArray[j] = charArray[j + 1];
                    charArray[j + 1] = temp;
                    isSwapped = true;
                }
            }
            if (!isSwapped) break;
            ;
        }

        return charArray;

    }

    @Test
    public void removeDuplicatesUsingList() {

        String input = "anbumaranchandrasekaran";

        List<Character> charList = getCharList(input);
        List<Character> uniqueChars = new ArrayList<>();

        for (Character ch : charList) {
            if (!uniqueChars.contains(ch)) {
                uniqueChars.add(ch);
            }
        }

        StringBuilder stringBuilder = new StringBuilder();

        uniqueChars.forEach(stringBuilder::append);

        System.out.println("Input String - '" + input + "' - Its Unique Characters - '" + stringBuilder + "'");

    }

    public List<Character> getCharList(String input) {
        List<Character> characterList = new ArrayList<>();
        for (char ch : input.toCharArray()) {
            characterList.add(ch);
        }
        return characterList;
    }

    @Test
    public void testVowel() {

        String vowel = "Hello";
        String nonVowel = "SkyFly";

        System.out.println("String - '" + vowel + "' - Contains Vowel? - " + isStringContainVowel(vowel));
        System.out.println("String - '" + nonVowel + "' - Contains Vowel? - " + isStringContainVowel(nonVowel));

    }

    public static boolean isStringContainVowel(String input) {
        return input.toLowerCase().matches(".*[aeiou].*");
    }

    @Test
    public void testMap() {

        Map<Integer, String> hashMap = new HashMap<>();

        hashMap.put(0, "Anbu");
        hashMap.put(3, "Sam");
        hashMap.put(2, "Rebecca");
        hashMap.put(6, "Mani");
        hashMap.put(4, "Shirly");
        hashMap.put(7, "sheela");
        hashMap.put(1, "Anitha");
        hashMap.put(5, "Sumi");
        hashMap.put(8, "shiny");

        System.out.println("\nHash Map - as Created : ");
        hashMap.forEach((key, value) -> System.out.println(key + " - " + value));

        System.out.println("\nHash Map - Filter names Starts with S : ");
        Map<Integer, String> sHashMap =
                hashMap.entrySet()
                        .stream().filter(x -> x.getValue().startsWith("S") || x.getValue().startsWith("s"))
                        .peek(e -> System.out.println(e.getKey() + " - " + e.getValue()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        System.out.println("\nList Values - Filter names Starts with S : ");
        List<String> sName = hashMap.values()
                .stream().filter(s -> s.startsWith("S") || s.startsWith("s"))
                .peek(System.out::println)
                .collect(Collectors.toList());

        System.out.println("\nList Keys - Filter Even Numbers and Square It : ");
        List<Integer> sorterEvenSquare = hashMap.keySet().stream()
                .sorted()
                .filter(x -> x % 2 == 0)
                .map(x -> x * x)
                .peek(System.out::println)
                .collect(Collectors.toList());

    }

    @Test
    public void testDataTime() {
        System.out.println("Current Date : " + LocalDate.now());
        System.out.println("Past Date : " + LocalDate.of(1986, Month.DECEMBER, 25));
        System.out.println("Time Location Specific : " + LocalTime.now(ZoneId.of(ZoneId.SHORT_IDS.get("ECT"))));
    }

}