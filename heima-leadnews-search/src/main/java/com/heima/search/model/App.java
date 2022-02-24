package com.heima.search.model;

/**
 * @author: tang
 * @date: Create in 19:42 2021/9/28
 * @description:
 */
public class App {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Singleton.getInstance());
                }
            }).start();
        }

    }
}
