package com.travelsky.ypb.business.dynamicProxy;

import com.travelsky.ypb.process.Event5501;

/**
 * Created by huc on 2017/11/27.
 * 单列工厂[懒]
 */
public class SingletoFactory {

    private static class Factory {
        private static Object instance = null;
        private static synchronized void initInstance(Class clazz) throws Exception{
            if(null == instance) {
                instance = clazz.newInstance();
            }
        }
        private static Object getInstance(Class clazz) throws Exception{
            if(null == instance) {
                initInstance(clazz);
            }
            return instance;
        }
    }

    public static Object newInstance(Class clazz) throws Exception {
        return Factory.getInstance(clazz);
    }


}
