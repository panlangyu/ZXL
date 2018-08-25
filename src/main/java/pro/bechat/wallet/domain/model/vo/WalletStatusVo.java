package pro.bechat.wallet.domain.model.vo;

/**
 * 封装币种信息
 */
public class WalletStatusVo {

    private String contractAddr;

    private Boolean status;

    public String getContractAddr() {
        return contractAddr;
    }

    public void setContractAddr(String contractAddr) {
        this.contractAddr = contractAddr;
    }

    public Boolean getStatus()
    {
        return this.status;
    }

    public void setStatus(Boolean status)
    {
        this.status = status;
    }
}
