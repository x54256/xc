package cn.x5456.xc.domain.cms.request;

import cn.x5456.xc.domain.cms.CmsPageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author x5456
 */
@Data
public class SavePageRequest {

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

    @ApiModelProperty("访问地址")
    private String pageWebPath;

    @ApiModelProperty("参数")
    private String pageParameter;

    @ApiModelProperty("物理路径")
    private String pagePhysicalPath;

    @ApiModelProperty("类型（静态/动态）")
    private String pageType;

    @ApiModelProperty("页面模版")
    private String pageTemplate;

    @ApiModelProperty("页面静态化内容")
    private String pageHtml;

    @ApiModelProperty("状态")
    private String pageStatus;

    @ApiModelProperty("参数列表")
    private List<CmsPageParam> pageParams;

//    @ApiModelProperty("模版文件Id")
//    private String templateFileId;

    @ApiModelProperty("静态文件Id")
    private String htmlFileId;

    @ApiModelProperty("数据Url")
    private String dataUrl;



}
