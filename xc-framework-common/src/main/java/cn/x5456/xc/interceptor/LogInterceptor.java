package cn.x5456.xc.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * todo: 无法获取{}中的参数
 * @author x5456
 */
public class LogInterceptor extends HandlerInterceptorAdapter {


    // 定义一个线程域，存放请求的参数们
    private static final ThreadLocal<Map> params = new ThreadLocal<>();


    /**
     * 获取请求参数，放入ThreadLocal中
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        return true;

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // remove;
    }

//  public static UserInfo getLoginUser() {
//      return tl.get();
//  }

    /**
     * 把request转为map
     *
     * @param request
     * @return
     */
    private Map<String, Object> getParameterMap(HttpServletRequest request) {

        // 返回值Map
        Map<String, Object> returnMap = new HashMap<String, Object>();


            // 参数Map
            Map<?, ?> properties = request.getParameterMap();

            Iterator<?> entries = properties.entrySet().iterator();

            Map.Entry<String, Object> entry;
            String name = "";
            String value = "";
            Object valueObj = null;
            while (entries.hasNext()) {
                entry = (Map.Entry<String, Object>) entries.next();
                name = (String) entry.getKey();
                valueObj = entry.getValue();
                if (null == valueObj) {
                    value = "";
                } else if (valueObj instanceof String[]) {
                    String[] values = (String[]) valueObj;
                    for (int i = 0; i < values.length; i++) {
                        value = values[i] + ",";
                    }
                    value = value.substring(0, value.length() - 1);
                } else {
                    value = valueObj.toString();
                }
                returnMap.put(name, value);
            }

        return returnMap;
    }


}
