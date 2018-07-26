package pro.bechat.wallet.domain.model.vo;

import java.math.BigDecimal;

/**
 * 封装钱包和币种数据对象VO
 */
//@Data
public class WalletCoinVo {

    private Integer id;                     //币种编号

    private Integer coinId;                 //币种ID

    private String address;                 //钱包地址

    private BigDecimal freeAmount;          //冻结金额

    private BigDecimal amount;              //钱包总额

    private String coinName;                //币种名称

    private String coinImg;                 //币种图片

    private BigDecimal straightPush;        //币种直推总额

    private BigDecimal interest;            //币种生息总额

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCoinId() {
        return coinId;
    }

    public void setCoinId(Integer coinId) {
        this.coinId = coinId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getFreeAmount() {
        return freeAmount;
    }

    public void setFreeAmount(BigDecimal freeAmount) {
        this.freeAmount = freeAmount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getCoinImg() {
        return coinImg;
    }

    public void setCoinImg(String coinImg) {
        this.coinImg = coinImg;
    }

    public BigDecimal getStraightPush() {
        return straightPush;
    }

    public void setStraightPush(BigDecimal straightPush) {
        this.straightPush = straightPush;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }
}
