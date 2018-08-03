package pro.bechat.wallet.publics;

import com.google.gson.Gson;

import java.io.*;
import java.math.BigDecimal;

/**
 * @Author ch
 * @Date Create int 2018/8/2 19:28
 * @email 869360026@qq.com
 * 奖励配置参数
 */
public class RewardConfigureUtils {

    static RewardConfigureUtils configureUtils;

    public RewardConfigureUtils(){
    }


    public static RewardConfigureUtils getInstance() {
        if(configureUtils == null){
            try {
                InputStream inputStream = Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("reward.configure");
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer sb = new StringBuffer();
                for (String line; (line = br.readLine()) != null; ) {
                   sb.append(line);
                }
                configureUtils = new Gson().fromJson(sb.toString(),RewardConfigureUtils.class);
            }catch (Exception e){}

        }
        return configureUtils;
    }

    public void update(){
        try {
            configureUtils = this;
            FileOutputStream fileOutputStream = new FileOutputStream(Thread.currentThread().getContextClassLoader().getResource("reward.configure").getFile());
            fileOutputStream.write(new Gson().toJson(this).getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 静态本金大于
     */
    private BigDecimal staticGreaterNumber;
    /**
     * 静态本金大于返回
     */
    private BigDecimal staticGreaterRation;


    /**
     * 静态本金小于
     */
    private BigDecimal staticLessNumber;
    /**
     * 静态本金小于百分比
     */
    private BigDecimal staticLessRation;

    /**
     * 推荐锁仓比列
     */
    private BigDecimal recommendLockRation;

    /**
     * 推荐每天解仓数量
     */
    private BigDecimal recommendUnLockRation;

    /**
     * 推荐人数限制
     */
    private int recommendNumber;

    /**
     * 动态奖本金
     */
    private BigDecimal dynamicPrincipal;


    /**
     * 动态奖奖励百分比
     */
    private BigDecimal dynamicRation;


    /**
     * 每日限制
     */
    private BigDecimal dayLimit;

    /**
     * 出局制度推荐人数
     */
    private BigDecimal outRecommendNumbers;

    /**
     * 出局制本金限制
     */
    private BigDecimal outPrincipalLimint;

    /**
     * 出局有推荐人本金的倍数
     */
    private BigDecimal outRecommendMultiple;

    /**
     * 出局无倍数奖励
     */
    private BigDecimal outMultiple;


    public static RewardConfigureUtils getConfigureUtils() {
        return configureUtils;
    }

    public static void setConfigureUtils(RewardConfigureUtils configureUtils) {
        RewardConfigureUtils.configureUtils = configureUtils;
    }

    public BigDecimal getStaticGreaterNumber() {
        return staticGreaterNumber;
    }

    public void setStaticGreaterNumber(BigDecimal staticGreaterNumber) {
        this.staticGreaterNumber = staticGreaterNumber;
    }

    public BigDecimal getStaticGreaterRation() {
        return staticGreaterRation;
    }

    public void setStaticGreaterRation(BigDecimal staticGreaterRation) {
        this.staticGreaterRation = staticGreaterRation;
    }

    public BigDecimal getStaticLessNumber() {
        return staticLessNumber;
    }

    public void setStaticLessNumber(BigDecimal staticLessNumber) {
        this.staticLessNumber = staticLessNumber;
    }

    public BigDecimal getStaticLessRation() {
        return staticLessRation;
    }

    public void setStaticLessRation(BigDecimal staticLessRation) {
        this.staticLessRation = staticLessRation;
    }

    public BigDecimal getRecommendLockRation() {
        return recommendLockRation;
    }

    public void setRecommendLockRation(BigDecimal recommendLockRation) {
        this.recommendLockRation = recommendLockRation;
    }

    public BigDecimal getRecommendUnLockRation() {
        return recommendUnLockRation;
    }

    public void setRecommendUnLockRation(BigDecimal recommendUnLockRation) {
        this.recommendUnLockRation = recommendUnLockRation;
    }

    public int getRecommendNumber() {
        return recommendNumber;
    }

    public void setRecommendNumber(int recommendNumber) {
        this.recommendNumber = recommendNumber;
    }

    public BigDecimal getDynamicPrincipal() {
        return dynamicPrincipal;
    }

    public void setDynamicPrincipal(BigDecimal dynamicPrincipal) {
        this.dynamicPrincipal = dynamicPrincipal;
    }

    public BigDecimal getDynamicRation() {
        return dynamicRation;
    }

    public void setDynamicRation(BigDecimal dynamicRation) {
        this.dynamicRation = dynamicRation;
    }

    public BigDecimal getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(BigDecimal dayLimit) {
        this.dayLimit = dayLimit;
    }

    public BigDecimal getOutRecommendNumbers() {
        return outRecommendNumbers;
    }

    public void setOutRecommendNumbers(BigDecimal outRecommendNumbers) {
        this.outRecommendNumbers = outRecommendNumbers;
    }

    public BigDecimal getOutPrincipalLimint() {
        return outPrincipalLimint;
    }

    public void setOutPrincipalLimint(BigDecimal outPrincipalLimint) {
        this.outPrincipalLimint = outPrincipalLimint;
    }

    public BigDecimal getOutRecommendMultiple() {
        return outRecommendMultiple;
    }

    public void setOutRecommendMultiple(BigDecimal outRecommendMultiple) {
        this.outRecommendMultiple = outRecommendMultiple;
    }

    public BigDecimal getOutMultiple() {
        return outMultiple;
    }

    public void setOutMultiple(BigDecimal outMultiple) {
        this.outMultiple = outMultiple;
    }
}
