package com.heima.search.model;

/**
 * @author: tang
 * @date: Create in 19:37 2021/9/28
 * @description:
 */
public class Singleton {
//    private static Singleton singleton = null;
    private Singleton(){

    }

    public static Singleton getInstance(){
       /* if(null == singleton){
            singleton = new Singleton();
        }*/
        return LazyHolder.singleton;
    }
    static class LazyHolder{
        private static final Singleton singleton = new Singleton();
    }
}
