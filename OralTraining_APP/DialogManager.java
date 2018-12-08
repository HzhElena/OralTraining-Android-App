package com.sunm0710.freechat;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DialogManager {
    private Context mContext;
    private Dialog mDialog;
    private ImageView mIcon;
    private ImageView mVoice;
    private TextView mLable;

    public DialogManager(Context context) {
        mContext = context;
    }

    public void showRecordingDialog() {
        mDialog.show();
    }

    public void recording() {
        if (mDialog != null && mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.VISIBLE);
            mLable.setVisibility(View.VISIBLE);

            //mIcon.setImageResource(R.mipmap.recorder);
            mLable.setText("手指上滑，取消发送");
        }
    }

    public void wantToCancel() {
        // TODO Auto-generated method stub
        if (mDialog != null && mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLable.setVisibility(View.VISIBLE);

            //mIcon.setImageResource(R.mipmap.cancel);
            mLable.setText("松开手指，取消发送");
        }
    }

    public void tooShort() {
        // TODO Auto-generated method stub
        if (mDialog != null && mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLable.setVisibility(View.VISIBLE);

            //mIcon.setImageResource(R.mipmap.voice_too_short);
            mLable.setText("录音时间过短");
        }
    }

    public void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    public void updateVoiceLevel(int level) {
        if (mDialog != null && mDialog.isShowing()) {
            // name - The name of the desired resource.
            // defType - Optional default resource type to find, if "type/" is not included in the name. Can be null to require an explicit type.
            // defPackage - Optional default package to find, if "package:" is not included in the name. Can be null to require an explicit package.
            int voiceResourceId = mContext.getResources().getIdentifier("v" + level, "mipmap", mContext.getPackageName());
            mVoice.setImageResource(voiceResourceId);
        }
    }

}
