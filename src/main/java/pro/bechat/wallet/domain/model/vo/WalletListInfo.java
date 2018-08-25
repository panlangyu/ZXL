package pro.bechat.wallet.domain.model.vo;

import java.math.BigDecimal;

public class WalletListInfo {

    private Integer id;                 //编号

    private String address;             //ETH地址

    private String contractAddr;        //合约币地址

    //private BigDecimal amount;          //数量

    private String coinName;            //币种名称

    private String coinImg;             //币种图片

    //private BigDecimal walletTotal;     //市场价格

    public Integer getId()
    {
        return this.id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getAddress()
    {
        return this.address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getContractAddr()
    {
        return this.contractAddr;
    }

    public void setContractAddr(String contractAddr)
    {
        this.contractAddr = contractAddr;
    }

   // public BigDecimal getAmount()
   // {
    ///    return this.amount;
    //}

   // public void setAmount(BigDecimal amount)
   // {
  //      this.amount = amount;
 //   }

    public String getCoinName()
    {
        return this.coinName;
    }

    public void setCoinName(String coinName)
    {
        this.coinName = coinName;
    }

    public String getCoinImg()
    {
        return this.coinImg;
    }

    public void setCoinImg(String coinImg)
    {
        this.coinImg = coinImg;
    }

   // public BigDecimal getWalletTotal()
   // {
   //     return this.walletTotal;
   // }

    //public void setWalletTotal(BigDecimal walletTotal)
   // {
   //     this.walletTotal = walletTotal;
   // }
}
