package cn.x5456.xc.exception;

import cn.x5456.xc.model.response.ExceptionCode;
import cn.x5456.xc.model.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


// TODO: 2019/1/25 log拦截器 ThreadLocal获取请求参数
/**
 * 统一异常处理：
 *
 *    自定义异常，返回前端msg，后台日志打印堆栈信息
 *    其它异常，返回前端堆栈信息，状态码统一99999
 * @author x5456
 */
@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    /**
     * 对象为空
     * @param ex
     * @return
     */
    @ExceptionHandler({ObjectIsNullException.class})
    @ResponseBody
    public ResponseResult exceptionHandler(ObjectIsNullException ex) {
        log.error("ObjectIsNullException detail：" + ex.getResultCode().message(),ex);
        return new ResponseResult(ex.getResultCode());
    }


    /**
     * 参数非法
     * @param ex
     * @return
     */
    @ExceptionHandler({IllegalParameterException.class})
    @ResponseBody
    public ResponseResult exceptionHandler(IllegalParameterException ex) {
        log.error("IllegalParameterException detail：" + ex.getResultCode().message(),ex);
        return new ResponseResult(ex.getResultCode());
    }


    /**
     * 记录已存在
     * @param ex
     * @return
     */
    @ExceptionHandler({ExistedRecordedException.class})
    @ResponseBody
    public ResponseResult exceptionHandler(ExistedRecordedException ex) {
        log.error("ExistedRecordedException detail：" + ex.getResultCode().message(),ex);
        return new ResponseResult(ex.getResultCode());
    }

    /**
     * 系统（未知）异常
     * @param ex
     * @return
     * @throws Exception
     */
    @ExceptionHandler({ Exception.class })
    @ResponseBody
    public ResponseResult exceptionHandler(Exception ex) {
        log.error("RuntimeException/Exception detail：", ex);
//        return new ResponseResult(CommonCode.SERVER_ERROR);
//        或  将异常信息传给前端

        ExceptionCode exceptionCode = new ExceptionCode();
        exceptionCode.setMessage(this.exceptionDetail(ex));

        return new ResponseResult(exceptionCode);
    }

    /**
     * 判断异常类型并设置对应提示信息
     * @param e
     * @return
     */
    private String exceptionDetail(Exception e) {
        StackTraceElement stackTraceElement = e.getStackTrace()[0];
        StringBuilder detail = new StringBuilder();
        String exclass = stackTraceElement.getClassName();
        String method = stackTraceElement.getMethodName();
        detail.append("类[");
        detail.append(exclass);
        detail.append("]调用[");
        detail.append(method);
        detail.append("]方法时在第[");
        detail.append(stackTraceElement.getLineNumber());
        detail.append("]行代码处发生[");
        detail.append(e.getClass().getName());
        detail.append("]异常，");

        // 自选是否输出堆栈信息给前端
//        detail.append("\n");
//        detail.append("异常堆栈信息：");
//        for (StackTraceElement stack : e.getStackTrace()){
//            detail.append("\t").append(stack);
//            detail.append("\n");
//        }

        detail.append("异常信息为：[");
        detail.append(e.getMessage());
        detail.append("]");

        return detail.toString();
    }
}
