package cn.x5456.xc.domain.cms.response;

import cn.x5456.xc.model.response.ResultCode;
import lombok.ToString;

/**
 * 定义成枚举的
 * 好处：可以统一管理
 *
 * 坏处：看的时候需要点一下才能看到具体的信息
 */
@ToString
public enum CmsCode implements ResultCode {
    CMS_ADDPAGE_EXISTS(false,24001,"页面信息已存在！"),
    CMS_GENERATEHTML_DATAURLISNULL(false,24002,"从页面信息中找不到获取数据的url！"),
    CMS_GENERATEHTML_DATAISNULL(false,24003,"根据页面的数据url获取不到数据！"),
    CMS_GENERATEHTML_TEMPLATEISNULL(false,24004,"页面模板为空！"),
    CMS_GENERATEHTML_HTMLISNULL(false,24005,"生成的静态html为空！"),
    CMS_GENERATEHTML_SAVEHTMLERROR(false,24006,"保存静态html出错！"),
    CMS_COURSE_PERVIEWISNULL(false,24007,"预览页面为空！"),

    CMS_INPUT_PARAM_PATH_ERROR(false,24008,"page传入的值<=0，请检查参数是否正确"),
    CMS_INPUT_PARAM_SIZE_ERROR(false,24009,"size传入的值<=0，请检查参数是否正确"),
    CMS_INPUT_PARAMS_ERROR(false,24010,"pageName、siteId、pageWebPath需要全部不为空，请检查请求参数！"),
    CMS_INPUT_PARAM_PATHID_ERROR(false,24011,"数日的pathid为空，请检查请求参数！"),
    CMS_INPUT_PARAMS_PAGEID_ERROR(false,24012,"pageName、siteId、pageWebPath、pathid需要全部不为空，请检查请求参数！"),
    CMS_INPUT_PARAMS_CONFIGID_ERROR(false,24013,"输入的CmsConfigId为空！"),



    CMS_FIND_PAGE_IS_NULL(false,24020,"根据pageId查询到的数据为空，请检查输入参数！"),

    ;



    //是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;
    private CmsCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
