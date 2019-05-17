package com.brest.romark.tictactoe.entity;

import java.util.ArrayList;

public class UserView {

    private long id;

    private String login;

    private String avatar_url;

    private String html_url;

    private String repos_url;

    private String name;

    private ArrayList<Repo> repos;

    private String email;

    private String location;

    private String company;

    private String blog;

    private long followers;

    private long following;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Repo> getRepos() {
        return repos;
    }

    public void setRepos(ArrayList<Repo> repos) {
        this.repos = repos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public long getFollowers() {
        return followers;
    }

    public void setFollowers(long followers) {
        this.followers = followers;
    }

    public long getFollowing() {
        return following;
    }

    public void setFollowing(long following) {
        this.following = following;
    }

    public String getRepos_url() {
        return repos_url;
    }

    public UserView() {
    }

    @Override
    public String toString() {
        return "UserView{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                ", html_url='" + html_url + '\'' +
                ", repos_url='" + repos_url + '\'' +
                ", name='" + name + '\'' +
                ", repos=" + repos +
                ", email='" + email + '\'' +
                ", location='" + location + '\'' +
                ", company='" + company + '\'' +
                ", blog='" + blog + '\'' +
                ", followers=" + followers +
                ", following=" + following +
                '}';
    }
}
