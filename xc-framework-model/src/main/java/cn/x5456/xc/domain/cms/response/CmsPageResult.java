package cn.x5456.xc.domain.cms.response;

import cn.x5456.xc.domain.cms.vo.CmsPageVO;
import cn.x5456.xc.model.response.ResponseResult;
import cn.x5456.xc.model.response.ResultCode;
import lombok.Data;

@Data
public class CmsPageResult extends ResponseResult {
    CmsPageVO cmsPageVO;
    public CmsPageResult(ResultCode resultCode,CmsPageVO cmsPageVO) {
        super(resultCode);
        this.cmsPageVO = cmsPageVO;
    }
}
