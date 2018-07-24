package pro.bechat.wallet.domain.model.model;

import java.util.Date;

/**
 * @Author ch
 * @Date Create int 2018/7/24 17:26
 * @email 869360026@qq.com
 */
public class Code {

    public static final int STATUS_CAN_USE = 0;
    public static final int STATUS_SEND_FAILE = 1;
    public static final int STATUS_IS_USED = 1;

    private long id;

    /**
     * 短信发送序列号
     */
    private String sendid;

    /**
     * 用户手机号码
     */
    private String tel;

    /**
     * 短信内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;


    /**
     * 状态 0发送短信成功 (未使用)
     *      1发送短信失败 发送失败 不能校验
     *      2 已使用 不能再次获取
     */
    private int status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSendid() {
        return sendid;
    }

    public void setSendid(String sendid) {
        this.sendid = sendid;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
