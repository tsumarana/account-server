package com.zjnu.pojo;

public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String idCard;
    private String name;
    private String vip;
    private String img;
    private String token;
    private String status;
    private String success;
    private String fail;

    public String getFail() {
        return fail;
    }

    public void setFail(String fail) {
        this.fail = fail;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public User(Integer id, String username, String password, String email, String phone, String idCard, String name, String vip, String img, String token, String status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.idCard = idCard;
        this.name = name;
        this.vip = vip;
        this.img = img;
        this.token = token;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", idCard='" + idCard + '\'' +
                ", name='" + name + '\'' +
                ", vip='" + vip + '\'' +
                ", img='" + img + '\'' +
                ", token='" + token + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public User() {
    }

    public User(Integer id, String username, String password, String email, String phone, String idCard, String name, String vip, String img, String token) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.idCard = idCard;
        this.name = name;
        this.vip = vip;
        this.img = img;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
