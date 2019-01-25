package cn.x5456.xc.manage_cms.service.impl;

import cn.x5456.xc.domain.cms.CmsPage;
import cn.x5456.xc.domain.cms.dto.CmsPageDTO;
import cn.x5456.xc.dto.PageDTO;
import cn.x5456.xc.exception.ExistedRecordedException;
import cn.x5456.xc.exception.ObjectIsNullException;
import cn.x5456.xc.manage_cms.dao.CmsPageRepository;
import cn.x5456.xc.manage_cms.dozer.IGenerator;
import cn.x5456.xc.manage_cms.service.CmsPageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author x5456
 */
@Slf4j
@Service
public class CmsPageServiceImpl implements CmsPageService {

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Autowired
    protected IGenerator dozer;


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

        // 3、构建条件匹配器，并对pageAliase设置模糊查询
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase",ExampleMatcher.GenericPropertyMatchers.contains());

        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);

        // 4、查询
        Page<CmsPage> cmsPages = cmsPageRepository.findAll(example, pageable);

        // 5、转成dto，传给controller层
        return dozer.convert(cmsPages.getContent(), CmsPageDTO.class);
    }

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
            log.error("cms-新增页面时，经检查 数据已存在");
            throw new ExistedRecordedException();
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

    @Override
    public CmsPageDTO findByPageId(String pageId) {

        CmsPage cmsPage = this.getPageByPageId(pageId);

        return dozer.convert(cmsPage, CmsPageDTO.class);
    }

    @Override
    public CmsPageDTO update(CmsPageDTO cmsPageDTO) {

        // 1、查询mongo，判断是否有数据
        CmsPage cmsPage = this.getPageByPageId(cmsPageDTO.getPageId());
        if (cmsPage == null) {
            log.error("cms-修改页面时，根据pageId查询到的数据为空");
            throw new ObjectIsNullException("根据pageId查询到的数据为空，请检查输入参数！");
        }

        // 2、手动对输入参数进行判断，因为有些可能我们不能让它改
        // 模板id
        String templateId = cmsPageDTO.getTemplateId();
        // 所属站点
        String siteId = cmsPageDTO.getSiteId();
        // 页面别名
        String pageAliase = cmsPageDTO.getPageAliase();
        // 页面名称
        String pageName = cmsPageDTO.getPageName();
        // 访问路径
        String pageWebPath = cmsPageDTO.getPageWebPath();
        // 物理路径
        String pagePhysicalPath = cmsPageDTO.getPagePhysicalPath();

        if (StringUtils.isNotBlank(templateId)) {
            cmsPage.setTemplateId(templateId);
        }
        if (StringUtils.isNotBlank(siteId)) {
            cmsPage.setSiteId(siteId);
        }
        if (StringUtils.isNotBlank(pageAliase)) {
            cmsPage.setPageAliase(pageAliase);
        }
        if (StringUtils.isNotBlank(pageName)) {
            cmsPage.setPageName(pageName);
        }
        if (StringUtils.isNotBlank(pageWebPath)) {
            cmsPage.setPageWebPath(pageWebPath);
        }
        if (StringUtils.isNotBlank(pagePhysicalPath)) {
            cmsPage.setPagePhysicalPath(pagePhysicalPath);
        }

        // 3、执行操作，转成dto
        CmsPage update = cmsPageRepository.save(cmsPage);

        return dozer.convert(update,CmsPageDTO.class);
    }

    @Override
    public void deleteByPageId(String pageId) {
        // 1、查询mongo，判断是否有数据
        CmsPage cmsPage = this.getPageByPageId(pageId);
        if (cmsPage == null) {
            log.error("cms-删除页面时，根据pageId查询到的数据为空");
            throw new ObjectIsNullException("根据pageId查询到的数据为空，请检查输入参数！");
        }

        // 2、删除操作
        cmsPageRepository.delete(cmsPage);
    }


    /**
     * 其实这个功能应该是dao层做的，但是现在dao层被jpa占据了，应该再多一层dmn层。但是已经拆成微服务了，就不用加了；就先这样写吧
     * @param pageId
     * @return
     */
    private CmsPage getPageByPageId(String pageId){

        CmsPage cmsPage = null;

        // 1、执行查询
        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);

        // 2、获取结果，返回
        if (optional.isPresent()) {
            cmsPage = optional.get();
        }

        return cmsPage;
    }
}