package pro.bechat.wallet.domain.model.model;


import java.math.BigDecimal;
import java.util.Date;

/**
 * 钱包实体类(Model)
 */
public class Wallet {

    private Integer id;                     //币种编号

    private Integer coinId;                 //币种ID

    private String address;                 //钱包地址

    private String privateKey;              //钱包私钥

    private String passwd;                  //密码

    private Date createTime;                //钱包创建时间

    private Date updateTime;                //钱包更新时间

    private String keystore;                //keystore(密钥库)

    private Integer userId;                 //用户ID

    private BigDecimal freeAmount;          //冻结金额

    private BigDecimal amount;              //钱包总额

    private BigDecimal availableAmount;     //可用余额

    private String coinName;                //币种名称

    private String coinImg;                 //币种图片

    private String remark;                  //备注

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

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
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

    public String getKeystore() {
        return keystore;
    }

    public void setKeystore(String keystore) {
        this.keystore = keystore;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
