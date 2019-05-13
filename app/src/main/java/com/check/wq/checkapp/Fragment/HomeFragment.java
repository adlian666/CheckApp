package com.check.wq.checkapp.Fragment;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.check.wq.checkapp.Activity.ExportActivity;
import com.check.wq.checkapp.Activity.InfoActivity;
import com.check.wq.checkapp.Activity.MyOpenHelper;
import com.check.wq.checkapp.Adapter.HomeRecycleAdapter;
import com.check.wq.checkapp.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    public List <Map <String, Object>> list = new ArrayList <Map <String, Object>>();
    private View view;
    private Button btn_export;
    int i = 2;
    int size,page,lastPageSize;
    private HomeRecycleAdapter homeRecycleAdapter;
    private RecyclerView rc_home_list;
    private RefreshLayout mRefreshLayout;
    SQLiteDatabase db;
    Cursor dt_account;
    MyOpenHelper myOpenHelper;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
         myOpenHelper = new MyOpenHelper(getContext());
         db = myOpenHelper.getReadableDatabase();
         dt_account = db.query("dt_account", null, null, null, null, null, null);
         size = dt_account.getCount();

         page = size/7+1;
        lastPageSize = size%7;

        getData(1);
        btn_export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent = new Intent(getContext(), ExportActivity.class);
                getContext().startActivity(intent);
            }
        });
        return view;

    }

    private void getData(int currentPage) {
        Toast.makeText(getContext(),"共"+size+"条记录!",Toast.LENGTH_LONG).show();
        Cursor dt_account_page;
        dt_account_page = db.query("dt_account", null, null, null, null, null, null,(currentPage-1)*7+","+7);
        if (dt_account_page!= null && dt_account_page.getCount() > 0) {
            while (dt_account_page.moveToNext()) {
                Map <String, Object> map = new HashMap <>();
                String id,check_time, class_number, drill_pro, drill_no, machine_model, machine_code,
                        drill_d, drill_, drill_depth, fk_depth, check_result, builder,
                        monitor, checker;
                id = dt_account_page.getString(0);
                check_time = dt_account_page.getString(2);
                class_number = dt_account_page.getString(3);
                drill_pro = dt_account_page.getString(4);
                drill_no = dt_account_page.getString(5);
                machine_model = dt_account_page.getString(6);
                machine_code = dt_account_page.getString(7);
                drill_d = dt_account_page.getString(8);
                drill_ = dt_account_page.getString(9);
                drill_depth = dt_account_page.getString(10);
                fk_depth = dt_account_page.getString(11);
                check_result = dt_account_page.getString(12);
                builder = dt_account_page.getString(13);
                monitor = dt_account_page.getString(14);
                checker = dt_account_page.getString(15);
                map.put("id", id);
                map.put("check_time", check_time);
                map.put("class_number", class_number);
                map.put("drill_pro", drill_pro);
                map.put("drill_no", drill_no);
                map.put("machine_model", machine_model);
                map.put("machine_code", machine_code);
                map.put("drill_d", drill_d);
                map.put("drill_", drill_);
                map.put("drill_depth", drill_depth);
                map.put("fk_depth", fk_depth);
                map.put("check_result", check_result);
                map.put("builder", builder);
                map.put("monitor", monitor);
                map.put("checker", checker);
                list.add(map);



            }
        }
        Message msg = new Message();
        msg.what = 1;
        handler.sendMessage(msg);

    }
    private void onLoad(int currentPage) {
        Cursor dt_account_page;
        dt_account_page = db.query("dt_account", null, null, null, null, null, null,(currentPage-1)*7+","+7);
        if (dt_account_page!= null && dt_account_page.getCount() > 0) {
            while (dt_account_page.moveToNext()) {
                Map <String, Object> map = new HashMap <>();
                String id,check_time, class_number, drill_pro, drill_no, machine_model, machine_code,
                        drill_d, drill_, drill_depth, fk_depth, check_result, builder,
                        monitor, checker;
                id = dt_account_page.getString(0);
                check_time = dt_account_page.getString(2);
                class_number = dt_account_page.getString(3);
                drill_pro = dt_account_page.getString(4);
                drill_no = dt_account_page.getString(5);
                machine_model = dt_account_page.getString(6);
                machine_code = dt_account_page.getString(7);
                drill_d = dt_account_page.getString(8);
                drill_ = dt_account_page.getString(9);
                drill_depth = dt_account_page.getString(10);
                fk_depth = dt_account_page.getString(11);
                check_result = dt_account_page.getString(12);
                builder = dt_account_page.getString(13);
                monitor = dt_account_page.getString(14);
                checker = dt_account_page.getString(15);
                map.put("id", id);
                map.put("check_time", check_time);
                map.put("class_number", class_number);
                map.put("drill_pro", drill_pro);
                map.put("drill_no", drill_no);
                map.put("machine_model", machine_model);
                map.put("machine_code", machine_code);
                map.put("drill_d", drill_d);
                map.put("drill_", drill_);
                map.put("drill_depth", drill_depth);
                map.put("fk_depth", fk_depth);
                map.put("check_result", check_result);
                map.put("builder", builder);
                map.put("monitor", monitor);
                map.put("checker", checker);
                list.add(map);
            }
        }
    }
    private void initView( View view ) {
        btn_export = view.findViewById(R.id.btn_export);
        rc_home_list = view.findViewById(R.id.rc_home_list);
        mRefreshLayout = view.findViewById(R.id.refreshLayout);
       // mRefreshLayout.setEnableScrollContentWhenLoaded(true);
    }
    public Handler handler = new Handler() {

        @Override
        public void handleMessage( Message msg ) {
            switch (msg.what) {
                case 1:
                    ///添加分割线
                    homeRecycleAdapter = new HomeRecycleAdapter(getActivity(), list);
                    rc_home_list.setAdapter(homeRecycleAdapter);
                    //设置布局显示格式
                    rc_home_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

                    mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

                        public void onRefresh( RefreshLayout refreshlayout ) {

                            list.clear();
                            i = 2;
                            getData(1);
                            refreshlayout.finishRefresh();//结束刷新
                            refreshlayout.finishLoadmore();
                            homeRecycleAdapter.notifyDataSetChanged();

                        }
                    });
//加载更多
                    mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
                        @Override
                        public void onLoadmore( RefreshLayout refreshlayout ) {

                           if(i<=page){
                               onLoad(i);
                               refreshlayout.finishRefresh();//结束刷新
                               refreshlayout.finishLoadmore();
                               i++;
                               homeRecycleAdapter.notifyDataSetChanged();
                           }else{
                               Toast.makeText(getContext(),"已经是最后一页了",Toast.LENGTH_SHORT).show();
                               refreshlayout.finishRefresh();//结束刷新
                               refreshlayout.finishLoadmore();

                           }


                        }
                    });
                      //rc_home_list.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
                    break;
            }
        }
    };

}
