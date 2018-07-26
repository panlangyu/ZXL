package pro.bechat.wallet.domain.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 币种交易订单记录延伸类VO
 */
//@Data
public class TranscationVo {

    private Integer id;                     //订单编号

    private Integer txType;                 //交易类型

    private BigDecimal amount;              //交易金额

    private String hash;                    //交易ID

    private Date createTime;                //订单创建时间


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTxType() {
        return txType;
    }

    public void setTxType(Integer txType) {
        this.txType = txType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
