package online.chat.network.request.user;

public class ActiveUserReq {
    private String code;

    public ActiveUserReq(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ActiveUserReq{" +
                "code='" + code + '\'' +
                '}';
    }
}
