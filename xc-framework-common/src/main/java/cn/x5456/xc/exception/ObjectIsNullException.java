package cn.x5456.xc.exception;

import cn.x5456.xc.model.response.ResultCode;

/**
 * 对象为null异常
 * @author x5456
 */
public class ObjectIsNullException extends RuntimeException {

    private ResultCode resultCode;

    public ObjectIsNullException() {
        super();
    }

    public ObjectIsNullException(ResultCode resultCode) {
        super(resultCode.message());
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
