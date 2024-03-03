package com.asapp.jbasics;

import org.testng.annotations.Test;

public class MyJavaBasics {

    @Test
    public void testJava() {
        String input = "radar";

        System.out.println("Input String '"+ input+"'is Palindrom? "+isPalindrom(input));
    }

    public static boolean isPalindrom(String input) {
        StringBuffer rev = new StringBuffer();
        int i = input.length() - 1;

        for (; i >= 0; i--) {
            rev.append(input.charAt(i));
            System.out.println(input.charAt(i));
        }
        return rev.toString().equals(input);

    }


}
