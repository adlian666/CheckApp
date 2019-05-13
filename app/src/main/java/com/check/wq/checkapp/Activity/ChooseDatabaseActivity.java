package com.check.wq.checkapp.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.check.wq.checkapp.R;

import org.apache.poi.ss.formula.functions.Choose;

import java.io.File;
import java.net.URISyntaxException;

public class ChooseDatabaseActivity extends Activity {
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private SharedPreferences pref;
    private SharedPreferences.Editor mEditor;
    private static final int FILE_SELECT_CODE = 0;
    Button btn_choose;
private ImageView img_result;
private TextView tv_check;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_database);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mEditor = pref.edit();
        //Android6.0需要申请权限
        int permission = ActivityCompat.checkSelfPermission(ChooseDatabaseActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    ChooseDatabaseActivity.this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );}
        tv_check = findViewById(R.id.tv_check);
        img_result = findViewById(R.id.img_result);
        btn_choose = findViewById(R.id.btn_choose);
        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {


                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivityForResult(Intent.createChooser(intent, "选择文件"), FILE_SELECT_CODE);



        }
    });
    }
    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();

                    try {
                        String path = getPath(uri);
                        File tempFile = new File(path.trim());
                        String fileName = tempFile.getName();
                        if (fileName.equals("drill.db")) {
                           mEditor.putString("fileName",fileName);
                            mEditor.putString("path",path);
                            mEditor.commit();
                            img_result.setImageResource(R.mipmap.right);
                            tv_check.setText("导入成功！");
                            Intent intent = new Intent(ChooseDatabaseActivity.this
                                    , LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            img_result.setImageResource(R.mipmap.wrong);
                            tv_check.setText("导入失败，请重新选择！");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE:

                break;
        }
    }

    //获取选择文件的路径
    public String getPath( Uri uri ) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;
            try {
                cursor = getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }


}
