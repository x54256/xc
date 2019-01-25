package cn.x5456.xc.exception;

/**
 * 对象为null异常
 * @author x5456
 */
public class ObjectIsNullException extends RuntimeException {

    public ObjectIsNullException() {
        super();
    }

    public ObjectIsNullException(String s) {
        super(s);
    }
}
