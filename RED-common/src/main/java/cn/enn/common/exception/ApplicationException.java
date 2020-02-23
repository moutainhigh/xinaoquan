package cn.enn.common.exception;


import cn.enn.common.utils.ErrorConstants;
import cn.enn.common.utils.ErrorMessageCache;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

/**
 * @Author: tiantao
 * @Date: 2019/10/11 9:11 AM
 * @Version 1.0
 */
public class ApplicationException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -3684082650707387064L;

    /**
     * 异常错误码
     */
    private int code;

    /**
     * 异常错误消息
     */
    private String message;

    public ApplicationException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public ApplicationException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ApplicationException(ErrorMessageCache.MeteringError error) {
        super(error.getMessage());
        this.code = error.getCode();
        this.message = error.getMessage();
    }

    public ApplicationException(ErrorMessageCache.MeteringError error, Throwable cause) {
        super(error.getMessage(), cause);
        this.code = error.getCode();
        this.message = error.getMessage();
    }

    public ApplicationException(ErrorConstants error) {
        super(error.getMessage());
        this.code = error.getCode();
        this.message = error.getMessage();
    }

    public ApplicationException(ErrorConstants error, Object... argArray) {
        super(error.getMessage());
        this.code = error.getCode();
        this.message = StringUtils.format(error.getMessage(), argArray);
    }

    public ApplicationException(ErrorConstants error, Throwable cause) {
        super(error.getMessage(), cause);
        this.code = error.getCode();
        this.message = error.getMessage();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
