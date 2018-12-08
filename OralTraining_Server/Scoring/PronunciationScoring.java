package Scoring;

import java.io.File;  
import java.io.FileInputStream;  
import java.io.IOException;  
import java.util.ArrayList;  
import java.util.Scanner;

import javax.swing.JLabel;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


import com.iflytek.cloud.speech.DataUploader;  
import com.iflytek.cloud.speech.LexiconListener;  
import com.iflytek.cloud.speech.RecognizerListener;  
import com.iflytek.cloud.speech.RecognizerResult;  
import com.iflytek.cloud.speech.Setting;  
import com.iflytek.cloud.speech.SpeechConstant;  
import com.iflytek.cloud.speech.SpeechError;  
import com.iflytek.cloud.speech.SpeechListener;  
import com.iflytek.cloud.speech.SpeechRecognizer;  
import com.iflytek.cloud.speech.SpeechSynthesizer;  
import com.iflytek.cloud.speech.SpeechUtility;  
import com.iflytek.cloud.speech.SynthesizeToUriListener;  
import com.iflytek.cloud.speech.UserWords;
//import com.iflytek.util.DebugLog;  
  
public class PronunciationScoring {  
  
    private static final String APPID = "58fe06fb";  

    private StringBuffer mResult = new StringBuffer();  
      
    private int maxWaitTime = 500;  
    private int perWaitTime = 100;  
    private int maxQueueTimes = 3;  
    private String fileName = "";  
      
    static {  
        Setting.setShowLog( false );  
        SpeechUtility.createUtility("appid=" + APPID);  
    }  
      
    public String voice2words(String fileName) throws InterruptedException {  
        return voice2words(fileName, true);  
    }  
      
    public String voice2words(String fileName, boolean init) throws InterruptedException {  
        if(init) {  
            maxWaitTime = 500;  
            maxQueueTimes = 3;  
        }  
        if(maxQueueTimes <= 0) {  
            mResult.setLength(0);  
            mResult.append("½âÎöÒì³££¡");  
            return mResult.toString();  
        }  
        this.fileName = fileName;  
          
        return recognize();  
    }  
  
  
    private String recognize() throws InterruptedException {  
        if (SpeechRecognizer.getRecognizer() == null)  
            SpeechRecognizer.createRecognizer();  
        return RecognizePcmfileByte();  
    }  

    private String RecognizePcmfileByte() throws InterruptedException {  
        FileInputStream fis = null;  
        byte[] voiceBuffer = null;  
        try {  
            fis = new FileInputStream(new File(fileName));  
            voiceBuffer = new byte[fis.available()];  
            fis.read(voiceBuffer);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (null != fis) {  
                    fis.close();  
                    fis = null;  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        if (0 == voiceBuffer.length) {  
            mResult.append("no audio avaible!");  
        } else {  
            mResult.setLength(0);  
            SpeechRecognizer recognizer = SpeechRecognizer.getRecognizer();  
            recognizer.setParameter(SpeechConstant.DOMAIN, "iat");  
            recognizer.setParameter(SpeechConstant.LANGUAGE, "en_us");  
            recognizer.setParameter(SpeechConstant.AUDIO_SOURCE, "-1");  
            recognizer.setParameter( SpeechConstant.RESULT_TYPE, "json" );  
            recognizer.startListening(recListener);  
            ArrayList<byte[]> buffers = splitBuffer(voiceBuffer,  
                    voiceBuffer.length, 4800);  
            for (int i = 0; i < buffers.size(); i++) {  
                recognizer.writeAudio(buffers.get(i), 0, buffers.get(i).length);  
            }  
            while(recognizer.isListening()) {  
            }  
            recognizer.stopListening();  
            
        }  
        return mResult.toString();  
    }  
  
    private ArrayList<byte[]> splitBuffer(byte[] buffer, int length, int spsize) {  
        ArrayList<byte[]> array = new ArrayList<byte[]>();  
        if (spsize <= 0 || length <= 0 || buffer == null  
                || buffer.length < length)  
            return array;  
        int size = 0;  
        while (size < length) {  
            int left = length - size;  
            if (spsize < left) {  
                byte[] sdata = new byte[spsize];  
                System.arraycopy(buffer, size, sdata, 0, spsize);  
                array.add(sdata);  
                size += spsize;  
            } else {  
                byte[] sdata = new byte[left];  
                System.arraycopy(buffer, size, sdata, 0, left);  
                array.add(sdata);  
                size += left;  
            }  
        }  
        return array;  
    }  
  
    private RecognizerListener recListener = new RecognizerListener() {  
  
        public void onBeginOfSpeech() { }  
  
        public void onEndOfSpeech() { }  
  
        public void onVolumeChanged(int volume) { }  
  
        public void onResult(RecognizerResult result, boolean islast) {  

            StringBuffer ret = new StringBuffer();  
            try {  
                JSONTokener tokener = new JSONTokener(result.getResultString());  
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
            String str =  ret.toString(); 
            
            mResult.append(str); 
        }  
  
        public void onError(SpeechError error) {  
            try {  
                voice2words(fileName);  
                maxQueueTimes--;  
            } catch (InterruptedException e) {  
                Thread.currentThread().interrupt();  
                throw new RuntimeException(e);  
            }  
        }  
  
        public void onEvent(int eventType, int arg1, int agr2, String msg) { }  
  
    };  
  
}  