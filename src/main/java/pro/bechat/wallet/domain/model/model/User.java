package pro.bechat.wallet.domain.model.model;

/**
 * create User by huc
 * 2018/4/20  下午2:16
 */
public class User {

    private String userId;
    private String userName;
    private String sex;
    private String userTrueName;
    private String userPhone;
    private String userIdCard;
    private String userCountry;
    private String userEmail;
    private String createTime;
    private String updateTime;
    private String seeds;
    private String transactionPW;
    private String machinePW;
    private String machineId;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userIdl) {
        this.userId = userIdl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserTrueName() {
        return userTrueName;
    }

    public void setUserTrueName(String userTrueName) {
        this.userTrueName = userTrueName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserIdCard() {
        return userIdCard;
    }

    public void setUserIdCard(String userIdCard) {
        this.userIdCard = userIdCard;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getSeeds() {
        return seeds;
    }

    public void setSeeds(String seeds) {
        this.seeds = seeds;
    }

    public String getTransactionPW() {
        return transactionPW;
    }

    public void setTransactionPW(String transactionPW) {
        this.transactionPW = transactionPW;
    }

    public String getMachinePW() {
        return machinePW;
    }

    public void setMachinePW(String machinePW) {
        this.machinePW = machinePW;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String macineId) {
        this.machineId = macineId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("userId='").append(userId).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", sex='").append(sex).append('\'');
        sb.append(", userTrueName='").append(userTrueName).append('\'');
        sb.append(", userPhone='").append(userPhone).append('\'');
        sb.append(", userIdCard='").append(userIdCard).append('\'');
        sb.append(", userCountry='").append(userCountry).append('\'');
        sb.append(", userEmail='").append(userEmail).append('\'');
        sb.append(", createTime='").append(createTime).append('\'');
        sb.append(", updateTime='").append(updateTime).append('\'');
        sb.append(", seeds='").append(seeds).append('\'');
        sb.append(", transactionPW='").append(transactionPW).append('\'');
        sb.append(", machinePW='").append(machinePW).append('\'');
        sb.append(", machineId='").append(machineId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
