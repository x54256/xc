package cn.x5456.xc.api.cms;

import cn.x5456.xc.domain.cms.request.SavePageRequest;
import cn.x5456.xc.domain.cms.request.UpdatePageRequest;
import cn.x5456.xc.domain.cms.response.CmsPageResult;
import cn.x5456.xc.model.response.QueryResponseResult;
import cn.x5456.xc.model.response.ResponseResult;

/**
 * TODO:别人使用这个接口的时候，并不知道参数是干啥的
 * @author x5456
 */
public interface CmsPageControllerApi {

    // CMS页面查询
    QueryResponseResult findList(Integer page, Integer size, String siteId, String pageId, String pageName, String pageAliase, String templateId);

    // 新增CMS页面
    CmsPageResult add(SavePageRequest savePageRequest);

    //根据页面id查询页面信息
    CmsPageResult findByPageId(String pageId);

    //修改页面
    CmsPageResult updatePageByPageId(UpdatePageRequest updatePageRequest);

    //删除页面
    ResponseResult deletePageByPageId(String pageId);

}
