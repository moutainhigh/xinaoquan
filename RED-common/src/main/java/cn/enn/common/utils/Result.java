package cn.enn.common.utils;

import cn.enn.common.exception.ApplicationException;
import io.swagger.annotations.ApiModelProperty;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Result<T> extends Resource<T> {

    public static final Integer SUCCESS = 1;
    public static final Integer FAILURE = 0;

    public static final Integer NONE = -1;

    @ApiModelProperty(value = "返回内容")
    private T content;
    @ApiModelProperty(value = "状态码 成功为1 失败为0")
    private Integer status;
    @ApiModelProperty(value = "错误码 成功为-1 失败为具体错误码")
    private int errorCode;
    @ApiModelProperty(value = "错误信息，成功是该字段为空")
    private String errorMsg;

    public static <T> Result<T> rstSuccess() {
        return new Result<T>(null, SUCCESS, NONE, null);
    }

    public static <T> Result<T> rstSuccess(T content) {
        return new Result<T>(content, SUCCESS, NONE, null);
    }

    public static <T> Result<T> fail(ApplicationException e) {
        return new Result<T>(null, FAILURE, e.getCode(), e.getMessage());
    }

    public static <T> Result<T> fail(int errorCode, String errorMsg) {
        return new Result<T>(null, FAILURE, errorCode, errorMsg);
    }

    public static <T> Result<T> fail(ErrorConstants ec) {
        return new Result<T>(null, FAILURE, ec.getCode(), ec.getMessage());
    }

    private Result(T content, Integer status, int errorCode, String errorMsg) {
        this.content = content;
        this.status = status;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    /**
     * Creates an empty {@link org.springframework.hateoas.Resource}.
     */
    public Result() {
        super(null);
    }
    @Override
    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
