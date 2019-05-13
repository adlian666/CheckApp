package com.check.wq.checkapp.Activity;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.check.wq.checkapp.Activity.MyOpenHelper;
import com.check.wq.checkapp.Adapter.SpinnerAdaptor;
import com.check.wq.checkapp.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PrimitiveIterator;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckActivity extends Activity {
    private View view;
    private Button btn_submmit, btn_check_time;
    private Spinner sp_class_number, sp_location, sp_check_result;
    private EditText et_drill_no, et_machine_model, et_machine_code,
            et_drill_d, et_drill_, et_drill_depth, et_fk_depth, et_builder,
            et_monitor, et_checker;
    String drill_id, check_time, class_number, drill_pro, drill_no, machine_model, machine_code,
            drill_d, drill_, drill_depth, fk_depth, check_result, builder,
            monitor, checker, location;
    List <String> locationList = new ArrayList <>();
    List <String> classNumberList = new ArrayList <>();
    SharedPreferences pref;
    SharedPreferences.Editor mEditor;
    private ImageButton imbtn_back;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_check);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mEditor = pref.edit();
        initView();
        //获取施工地点
        getLocation();
        //获取班次
        getClassNumber();
        imbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                finish();
            }
        });
//验收提交
        btn_submmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {

                drill_no = et_drill_no.getText().toString();
                drill_pro = pref.getString("drill_pro", "");
                machine_model = et_machine_model.getText().toString();
                machine_code = et_machine_code.getText().toString();
                drill_d = et_drill_d.getText().toString();
                drill_ = et_drill_.getText().toString();
                drill_depth = et_drill_depth.getText().toString();
                fk_depth = et_fk_depth.getText().toString();
                builder = et_builder.getText().toString();
                monitor = et_monitor.getText().toString();
                checker = et_checker.getText().toString();

                if (check()) {
                    addData();
                }
            }
        });

        //选择验收情况
        sp_check_result.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView <?> parent, View view,
                                        int position, long id ) {
                //拿到被选择项的值
                check_result = (String) sp_check_result.getSelectedItem();
            }

            @Override
            public void onNothingSelected( AdapterView <?> parent ) {
                // TODO Auto-generated method stub

            }
        });
        //选择日期
        btn_check_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                AlertDialog.Builder localBuilder = new AlertDialog.Builder(CheckActivity.this);
                final LinearLayout layout_alert = (LinearLayout) View.inflate(CheckActivity.this, R.layout.dateselect, null);
                localBuilder.setView(layout_alert);
                localBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt ) {
                        DatePicker datepicker1 = (DatePicker) layout_alert.findViewById(R.id.datepicker1);
                        int y = datepicker1.getYear();
                        int m = datepicker1.getMonth() + 1;
                        int d = datepicker1.getDayOfMonth();
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
                        check_time = y + "-" + mon + "-" + date;
                        btn_check_time.setText(y + "年" + m + "月" + d + "日"); //  获取时间
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt ) {

                    }
                }).create().show();
            }
        });


    }

    private void getClassNumber() {
        MyOpenHelper myOpenHelper = new MyOpenHelper(this);
        //打开或创建数据库
        SQLiteDatabase db = myOpenHelper.getReadableDatabase();
        Cursor cursor = db.query("dt_dic", null, null, null, null, null, null);

        if (cursor != null && cursor.getColumnCount() > 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(3);
                //Map <String, Object> map = new HashMap <>();
                classNumberList.add(name);
            }
            cursor.close();
        }
        db.close();
        SpinnerAdapter spinnerAdapter = new SpinnerAdaptor(CheckActivity.this, classNumberList);
        sp_class_number.setAdapter(spinnerAdapter);
        //选择班次
        sp_class_number.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected( AdapterView <?> arg0, View arg1, int arg2, long arg3 ) {
                class_number = spinnerAdapter.getItem(arg2).toString();
            }

            public void onNothingSelected( AdapterView <?> arg0 ) {

            }
        });
    }

    private void getLocation() {
        MyOpenHelper myOpenHelper = new MyOpenHelper(this);
        //打开或创建数据库
        SQLiteDatabase db = myOpenHelper.getReadableDatabase();
        Cursor cursor = db.query("dt_drill", null, null, null, null, null, null);
        if (cursor != null && cursor.getColumnCount() > 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(1);
                locationList.add(name);
            }
        }
        SpinnerAdapter spinnerAdapter = new SpinnerAdaptor(CheckActivity.this, locationList);
        sp_location.setAdapter(spinnerAdapter);
        //选择施工地点
        sp_location.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected( AdapterView <?> arg0, View arg1, int arg2, long arg3 ) {
                MyOpenHelper myOpenHelper = new MyOpenHelper(CheckActivity.this);
                //打开或创建数据库
                SQLiteDatabase db1 = myOpenHelper.getReadableDatabase();
                location = spinnerAdapter.getItem(arg2).toString();
                Cursor cursor1 = db1.rawQuery("SELECT * FROM dt_drill WHERE name = ?", new String[]{location});
                if (cursor1 != null && cursor1.getColumnCount() > 0) {
                    while (cursor1.moveToNext()) {
                        drill_id = cursor1.getString(cursor1.getColumnIndex("id"));
                    }
                }
            }

            public void onNothingSelected( AdapterView <?> arg0 ) {

            }
        });
    }

    public static boolean isNumeric( String str ) {

        String reg = "\\d+(\\.\\d+)?";
        return str.matches(reg);
    }

    private void addData() {
        MyOpenHelper myOpenHelper = new MyOpenHelper(this);
        //打开或创建数据库
        SQLiteDatabase db = myOpenHelper.getReadableDatabase();
        DecimalFormat format = new DecimalFormat("0.00");
        drill_d = format.format( new BigDecimal(drill_d));
        drill_ = format.format( new BigDecimal(drill_));
        drill_depth = format.format( new BigDecimal(drill_depth));
        fk_depth = format.format( new BigDecimal(fk_depth));
        Object[] obj = {drill_id, check_time, class_number, drill_pro, drill_no, machine_model, machine_code, drill_d, drill_, drill_depth, fk_depth, check_result, builder, monitor, checker};
        //db.insert("INSERT INTO dt_account[(drill_id,check_time,class_number,drill_pro,drill_no,machine_model,machine_code,drill_d,drill_,drill_depth,fk_depth,check_result,builder,monitor,checker)] VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)",(check_time,class_number,drill_pro,drill_no,machine_model,machine_code,drill_d,drill_,drill_depth,fk_depth,check_result,builder,monitor,checker));
        db.execSQL("INSERT INTO dt_account(drill_id,check_time,class_number,drill_pro,drill_no,machine_model,machine_code,drill_d,drill_,drill_depth,fk_depth,check_result,builder,monitor,checker) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", obj);
        Toast.makeText(this, "添加成功！", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(CheckActivity.this
                , Main1Activity.class);
        startActivity(intent);
        finish();

    }

    private Boolean check() {
        if (!isNumeric(drill_d)) {
            Toast.makeText(this, "孔径输入格式不正确！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isNumeric(drill_)) {
            Toast.makeText(this, "倾角输入格式不正确！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isNumeric(drill_depth)) {
            Toast.makeText(this, "钻孔深度输入格式不正确！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isNumeric(fk_depth)) {
            Toast.makeText(this, "封孔深度输入格式不正确！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (check_time == null || class_number == null || location == null || drill_no == null || machine_model == null ||
                drill_d == null || drill_ == null || drill_depth == null || fk_depth == null ||
                check_result == null || builder == null || monitor == null || checker == null) {
            Toast.makeText(this, "输入不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initView() {
        imbtn_back = findViewById(R.id.imbtn_back);
        btn_submmit = findViewById(R.id.btn_submmit);
        btn_check_time = findViewById(R.id.btn_check_time);
        sp_class_number = findViewById(R.id.sp_class_number);
        sp_location = findViewById(R.id.sp_location);
        et_drill_no = findViewById(R.id.et_drill_no);
        et_machine_model = findViewById(R.id.et_machine_model);
        et_machine_code = findViewById(R.id.et_machine_code);
        et_drill_ = findViewById(R.id.et_drill_);
        et_drill_depth = findViewById(R.id.et_drill_depth);
        et_drill_d = findViewById(R.id.et_drill_d);
        et_fk_depth = findViewById(R.id.et_fk_depth);
        sp_check_result = findViewById(R.id.sp_check_result);
        et_builder = findViewById(R.id.et_builder);
        et_monitor = findViewById(R.id.et_monitor);
        et_checker = findViewById(R.id.et_checker);


    }


}
