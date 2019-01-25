package cn.x5456.xc.manage_cms.dao;

import cn.x5456.xc.domain.cms.CmsPage;
import cn.x5456.xc.domain.cms.CmsPageParam;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author x5456
 */
//@SpringBootTest
//@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {

    @Autowired
    private CmsPageRepository cmsPageRepository;

    // 增加
    @Test
    public void testInsert() {
        //定义实体类
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId("s01");
        cmsPage.setTemplateId("t01");
        cmsPage.setPageName("测试页面");
        cmsPage.setPageCreateTime(new Date());
        List<CmsPageParam> cmsPageParams = new ArrayList<>();
        CmsPageParam cmsPageParam = new CmsPageParam();
        cmsPageParam.setPageParamName("param1");
        cmsPageParam.setPageParamValue("value1");
        cmsPageParams.add(cmsPageParam);
        cmsPage.setPageParams(cmsPageParams);
        cmsPageRepository.save(cmsPage);
        System.out.println(cmsPage);
    }

    /*
     * Optional的优点是:
     *    1、提醒你非空判断。
     *    2、将对象非空检测标准化。
     */
    // 修改
    @Test
    public void testUpdate() {
        // 根据id查询
        Optional<CmsPage> optional = cmsPageRepository.findById("5c4915bdabc6f52589dfb0aa");
        if (optional.isPresent()) {
            CmsPage cmsPage = optional.get();
            cmsPage.setPageName("测试页面111111");
            // 和hibernate不一样，还要再save一次
            cmsPageRepository.save(cmsPage);
        }

    }

    @Test
    public void testFindAllByExample() {

        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        // 条件值对象
        CmsPage cmsPage = new CmsPage();
        //要查询5a751fab6abb5044e0d19ea1站点的页面
        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
//        //设置模板id条件
//        cmsPage.setTemplateId("5ad9a24d68db5239b8fef199");
//        //设置页面别名
//        cmsPage.setPageAliase("轮播");

//        ExampleMatcher.matching()
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        //ExampleMatcher.GenericPropertyMatchers.contains() 包含关键字
//        ExampleMatcher.GenericPropertyMatchers.startsWith()//前缀匹配
        //定义Example
        Example<CmsPage> example = Example.of(cmsPage,exampleMatcher);
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        List<CmsPage> content = all.getContent();
        System.out.println(content);
    }


    @Test
    public void testStringUtils(){
        boolean allBlank = StringUtils.isAllBlank("  ", "");
        System.out.println("allBlank = " + allBlank);
    }

}