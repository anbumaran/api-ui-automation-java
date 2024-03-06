
package com.asapp.coding;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class NestedTry {

    @Test
    public void testNested() {
        String aErrorMsg = "";
        String bErrorMsg = "";
        String cErrorMsg = "";

        try {
            boolean testResult = true;


            try {
                System.out.println("A ! ");
                Assert.assertEquals("", "A");
            } catch (AssertionError e) {
                aErrorMsg = "A is fault";
                testResult = false;
            }
            System.out.println("One ! ");
            try {
                System.out.println("B ! ");
                Assert.assertEquals("B", "B");
            } catch (AssertionError e) {
                testResult = false;
                bErrorMsg = "B is fault";
            }
            System.out.println("Two ! ");
            try {
                System.out.println("C ! ");
                Assert.assertEquals("C", "");
            } catch (AssertionError e) {
                testResult = false;
                cErrorMsg = "C is fault";
            }
            System.out.println("After all....");
            if (!testResult) {
                throw new AssertionError();
            }
        } catch (final AssertionError e) {
            System.out.println(">>>>>>>>>>>>>>>");
            System.out.println(aErrorMsg + " + " + bErrorMsg + " + " + cErrorMsg);
            throw  e;
        }
    }

}