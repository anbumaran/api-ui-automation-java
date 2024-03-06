package com.asapp.coding.inheritance;

public class IFaceImplChild extends IFaceImplParent {

    public IFaceImplChild(String input) {
        super(input);
        System.out.println("IFaceImplChild : " + input);
    }

    public IFaceImplChild() {
        System.out.println("IFaceImplChild No Arguments");
    }

    public void show() {
        System.out.println("IFaceImplChild - Show - Employee Object : " + employee);
        super.show();
    }

}
