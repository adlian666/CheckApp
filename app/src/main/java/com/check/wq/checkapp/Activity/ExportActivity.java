package com.check.wq.checkapp.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.check.wq.checkapp.Adapter.HomeRecycleAdapter;
import com.check.wq.checkapp.Adapter.SpinnerAdaptor;
import com.check.wq.checkapp.R;
import com.check.wq.checkapp.Utils.LoadingDialogUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExportActivity extends Activity {
    private Button btn_start_time, btn_end_time, btn_search, btn_export_excel, btn_export_all;
    private ImageButton imbtn_back;
    String start_time, end_time, class_number1, location;
    private Spinner sp_class_number, sp_location;
    private List <Map <String, Object>> list = new ArrayList <Map <String, Object>>();
    private int y, y1, m, m1, d, d1;
    private HomeRecycleAdapter homeRecycleAdapter;
    private RecyclerView rc_export_list;
    String drill_id,id, check_time, class_number, drill_pro, drill_no, machine_model, machine_code,
            drill_d, drill_, drill_depth, fk_depth, check_result, builder,
            monitor, checker;
    private RecyclerView rc_home_list;
    private RefreshLayout mRefreshLayout;
    int i = 2;
    int size,page,lastPageSize;
    SQLiteDatabase db;
    Cursor dt_account;
    MyOpenHelper myOpenHelper;
    List <String> locationList = new ArrayList <>();
    List <Map <String, Object>> mlist = new ArrayList <>();
    SharedPreferences pref;
    SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mEditor = pref.edit();
        setContentView(R.layout.activity_export);
        initView();
        list.clear();
         myOpenHelper = new MyOpenHelper(ExportActivity.this);
        //打开或创建数据库
         db = myOpenHelper.getReadableDatabase();
        Cursor cursor = db.query("dt_drill", null, null, null, null, null, null);
        if (cursor != null && cursor.getColumnCount() > 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(1);
                locationList.add(name);
            }
        }
        try {
            //选择日期
            btn_start_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    AlertDialog.Builder localBuilder = new AlertDialog.Builder(ExportActivity.this);
                    final LinearLayout layout_alert = (LinearLayout) View.inflate(ExportActivity.this, R.layout.dateselect, null);
                    localBuilder.setView(layout_alert);
                    localBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick( DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt ) {
                            DatePicker datepicker1 = (DatePicker) layout_alert.findViewById(R.id.datepicker1);
                            y = datepicker1.getYear();
                            m = datepicker1.getMonth() + 1;
                            d = datepicker1.getDayOfMonth();
                            String mon;
                            String date;
                            if (m < 10) {
                                mon = "0" + m;
                            } else {
                                mon = "" + m;
                            }
                            if (d < 10) {
                                date = "0" + d;
                            } else {
                                date = "" + d;
                            }
                            start_time = y + "-" + mon + "-" + date;
                            btn_start_time.setText(y + "年" + m + "月" + d + "日"); //  获取时间
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick( DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt ) {

                        }
                    }).create().show();
                }
            });
            btn_end_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    AlertDialog.Builder localBuilder = new AlertDialog.Builder(ExportActivity.this);
                    final LinearLayout layout_alert = (LinearLayout) View.inflate(ExportActivity.this, R.layout.dateselect, null);
                    localBuilder.setView(layout_alert);
                    localBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick( DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt ) {
                            DatePicker datepicker1 = (DatePicker) layout_alert.findViewById(R.id.datepicker1);
                            y1 = datepicker1.getYear();
                            m1 = datepicker1.getMonth() + 1;
                            d1 = datepicker1.getDayOfMonth();
                            String mon;
                            String date;
                            if (m1 < 10) {
                                mon = "0" + m1;
                            } else {
                                mon = "" + m1;
                            }
                            if (d1 < 10) {
                                date = "0" + d1;
                            } else {
                                date = "" + d1;
                            }
                            end_time = y1 + "-" + mon + "-" + date;
                            btn_end_time.setText(y1 + "年" + m1 + "月" + d1 + "日"); //  获取时间
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick( DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt ) {

                        }
                    }).create().show();
                }
            });
            btn_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    if (checkFormat()) {
                        search(1);
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        //导出excel
        btn_export_excel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {

                if (checkFormat()) {
                    search(1);
                    LoadingDialogUtils.show(ExportActivity.this, "导出中...");//展示
                    HSSFWorkbook mWorkbook = new HSSFWorkbook();
                    for(int j = 0; j < locationList.size(); j++){
                        HSSFSheet mSheet = mWorkbook.createSheet(locationList.get(j));
                        createExcelHead(mSheet);
                        //打开或创建数据库
                         db = myOpenHelper.getReadableDatabase();
                        Cursor cursor1 = db.rawQuery("SELECT * FROM dt_drill WHERE name = ?", new String[]{locationList.get(j)});
                        if (cursor1 != null && cursor1.getColumnCount() > 0) {
                            while (cursor1.moveToNext()) {
                                drill_id = cursor1.getString(cursor1.getColumnIndex("id"));
                            }
                            cursor1.close();
                        }
                        List <Map <String, Object>> mmlist = new ArrayList <>() ;
                        for(Map <String, Object> map :list){
                            if(map.get("drill_id").equals(drill_id)){
                                mmlist.add(map);
                            }
                        }
                        for (int i = 0; i < mmlist.size(); i++) {
                            id = mmlist.get(i).get("id").toString();
                            drill_id = mmlist.get(i).get("drill_id").toString();
                            check_time = mmlist.get(i).get("check_time").toString();
                            class_number = mmlist.get(i).get("class_number").toString();
                            drill_pro = mmlist.get(i).get("drill_pro").toString();
                            drill_no = mmlist.get(i).get("drill_no").toString();
                            machine_model = mmlist.get(i).get("machine_model").toString();
                            machine_code = mmlist.get(i).get("machine_code").toString();
                            drill_d = mmlist.get(i).get("drill_d").toString();
                            drill_ = mmlist.get(i).get("drill_").toString();
                            drill_depth = mmlist.get(i).get("drill_depth").toString();
                            fk_depth = mmlist.get(i).get("fk_depth").toString();
                            check_result = mmlist.get(i).get("check_result").toString();
                            builder = mmlist.get(i).get("builder").toString();
                            monitor = mmlist.get(i).get("monitor").toString();
                            checker = mmlist.get(i).get("checker").toString();
                            createCell(drill_id,check_time, class_number, drill_pro, drill_no, machine_model, machine_code, drill_d,
                                    drill_, drill_depth, fk_depth, check_result,
                                    builder, monitor, checker, mSheet);
                        }
                        mmlist.clear();
                    }
                    File xlsFile = new File(Environment.getExternalStorageDirectory(), "excel.xls");
                    try {
                        if (!xlsFile.exists()) {
                            xlsFile.createNewFile();
                        }
                        mWorkbook.write(new FileOutputStream(xlsFile));// 或者以流的形式写入文件 mWorkbook.write(new FileOutputStream(xlsFile));

                        Toast.makeText(ExportActivity.this, "导出成功！" + Environment.getExternalStorageDirectory(), Toast.LENGTH_SHORT).show();
                        mWorkbook.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                LoadingDialogUtils.dismiss();//隐藏
                LoadingDialogUtils.unInit();//注销加载框，避免内存泄露。
            }
        });
        imbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                finish();
            }
        });
        //导出所有记录
        btn_export_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                searchAll();
                HSSFWorkbook mWorkbook = new HSSFWorkbook();
                for(int j = 0; j < locationList.size(); j++){
                    HSSFSheet mSheet = mWorkbook.createSheet(locationList.get(j));
                    createExcelHead(mSheet);
                    //打开或创建数据库
                    db = myOpenHelper.getReadableDatabase();
                    Cursor cursor1 = db.rawQuery("SELECT * FROM dt_drill WHERE name = ?", new String[]{locationList.get(j)});
                    if (cursor1 != null && cursor1.getColumnCount() > 0) {
                        while (cursor1.moveToNext()) {
                            drill_id = cursor1.getString(cursor1.getColumnIndex("id"));
                        }
                        cursor1.close();
                    }
                    List <Map <String, Object>> mmlist = new ArrayList <>() ;
                    for(Map <String, Object> map :list){
                        if(map.get("drill_id").equals(drill_id)){
                            mmlist.add(map);
                        }
                    }
                for (int i = 0; i < mmlist.size(); i++) {
                    id = mmlist.get(i).get("id").toString();
                    check_time = mmlist.get(i).get("check_time").toString();
                    class_number = mmlist.get(i).get("class_number").toString();
                    drill_pro = mmlist.get(i).get("drill_pro").toString();
                    drill_no = mmlist.get(i).get("drill_no").toString();
                    machine_model = mmlist.get(i).get("machine_model").toString();
                    machine_code = mmlist.get(i).get("machine_code").toString();
                    drill_d = mmlist.get(i).get("drill_d").toString();
                    drill_ = mmlist.get(i).get("drill_").toString();
                    drill_depth = mmlist.get(i).get("drill_depth").toString();
                    fk_depth = mmlist.get(i).get("fk_depth").toString();
                    check_result = mmlist.get(i).get("check_result").toString();
                    builder = mmlist.get(i).get("builder").toString();
                    monitor = mmlist.get(i).get("monitor").toString();
                    checker = mmlist.get(i).get("checker").toString();
                    createCell(drill_id,check_time, class_number, drill_pro, drill_no, machine_model, machine_code, drill_d,
                            drill_, drill_depth, fk_depth, check_result,
                            builder, monitor, checker, mSheet);
                }}
                File xlsFile = new File(Environment.getExternalStorageDirectory(), "excel.xls");
                try {
                    if (!xlsFile.exists()) {
                        xlsFile.createNewFile();
                    }
                    mWorkbook.write(new FileOutputStream(xlsFile));// 或者以流的形式写入文件 mWorkbook.write(new FileOutputStream(xlsFile));
                    LoadingDialogUtils.dismiss();//隐藏
                    LoadingDialogUtils.unInit();//注销加载框，避免内存泄露。
                    Toast.makeText(ExportActivity.this, "导出成功！" + Environment.getExternalStorageDirectory(), Toast.LENGTH_SHORT).show();
                    mWorkbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // 创建Excel标题行，第一行。
    private void createExcelHead( HSSFSheet mSheet ) {
        HSSFRow headRow = mSheet.createRow(0);
        headRow.createCell(0).setCellValue("巷道id");
        headRow.createCell(1).setCellValue("验收日期");
        headRow.createCell(2).setCellValue("班次");
        headRow.createCell(3).setCellValue("钻孔性质");
        headRow.createCell(4).setCellValue("钻孔号");
        headRow.createCell(5).setCellValue("钻机型号");
        headRow.createCell(6).setCellValue("钻机编号");
        headRow.createCell(7).setCellValue("孔 径");
        headRow.createCell(8).setCellValue("倾 角");
        headRow.createCell(9).setCellValue("钻孔深度");
        headRow.createCell(10).setCellValue("封孔深度");
        headRow.createCell(11).setCellValue("验收情况");
        headRow.createCell(12).setCellValue("施工人员");
        headRow.createCell(13).setCellValue("跟班队长");
        headRow.createCell(14).setCellValue("验收人");
    }

    // 创建Excel的一行数据。
    private static void createCell( String drill_id,String check_time, String class_number, String drill_pro, String drill_no, String machine_model, String machine_code, String drill_d,
                                    String drill_, String drill_depth, String fk_depth, String check_result,
                                    String builder, String monitor, String checker, HSSFSheet sheet ) {
        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
        dataRow.createCell(0).setCellValue(drill_id);
        dataRow.createCell(1).setCellValue(check_time);
        dataRow.createCell(2).setCellValue(class_number);
        dataRow.createCell(3).setCellValue(drill_pro);
        dataRow.createCell(4).setCellValue(drill_no);
        dataRow.createCell(5).setCellValue(machine_model);
        dataRow.createCell(6).setCellValue(machine_code);
        dataRow.createCell(7).setCellValue(drill_d);
        dataRow.createCell(8).setCellValue(drill_);
        dataRow.createCell(9).setCellValue(drill_depth);
        dataRow.createCell(10).setCellValue(fk_depth);
        dataRow.createCell(11).setCellValue(check_result);
        dataRow.createCell(12).setCellValue(builder);
        dataRow.createCell(13).setCellValue(monitor);
        dataRow.createCell(14).setCellValue(checker);
    }

    //检查输入是否为空
    private Boolean checkFormat() {
        if (start_time == null) {
            Toast.makeText(ExportActivity.this, "请选择起始日期", Toast.LENGTH_SHORT).show();
            return false;

        } else if (end_time == null) {
            Toast.makeText(ExportActivity.this, "请选择截止日期", Toast.LENGTH_SHORT).show();
            return false;

        } else if (y1 < y) {
            Toast.makeText(ExportActivity.this, "日期设置有误！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (m1 < m) {
            Toast.makeText(ExportActivity.this, "日期设置有误！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (d1 < d) {
            Toast.makeText(ExportActivity.this, "日期设置有误！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //筛选
    private void search(int currentPage) {
        MyOpenHelper myOpenHelper = new MyOpenHelper(ExportActivity.this);
        //打开或创建数据库
        SQLiteDatabase db = myOpenHelper.getReadableDatabase();
        Cursor search = db.query("dt_account", null, "check_time>=? and check_time<=?", new String[]{start_time, end_time}, null, null, null,(currentPage-1)*30+","+30);
        size = search.getCount();
        page = size/30+1;
        lastPageSize = size%30;
        Toast.makeText(ExportActivity.this, "共" + search.getCount() + "条!", Toast.LENGTH_SHORT).show();
        if (search != null && search.getCount() > 0) {
            while (search.moveToNext()) {
                Map <String, Object> map = new HashMap <>();
                id = search.getString(0);
                drill_id= search.getString(1);
                check_time = search.getString(2);
                class_number = search.getString(3);
                drill_pro = search.getString(4);
                drill_no = search.getString(5);
                machine_model = search.getString(6);
                machine_code = search.getString(7);
                drill_d = search.getString(8);
                drill_ = search.getString(9);
                drill_depth = search.getString(10);
                fk_depth = search.getString(11);
                check_result = search.getString(12);
                builder = search.getString(13);
                monitor = search.getString(14);
                checker = search.getString(15);
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

    //查询所有
    private void searchAll() {
        list.clear();
        MyOpenHelper myOpenHelper = new MyOpenHelper(ExportActivity.this);
        //打开或创建数据库
        SQLiteDatabase db = myOpenHelper.getReadableDatabase();
        Cursor search = db.query("dt_account", null, null, null, null, null, "id desc");
        if (search != null && search.getCount() > 0) {
            while (search.moveToNext()) {
                Map <String, Object> map = new HashMap <>();
                id = search.getString(0);
                drill_id = search.getString(1);
                check_time = search.getString(2);
                class_number = search.getString(3);
                drill_pro = search.getString(4);
                drill_no = search.getString(5);
                machine_model = search.getString(6);
                machine_code = search.getString(7);
                drill_d = search.getString(8);
                drill_ = search.getString(9);
                drill_depth = search.getString(10);
                fk_depth = search.getString(11);
                check_result = search.getString(12);
                builder = search.getString(13);
                monitor = search.getString(14);
                checker = search.getString(15);
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

    private void initView() {
        btn_export_all = findViewById(R.id.btn_export_all);
        imbtn_back = findViewById(R.id.imbtn_back);
        rc_export_list = findViewById(R.id.rc_export_list);
        btn_search = findViewById(R.id.btn_search);
        btn_start_time = findViewById(R.id.btn_start_time);
        btn_end_time = findViewById(R.id.btn_end_time);
        btn_export_excel = findViewById(R.id.btn_export_excel);
        mRefreshLayout =findViewById(R.id.refreshLayout);
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
    }
    public Handler handler = new Handler() {

        @Override
        public void handleMessage( Message msg ) {
            switch (msg.what) {
                case 1:
                    ///添加分割线
                    homeRecycleAdapter = new HomeRecycleAdapter(ExportActivity.this, list);
                    rc_export_list.setAdapter(homeRecycleAdapter);
                    //设置布局显示格式
                    rc_export_list.setLayoutManager(new LinearLayoutManager(ExportActivity.this, LinearLayoutManager.VERTICAL, false));
                    homeRecycleAdapter.setOnDelListener(new HomeRecycleAdapter.onSwipeListener() {
                        @Override
                        public void onDel( int pos ) {
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
                    //rc_home_list.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
                    mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                        public void onRefresh( RefreshLayout refreshlayout ) {
                            list.clear();
                            i = 2;
                            search(1);
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
                                Toast.makeText(ExportActivity.this,"已经是最后一页了",Toast.LENGTH_SHORT).show();
                                refreshlayout.finishRefresh();//结束刷新
                                refreshlayout.finishLoadmore();

                            }


                        }
                    });
                    break;
            }
        }
    };

}
