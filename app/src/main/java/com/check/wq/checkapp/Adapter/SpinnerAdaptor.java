package com.check.wq.checkapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.check.wq.checkapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**

 * 自定义适配器类

 * @author jiangqq  <a href=http://blog.csdn.net/jiangqq781931404></a>

 *

 */

public class SpinnerAdaptor extends BaseAdapter {

    private List<String> mlist ;

    private Context mContext;



    public SpinnerAdaptor(Context pContext, List<String> list) {

        this.mContext = pContext;

        this.mlist = list;

    }



    @Override

    public int getCount() {

        return mlist.size();

    }



    @Override

    public Object getItem(int position) {

        return mlist.get(position);

    }



    @Override

    public long getItemId(int position) {

        return position;

    }



    /**

     * 下面是重要代码

     */

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater _LayoutInflater=LayoutInflater.from(mContext);

        convertView=_LayoutInflater.inflate(R.layout.spinner_layout, null);

        if(convertView!=null) {
            TextView tv_spinner=(TextView)convertView.findViewById(R.id.tv_spinner);
            tv_spinner.setText(mlist.get(position));

        }

        return convertView;

    }

}