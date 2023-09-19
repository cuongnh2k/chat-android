package online.chat.network.request.device;

public class ActiveDeviceReq {
    private String code;

    public ActiveDeviceReq(String code) {
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
        return "ActiveDeviceReq{" +
                "code='" + code + '\'' +
                '}';
    }
}
