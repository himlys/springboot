package com.rain.java.concurrent;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
// 普通的Atomic也就更没啥可以看得了
// 都是使用unsafe（CAS compare and swap，大意是你告诉我内存地址，和原值，CPU去比较内存地址的值和你的原值是否一致，如果一致，set，否则返回内存地址的值）
// 这样可能会出现ABA的问题，比如你希望是1，之后别人改成0了，又有人改成1，这时系统是不知道有人改过的，可以使用AtomicStampedReference来解决这个问题
// 另外就是这种操作一般只针对一个对象比如Integer或者Boolean，不过后面出现了AtomicReference，可以把我们需要处理的对象都放到这个里，或者使用多个AtomicReferenceFieldUpdater
// 作为线程间共享变量的解决方式，使用park和unpark来处理线程访问变量。
public class SimpleAtomicReferenceFieldUpdater {
    static AtomicReferenceFieldUpdater<FieldClassUpdate,String> updater = AtomicReferenceFieldUpdater.newUpdater(FieldClassUpdate.class,String.class,"field");
    public static void main(String args[]){
        FieldClassUpdate fieldClassUpdate = new FieldClassUpdate();
        String field = updater.get(fieldClassUpdate);
        System.out.println(field);
        updater.getAndSet(fieldClassUpdate,"ok");
        System.out.println(fieldClassUpdate.field);
        updater.getAndUpdate(fieldClassUpdate,(f)->{
//            f 是field
            if("ok".equals(f)){
                return "ok updated";
            }
           return "update";
        });
        System.out.println(fieldClassUpdate.field);
    }
}
class FieldClassUpdate{
    volatile String field = "ceshi";
}
