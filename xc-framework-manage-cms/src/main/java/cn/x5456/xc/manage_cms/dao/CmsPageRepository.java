package cn.x5456.xc.manage_cms.dao;

import cn.x5456.xc.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author x5456
 */
public interface CmsPageRepository extends MongoRepository<CmsPage, String> {

    CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName, String siteId, String pageWebPath);

}
