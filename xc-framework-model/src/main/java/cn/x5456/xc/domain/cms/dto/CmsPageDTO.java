package cn.x5456.xc.domain.cms.dto;

import cn.x5456.xc.domain.cms.CmsPageParam;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 一个dto就是一个完整的对象，基本上和entity相同
 * @author x5456
 */
@Data
public class CmsPageDTO {

    //站点ID
    private String siteId;
    //页面ID
    private String pageId;
    //页面名称
    private String pageName;
    //别名
    private String pageAliase;
    //访问地址
    private String pageWebPath;
    //参数
    private String pageParameter;
    //物理路径
    private String pagePhysicalPath;
    //类型（静态/动态）
    private String pageType;
    //页面模版
    private String pageTemplate;
    //页面静态化内容
    private String pageHtml;
    //状态
    private String pageStatus;
    //创建时间
    private Date pageCreateTime;
    //模版id
    private String templateId;
    //参数列表
    private List<CmsPageParam> pageParams;
    //模版文件Id
//    private String templateFileId;
    //静态文件Id
    private String htmlFileId;
    //数据Url
    private String dataUrl;

}
