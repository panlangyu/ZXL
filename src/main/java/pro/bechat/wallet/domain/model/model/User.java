package pro.bechat.wallet.domain.model.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * create User by huc
 * 2018/4/20  下午2:16
 */
public class User {

    public static int STATUS_NOMARL = 1;
    public static int STATUS_OUT = 2;
    public static int STATUS_FREE = 3;

    private int id;

    private String phoneNum;

    private String passwd;

    private String seeds;

    private String headImg;

    private int status;

    private String nickName;

    private int sex;

    private Date createTime;

    private Date updateTime;

    private String relationship;

    private String invitationCode;

    private String mnemonit;

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getSeeds() {
        return seeds;
    }

    public void setSeeds(String seeds) {
        this.seeds = seeds;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
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

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getMnemonit() {
        return mnemonit;
    }

    public void setMnemonit(String mnemonit) {
        this.mnemonit = mnemonit;
    }
}
