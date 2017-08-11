package com.lifeistech.naoto.myapplication_app_contest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by naoto on 2017/07/24.
 */

public class ListSetUp extends ArrayAdapter<GroupTwoWords> {
    Context mContext;
    LayoutInflater mLayoutInflater;
    int mCheck;

    public ListSetUp(Context context, int check){
        //コンストラクタ
        super(context, check);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mCheck = check;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewSetUp2 view_set_up2;
        if(convertView == null){
            convertView = mLayoutInflater.inflate(mCheck, parent, false);
            view_set_up2 = new ViewSetUp2(convertView);
            convertView.setTag(view_set_up2);
        } else {
            view_set_up2 = ((ViewSetUp2) convertView.getTag());
        }
        GroupTwoWords item = getItem(position);
        if(item != null){
            view_set_up2.GROUP_NAME.setText(item.getGROUP_NAME());

        }
        return convertView;
    }

    private class ViewSetUp2{
        // Listviewのカスタマイズしている
        TextView GROUP_NAME;
        public ViewSetUp2(View view){
            GROUP_NAME = (TextView)view.findViewById(R.id.group_name_textview);
            GROUP_NAME.setTextSize(50);
        }
    }
}

