package online.chat.network.base;

public class BaseResponse {
    private String message;
    private Integer errorCode;
    private boolean success;
    private Object data;

    public BaseResponse(String message, Integer errorCode, boolean success, Object data) {
        this.message = message;
        this.errorCode = errorCode;
        this.success = success;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}