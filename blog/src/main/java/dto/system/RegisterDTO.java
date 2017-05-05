package dto.system;

/**
 * 注册使用对象
 * Created by scott on 2017/5/5.
 */
public class RegisterDTO {
    private String rNickname;

    private String rAccount;

    private String rPassword;

    private String rRetypePassword;

    private String rEmail;

    private String roleId;

    public String getrNickname() {
        return rNickname;
    }

    public void setrNickname(String rNickname) {
        this.rNickname = rNickname;
    }

    public String getrAccount() {
        return rAccount;
    }

    public void setrAccount(String rAccount) {
        this.rAccount = rAccount;
    }

    public String getrPassword() {
        return rPassword;
    }

    public void setrPassword(String rPassword) {
        this.rPassword = rPassword;
    }

    public String getrRetypePassword() {
        return rRetypePassword;
    }

    public void setrRetypePassword(String rRetypePassword) {
        this.rRetypePassword = rRetypePassword;
    }

    public String getrEmail() {
        return rEmail;
    }

    public void setrEmail(String rEmail) {
        this.rEmail = rEmail;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
