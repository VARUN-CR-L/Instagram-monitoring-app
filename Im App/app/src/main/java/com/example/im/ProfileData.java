package com.example.im;

public class ProfileData {

    private String username;
    private String full_name;
    private String biography;
    private int follower_count;
    private int following_count;
    private String profile_pic_url;
    private boolean is_private;
    private boolean is_verified;

    public ProfileData(String username, String full_name, String biography, int follower_count, int following_count, String profile_pic_url, boolean is_private, boolean is_verified) {
        this.username = username;
        this.full_name = full_name;
        this.biography = biography;
        this.follower_count = follower_count;
        this.following_count = following_count;
        this.profile_pic_url = profile_pic_url;
        this.is_private = is_private;
        this.is_verified = is_verified;
    }

    public ProfileData() {
    }




    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public int getFollower_count() {
        return follower_count;
    }

    public void setFollower_count(int follower_count) {
        this.follower_count = follower_count;
    }

    public int getFollowing_count() {
        return following_count;
    }

    public void setFollowing_count(int following_count) {
        this.following_count = following_count;
    }

    public String getProfile_pic_url() {
        return profile_pic_url;
    }

    public void setProfile_pic_url(String profile_pic_url) {
        this.profile_pic_url = profile_pic_url;
    }

    public boolean isIs_private() {
        return is_private;
    }

    public void setIs_private(boolean is_private) {
        this.is_private = is_private;
    }

    public boolean isIs_verified() {
        return is_verified;
    }

    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }
}


