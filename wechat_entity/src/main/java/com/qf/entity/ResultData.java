package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/30 10:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultData<T> {

    //返回码
    private Integer code;
    //结果描述
    private String msg;
    //返回结果
    private T data;

    //返回一个成功的ResultData对象
    public static <S> ResultData<S> createSuccResult(S data){
        ResultData<S> resultData = new ResultData<>();
        resultData.setCode(ResultCode.RESULT_SUCC);
        resultData.setMsg("success!");
        resultData.setData(data);
        return resultData;
    }

    //返回一个失败的ResultData对象
    public static <S> ResultData<S> createFailResult(Integer code, String msg, S data){
        ResultData<S> resultData = new ResultData<>();
        resultData.setCode(code);
        resultData.setMsg(msg);
        resultData.setData(data);
        return resultData;
    }
}
