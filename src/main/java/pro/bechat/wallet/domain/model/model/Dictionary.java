package pro.bechat.wallet.domain.model.model;

import java.util.Date;

/**
 * 数据字典实体类(Model)
 */

public class Dictionary {


    private Integer id;                 //编号

    private Integer parentId;           //父节点ID

    private String name;                //属性名

    private String value;               //属性值

    private Integer code;               //属性码

    private String type;                //类型

    private Integer sortSign;           //排序

    private Integer status;             //1-有效 2-无效

    private Integer createAid;          //添加人

    private Date createTime;            //添加时间

    private Integer updateAid;          //修改人

    private Date updateTime;            //修改时间


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSortSign() {
        return sortSign;
    }

    public void setSortSign(Integer sortSign) {
        this.sortSign = sortSign;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCreateAid() {
        return createAid;
    }

    public void setCreateAid(Integer createAid) {
        this.createAid = createAid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateAid() {
        return updateAid;
    }

    public void setUpdateAid(Integer updateAid) {
        this.updateAid = updateAid;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }





}
