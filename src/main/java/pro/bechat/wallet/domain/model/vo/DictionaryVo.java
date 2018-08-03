package pro.bechat.wallet.domain.model.vo;


/**
 *  数据字典 装配参数VO
 */
public class DictionaryVo {


    private Integer id;                 //编号

    private Integer parentId;           //父节点ID

    private String name;                //属性名

    private String value;               //属性值

    private Integer code;               //属性码

    private Integer sortSign;           //排序

    private Integer status;             //1-有效 2-无效


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
}
