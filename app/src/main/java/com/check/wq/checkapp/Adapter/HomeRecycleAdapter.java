package com.check.wq.checkapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.check.wq.checkapp.Activity.ExportActivity;
import com.check.wq.checkapp.Activity.InfoActivity;
import com.check.wq.checkapp.Activity.LoginActivity;
import com.check.wq.checkapp.Activity.Main1Activity;
import com.check.wq.checkapp.Activity.Modify_info_Activity;
import com.check.wq.checkapp.Activity.MyOpenHelper;
import com.check.wq.checkapp.R;
import com.check.wq.checkapp.Utils.LoadingDialogUtils;
import com.check.wq.checkapp.Utils.RecycleItemTouchHelper;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeRecycleAdapter extends RecyclerView.Adapter <HomeRecycleAdapter.homeViewHodler> implements RecycleItemTouchHelper.ItemTouchHelperCallback {
    private Context context;
    public List <Map <String, Object>> list = new ArrayList <>();
    public LayoutInflater inflater;
    SQLiteDatabase db;
    Cursor dt_account;
    MyOpenHelper myOpenHelper;
    TextView tvContent;
    SwipeMenuLayout swipeMenuLayout;

    //创建构造函数
    public HomeRecycleAdapter( Context context, List <Map <String, Object>> list ) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//上下文
        this.list = list;

        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public HomeRecycleAdapter.homeViewHodler onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View homeitemView = (LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false));
        return new homeViewHodler(homeitemView);
    }

    @Override
    public void onItemDelete( int positon ) {

    }

    @Override
    public void onMove( int fromPosition, int toPosition ) {

    }

    class homeViewHodler extends RecyclerView.ViewHolder {
        private TextView tv_id, tv_check_time, tv_drill_id, tv_class_number, tv_drill_no, tv_drill_pro, tv_checker, tv_check_result;
        private Button btnDelete, btn_modify;
        private LinearLayout ll_item;

        public homeViewHodler( View homeitemView ) {
            super(homeitemView);
            try {
                ll_item = homeitemView.findViewById(R.id.ll_item);
                // tvContent= (TextView) homeitemView.findViewById(R.id.tvContent);
                btnDelete = (Button) homeitemView.findViewById(R.id.btnDelete);
                // swipeMenuLayout= (SwipeMenuLayout) homeitemView.findViewById(R.id.swipeMenuLayout);
                btn_modify = homeitemView.findViewById(R.id.btn_modify);
                tv_check_time = homeitemView.findViewById(R.id.tv_check_time);
                tv_drill_id = homeitemView.findViewById(R.id.tv_drill_id);
                tv_class_number = homeitemView.findViewById(R.id.tv_class_number);
                tv_drill_no = homeitemView.findViewById(R.id.tv_drill_no);
                tv_drill_pro = homeitemView.findViewById(R.id.tv_drill_pro);
                tv_checker = homeitemView.findViewById(R.id.tv_checker);
                tv_check_result = homeitemView.findViewById(R.id.tv_check_result);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBindViewHolder( @NonNull HomeRecycleAdapter.homeViewHodler homeViewHodler, int i ) {
        String id = list.get(i).get("id").toString();
        String drill_id = list.get(i).get("drill_id").toString();
        //获取施工地点
        myOpenHelper = new MyOpenHelper(context);
        //打开或创建数据库
        db = myOpenHelper.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM dt_drill WHERE id = ?",new String[]{drill_id});
        if (cursor != null && cursor.getColumnCount() > 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                homeViewHodler.tv_drill_id.setText(name);
            }
            cursor.close();
        }
        db.close();
        String check_time = list.get(i).get("check_time").toString();
        //String  drill_id =  list.get(i).get("tv_drill_id").toString();
        String class_number = list.get(i).get("class_number").toString();
        String drill_no = list.get(i).get("drill_no").toString();
        String drill_pro = list.get(i).get("drill_pro").toString();
        String checker = list.get(i).get("checker").toString();
        String check_result = list.get(i).get("check_result").toString();
        homeViewHodler.tv_check_time.setText(check_time);
        homeViewHodler.tv_class_number.setText(class_number);
        homeViewHodler.tv_drill_no.setText(drill_no);
        homeViewHodler.tv_drill_pro.setText(drill_pro);
        homeViewHodler.tv_checker.setText(checker);
        homeViewHodler.tv_check_result.setText(check_result);

        //修改
        homeViewHodler.btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Map <String, Object> mlist = list.get(i);
                Intent intent = new Intent(context, Modify_info_Activity.class);
                intent.putExtra("checkInfo", (Serializable) mlist);
                context.startActivity(intent);

            }
        });
        //删除
        homeViewHodler.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                myOpenHelper = new MyOpenHelper(context);
                db = myOpenHelper.getReadableDatabase();
                //返回值代表影响的行数
                int delete = db.delete("dt_account", "id=?", new String[]{id});
                if (delete == 1) {
                    Toast.makeText(context, "删除成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "删除失败！", Toast.LENGTH_SHORT).show();
                }
                db.close();
               mOnSwipeListener.onDel(homeViewHodler.getAdapterPosition());
            }
        });
        //item点击事件
        homeViewHodler.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Map <String, Object> mlist = list.get(i);
                Intent intent = new Intent(context, InfoActivity.class);
                intent.putExtra("checkInfo", (Serializable) mlist);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 和Activity通信的接口
     */
    public interface onSwipeListener {
        void onDel( int pos );

        void onClick( int pos );

        void onTop( int pos );
    }

    private onSwipeListener mOnSwipeListener;

    public onSwipeListener getOnDelListener() {
        return mOnSwipeListener;
    }

    public void setOnDelListener( onSwipeListener mOnDelListener ) {
        this.mOnSwipeListener = mOnDelListener;
    }

    class FullDelDemoVH extends RecyclerView.ViewHolder {
        TextView content;
        Button btnDelete;
//        Button btnUnRead;
//        Button btnTop;

        public FullDelDemoVH( View itemView ) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.content);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
//            btnUnRead = (Button) itemView.findViewById(R.id.btnUnRead);
//            btnTop = (Button) itemView.findViewById(R.id.btnTop);
        }
    }
}
