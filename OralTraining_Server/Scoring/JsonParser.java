package Scoring;

import org.json.JSONArray;  
import org.json.JSONObject;  
import org.json.JSONTokener;  
  

public class JsonParser {  
  
    public static String parseIatResult(String json) {  
        StringBuffer ret = new StringBuffer();  
        try {  
            JSONTokener tokener = new JSONTokener(json);  
            JSONObject joResult = new JSONObject(tokener);  
  
            JSONArray words = joResult.getJSONArray("ws");  
            for (int i = 0; i < words.length(); i++) {  

                JSONArray items = words.getJSONObject(i).getJSONArray("cw");  
                JSONObject obj = items.getJSONObject(0);  
                ret.append(obj.getString("w"));  

            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return ret.toString();  
    }  
  
    public static String parseGrammarResult(String json) {  
        StringBuffer ret = new StringBuffer();  
        try {  
            JSONTokener tokener = new JSONTokener(json);  
            JSONObject joResult = new JSONObject(tokener);  
  
            JSONArray words = joResult.getJSONArray("ws");  
            for (int i = 0; i < words.length(); i++) {  
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");  
                for (int j = 0; j < items.length(); j++) {  
                    JSONObject obj = items.getJSONObject(j);  
                    if (obj.getString("w").contains("nomatch")) {   
                        return ret.toString();  
                    }  
                    ret.append(obj.getString("w"));  
                    ret.append(obj.getInt("sc"));  
                    ret.append("n");  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return ret.toString();  
    }  
}  

