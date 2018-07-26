package pro.bechat.wallet.domain.model.model;

import lombok.Data;

import java.util.Date;

/**
 * 币种实体类(Model)
 */
//@Data
public class Coin {

    private Integer id;                 //币种编号

    private String coinName;            //币种名称

    private String coinImg;             //币种图标

    private Date createTime;            //创建时间

    private Date updateTime;            //更新时间

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
