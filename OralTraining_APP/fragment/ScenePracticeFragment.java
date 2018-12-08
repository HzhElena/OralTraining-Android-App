package com.sunm0710.freechat.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.sunm0710.freechat.R;
import com.sunm0710.freechat.activity.MainActivity;

import java.util.ArrayList;


public class ScenePracticeFragment extends Fragment {

    RadioGroup scenelist;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private ArrayList<String> list;

    public ScenePracticeFragment() {
        // Required empty public constructor
    }

    public static ScenePracticeFragment newInstance() {
        ScenePracticeFragment fragment = new ScenePracticeFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scene_practice, container, false);
        ((MainActivity)getActivity()).changeTitle(getString(R.string.title_fragment_scene));
        scenelist = (RadioGroup) view.findViewById(R.id.scene_selector);

        addList(scenelist);
        return view;
    }

    public void addList(RadioGroup raw)
    {
        int bklist[] = {R.mipmap.tmp1,R.mipmap.tmp0};
        final int idlist[] = {R.id.scene_0,R.id.scene_1};
        final int num = bklist.length;
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,0,20);

        for(int i = 0;i<num;i++)
        {
            final int sceneID = i;
            RadioButton button = new RadioButton(this.getContext());
            button.setId(idlist[i]);
            button.setBackgroundResource(bklist[i]);
            button.setText("test"+(Integer.toString(i)));
            button.setLayoutParams(params);
            button.setButtonDrawable(null);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.realtabcontent,ScenePracticeChosenFragment.newInstance(sceneID))
                        .commit();
                }
            });
            raw.addView(button);
        }
    }



}
