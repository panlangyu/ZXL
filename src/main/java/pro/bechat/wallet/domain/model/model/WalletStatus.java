package pro.bechat.wallet.domain.model.model;


import java.util.Date;

/**
 * 锁仓表
 */
public class WalletStatus {

    private Integer id;                 //编号

    private Integer contractAddr;       //合约币地址

    private Integer status;             //是否锁仓 1、锁仓 2、正常

    private Date createTime;            //创建时间

    private Date updateTime;            //修改时间


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContractAddr() {
        return contractAddr;
    }

    public void setContractAddr(Integer contractAddr) {
        this.contractAddr = contractAddr;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
}
