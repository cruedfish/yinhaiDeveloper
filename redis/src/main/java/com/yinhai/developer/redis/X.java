package com.yinhai.developer.redis;

public class X {
    Y b = new Y();
    X() {
        System.out.print("X");
    }
}
class Y {
    Y() {
        System.out.print("Y");
    }
}
 class Z extends X {
    Y y = new Y();
    Z() {
        System.out.print("Z");
    }
    public static void main(String[] args) {
        new Z();     //结果是 YXYZ
    }

}
