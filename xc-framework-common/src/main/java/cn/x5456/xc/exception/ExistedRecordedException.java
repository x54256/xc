package cn.x5456.xc.exception;

/**
 * 记录已经存在异常
 * @author x5456
 */
public class ExistedRecordedException extends RuntimeException {

    private static String EXISTED_RECORDED_EXCEPTION = "当前记录已存在";

    public ExistedRecordedException() {
        super(EXISTED_RECORDED_EXCEPTION);
    }

    public ExistedRecordedException(String msg) {
        super(msg);
    }

}
