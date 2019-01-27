package cn.x5456.xc.domain.cms.response;

import cn.x5456.xc.domain.cms.vo.CmsConfigVO;
import cn.x5456.xc.model.response.ResponseResult;
import cn.x5456.xc.model.response.ResultCode;
import lombok.Data;

@Data
public class CmsConfigResult extends ResponseResult {
    CmsConfigVO cmsConfigVO;
    public CmsConfigResult(ResultCode resultCode, CmsConfigVO cmsConfigVO) {
        super(resultCode);
        this.cmsConfigVO = cmsConfigVO;
    }
}
