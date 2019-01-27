package cn.x5456.xc.manage_cms.service.impl;

import cn.x5456.xc.domain.cms.CmsConfig;
import cn.x5456.xc.domain.cms.dto.CmsConfigDTO;
import cn.x5456.xc.manage_cms.dao.CmsConfigRepository;
import cn.x5456.xc.manage_cms.dozer.IGenerator;
import cn.x5456.xc.manage_cms.service.CmsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author x5456
 */
@Service
public class CmsConfigServiceImpl implements CmsConfigService {

    @Autowired
    private CmsConfigRepository cmsConfigRepository;

    @Autowired
    private IGenerator dozer;

    @Override
    public CmsConfigDTO findCmsConfigById(String id) {

        CmsConfigDTO cmsConfigDTO = null;

        Optional<CmsConfig> optional = cmsConfigRepository.findById(id);

        if (optional.isPresent()){
            CmsConfig cmsConfig = optional.get();
            cmsConfigDTO = dozer.convert(cmsConfig, CmsConfigDTO.class);
        }

        return cmsConfigDTO;
    }
}
