package online.chat.network.request.user;

public class UpdateUserReq {
    private String email;
    private String oldEmail;
    private String password;
    private String oldPassword;
    private String firstName;
    private String lastName;
    private String avatarId;
    private String avatarUrl;

    public UpdateUserReq(String email, String oldEmail, String password, String oldPassword, String firstName, String lastName, String avatarId, String avatarUrl) {
        this.email = email;
        this.oldEmail = oldEmail;
        this.password = password;
        this.oldPassword = oldPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatarId = avatarId;
        this.avatarUrl = avatarUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldEmail() {
        return oldEmail;
    }

    public void setOldEmail(String oldEmail) {
        this.oldEmail = oldEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "UpdateUserReq{" +
                "email='" + email + '\'' +
                ", oldEmail='" + oldEmail + '\'' +
                ", password='" + password + '\'' +
                ", oldPassword='" + oldPassword + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", avatarId='" + avatarId + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
