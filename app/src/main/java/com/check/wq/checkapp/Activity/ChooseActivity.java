package com.check.wq.checkapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.check.wq.checkapp.R;

public class ChooseActivity extends Activity {
private Button btn_bp,btn_xy;
    SharedPreferences pref;
    SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mEditor = pref.edit();
        setContentView(R.layout.activity_choose);
        btn_bp = findViewById(R.id.btn_bp);
        btn_xy = findViewById(R.id.btn_xy);
        btn_bp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {

                mEditor.putString("drill_pro","爆破孔");
                mEditor.commit();
                Intent intent = new Intent(ChooseActivity.this,Main1Activity.class);
                startActivity(intent);
            }
        });
        btn_xy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                mEditor.putString("drill_pro","卸压孔");
                mEditor.commit();
                Intent intent = new Intent(ChooseActivity.this,Main1Activity.class);
                startActivity(intent);

            }
        });
    }
}
