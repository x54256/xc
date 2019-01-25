package cn.x5456.xc.manage_cms.service;

import cn.x5456.xc.domain.cms.dto.CmsPageDTO;
import cn.x5456.xc.dto.PageDTO;

import java.util.List;

/**
 * @author x5456
 */
public interface CmsPageService {

    List<CmsPageDTO> findList(PageDTO pageDTO, CmsPageDTO cmsPageDTO);

    CmsPageDTO add(CmsPageDTO cmsPageDTO);

    CmsPageDTO findByPageId(String pageId);

    CmsPageDTO update(CmsPageDTO cmsPageDTO);

    void deleteByPageId(String pageId);
}
