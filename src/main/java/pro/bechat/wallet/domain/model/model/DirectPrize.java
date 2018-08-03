package pro.bechat.wallet.domain.model.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 直推奖记录实体(Model)
 */
public class DirectPrize {


    private Integer id;                     //编号

    private Integer refereeId;              //推荐人编号

    private Integer coverRefereeId;         //被推荐人编号

    private Integer coinId;                 //币种编号

    private BigDecimal amountUsed;          //已用金额

    private BigDecimal amount;              //直推本金( 被推荐人本金*30%)

    private BigDecimal amountAvailable;     //可用余额

    private Date createTime;                //创建时间

    private Date updateTime;                //修改时间


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRefereeId() {
        return refereeId;
    }

    public void setRefereeId(Integer refereeId) {
        this.refereeId = refereeId;
    }

    public Integer getCoverRefereeId() {
        return coverRefereeId;
    }

    public void setCoverRefereeId(Integer coverRefereeId) {
        this.coverRefereeId = coverRefereeId;
    }

    public Integer getCoinId() {
        return coinId;
    }

    public void setCoinId(Integer coinId) {
        this.coinId = coinId;
    }

    public BigDecimal getAmountUsed() {
        return amountUsed;
    }

    public void setAmountUsed(BigDecimal amountUsed) {
        this.amountUsed = amountUsed;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmountAvailable() {
        return amountAvailable;
    }

    public void setAmountAvailable(BigDecimal amountAvailable) {
        this.amountAvailable = amountAvailable;
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
