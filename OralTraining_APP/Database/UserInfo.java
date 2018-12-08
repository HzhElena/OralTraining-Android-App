package com.sunm0710.freechat.Database;

import java.util.HashMap;
import java.util.Map;

public class UserInfo {
    public static final String TABLE="User";
    public static final String KEY_ID="userid";
    public static final String KEY_name="username";
    public static final String KEY_email="email";
    public static final String KEY_pass="password";
    public static final String KEY_record = "records";
    public  String username = "";
    public  String password = "";
    public  String email = "";
    public Integer userid =null;
    public String records ="";
    public Map<String,Integer> medals = new HashMap<String,Integer>();

    public void add_one_medal(String medal){
        Integer num = medals.remove(medal);
        medals.put(medal,num+1);
    }
    public int get_medal(String medal){
        return medals.get(medal);
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setUserid(int id)
    {
        userid = id;
    }
}
