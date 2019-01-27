package cn.x5456.xc.model.response;

/**
 * @author x5456
 */
public class ExceptionCode implements ResultCode {

    //提示信息
    private String message;

    @Override
    public boolean success() {
        return false;
    }

    @Override
    public int code() {
        return 99999;
    }

    @Override
    public String message() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
