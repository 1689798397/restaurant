package com.example.common.vo;

import lombok.Data;

@Data
public class ResultVO {
    private Integer code;
    private String msg;
    private Object data;

    private ResultVO() {
    }

    private ResultVO(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResultVO ok() {
        return new ResultVO(200, "请求成功！", null);
    }

    public static ResultVO ok(Object data) {
        return new ResultVO(200, "请求成功！", data);
    }

    public static ResultVO error() {
        return new ResultVO(505, "非法参数！", null);
    }

    public static ResultVO error(String msg) {
        return new ResultVO(505, msg, null);
    }

    public static ResultVO error(Object data) {
        return new ResultVO(505, "请求失败！", data);
    }
}
