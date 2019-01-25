package cn.x5456.xc.domain.media.response;

import cn.x5456.xc.domain.media.MediaFile;
import cn.x5456.xc.domain.media.MediaVideoCourse;
import cn.x5456.xc.model.response.ResponseResult;
import cn.x5456.xc.model.response.ResultCode;
import lombok.Data;
import lombok.ToString;

/**
 * Created by admin on 2018/3/5.
 */
@Data
@ToString
public class MediaCourseResult extends ResponseResult {
    public MediaCourseResult(ResultCode resultCode, MediaVideoCourse mediaVideoCourse) {
        super(resultCode);
        this.mediaVideoCourse = mediaVideoCourse;
    }

    public MediaCourseResult(){

    }

    MediaFile mediaVideo;
    MediaVideoCourse mediaVideoCourse;
}
