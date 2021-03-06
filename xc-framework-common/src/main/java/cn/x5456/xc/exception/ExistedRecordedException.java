package cn.x5456.xc.exception;

import cn.x5456.xc.model.response.ResultCode;

/**
 * 记录已经存在异常
 * @author x5456
 */
public class ExistedRecordedException extends RuntimeException {


    //错误代码
    private ResultCode resultCode;

    private static String EXISTED_RECORDED_EXCEPTION = "当前记录已存在";

    public ExistedRecordedException() {
        super(EXISTED_RECORDED_EXCEPTION);
    }

//    public ExistedRecordedException(String msg) {
//        super(msg);
//    }

    public ExistedRecordedException(ResultCode resultCode){
        super(resultCode.message());
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
