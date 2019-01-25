package cn.x5456.xc.domain.cms.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author x5456
 */
@Data
public class QueryPageRequest {

    @ApiModelProperty("站点id")
    private String siteId;

    @ApiModelProperty("页面ID")
    private String pageId;

    @ApiModelProperty("页面名称")
    private String pageName;

    @ApiModelProperty("别名")
    private String pageAliase;

    @ApiModelProperty("模版id")
    private String templateId;

}
