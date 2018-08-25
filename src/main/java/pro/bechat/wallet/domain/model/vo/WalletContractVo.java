package pro.bechat.wallet.domain.model.vo;

/**
 * 封装币种信息
 */
public class WalletContractVo {

    private Integer userId;         //用户编号

    private String name;            //币种全称

    private String coinName;        //币种名称

    private String contractAddr;    //合约币地址

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoinName() {
        return coinName;
    }
    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getContractAddr() {
        return contractAddr;
    }

    public void setContractAddr(String contractAddr) {
        this.contractAddr = contractAddr;
    }
}
