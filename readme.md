## xc

### 1、为什么加了@ComponentScan注解扫描api模块的包，请求的时候，就会404？

**我编的**

因为在mvc中是以这样格式进行存储的（{"url":"方法签名"}），当进行映射的时候会有两个相同的方法签名，然后他就蒙蔽了

**真正的**

springboot缺省是使用主配置类所在的包扫描，但是可以使用@ComponentScan设置，如果我只配`@ComponentScan(basePackages={"cn.x5456.xc.exception"})`的话就会覆盖掉默认的，所以扫描不到原来的包，请求的时候就会404

使用时，需要按照下面这样写
```java
@SpringBootApplication
@ComponentScan(basePackages={"cn.x5456.xc.exception"})
@ComponentScan(basePackages={"cn.x5456.xc.manage_cms"})//扫描本项目下的所有类（只有一个的时候可以不写）
public class ManageCmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCmsApplication.class,args);
    }
}
```

### 2、本系统中定义的返回结果

![](https://ws2.sinaimg.cn/large/006tNc79gy1fzktsx287xj30xe0igq5f.jpg)

我们可以直接返回ResponseResult，也就是说可以不返回给前端数据（例如增加时，只需要告诉前端是否成功，没必要添加数据）。

![](https://ws1.sinaimg.cn/large/006tNc79gy1fzktwetkfyj30xu0eqjt9.jpg)

> 枚举就是一个隐藏了构造方法（反射都无法创建枚举的对象<enum字段底层做了处理>）的特殊类

### 3、为什么ResultCode中要定义3个方法，而不是直接通过get方法获取值？

增加可读性，枚举要的是状态而不是动作（get/set/find是动作），rest风格的路由也是同理

![](https://ws1.sinaimg.cn/large/006tNc79ly1fzhqzcp6xoj31kg0ag77f.jpg)

### 4、rest风格路由的格式？

协议      权限       模块      版本（写在方法上，因为前面基本不会变） 资源（对应的实体类po）    数量

/socket private  resource               v1                         cmsPage           all
/mq     protect                         v2                                         single（一般省略）
/rest


### 5、Dubbo的service层是无法向web层抛出RuntimeException，但在本项目中无所谓，service的异常会抛到controller层的。

    解决方案：
    
    1、重写Dubbo的exceptionFilter
    2、将异常定义在与service层接口同模块下（因为不同jar包是通过不同的类加载器加载的）
    
### 6、统一异常处理的作用
          
    1、将错误日志打印
      
      1)减少日志监测器的个数，如果在每一个controller打印日志，那么就会有好多个日志监测器
      2)还有如果我要将请求日志放进一个文件中（用于做用户习惯分析 xxx用户xxx时间做了什么），异常放进一个文件中...
    
    2、统一返回给前端的格式

    我的统一异常处理：
      1)自定义异常，返回前端msg，后台日志打印堆栈信息
      2)其它异常，返回前端堆栈信息，状态码统一99999

### 7、一次请求过程中，代码要注意的点（具体参见CmsPageController）

    1、参数校验<无论何时都要放在方法的最上面，为了快速失败>，如果参数不对应该快速失败（抛出异常） -- service层同理
    2、如果service掉dao查询返回结果为null，要确定是因为什么为空，如果是null是合理的（例如查询的时候），那么直接返回controller层。如果不合理（例如更新前需要查询数据库有没有）那么就直接在service层throw new Exception()
    3、pojo不能跨层（解耦）  -- 我们的应该注重系统的可扩展、可读性，从而牺牲性能（性能可以通过硬件跟上）
    
        pojo最好只有一个空参构造，通过get，set赋值，这才是pojo的本意
        
    前端              Controller层              Service层             Dao层
    
         request dto                  dto                   po
            -->                       -->                   -->
            
            vo                        dto                   po
            <--                       <--                   <--
            
    request dto 一方面限制前端可以修改的内容，一方面告诉前端传递的参数（结合swagger）
    
    dto 一个dto就是一个完整的对象，基本上和entity中的字段相同
    
    po  数据库映射的实体
    
    vo  返回给前端展示的实体，基本上和dto中的字段相同

### 8、一个setCreateTime

controller层：
```java
/**
 * 当前端传一个{}过来的话，是会创建一个SavePageRequest的对象，只不过value全部为null
 *    所以不用对null进行判断
 *    只需要对必填项进行判断
 * @param savePageRequest
 * @return
 */
@Override
@PostMapping("/add")
@ApiOperation(value = "新增页面信息", httpMethod = "POST")
public CmsPageResult add(SavePageRequest savePageRequest) {

    // 1、参数校验
    // if (savePageRequest == null) {
    //     log.error("cms-新增页面时，接受到的json为空");
    //     throw new IllegalParameterException("接收到的json为空，请检查请求参数！");
    // }

    String pageName = savePageRequest.getPageName();
    String siteId = savePageRequest.getSiteId();
    String pageWebPath = savePageRequest.getPageWebPath();

    if (StringUtils.isAnyBlank(pageName, siteId, pageWebPath)){
        throw new IllegalParameterException(CmsCode.CMS_INPUT_PARAMS_ERROR);
    }

    // 2、封装DTO
    CmsPageDTO cmsPageDTO = dozer.convert(savePageRequest, CmsPageDTO.class);
    // cmsPageDTO.setPageCreateTime(new Date());  **不要在这set时间，dto根本不需要时间**

    // 3、调用service层，并返回结果
    CmsPageDTO add = cmsPageService.add(cmsPageDTO);

    // 4、转成vo
    CmsPageVO cmsPageVO = dozer.convert(add, CmsPageVO.class);

    // TODO: 2019/1/25 封装成工具类，这样可以在new的前后做一些事情（例如：参数校验）
    // 5、封装结果对象
    return new CmsPageResult(CommonCode.SUCCESS,cmsPageVO);
}
```
service层：
```java
/**
 * 1、尽量让系统快速失败
 * 2、虽然感觉第4步多余，直接在一开始给dto设置时间不就好了吗；但是我们要弄清楚是谁要时间，
 *    是cmsPage对象，而第4步只是对原有对象进行了复用，而且他是从save对象中取出的事件
 * @param cmsPageDTO
 */
@Override
public CmsPageDTO add(CmsPageDTO cmsPageDTO) {

    // 1、验证数据唯一性
    String pageName = cmsPageDTO.getPageName();
    String siteId = cmsPageDTO.getSiteId();
    String pageWebPath = cmsPageDTO.getPageWebPath();

    CmsPage checkUnique = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(pageName,siteId,pageWebPath);

    if (checkUnique !=  null) {
        throw new ExistedRecordedException(CmsCode.CMS_ADDPAGE_EXISTS);
    }

    // 2、转换成po
    CmsPage cmsPage = dozer.convert(cmsPageDTO, CmsPage.class);
    cmsPage.setPageCreateTime(new Date());

    // 3、保存
    CmsPage save = cmsPageRepository.save(cmsPage);

    // 4、
    cmsPageDTO.setPageCreateTime(save.getPageCreateTime());

    return cmsPageDTO;
}
```

### 9、mongo的分页查询

```java
/**
 * 分页条件查询Page
 * @param pageDTO 分页参数
 * @param cmsPageDTO 查询条件
 * @return
 */
@Override
public List<CmsPageDTO> findList(PageDTO pageDTO, CmsPageDTO cmsPageDTO) {

    // 1、构建pageable对象
    Integer page = pageDTO.getPage();
    Integer size = pageDTO.getSize();
    Pageable pageable = PageRequest.of(page, size);

    // 2、使用dozer将其转成po
    CmsPage cmsPage = dozer.convert(cmsPageDTO, CmsPage.class);

    // OK: 2019/1/25 改成if判断
    // 3、构建条件匹配器，并对pageAliase设置模糊查询
    ExampleMatcher exampleMatcher = ExampleMatcher.matching();

    if (StringUtils.isNotBlank(cmsPageDTO.getPageAliase())){
        exampleMatcher = exampleMatcher.withMatcher("pageAliase",ExampleMatcher.GenericPropertyMatchers.contains());
    }


    Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);

    // 4、查询
    Page<CmsPage> cmsPages = cmsPageRepository.findAll(example, pageable);

    // 5、转成dto，传给controller层
    return dozer.convert(cmsPages.getContent(), CmsPageDTO.class);
}
```  
    



为什么要在service层写测试？？

全局异常处理在网关还是每一个微服务都有？？

怎样分文件，例如：cmspage  cmspageconfig 的service层  是一起还是分开？？

CmsConfigModel这种的怎么办？？ -- CmsConfig下的List

// 2）验证数据唯一性
String siteId2 = siteId;
String pageName2 = pageName;
String pageWebPath2 = pageWebPath;



请求的参数没必要每次都打印，可以获取参数的键值对放进ThreadLocal中，当出现异常，被全局异常捕获到的时候，再取出进行打印（问题{}中的参数无法获取到，暂时放弃）

    网关的作用就是保证本次请求是安全的，里面可以使用intercept（例如LoginInterceptor）
    
    我觉得上面所说的日志interceptor和异常处理要在每一个微服务中定义一个，保证同一个Thread
    
        每一个微服务的异常可以定义在common模块中exception包下分包