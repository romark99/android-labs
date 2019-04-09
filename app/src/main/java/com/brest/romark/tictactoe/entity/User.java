package com.brest.romark.tictactoe.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User implements Parcelable {

    @PrimaryKey
    private long id;

    private String login;

    private String avatar_url;

    private String html_url;

    private long date;

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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public User() {
    }

    private User(Parcel in) {
        id = in.readLong();
        login = in.readString();
        avatar_url = in.readString();
        html_url = in.readString();
        date = in.readLong();
    }

    private static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", id=" + id +
                ", avatar_url='" + avatar_url + '\'' +
                ", html_url='" + html_url + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(login);
        parcel.writeString(avatar_url);
        parcel.writeString(html_url);
        parcel.writeLong(date);
    }
}
