
package com.asapp.coding;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

public class Permutation {


    @Test
    public void testPermutation() {
        String str = "abcd";
        stringPermuteAndPrint("", str);
    }


    private static void stringPermuteAndPrint(String prefix, String str) {
        int n = str.length();
        if (n == 0) {
            System.out.print(prefix + " ");
        } else {
            IntStream.range(0, n).forEach(
                    i -> stringPermuteAndPrint(prefix + str.charAt(i),
                            str.substring(i + 1, n) + str.substring(0, i)));
        }
    }

    @Test
    public void countSubStringInStringTest() {
        System.out.println(countSubStringInString("anbumaranchandrasekaran", "an"));
    }

    public static int countSubStringInString(String string, String toFind) {
        int position = 0;
        int count = 0;
        while ((position = string.indexOf(toFind, position)) != -1) {
            position = position + 1;
            count++;
        }
        return count;
    }

}

