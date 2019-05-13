package com.check.wq.checkapp.Fragment;


import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.check.wq.checkapp.Activity.MyOpenHelper;
import com.check.wq.checkapp.R;

import java.text.SimpleDateFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckFragment extends Fragment {
    private View view;
    private Button btn_submmit, btn_check_time;
    private Spinner sp_class_number, sp_drill_pro, sp_check_result;
    private EditText et_drill_no, et_machine_model, et_machine_code,
            et_drill_d, et_drill_, et_drill_depth, et_fk_depth, et_builder,
            et_monitor, et_checker;
    String check_time, class_number, drill_pro, drill_no, machine_model, machine_code,
            drill_d, drill_, drill_depth, fk_depth, check_result, builder,
            monitor, checker;

    public CheckFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_check, container, false);
        initView(view);

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
                addData();

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
                AlertDialog.Builder localBuilder = new AlertDialog.Builder(getContext());
                final LinearLayout layout_alert = (LinearLayout) View.inflate(getContext(), R.layout.dateselect, null);
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
        return view;

    }

    private void addData() {
        MyOpenHelper myOpenHelper = new MyOpenHelper(getContext());
        //打开或创建数据库
        SQLiteDatabase db = myOpenHelper.getReadableDatabase();
        Object[] obj = {check_time, class_number, drill_pro, drill_no, machine_model, machine_code, drill_d, drill_, drill_depth, fk_depth, check_result, builder, monitor, checker};
        //db.insert("INSERT INTO dt_account[(check_time,class_number,drill_pro,drill_no,machine_model,machine_code,drill_d,drill_,drill_depth,fk_depth,check_result,builder,monitor,checker)] VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)",(check_time,class_number,drill_pro,drill_no,machine_model,machine_code,drill_d,drill_,drill_depth,fk_depth,check_result,builder,monitor,checker));

        db.execSQL("INSERT INTO dt_account(check_time,class_number,drill_pro,drill_no,machine_model,machine_code,drill_d,drill_,drill_depth,fk_depth,check_result,builder,monitor,checker) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)", obj);

        Toast.makeText(getContext(), "添加成功" + machine_model, Toast.LENGTH_SHORT).show();
    }

    private void initView( View view ) {
        btn_submmit = view.findViewById(R.id.btn_submmit);
        btn_check_time = view.findViewById(R.id.btn_check_time);
        sp_class_number = view.findViewById(R.id.sp_class_number);
        sp_drill_pro = view.findViewById(R.id.sp_drill_pro);
        et_drill_no = view.findViewById(R.id.et_drill_no);
        et_machine_model = view.findViewById(R.id.et_machine_model);
        et_machine_code = view.findViewById(R.id.et_machine_code);
        et_drill_ = view.findViewById(R.id.et_drill_);
        et_drill_depth = view.findViewById(R.id.et_drill_depth);
        et_drill_d = view.findViewById(R.id.et_drill_d);
        et_fk_depth = view.findViewById(R.id.et_fk_depth);
        sp_check_result = view.findViewById(R.id.sp_check_result);
        et_builder = view.findViewById(R.id.et_builder);
        et_monitor = view.findViewById(R.id.et_monitor);
        et_checker = view.findViewById(R.id.et_checker);


    }


}
