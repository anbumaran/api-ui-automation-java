package com.asapp.coding.inheritance;

public class IFaceImplParent implements IFaceOne, IFaceTwo {

    public IFaceImplParent() {
        System.out.println("IFaceImplParent No Arguments");
    }

    public IFaceImplParent(String input) {
        System.out.println("IFaceImplParent : " + input);
    }

    @Override
    public void compareStrings(String input1, String input2) {

    }

}
