package pro.bechat.wallet.domain.support;

import javax.xml.bind.JAXBException;

/**
 * Created by huc on 2017/11/26.
 */
public interface ServiceSupport<T> {

    /**
     * 事件主体
     * @param instance
     */
    T process(T instance) throws JAXBException;

}
