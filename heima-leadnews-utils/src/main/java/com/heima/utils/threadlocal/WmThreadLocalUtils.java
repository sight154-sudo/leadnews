package com.heima.utils.threadlocal;

import com.heima.model.wemedia.pojos.WmUser;

/**
 * @author: tang
 * @date: Create in 18:50 2021/9/3
 * @description: 使用threadLocal获取登陆的用户
 */
public class WmThreadLocalUtils {

    private static final ThreadLocal<WmUser> tl = new ThreadLocal<>();

    public static void set(WmUser wmUser){
        tl.set(wmUser);
    }

    public static WmUser get(){
        return tl.get();
    }

    public static void remove(){
        tl.remove();
    }
}
