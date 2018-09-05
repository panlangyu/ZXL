package pro.bechat.wallet.domain.enums;

/**
 * 钱包枚举类，异常提示
 */
public enum WalletEnum {

    WALLET_NOT_NULL_ERROR(1001, "钱包地址不能为空"),
    WALLET_NOT_EXISTENT_ERROR(1002, "币种不存在"),
    WALLET_EXISTENT_COINNAME(1006, "该币种已存在"),
    WALLET_NOT_BLOCK_INFO(1007,"未查询到阻塞数信息"),
    WALLET_NOT_ACCOUNT_INFO(1008,"未查询到账户数据"),
    WALLET_INSERT_COINNAME(1009, "添加失败"),
    WALLET_SYSTEM_ERR(1010,"系统异常"),
    WALLET_NOT_OPEN_UP(1011,"开通钱包失败"),
    WALLET_NOT_USER_INFO(1012,"用户不存在"),
    WALLET_REPEAT_INFO(1015,"已添加过该币种"),
    WALLET_NOT_LIST_INFO(1016,"未查询到用户钱包信息"),
    WALLET_NOT_USER_REPEAT(1017,"用户未拥有该币种"),
    WALLET_NOT_GT_ZERO(1018,"请输入大于 0 的正数"),
    WALLET_AMOUNT_INSUFFICIENT(1019,"币种数量不足"),
    WALLET_CURRENCY_FAILURE(1020,"提币失败"),
    WALLET_ABSENTEEISM_REPEAT(1021,"旷工费不足"),
    WALLET_NOT_BEI_USER_INFO(1022,"被转账用户不存在"),
    WALLET_NOT_ETH_INFO(1026,"用户未拥有ETH钱包"),
    WALLET_PASSWD_FAIL(1027,"密码输入不正确"),
    WALLET_NOT_MARKET(1028,"未查询到市场资产"),
    WALLET_DELETE_FAIL(1029,"删除失败");



    private Integer code;               //状态码
    private String message;             //提示信息


    private WalletEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
