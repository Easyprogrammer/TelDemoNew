package com.example.kimo.axdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

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


public class SetBFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<Map<String, Object>> bindIds;

    public static SetBFragment newInstance() {
        SetBFragment fragment = new SetBFragment();
        return fragment;
    }

    public SetBFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListDataSave listDataSave = new ListDataSave(getContext(), "AxDemo");
        bindIds = listDataSave.getDataList("bindIds");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setb, container, false);
        view.setFocusableInTouchMode(true);
        view.setFocusable(true);
        view.requestFocus();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //设置垂直滚动，也可以设置横向滚动
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //另外两种显示模式
        //  mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2)); Grid视图
        //  mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL)); 这里用线性宫格显示 类似于瀑布流

        //RecyclerView设置布局管理器
        mRecyclerView.setLayoutManager(layoutManager);
        //RecyclerView设置Adapter

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), bindIds);
        mRecyclerView.setAdapter(recyclerViewAdapter);
    }

}
