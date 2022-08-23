package leonardo.ezio.personal.web.dto;

import java.io.Serializable;

/**
 * @Description :
 * @Author : LeonardoEzio
 * @Date: 2022-08-23 11:12
 */
public class AuthDto implements Serializable{

    private String userName;

    private String passWord;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "AuthDto{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }
}
