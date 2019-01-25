package cn.x5456.xc.domain.media.response;


import cn.x5456.xc.domain.media.MediaFile;
import cn.x5456.xc.model.response.ResponseResult;
import cn.x5456.xc.model.response.ResultCode;
import lombok.Data;

/**
 * Created by mrt on 2018/3/31.
 */
@Data
public class MediaFileResult extends ResponseResult {
    MediaFile mediaFile;

    public MediaFileResult(){}

    public MediaFileResult(ResultCode resultCode, MediaFile mediaFile) {
        super(resultCode);
        this.mediaFile = mediaFile;
    }
}
