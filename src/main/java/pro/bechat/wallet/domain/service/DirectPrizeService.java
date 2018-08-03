package pro.bechat.wallet.domain.service;



/**
 * 福利奖Service
 */
public interface DirectPrizeService {


    /**
     * 定时任务(利息生息、直推奖、动态奖)
     * @return
     * @throws Exception
     */
    public void timedTask()throws Exception;


}
