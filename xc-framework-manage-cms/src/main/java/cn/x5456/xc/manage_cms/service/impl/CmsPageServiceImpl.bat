package cn.x5456.xc.manage_cms.service.impl;

import cn.x5456.xc.domain.cms.CmsPage;
import cn.x5456.xc.domain.cms.request.QueryPageRequest;
import cn.x5456.xc.manage_cms.dao.CmsPageRepository;
import cn.x5456.xc.manage_cms.service.CmsPageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author x5456
 */
@Slf4j
@Service
public class CmsPageServiceImpl1 implements CmsPageService {

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Override
    public Page<CmsPage> findListByPagination(Integer page, Integer size, QueryPageRequest queryPageRequest) {

        // TODO: 2019/1/24 service层也要打印 这个 日志吗？

        Pageable pageable = PageRequest.of(page,size);
        // TODO: 2019/1/24 测试（查询所有，之后改成根据queryPageRequest条件查询）
        Page<CmsPage> cmsPages = cmsPageRepository.findAll(pageable);

//        不返回page对象，解耦

        return cmsPages;
    }
}
