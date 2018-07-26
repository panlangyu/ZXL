package pro.bechat.wallet.domain.model.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 币种交易订单记录
 */
//@Data
public class Transcation {

    private Integer id;                     //订单编号

    private Integer userId;                 //用户ID

    private String coinType;                //币种

    private BigDecimal amount;              //交易金额

    private Integer txType;                 //交易类型（1：转入，2：转出，3：直推，4：利息，5:团队奖）

    private String from;                    //转入地址

    private String to;                      //收款地址

    private String hash;                    //交易ID

    private Integer txStatus;               //交易状态（1：已提交，2：已完成）

    private BigDecimal capital;             //本金

    private Date createTime;                //订单创建时间

    private Date updateTime;                //订单修改时间

    private String remark;                  //备注


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getTxType() {
        return txType;
    }

    public void setTxType(Integer txType) {
        this.txType = txType;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getTxStatus() {
        return txStatus;
    }

    public void setTxStatus(Integer txStatus) {
        this.txStatus = txStatus;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
