package com.sunm0710.freechat.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunm0710.freechat.R;

public class WordPracticeFragment extends Fragment {


    public WordPracticeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static WordPracticeFragment newInstance(String param1, String param2) {
        WordPracticeFragment fragment = new WordPracticeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_word_practice, container, false);
    }



}
