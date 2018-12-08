package com.sunm0710.freechat;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;


import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.sunm0710.freechat.R;

public class NetUtil {
    final static String root = "http://10.182.96.192:8080/App/";

    public static String LoginGET(String username,String password)
    {
        HttpURLConnection connection = null;
        try{
            String data = "user="+username+"&pass="+password;
            String rootpath = root+"Login?"+data;
            URL url = new URL(rootpath);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setChunkedStreamingMode(0);
            connection.setDoInput(true);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(5000);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            String state;
            String json = getStringFromInputStream(inputStream);
            JSONObject jsonObject = new JSONObject(json);
            int c = jsonObject.getInt("loginState");
            switch (c)
            {
                case 1:
                    state = "登录成功！";
                    break;
                case -1:
                    state = "用户不存在！";
                    break;
                case -2:
                    state = "密码错误!";
                    break;
                default:
                    state = "";
                    break;
            }
            return state;

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error in Login",e.toString());
        } finally {
            if(connection != null) connection.disconnect();
        }

        return null;
    }

    public static String RegisterGET(String username,String email,String password)
    {
        HttpURLConnection connection = null;
        try {
            String data = "username="+username+"&password="+password+"&email="+email;
            String rootpath = root+"Register?"+data;
            URL url = new URL(rootpath);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(5000);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            String state;
            String json = getStringFromInputStream(inputStream);
            JSONObject jsonObject = new JSONObject(json);
            int c = jsonObject.getInt("registerState");
            switch(c)
            {
                case 1:
                    state = "注册成功！";
                    String imageurl = jsonObject.getString("image");
                    byte[] decode = Base64.decode(imageurl,Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
                    saveBitmap(bitmap,"portrait");
                    break;
                case -1:
                    state = "注册失败！";
                    break;
                default:
                    state = "";
                    break;
            }
            return state;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(connection != null) connection.disconnect();
        }
        return null;
    }


    public static String getStringFromInputStream (InputStream inputStream) throws Exception
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while((len=inputStream.read(buffer))!=-1){
            outputStream.write(buffer, 0, len);
        }
        inputStream.close();
        return outputStream.toString();
    }

    private static void saveBitmap(Bitmap bitmap,String picname) {
        try {
            String path = Environment.getExternalStorageDirectory().getPath()
                    +"/"+picname+".jpg";
            Log.d("linc","path is "+path);
            OutputStream stream = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
            stream.close();
            Log.e("linc","jpg okay!");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("linc","failed: "+e.getMessage());
        }
    }
}
