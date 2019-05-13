package com.check.wq.checkapp.Activity;




import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import com.check.wq.checkapp.Fragment.CheckFragment;
import com.check.wq.checkapp.R;
import com.check.wq.checkapp.Utils.RecycleItemTouchHelper;
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
public class Main1Activity extends Activity {
    public List <Map <String, Object>> list = new ArrayList <Map <String, Object>>();
    private View view;
    private Button btn_export,btn_add;
    int i = 2;
    int size,page,lastPageSize;
    private HomeRecycleAdapter homeRecycleAdapter;
    private RecyclerView rc_home_list;
    private RefreshLayout mRefreshLayout;
    SQLiteDatabase db;
    Cursor dt_account;
    MyOpenHelper myOpenHelper;
    SharedPreferences pref;
    SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mEditor = pref.edit();
        initView();

        btn_export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent = new Intent(Main1Activity.this, ExportActivity.class);
                startActivity(intent);
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent = new Intent(Main1Activity.this, CheckActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        list.clear();
        myOpenHelper = new MyOpenHelper(this);
        db = myOpenHelper.getReadableDatabase();
        dt_account = db.query("dt_account", null, null, null, null, null, "id desc");
        size = dt_account.getCount();
        page = size/30+1;
        lastPageSize = size%30;
        getData(1);
    }
    private void getData(int currentPage) {
        Cursor dt_account_page;
        dt_account_page = db.query("dt_account", null, null, null, null, null, "id desc",(currentPage-1)*30+","+30);
        if (dt_account_page!= null && dt_account_page.getCount() > 0) {
            while (dt_account_page.moveToNext()) {
                Map <String, Object> map = new HashMap <>();
                String id,drill_id,check_time, class_number, drill_pro, drill_no, machine_model, machine_code,
                        drill_d, drill_, drill_depth, fk_depth, check_result, builder,
                        monitor, checker;
                drill_id = dt_account_page.getString(1);
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
                map.put("drill_id", drill_id);
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
        dt_account_page = db.query("dt_account", null, null, null, null, null, "id desc",(currentPage-1)*30+","+30);
        if (dt_account_page!= null && dt_account_page.getCount() > 0) {
            while (dt_account_page.moveToNext()) {
                Map <String, Object> map = new HashMap <>();
                String drill_id,id,check_time, class_number, drill_pro, drill_no, machine_model, machine_code,
                        drill_d, drill_, drill_depth, fk_depth, check_result, builder,
                        monitor, checker;
                id = dt_account_page.getString(0);
                drill_id = dt_account_page.getString(0);
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
                map.put("drill_id", drill_id);
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
    private void initView(  ) {
        btn_add = findViewById(R.id.btn_add);
        btn_export = findViewById(R.id.btn_export);
        rc_home_list = findViewById(R.id.rc_home_list);
        mRefreshLayout =findViewById(R.id.refreshLayout);
        // mRefreshLayout.setEnableScrollContentWhenLoaded(true);
    }
    public Handler handler = new Handler() {

        @Override
        public void handleMessage( Message msg ) {
            switch (msg.what) {
                case 1:
                    ///添加分割线
                    homeRecycleAdapter = new HomeRecycleAdapter(Main1Activity.this, list);
                    rc_home_list.setAdapter(homeRecycleAdapter);
                   // ItemTouchHelper.Callback callback=new RecycleItemTouchHelper(homeRecycleAdapter);
                   // ItemTouchHelper itemTouchHelper=new ItemTouchHelper(callback);
                   // itemTouchHelper.attachToRecyclerView(rc_home_list);
                    //设置布局显示格式
                    rc_home_list.setLayoutManager(new LinearLayoutManager(Main1Activity.this, LinearLayoutManager.VERTICAL, false));
                    homeRecycleAdapter.setOnDelListener(new HomeRecycleAdapter.onSwipeListener() {
                        @Override
                        public void onDel(int pos) {
                            if (pos >= 0 && pos < list.size()) {
                                list.remove(pos);

                                homeRecycleAdapter.notifyItemRemoved(pos);//推荐用这个
                                //如果删除时，不使用mAdapter.notifyItemRemoved(pos)，则删除没有动画效果，
                                //且如果想让侧滑菜单同时关闭，需要同时调用 ((CstSwipeDelMenu) holder.itemView).quickClose();
                                homeRecycleAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onClick( int pos ) {

                        }

                        @Override
                        public void onTop( int pos ) {

                        }
                    });
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
                                Toast.makeText(Main1Activity.this,"已经是最后一页了",Toast.LENGTH_SHORT).show();
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
