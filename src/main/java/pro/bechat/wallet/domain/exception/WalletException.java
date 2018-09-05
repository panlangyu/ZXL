package pro.bechat.wallet.domain.exception;

import pro.bechat.wallet.domain.enums.WalletEnum;

/**
 * 钱包异常处理类
 */
public class WalletException extends RuntimeException {

    private WalletEnum walletEnum;

    public WalletException(){

    }

    public WalletException(WalletEnum walletEnum) {
        //super(walletEnum.getMessage());
        this.walletEnum = walletEnum;
    }

    public WalletEnum getWalletEnum() {

        return this.walletEnum;
    }

    public void setWalletEnum(WalletEnum walletEnum) {

        this.walletEnum = walletEnum;
    }


}
