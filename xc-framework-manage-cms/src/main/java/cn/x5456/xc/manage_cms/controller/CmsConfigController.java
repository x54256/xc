package cn.x5456.xc.manage_cms.controller;

import cn.x5456.xc.api.cms.CmsConfigControllerApi;
import cn.x5456.xc.domain.cms.dto.CmsConfigDTO;
import cn.x5456.xc.domain.cms.response.CmsCode;
import cn.x5456.xc.domain.cms.response.CmsConfigResult;
import cn.x5456.xc.domain.cms.vo.CmsConfigVO;
import cn.x5456.xc.exception.IllegalParameterException;
import cn.x5456.xc.manage_cms.dozer.IGenerator;
import cn.x5456.xc.manage_cms.service.CmsConfigService;
import cn.x5456.xc.model.response.CommonCode;
import cn.x5456.xc.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author x5456
 */
@RestController
@RequestMapping("/cms/config")
@Api(value="cms配置管理接口",description = "cms配置管理接口，提供数据模型的管理、查询接口")
public class CmsConfigController extends BaseController implements CmsConfigControllerApi {

    @Autowired
    private CmsConfigService cmsConfigService;

    @Autowired
    private IGenerator dozer;

    @Override
    @GetMapping("/getmodel/{id}")
    @ApiOperation(value = "根据id查询CMS配置信息", httpMethod = "GET")
    public CmsConfigResult findCmsConfigById(@ApiParam(value = "CmsConfig的id", required = true) @PathVariable("id") String id) {

        // 1、参数校验
        if (StringUtils.isBlank(id)){
            throw new IllegalParameterException(CmsCode.CMS_INPUT_PARAMS_CONFIGID_ERROR);
        }

        // 2、调用service层
        CmsConfigDTO cmsConfigById = cmsConfigService.findCmsConfigById(id);

        // 3、转成vo
        CmsConfigVO cmsConfigVO = dozer.convert(cmsConfigById, CmsConfigVO.class);

        // 4、封装对象返回
        return new CmsConfigResult(CommonCode.SUCCESS,cmsConfigVO);
    }
}
