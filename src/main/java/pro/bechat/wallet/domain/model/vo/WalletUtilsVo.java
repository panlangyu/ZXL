package pro.bechat.wallet.domain.model.vo;

/**
 * 转账封装参数
 */
public class WalletUtilsVo {


    private Integer id;                 //编号

    private Integer userId;             //用户编号

    private String phone;               //手机号

    private String passwd;              //交易密码

    private String coinName;            //币种名称

    //private String contractAddr;        //合约币地址

    //private String from;                //转出者地址

    private String address;             //收款方地址

    private String value;               //转账数量(金额)

    //private String hash;                //转账成功的hash值

    private String remark;              //备注

    public Integer getId()
    {
        return this.id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getPhone()
    {
        return this.phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getCoinName()
    {
        return this.coinName;
    }

    public void setCoinName(String coinName)
    {
        this.coinName = coinName;
    }

    //public String getContractAddr()
   // {
  //      return this.contractAddr;
 //   }

   // public void setContractAddr(String contractAddr)
   // {
   //     this.contractAddr = contractAddr;
   // }

    //public String getFrom()
  //  {
   //     return this.from;
   // }

   // public void setFrom(String from)
  //  {
  //      this.from = from;
  //  }

    public String getAddress()
    {
        return this.address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getValue()
    {
        return this.value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

   // public String getHash()
   // {
   //     return this.hash;
    //}

   // public void setHash(String hash)
   // {
   //     this.hash = hash;
   // }

    public String getRemark()
    {
        return this.remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

}
