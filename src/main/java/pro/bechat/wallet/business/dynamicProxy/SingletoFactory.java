package pro.bechat.wallet.business.dynamicProxy;

/**
 * Created by huc on 2017/11/27.
 * 单列工厂[懒]
 */
@SuppressWarnings("ALL")
public class SingletoFactory {
    private SingletoFactory() {

    }

    private static class Factory {
        private static Object instance = null;

        private Factory() {

        }

        private static synchronized void initInstance(Class clazz)
                throws IllegalAccessException, InstantiationException {
            if (null == instance) {
                instance = clazz.newInstance();
            }
        }

        private static Object getInstance(Class clazz)
                throws InstantiationException, IllegalAccessException {
            if (null == instance) {
                initInstance(clazz);
            }
            return instance;
        }
    }

    public static Object newInstance(Class clazz)
            throws IllegalAccessException, InstantiationException {
        return Factory.getInstance(clazz);
    }


}
