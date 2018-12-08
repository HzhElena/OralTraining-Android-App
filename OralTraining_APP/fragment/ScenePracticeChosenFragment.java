package com.sunm0710.freechat.fragment;

import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.menu.ActionMenuItemView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.sunm0710.freechat.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.jar.Manifest;


public class ScenePracticeChosenFragment extends Fragment {
    static int ID;
    File myFile;
    MediaRecorder mediaRecorder;
    ImageButton record_btn;
    public ScenePracticeChosenFragment() {
        // Required empty public constructor
    }

    public static ScenePracticeChosenFragment newInstance(int sceneID) {
        ScenePracticeChosenFragment fragment = new ScenePracticeChosenFragment();
        ID = sceneID;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_scene_practice_chosen, container, false);

        record_btn = (ImageButton) view.findViewById(R.id.btn_record_scene);
        record_btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(ContextCompat.checkSelfPermission(getContext(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    if(shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Toast.makeText(getContext(),"please!!!!",Toast.LENGTH_SHORT).show();
                    }
                    requestPermissions(new String[] {android.Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

                }
                if(ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    if(shouldShowRequestPermissionRationale(android.Manifest.permission.RECORD_AUDIO)) {
                        Toast.makeText(getContext(),"please!!!",Toast.LENGTH_SHORT).show();
                    }
                    requestPermissions(new String[] {android.Manifest.permission.RECORD_AUDIO},1);
                }
                if (!Environment.getExternalStorageState().equals(
                        android.os.Environment.MEDIA_MOUNTED)) {
                    Toast.makeText(getContext(), "请监测内存卡", Toast.LENGTH_SHORT).show();
                    return true;
                }
                try {
                    myFile = File.createTempFile("Sample", ".amr",
                            Environment.getExternalStorageDirectory());
                    mediaRecorder = new MediaRecorder();
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    mediaRecorder.setOutputFile(myFile.getAbsolutePath());
                    mediaRecorder.prepare();
                    mediaRecorder.start();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
                    Date date = new Date(System.currentTimeMillis());
                    Toast.makeText(getContext(),"start record"+dateFormat.format(date)+"\n"+myFile.getAbsolutePath(),Toast.LENGTH_SHORT).show();

                }catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(getContext(),"start record",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        record_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(myFile != null) {
                        Toast.makeText(getContext(),"stop record",Toast.LENGTH_SHORT).show();
                        mediaRecorder.stop();
                        mediaRecorder.release();
                        mediaRecorder = null;
                    }
                }
                //if(event.getAction() == MotionEvent.ACTION_BUTTON_PRESS)
                return false;
            }
        });
        return view;
    }

    private void doRecord(View v) {
        try {
            MediaRecorder recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile(getContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC).getPath()+"/scene_records");
            recorder.prepare();
            recorder.start();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}
