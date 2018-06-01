package com.service.sos.alpha.login;

public class UserAccount extends com.service.sos.alpha.chat.model.User {
    private String name;
    private String email;
    private String avata;
    private String phoneNum;
    private String NIC;
    private String account_type;

    public UserAccount(){
    }
    protected UserAccount(String name, String email, String phoneNum, String NIC) {
        this.email = email;
        this.name = name;
        this.phoneNum = phoneNum;
        this.NIC= NIC;
        this.avata="default";
        this.account_type="Friend";

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setNIC(String NIC) {
        this.NIC = NIC;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }


    public String getAvata() {
        return avata;
    }

    public void setAvata(String avata) {
        this.avata = avata;
    }
}
