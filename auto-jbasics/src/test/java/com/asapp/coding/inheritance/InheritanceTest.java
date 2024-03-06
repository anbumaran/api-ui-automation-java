
package com.asapp.coding.inheritance;

import org.junit.jupiter.api.Test;

public class InheritanceTest extends IFaceImplParent{

    public void myPrint(String input) {
        System.out.println("IFaceImplChildTest - Print : " + input);
    }

    @Test
    public void testInheritance(){
        super.myPrint("Via Super Keyword");
        IFaceOne.myPrint("With Interface Ref");
        myPrint("Without Interface Ref");
        show();
    }

}
