package pro.bechat.wallet.domain.model.vo;

import java.math.BigDecimal;

/**
 * 封装钱包和币种数据对象VO
 */
//@Data
public class WalletVo {

    private Integer id;                     //编号

    private String name;                    //币种全称

    private String address;                 //ETH地址

    private String contractAddr;            //合约币地址

    //private BigDecimal amount;              //数量

    private String coinName;                //币种名称

    private String coinImg;                 //币种头像

    private Integer userId;                 //用户编号

    //private BigDecimal walletTotal;         //市场价值


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //public BigDecimal getAmount() {
    //    return amount;
   // }

   // public void setAmount(BigDecimal amount) {
    //    this.amount = amount;
  //  }

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

    public String getContractAddr() {
        return contractAddr;
    }

    public void setContractAddr(String contractAddr) {
        this.contractAddr = contractAddr;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    //public BigDecimal getWalletTotal() {
    //    return walletTotal;
    //}

    //public void setWalletTotal(BigDecimal walletTotal) {
     //   this.walletTotal = walletTotal;
    //}


}
