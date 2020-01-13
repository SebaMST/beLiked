package com.example.abesg.beliked.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by abesg on 28/12/2019.
 */

public class User implements Parcelable {

    private String user_id;
    private String phone_number;
    private String email;
    private String username;
    private String fullname;

    public User(String user_id, String phone_number, String email, String username,String fullname) {
        this.user_id = user_id;
        this.phone_number = phone_number;
        this.email = email;
        this.username = username;
        this.fullname=fullname;
    }

    public User() {

    }


    protected User(Parcel in) {
        user_id = in.readString();
        phone_number = in.readString();
        email = in.readString();
        username = in.readString();
        fullname=in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
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


    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", fullname=' " + fullname +'\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeString(phone_number);
        dest.writeString(email);
        dest.writeString(username);
        dest.writeString(fullname
        );
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
