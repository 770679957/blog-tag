package com.waylau.spring.boot.blog.condition;

public class UserCondition {


    private Long id; // 实体一个唯一标识


    private String name; //昵称


    private String email; //邮箱


    private String username; // 用户账号，用户登录时的唯一标识


    private String password; // 登录时密码


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
