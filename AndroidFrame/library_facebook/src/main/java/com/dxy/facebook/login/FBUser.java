package com.dxy.facebook.login;


/**
 * @author duxueyang
 * 从facebook中获取的用户信息
 */
public class FBUser {
    //图像
    private final String picture;
    //名字
    private final String name;
    //id
    private final String id;
    //email
    private final String email;


    public FBUser(String picture, String name,
                  String id, String email) {
        this.picture = picture;
        this.name = name;
        this.id = id;
        this.email = email;
    }



    public String getPicture() {
        return picture;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

}
