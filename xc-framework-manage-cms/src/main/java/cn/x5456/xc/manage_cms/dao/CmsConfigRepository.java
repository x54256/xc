package cn.x5456.xc.manage_cms.dao;

import cn.x5456.xc.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author x5456
 */
public interface CmsConfigRepository extends MongoRepository<CmsConfig, String> {

}
