package com.check.wq.checkapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.check.wq.checkapp.R;

import java.util.HashMap;
import java.util.Map;

public class InfoActivity extends Activity {
ImageButton imbtn_back;
private TextView tv_check_time, tv_class_number, tv_drill_pro,tv_drill_no, tv_machine_model, tv_machine_code,
        tv_drill_d, tv_drill_, tv_drill_depth, tv_fk_depth, tv_check_result, tv_builder,
        tv_monitor, tv_checker;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        final Map<String, Object> checkInfo = (HashMap<String, Object>)
                intent.getSerializableExtra("checkInfo");
        setContentView(R.layout.activity_info);
        initView(checkInfo);
        imbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                finish();
            }
        });
    }

    private void initView( Map <String, Object> checkInfo ) {
        imbtn_back = findViewById(R.id.imbtn_back);
        tv_check_time = findViewById(R.id.tv_check_time);
        tv_class_number = findViewById(R.id.tv_class_number);
        tv_drill_pro = findViewById(R.id.tv_drill_pro);
        tv_drill_no = findViewById(R.id.tv_drill_no);
        tv_machine_model = findViewById(R.id.tv_machine_model);
        tv_machine_code = findViewById(R.id.tv_machine_code);
        tv_drill_d = findViewById(R.id.tv_drill_d);
        tv_drill_ = findViewById(R.id.tv_drill_);
        tv_drill_depth = findViewById(R.id.tv_drill_depth);
        tv_fk_depth = findViewById(R.id.tv_fk_depth);
        tv_check_result = findViewById(R.id.tv_check_result);
        tv_builder = findViewById(R.id.tv_builder);
        tv_monitor = findViewById(R.id.tv_monitor);
        tv_checker = findViewById(R.id.tv_checker);
        tv_check_time.setText(checkInfo.get("check_time").toString());
        tv_class_number.setText(checkInfo.get("class_number").toString());
        tv_drill_pro.setText(checkInfo.get("drill_pro").toString());
        tv_drill_no.setText(checkInfo.get("drill_no").toString());
        tv_machine_model.setText(checkInfo.get("machine_model").toString());
        tv_machine_code.setText(checkInfo.get("machine_code").toString());
        tv_drill_d.setText(checkInfo.get("drill_d").toString());
        tv_drill_.setText(checkInfo.get("drill_").toString());
        tv_drill_depth.setText(checkInfo.get("drill_depth").toString());
        tv_fk_depth.setText(checkInfo.get("fk_depth").toString());
        tv_check_result.setText(checkInfo.get("check_result").toString());
        tv_builder.setText(checkInfo.get("builder").toString());
        tv_monitor.setText(checkInfo.get("monitor").toString());
        tv_checker.setText(checkInfo.get("checker").toString());

    }
}
