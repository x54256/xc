## xc

### 1、为什么扫描到controller的内个接口，就会404

**我编的**
因为在mvc中是以这样格式进行存储的（{"url":"方法签名"}），当进行映射的时候会有两个相同的方法签名，然后他就蒙蔽了





请求的参数没必要每次都打印，可以获取参数的键值对放进ThreadLocal中，当出现异常，被全局异常捕获到的时候，再取出进行打印

    网关的作用就是保证本次请求是安全的，里面可以使用intercept（例如LoginInterceptor）
    
    我觉得上面所说的日志interceptor和异常处理要在每一个微服务中定义一个，保证同一个Thread
    
        每一个微服务的异常可以定义在common模块中exception包下分包

参数校验，如果参数不对应该快速失败（这是个异常）

pojo不能跨层（解耦）  应该注重系统的可扩展、可读性，从而牺牲性能

返回结果 null判断，要确定是因为什么为空，如果是数据库没有数据，那么直接return success。如果是因为异常那么就throw new Exception()


service异常？？  日志打印？？     例如出现空指针异常 怎么办？？  --那样返回前端是不是就是堆栈信息了

异常处理之后，返回前端堆栈信息吗？？  全局异常处理在网关还是每一个微服务都有？？


为什么ResultCode中不用getXXX方法？

增加可读性，枚举要的是状态而不是动作（get/set/find是动作）

![](https://ws1.sinaimg.cn/large/006tNc79ly1fzhqzcp6xoj31kg0ag77f.jpg)

rest路由的格式？

协议   权限    模块   版本（写在方法上，因为前面基本不会变） 资源（对应的实体类po）  ===  数量/    

/socket private                                                                 all
/mq     protect                                                                 single（省略）


pojo最好只有一个空参构造，通过get，set赋值，这才是pojo的本意



dubbo service层无法向web层抛出RuntimeException

    解决方案：
    
    1、重写Dubbo的exceptionFilter
    2、将异常定义在与service层接口同模块下（因为不同jar包是通过不同的类加载器加载的）


全局异常捕获的作用：
    
    1、将错误日志打印
        
        1)减少日志监测器的个数，如果在每一个controller打印日志，那么就会有好多个日志监测器
        2)还有如果我要将请求日志放进一个文件中（用于做用户习惯分析 xxx用户xxx时间做了什么），异常放进一个文件中...
    
    2、统一返回给前端的格式
    