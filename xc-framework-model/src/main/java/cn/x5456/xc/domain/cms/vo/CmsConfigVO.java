package cn.x5456.xc.domain.cms.vo;

import cn.x5456.xc.domain.cms.CmsConfigModel;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author x5456
 */
@Data
@ToString
public class CmsConfigVO {

    private String id;
    private String name;
    private List<CmsConfigModel> model;

}
