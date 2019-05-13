package com.check.wq.checkapp.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.check.wq.checkapp.R;

import java.util.HashMap;
import java.util.Map;

public class Modify_info_Activity extends Activity {
    private View view;
    private Button btn_submmit, btn_check_time;
    private ImageView imbtn_back;
    private Spinner sp_class_number, sp_drill_pro, sp_check_result;
    private EditText et_drill_no, et_machine_model, et_machine_code,
            et_drill_d, et_drill_, et_drill_depth, et_fk_depth, et_builder,
            et_monitor, et_checker;
    String check_time, class_number, drill_pro, drill_no, machine_model, machine_code,
            drill_d, drill_, drill_depth, fk_depth, check_result, builder,
            monitor, checker;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_info_);
        Intent intent = getIntent();
        final Map <String, Object> checkInfo = (HashMap <String, Object>)
                intent.getSerializableExtra("checkInfo");
        initView(checkInfo);

        //验收提交
        btn_submmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                drill_no = et_drill_no.getText().toString();
                machine_model = et_machine_model.getText().toString();
                machine_code = et_machine_code.getText().toString();
                drill_d = et_drill_d.getText().toString();
                drill_ = et_drill_.getText().toString();
                drill_depth = et_drill_depth.getText().toString();
                fk_depth = et_fk_depth.getText().toString();
                builder = et_builder.getText().toString();
                monitor = et_monitor.getText().toString();
                checker = et_checker.getText().toString();
                String id = checkInfo.get("id").toString();
                modifyData(id);
            }
        });
//选择班次
        sp_class_number.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected( AdapterView <?> parent, View view,
                                        int position, long id ) {

                //拿到被选择项的值
                class_number = (String) sp_class_number.getSelectedItem();


            }

            @Override
            public void onNothingSelected( AdapterView <?> parent ) {
                // TODO Auto-generated method stub

            }
        });
        //选择钻孔性质
        sp_drill_pro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected( AdapterView <?> parent, View view,
                                        int position, long id ) {

                //拿到被选择项的值
                drill_pro = (String) sp_drill_pro.getSelectedItem();

            }

            @Override
            public void onNothingSelected( AdapterView <?> parent ) {
                // TODO Auto-generated method stub

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
                AlertDialog.Builder localBuilder = new AlertDialog.Builder(Modify_info_Activity.this);
                final LinearLayout layout_alert = (LinearLayout) View.inflate(Modify_info_Activity.this, R.layout.dateselect, null);
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
        imbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent = new Intent(Modify_info_Activity.this,Main1Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void modifyData( String id ) {

        drill_no = et_drill_no.getText().toString();
        machine_model = et_machine_model.getText().toString();
        machine_code = et_machine_code.getText().toString();
        drill_d = et_drill_d.getText().toString();
        drill_ = et_drill_.getText().toString();
        drill_depth = et_drill_depth.getText().toString();
        fk_depth = et_fk_depth.getText().toString();
        builder = et_builder.getText().toString();
        monitor = et_monitor.getText().toString();
        checker = et_checker.getText().toString();

        MyOpenHelper myOpenHelper = new MyOpenHelper(Modify_info_Activity.this);
        //打开或创建数据库
        SQLiteDatabase db = myOpenHelper.getReadableDatabase();
        Object[] obj = {check_time, class_number, drill_pro, drill_no, machine_model, machine_code, drill_d, drill_, drill_depth, fk_depth, check_result, builder, monitor, checker};
        //db.insert("INSERT INTO dt_account[(check_time,class_number,drill_pro,drill_no,machine_model,machine_code,drill_d,drill_,drill_depth,fk_depth,check_result,builder,monitor,checker)] VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)",(check_time,class_number,drill_pro,drill_no,machine_model,machine_code,drill_d,drill_,drill_depth,fk_depth,check_result,builder,monitor,checker));

        //db.execSQL("INSERT INTO dt_account(check_time,class_number,drill_pro,drill_no,machine_model,machine_code,drill_d,drill_,drill_depth,fk_depth,check_result,builder,monitor,checker) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)", obj);
        ContentValues values = new ContentValues();
        values.put("check_time", check_time);
        values.put("class_number", class_number);
        values.put("drill_pro", drill_pro);
        values.put("drill_no", drill_no);
        values.put("machine_model", machine_model);
        values.put("machine_code", machine_code);
        values.put("drill_d", drill_d);
        values.put("drill_", drill_);
        values.put("drill_depth", drill_depth);
        values.put("fk_depth", fk_depth);
        values.put("check_result", check_result);
        values.put("builder", builder);
        values.put("monitor", monitor);
        values.put("checker", checker);
        int update = db.update("dt_account", values, "id=?", new String[]{"" + id});
        if (update > 0) {
            Toast.makeText(Modify_info_Activity.this, "修改成功!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,Main1Activity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(Modify_info_Activity.this, "修改失败!", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView( Map <String, Object> checkInfo ) {
        imbtn_back = findViewById(R.id.imbtn_back);
        btn_submmit = findViewById(R.id.btn_submmit);
        btn_check_time = findViewById(R.id.btn_check_time);
        sp_class_number = findViewById(R.id.sp_class_number);
        sp_drill_pro = findViewById(R.id.sp_drill_pro);
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
        btn_check_time.setText(checkInfo.get("check_time").toString());
        et_drill_no.setText(checkInfo.get("drill_no").toString());
        et_machine_model.setText(checkInfo.get("machine_model").toString());
        et_machine_code.setText(checkInfo.get("machine_code").toString());
        et_drill_.setText(checkInfo.get("drill_").toString());
        et_drill_d.setText(checkInfo.get("drill_d").toString());
        et_drill_depth.setText(checkInfo.get("drill_depth").toString());
        et_fk_depth.setText(checkInfo.get("fk_depth").toString());
        et_builder.setText(checkInfo.get("builder").toString());
        et_monitor.setText(checkInfo.get("monitor").toString());
        et_checker.setText(checkInfo.get("checker").toString());
        check_time = checkInfo.get("check_time").toString();
        class_number = checkInfo.get("class_number").toString();
        drill_pro = checkInfo.get("drill_pro").toString();
        check_result = checkInfo.get("check_result").toString();

    }
}
