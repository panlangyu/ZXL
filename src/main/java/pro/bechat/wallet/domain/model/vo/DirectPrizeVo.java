package pro.bechat.wallet.domain.model.vo;


import java.math.BigDecimal;

/**
 *  直推奖记录 装配参数VO
 */
public class DirectPrizeVo {

    private Integer id;                     //编号

    private Integer refereeId;              //推荐人编号

    private Integer coverRefereeId;         //被推荐人编号

    private Integer coinId;                 //币种编号

    private BigDecimal amountUsed;          //已用金额

    private BigDecimal amount;              //直推本金( 被推荐人本金*30%)

    private BigDecimal amountAvailable;     //可用余额


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




}
