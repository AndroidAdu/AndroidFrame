package com.dxy.designpatterns.single;

/**
 * Created by duxueyang on 2019/4/23.
 * 单例设计模式
 * 懒汉模式
 *
 *  线程安全，效率较高，双重检查
 *
 */
public class SingleExample2 {


    private static volatile SingleExample2 INSTANCE;

    private SingleExample2() {}

    public static SingleExample2 getInstance() {
        if (INSTANCE == null) {
            synchronized (SingleExample2.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SingleExample2();
                }
            }
        }
        return INSTANCE;
    }


}
