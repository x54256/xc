package cn.x5456.xc.exception;

import cn.x5456.xc.model.response.ResultCode;

/**
 * 参数非法异常（校验不通过）
 * @author x5456
 */
public class IllegalParameterException extends RuntimeException{

    //错误代码
    private ResultCode resultCode;


    public IllegalParameterException() {
        super();
    }

//    public IllegalParameterException(String s) {
//        super(s);
//    }

    public IllegalParameterException(ResultCode resultCode) {
        super(resultCode.message());
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}