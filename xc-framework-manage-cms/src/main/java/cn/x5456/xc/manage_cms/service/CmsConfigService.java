package cn.x5456.xc.manage_cms.service;

import cn.x5456.xc.domain.cms.dto.CmsConfigDTO;

/**
 * @author x5456
 */
public interface CmsConfigService {
    CmsConfigDTO findCmsConfigById(String id);
}
