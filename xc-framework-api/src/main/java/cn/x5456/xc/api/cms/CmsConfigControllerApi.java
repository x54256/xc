package cn.x5456.xc.api.cms;

import cn.x5456.xc.domain.cms.response.CmsConfigResult;

public interface CmsConfigControllerApi {

    // 根据id查询CMS配置信息
    CmsConfigResult findCmsConfigById(String id);
}
