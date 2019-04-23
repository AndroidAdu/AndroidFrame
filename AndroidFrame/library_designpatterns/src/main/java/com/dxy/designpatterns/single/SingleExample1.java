package com.dxy.designpatterns.single;

/**
 * Created by duxueyang on 2019/4/23.
 * 单例设计模式
 * 恶汉模式
 * 类装载的时候就完成实例化，线程安全 ，若不使用就会占用内存
 */
public class SingleExample1 {


    private final static SingleExample1 INSTANCE = new SingleExample1();


    private SingleExample1() {
    }


    public static SingleExample1 getInstance() {
        return INSTANCE;
    }

}
