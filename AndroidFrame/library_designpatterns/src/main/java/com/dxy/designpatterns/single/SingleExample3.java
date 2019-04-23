package com.dxy.designpatterns.single;

/**
 * Created by duxueyang on 2019/4/23.
 * 单例设计模式
 * 懒汉模式
 *
 *  线程安全，延迟加载，效率高
 *
 *  最推荐的一种方式（ 序列化类可能会出问题 ）
 */
public class SingleExample3 {


    private SingleExample3() {}

    private static class SingletonInstance {
        private static final SingleExample3 INSTANCE = new SingleExample3();
    }

    public static SingleExample3 getInstance() {
        return SingletonInstance.INSTANCE;
    }


}
