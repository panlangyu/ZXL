package pro.bechat.wallet.domain.model.model;

import java.math.BigDecimal;

/**
 * @Author ch
 * @Date Create int 2018/8/3 16:30
 * @email 869360026@qq.com
 */
public class SystemStatistics {
    private int userNumber;
    private BigDecimal recharge;
    private BigDecimal foward;
    private BigDecimal recommend;
    private BigDecimal interest;
    private BigDecimal active;

    public int getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }

    public BigDecimal getRecharge() {
        return recharge;
    }

    public void setRecharge(BigDecimal recharge) {
        this.recharge = recharge;
    }

    public BigDecimal getFoward() {
        return foward;
    }

    public void setFoward(BigDecimal foward) {
        this.foward = foward;
    }

    public BigDecimal getRecommend() {
        return recommend;
    }

    public void setRecommend(BigDecimal recommend) {
        this.recommend = recommend;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getActive() {
        return active;
    }

    public void setActive(BigDecimal active) {
        this.active = active;
    }
}
