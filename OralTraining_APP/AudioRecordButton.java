package com.sunm0710.freechat;

import android.content.Context;

import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class AudioRecordButton extends Button implements com.sunm0710.freechat.AudioManager.AudioStageListener {

    private com.sunm0710.freechat.AudioManager audioManager;
    private static final int STATE_NORMAL = 1;
    private static final int STATE_RECORDING = 2;
    private static final int STATE_WANT_TO_CANCEL = 3;
    private static final int DISTANCE_Y_CANCEL = 50;
    private int mCurrentState = STATE_NORMAL;

    private boolean isRecording = false;
    private DialogManager mDialogManager;
    private float mTime = 0;

    private boolean mReady;
    private Vibrator vibrator;

    public AudioRecordButton(Context context)
    {
        this(context,null);
    }
    public AudioRecordButton(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        String sdir = attrs.getAttributeValue(0);//the path of the audio records should be placed
        String dir = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC) + sdir;
        audioManager = com.sunm0710.freechat.AudioManager.getInstance(dir);
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startRecording();
                return false;
            }
        });
    }


    public interface AudioFinishRecorderListener {
        void onFinished(float seconds, String filePath);
    }

    private AudioFinishRecorderListener mListener;

    public void setAudioFinishRecorderListener(AudioFinishRecorderListener listener) {
        mListener = listener;
    }

    private Runnable mGetVoiceLevelRunnable = new Runnable() {

        @Override
        public void run() {
            while (isRecording) {
                try {
                    Thread.sleep(100);
                    mTime += 0.1f;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private static final int MSG_AUDIO_PREPARED = 0X110;
    private static final int MSG_VOICE_CHANGE = 0X111;
    private static final int MSG_DIALOG_DISMISS = 0X112;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_AUDIO_PREPARED:
                    mDialogManager.showRecordingDialog();
                    isRecording = true;
                    new Thread(mGetVoiceLevelRunnable).start();
                    break;
                case MSG_VOICE_CHANGE:
                    break;
                case MSG_DIALOG_DISMISS:
                    mDialogManager.dismissDialog();
                    break;
            }
        }
    };

    @Override
    public void wellPrepared() {
        mHandler.sendEmptyMessage(MSG_AUDIO_PREPARED);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                changeState(STATE_RECORDING);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isRecording) {
                    if (wantToCancel(x, y)) {
                        changeState(STATE_WANT_TO_CANCEL);
                    } else {
                        changeState(STATE_RECORDING);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!mReady) {
                    reset();
                    return super.onTouchEvent(event);
                }
                if (!isRecording || mTime < 0.6f) {
                    mDialogManager.tooShort();
                    //mAudioManager.discardRecording();
                    mHandler.sendEmptyMessageDelayed(MSG_DIALOG_DISMISS, 1300);
                } else if (mCurrentState == STATE_RECORDING) {
                    //mAudioManager.stopRecoding();
                    if (mListener != null) {
                        //mListener.onFinished(mTime, mAudioManager.getCurrentFilePath());
                    }
                } else if (mCurrentState == STATE_WANT_TO_CANCEL) {
                    // cancel
                    //mAudioManager.discardRecording();
                }
                reset();
                break;
        }
        return super.onTouchEvent(event);
    }

    private void reset() {
        isRecording = false;
        mDialogManager.dismissDialog();
        changeState(STATE_NORMAL);
        mReady = false;
        mTime = 0;
    }

    private boolean wantToCancel(int x, int y) {
        if (x < 0 || x > getWidth()) {
            return true;
        }
        if (y < -DISTANCE_Y_CANCEL || y > getHeight() + DISTANCE_Y_CANCEL) {
            return true;
        }
        return false;
    }

    private void changeState(int state) {
        if (mCurrentState != state) {
            mCurrentState = state;
            switch (mCurrentState) {
                case STATE_NORMAL:
                    this.setText("按住 说话");
                    break;
                case STATE_RECORDING:
                    this.setText("松开 结束");
                    if (isRecording) {
                        mDialogManager.recording();
                    }
                    break;
                case STATE_WANT_TO_CANCEL:
                    this.setText("松开手指，取消录音");
                    // dialog want to cancel
                    mDialogManager.wantToCancel();
                    break;
            }
        }
    }

    @Override
    public boolean onPreDraw() {
        return false;
    }

}
