package com.sunm0710.freechat;

import android.media.MediaRecorder;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


public class AudioManager {
    private String mDirString;

    private boolean isPrepared;
    private String mCurrentFilePath;
    private MediaRecorder mRecorder;

    private static AudioManager mInstance;

    private AudioManager(String dir) {
        mDirString = dir;
    }

    public static AudioManager getInstance(String dir) {
        if (mInstance == null) {
            synchronized (AudioManager.class) {
                if (mInstance == null) {
                    mInstance = new AudioManager(dir);
                }
            }
        }
        return mInstance;
    }


    public interface AudioStageListener {
        void wellPrepared();
    }

    private AudioStageListener mListener;

    public void setOnAudioStageListener(AudioStageListener listener) {
        mListener = listener;
    }

    public void prepareAudio() {
        try {
            isPrepared = false;

            // path - the path to be used for the file.
            File dir = new File(mDirString);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName = generalFileName();
            File file = new File(dir, fileName);

            mCurrentFilePath = file.getAbsolutePath();

            mRecorder = new MediaRecorder();

            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);

            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            mRecorder.setOutputFile(mCurrentFilePath);
            mRecorder.prepare();
            mRecorder.start();

            isPrepared = true;

            if (mListener != null) {
                mListener.wellPrepared();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
     randomly generates a filename 
     */
    private String generalFileName() {
        return UUID.randomUUID().toString() + ".amr";
    }

    public int getVoiceLevel(int maxLevel) {
        if (isPrepared) {
            try {
                return maxLevel * mRecorder.getMaxAmplitude() / 32768 + 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 1;
    }


    public void release() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }


    public void cancel() {
        release();
        if (mCurrentFilePath != null) {
            File file = new File(mCurrentFilePath);
            file.delete();
            mCurrentFilePath = null;
        }
    }

    public String getCurrentFilePath() {
        return mCurrentFilePath;
    }
}
