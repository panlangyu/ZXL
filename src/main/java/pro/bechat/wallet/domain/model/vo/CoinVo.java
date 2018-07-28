package pro.bechat.wallet.domain.model.vo;



/**
 *  币种 装配参数VO
 */
public class CoinVo {

    private Integer id;                 //币种编号

    private String coinName;            //币种名称

    private String coinImg;             //币种图标

    private String address;             //合约币地址

    private Integer status;             //状态（1：正常，2：无效）


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
