package com.rain.java.concurrent;

import java.util.concurrent.atomic.AtomicReference;

public class SimpleAtomicReference {
    static SimpleAR simpleAR = new SimpleAR(20);
    static AtomicReference<SimpleAR> atomicReference = new AtomicReference<>(simpleAR);
    public static void main(String args[]){
        SimpleAR inner = atomicReference.get();
        SimpleAR s = new SimpleAR(40);
//        获取的是之前的value,先get的话，总会先获取之前的数据
        SimpleAR old = atomicReference.getAndSet(s);
        SimpleAR newV = atomicReference.get();
        System.out.println(newV.a);

    }
}
class SimpleAR{
    int a = 0;
    public SimpleAR(int i){
        this.a = i;
    }
}
