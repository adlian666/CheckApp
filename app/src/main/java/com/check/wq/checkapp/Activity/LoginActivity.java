package com.check.wq.checkapp.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.check.wq.checkapp.R;
import com.check.wq.checkapp.Utils.MD5Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity {
    private EditText et_password, et_port, et_server;
    private EditText et_workername;
    private SharedPreferences pref;
    private SharedPreferences.Editor mEditor;
    private CheckBox ck_remember_psw;
    private String workername, password, pw;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mEditor = pref.edit();
        initView();
        final List <String> qlist = new ArrayList <>();
        //运行时权限，没有注册就重新注册
        if (Build.VERSION.SDK_INT >= 23) {

            if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                qlist.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (!qlist.isEmpty()) {//没有权限就添加
                String[] permissions = qlist.toArray(new String[qlist.size()]);//如果list不为空，就调用ActivityCompat.requestPermissions添加权限
                requestPermissions(permissions, 1);
            } else {
                copyDatabase();
            }
        }

    }

    private void initView() {
        et_password = findViewById(R.id.et_password);
        et_workername = findViewById(R.id.et_workername);
        //ck_remember_psw = findViewById(R.id.ck_remember_psw);
    }

    public void copyDatabase() {
        String path = pref.getString("path", "");
        String fileName = pref.getString("fileName", "");
        String DB_PATH = "/data/data/com.check.wq.checkapp/databases/";
        String DB_NAME = "drill.db";
        // 检查 SQLite 数据库文件是否存在
        File file = new File(DB_PATH + DB_NAME);
        if (!(file).exists()) {
            // 如 SQLite 数据库文件不存在，再检查一下 database 目录是否存在
            File f = new File(DB_PATH);
            // 如 database 目录不存在，新建该目录
            if (!f.exists()) {
                f.mkdir();
            }
        } else {
            file.delete();
        }
        try {
            // 得到 assets 目录下我们实现准备好的 SQLite 数据库作为输入流
            InputStream is = new FileInputStream(path);
            // 输出流,在指定路径下生成db文件
            OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);
            // 文件写入
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            // 关闭文件流
            os.flush();
            os.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //登陆按钮
    public void login( View v ) {
        workername = et_workername.getText().toString();
        password = et_password.getText().toString();
        password = MD5Utils.MD5(password);
        et_workername.setText(workername);
        try {
            MyOpenHelper myOpenHelper = new MyOpenHelper(getApplicationContext());
            //打开或创建数据库
            //SQLiteDatabase db =  this.openOrCreateDatabase();
            SQLiteDatabase db = myOpenHelper.getReadableDatabase();
            Cursor cursor2 = db.query("dt_userinfo", new String[]{"pw"}, "loginname=?", new String[]{"" + workername}, null, null, null);
       /* db.execSQL("CREATE  TABLE  if not exists dt_account(Id INTEGER PRIMARY KEY AUTOINCREMENT,drill_id Varchar(50) ,check_time date,class_number varchar(20)," +
                "drill_pro varchar(20),drill_no varchar(20),machine_model varchar(50),machine_code varchar(50),drill_d numeric(10,2),drill_ numeric(10,2)," +
                "drill_depth numeric(10,2),fk_depth numeric(10,2),check_result varchar(50),builder varchar(50),monitor varchar(50)," +
                "checker varchar(50))");*/
            if (cursor2 != null && cursor2.getColumnCount() > 0) {
                while (cursor2.moveToNext()) {
                    pw = cursor2.getString(0);
                }
                cursor2.close();
            } else {
                Toast.makeText(LoginActivity.this, "该用户不存在!", Toast.LENGTH_SHORT).show();
            }
            db.close();
            if (password.equals(pw)) {
                Intent intent = new Intent(LoginActivity.this
                        , ChooseActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "用户名或密码输入有误，请重新输入!", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
