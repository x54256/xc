package cn.x5456.xc.domain.cms.dto;

import cn.x5456.xc.domain.cms.CmsConfigModel;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author x5456
 */
@Data
@ToString
public class CmsConfigDTO {

    private String id;
    private String name;
    private List<CmsConfigModel> model;

}
