package cn.x5456.xc.manage_cms.controller;

import cn.x5456.xc.api.cms.CmsPageControllerApi;
import cn.x5456.xc.domain.cms.dto.CmsPageDTO;
import cn.x5456.xc.domain.cms.request.SavePageRequest;
import cn.x5456.xc.domain.cms.request.UpdatePageRequest;
import cn.x5456.xc.domain.cms.response.CmsCode;
import cn.x5456.xc.domain.cms.response.CmsPageResult;
import cn.x5456.xc.domain.cms.vo.CmsPageVO;
import cn.x5456.xc.dto.PageDTO;
import cn.x5456.xc.exception.IllegalParameterException;
import cn.x5456.xc.manage_cms.dozer.IGenerator;
import cn.x5456.xc.manage_cms.service.CmsPageService;
import cn.x5456.xc.model.response.CommonCode;
import cn.x5456.xc.model.response.QueryResponseResult;
import cn.x5456.xc.model.response.QueryResult;
import cn.x5456.xc.model.response.ResponseResult;
import cn.x5456.xc.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// OK: 2019/1/25 把这里的log.xxx放到统一异常处理那处理
// TODO: 2019/1/25 这个地址定义的不规范，详情请见readme.md
//@Slf4j
@RestController
@RequestMapping("/cms/page")
@Api(value="cms页面管理接口",description = "cms页面管理接口，提供页面的增、删、改、查")
public class CmsPageController extends BaseController implements CmsPageControllerApi {

    @Autowired
    private CmsPageService cmsPageService;

    @Autowired
    private IGenerator dozer;

    @Override
    @GetMapping("/list/{page}/{size}")
    @ApiOperation(value = "分页条件查询页面列表", httpMethod = "GET")
    public QueryResponseResult findList(@ApiParam(value = "分页查询页码，从1开始", required = true) @PathVariable(value = "page") Integer page,
                                        @ApiParam(value = "每页记录的条数", required = true) @PathVariable(value = "size") Integer size,
                                        @ApiParam(value = "站点id") @RequestParam(value = "siteId",required = false) String siteId,
                                        @ApiParam(value = "页面ID") @RequestParam(value = "pageId",required = false) String pageId,
                                        @ApiParam(value = "页面名称") @RequestParam(value = "pageName",required = false) String pageName,
                                        @ApiParam(value = "别名") @RequestParam(value = "pageAliase",required = false) String pageAliase,
                                        @ApiParam(value = "模版id") @RequestParam(value = "templateId",required = false) String templateId
    ) {

        // controller层的职责
        // 1、参数校验
        if (page <= 0) {
            throw new IllegalParameterException(CmsCode.CMS_INPUT_PARAM_PATH_ERROR);
        }
        if (size <= 0) {
            throw new IllegalParameterException(CmsCode.CMS_INPUT_PARAM_SIZE_ERROR);
        }
        // 2、封装DTO（只用于向service层传递，解耦）
        PageDTO pageDTO = new PageDTO();

        pageDTO.setPage(page - 1);
        pageDTO.setSize(size);

        CmsPageDTO cmsPageDTO = new CmsPageDTO();

        cmsPageDTO.setSiteId(siteId);
        cmsPageDTO.setPageId(pageId);
        cmsPageDTO.setPageName(pageName);
        cmsPageDTO.setPageAliase(pageAliase);
        cmsPageDTO.setTemplateId(templateId);

        // 3、调用service层，并返回结果
        List<CmsPageDTO> cmsPageDTOList = cmsPageService.findList(pageDTO, cmsPageDTO);

        // 4、返回结果*字段*检验，而不是下面这个错误的例子
        // （由于为空就说明出现异常了（正常是返回一个空[]），所以不需要进行判断null）
        // 在全局异常处理一下RunTime异常
//        if (cmsPageDTOList != null) {
            // 5、转成vo
            QueryResult<CmsPageDTO> queryResult = new QueryResult<>();
            queryResult.setList(cmsPageDTOList);
            queryResult.setTotal(cmsPageDTOList.size());

            // 6、封装结果对象
            return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
//        }

    }

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
//        if (savePageRequest == null) {
//            log.error("cms-新增页面时，接受到的json为空");
//            throw new IllegalParameterException("接收到的json为空，请检查请求参数！");
//        }

        String pageName = savePageRequest.getPageName();
        String siteId = savePageRequest.getSiteId();
        String pageWebPath = savePageRequest.getPageWebPath();

        if (StringUtils.isAnyBlank(pageName, siteId, pageWebPath)){
            throw new IllegalParameterException(CmsCode.CMS_INPUT_PARAMS_ERROR);
        }

        // 2、封装DTO
        CmsPageDTO cmsPageDTO = dozer.convert(savePageRequest, CmsPageDTO.class);
//        cmsPageDTO.setPageCreateTime(new Date());  不要在这set时间，dto根本不需要时间

        // 3、调用service层，并返回结果
        CmsPageDTO add = cmsPageService.add(cmsPageDTO);

        // 4、转成vo
        CmsPageVO cmsPageVO = dozer.convert(add, CmsPageVO.class);

        // TODO: 2019/1/25 封装成工具类，这样可以在new的前后做一些事情（例如：参数校验）
        // 5、封装结果对象
        return new CmsPageResult(CommonCode.SUCCESS,cmsPageVO);
    }

    @Override
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据页面id查询页面信息", httpMethod = "GET")
    public CmsPageResult findByPageId(@ApiParam(value = "页面id", required = true) @PathVariable("id") String pageId) {

        // 1、参数校验
        if (StringUtils.isBlank(pageId)) {
            throw new IllegalParameterException(CmsCode.CMS_INPUT_PARAMS_PAGEID_ERROR);
        }

        // 2、调用service层
        CmsPageDTO cmsPageDTO = cmsPageService.findByPageId(pageId);

        // 3、dozer转成vo。我在工具类中已经做了null的判断
        CmsPageVO cmsPageVO = dozer.convert(cmsPageDTO, CmsPageVO.class);

        return new CmsPageResult(CommonCode.SUCCESS,cmsPageVO);
    }

    @Override
    @PutMapping("/edit")
    @ApiOperation(value = "修改页面信息", httpMethod = "PUT")
    public CmsPageResult updatePageByPageId(UpdatePageRequest updatePageRequest) {

        // 1、参数校验
//        String pageName = updatePageRequest.getPageName();
//        String siteId = updatePageRequest.getSiteId();
//        String pageWebPath = updatePageRequest.getPageWebPath();
        String pageId = updatePageRequest.getPageId();

//        if (StringUtils.isAnyBlank(pageName, siteId, pageWebPath ,pageId)){
//            throw new IllegalParameterException(CmsCode.CMS_INPUT_PARAMS_ERROR2);
//        }
        if (StringUtils.isBlank(pageId)){
            throw new IllegalParameterException(CmsCode.CMS_INPUT_PARAM_PATHID_ERROR);
        }

        // 2、转dto
        CmsPageDTO cmsPageDTO = dozer.convert(updatePageRequest, CmsPageDTO.class);
        CmsPageDTO update = cmsPageService.update(cmsPageDTO);

        // 3、转成vo，封装返回
        CmsPageVO cmsPageVO = dozer.convert(update, CmsPageVO.class);

        return new CmsPageResult(CommonCode.SUCCESS,cmsPageVO);
    }

    @Override
    @DeleteMapping("/del/{id}")
    @ApiOperation(value = "删除页面信息", httpMethod = "DELETE")
    public ResponseResult deletePageByPageId(String pageId) {

        // 1、参数校验
        if (StringUtils.isBlank(pageId)) {
            throw new IllegalParameterException(CmsCode.CMS_INPUT_PARAMS_PAGEID_ERROR);
        }

        // 2、调用service层
        cmsPageService.deleteByPageId(pageId);

        // 3、返回成功
        return ResponseResult.SUCCESS();
    }
}
