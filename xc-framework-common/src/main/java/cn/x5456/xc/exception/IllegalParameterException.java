package cn.x5456.xc.exception;

/**
 * 参数非法异常（校验不通过）
 * @author x5456
 */
public class IllegalParameterException extends RuntimeException{

    public IllegalParameterException() {
        super();
    }

    public IllegalParameterException(String s) {
        super(s);
    }
}