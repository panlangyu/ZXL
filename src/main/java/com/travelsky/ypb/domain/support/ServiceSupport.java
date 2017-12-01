package com.travelsky.ypb.domain.support;

import com.travelsky.ypb.domain.message.Instance;

/**
 * Created by huc on 2017/11/26.
 *
 */
public interface ServiceSupport<T extends Instance> {

    /**
     * 事件主体
     * @param instance
     */
    T process(T instance);

}
