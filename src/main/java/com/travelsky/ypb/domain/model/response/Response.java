package com.travelsky.ypb.domain.model.response;

import java.io.Serializable;

/**
 * create Response by huc
 * 2018/1/15  下午3:23
 */
public class Response implements Serializable{

    private String code;
    private String msg;
    private Object data;
    private boolean success;


    public Response(){

    }

    public Response(Object t){
        this.code = "200";
        this.msg = "成功";
        this.success = true;
        this.data = t;
    }

    public  Response(String t){
        this.code = "400";
        this.msg = "失败";
        this.success = false;
        this.data = null;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Response{");
        sb.append("code='").append(code).append('\'');
        sb.append(", msg='").append(msg).append('\'');
        sb.append(", data=").append(data);
        sb.append(", success=").append(success);
        sb.append('}');
        return sb.toString();
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
