package com.brest.romark.tictactoe.entity;

import java.math.BigDecimal;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//@Entity
public class User {

    @PrimaryKey
    private BigDecimal id;

    private String login;

    private String avatar_url;

    private String html_url;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", id=" + id +
                ", avatar_url='" + avatar_url + '\'' +
                ", html_url='" + html_url + '\'' +
                '}';
    }
}
