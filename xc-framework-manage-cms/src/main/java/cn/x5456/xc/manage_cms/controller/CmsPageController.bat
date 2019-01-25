package cn.x5456.xc.manage_cms.controller;

import cn.x5456.xc.api.cms.CmsPageControllerApi;
import cn.x5456.xc.domain.cms.CmsPage;
import cn.x5456.xc.domain.cms.request.QueryPageRequest;
import cn.x5456.xc.domain.cms.response.CmsCode;
import cn.x5456.xc.manage_cms.service.CmsPageService;
import cn.x5456.xc.model.response.CommonCode;
import cn.x5456.xc.model.response.QueryResponseResult;
import cn.x5456.xc.model.response.QueryResult;
import cn.x5456.xc.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/cms/page")
@Api(value="cms页面管理接口",description = "cms页面管理接口，提供页面的增、删、改、查")
public class CmsPageController1 extends BaseController implements CmsPageControllerApi {

    @Autowired
    private CmsPageService cmsPageService;


    @Override
    @GetMapping("/list/{page}/{size}")
    @ApiOperation(value = "分页查询页面列表", httpMethod = "GET")
    public QueryResponseResult findList(@ApiParam(value = "分页查询页码，从1开始", required = true) @PathVariable("page") Integer page,
                                        @ApiParam(value = "每页记录的条数", required = true) @PathVariable(value = "size") Integer size,
                                        QueryPageRequest queryPageRequest) {

        // TODO: 2019/1/24 日志怎么打印
//        log.info();
//使用拦截器将参数放入local  map  k,v   在异常是打印参数


//        // TODO: 2019/1/24 参数校验 是快速失败，还是使用默认值
//        if (page < 1) {
////            page = 1;
//            throw new RuntimeException("参数错误");
//        }
//        if (size < 1) {
//            size = 10;
//        }
//        if (queryPageRequest == null) {
//            queryPageRequest = new QueryPageRequest();
//        }

        // TODO: 2019/1/24 参数怎么传  换一个dto，解耦  前端跨到service层了
        Page<CmsPage> cmsPages = cmsPageService.findListByPagination(page - 1, size, queryPageRequest);


        // 封装返回结果
        if (cmsPages == null) {
            // todo: 如果结果为null，是throw 还是 return fail  看是为什么null，是数据库查为空还是错误为空
            // todo: 先暂时使用这个状态码
            return new QueryResponseResult(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }


//        vo   可扩展可读性 牺牲性能
        QueryResult<CmsPage> queryResult = new QueryResult<>();
        queryResult.setList(cmsPages.getContent());
        queryResult.setTotal(cmsPages.getTotalElements());
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }
}
