package pro.bechat.wallet.domain.model.response;

/**
 * @Author ch
 * @Date Create int 2018/7/24 15:13
 * @email 869360026@qq.com
 * 修改返回类型
 */
public class Result {
    public static final int ERROR = 1;
    public static final int WARNING = 2;
    public static final int INFO = 3;
    public static final int OK = 4;
    public static final int TOO_BUSY = 5;
    private int code;
    private String type;
    private String message;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Result(Object data) {
        this.data = data;
    }

    public Result(int code, String type, String message, Object data) {
        this.code = code;
        this.type = type;
        this.message = message;
        this.data = data;
    }

    public static Result getErro(String msg){
        return new Result(ERROR,"error",msg,null);
    }

    public static Result getErro(int code,String msg){
        return new Result(code,"error",msg,null);
    }

    public static Result getSuccess(Object data){
        return new Result(200,"error","suscc",data);
    }

}
