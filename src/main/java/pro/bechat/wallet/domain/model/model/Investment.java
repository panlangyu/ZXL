package pro.bechat.wallet.domain.model.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 投资(福利)Model
 */
public class Investment {


    private Integer id;                     //编号

    private Integer walletId;               //钱包(币种) 编号

    private BigDecimal interests;           //利息 按存入不同币种的数值 0.3%-0.5%生息

    private BigDecimal recommend;           //直推奖（30%） 拿下一个用户的30%币

    private BigDecimal dynamicAward;        //动态奖 阶级，515 遇到当前用户下人数的5的倍数进行奖励

    private Date createTime;                //创建时间

    private Date updateTime;                //修改时间


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWalletId() {
        return walletId;
    }

    public void setWalletId(Integer walletId) {
        this.walletId = walletId;
    }

    public BigDecimal getInterests() {
        return interests;
    }

    public void setInterests(BigDecimal interests) {
        this.interests = interests;
    }

    public BigDecimal getRecommend() {
        return recommend;
    }

    public void setRecommend(BigDecimal recommend) {
        this.recommend = recommend;
    }

    public BigDecimal getDynamicAward() {
        return dynamicAward;
    }

    public void setDynamicAward(BigDecimal dynamicAward) {
        this.dynamicAward = dynamicAward;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
