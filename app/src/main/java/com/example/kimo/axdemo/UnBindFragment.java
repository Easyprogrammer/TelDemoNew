package com.example.kimo.axdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * ClassName:${TYPE_NAME} <br/>
 * Function: ${TODO} ADD FUNCTION. <br/>
 * Reason:   ${TODO} ADD REASON. <br/>
 * Date:     2017/4/16 12:34 <br/>
 *
 * @author 76dgs02
 * @see
 * @since JDK 1.6
 */


public class UnBindFragment extends Fragment {
    public static UnBindFragment newInstance() {
        UnBindFragment fragment = new UnBindFragment();
        return fragment;
    }

    public UnBindFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unbind, container, false);
        return view;
    }

}
