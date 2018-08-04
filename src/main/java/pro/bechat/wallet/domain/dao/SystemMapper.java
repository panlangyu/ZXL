package pro.bechat.wallet.domain.dao;

import pro.bechat.wallet.domain.model.model.SystemStatistics;

/**
 * @Author ch
 * @Date Create int 2018/8/3 16:27
 * @email 869360026@qq.com
 */
public interface SystemMapper extends BasicMapper {
    SystemStatistics selectStatistics();
}
