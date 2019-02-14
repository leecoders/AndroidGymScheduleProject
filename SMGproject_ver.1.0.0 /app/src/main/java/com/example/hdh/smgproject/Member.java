package com.example.hdh.smgproject;

import android.media.Image;
import android.widget.ImageView;

/**
 * Created by hwangdahyeon on 2018. 5. 21..
 */

public class Member {

    String imageLink;
    String MemberName;
    String MemberMajor;
    String MemberEmail;

    public Member(String imageLink, String memberName, String memberMajor, String memberEmail) {
        this.imageLink = imageLink;
        MemberName = memberName;
        MemberMajor = memberMajor;
        MemberEmail = memberEmail;
    }

    public String getImage() {
        return imageLink;
    }

    public void setImage(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getMemberName() {
        return MemberName;
    }

    public void setMemberName(String memberName) {
        MemberName = memberName;
    }

    public String getMemberMajor() {
        return MemberMajor;
    }

    public void setMemberMajor(String memberMajor) {
        MemberMajor = memberMajor;
    }

    public String getMemberEmail() {
        return MemberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        MemberEmail = memberEmail;
    }
}
