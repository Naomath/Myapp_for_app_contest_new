package com.lifeistech.naoto.myapplication_app_contest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lifeistech.naoto.myapplication_app_contest.R;
import com.lifeistech.naoto.myapplication_app_contest.sugar.GroupTwoWords;

/**
 * Created by naoto on 2017/08/29.
 */

public class ListDownLoadAdapter extends ArrayAdapter<GroupTwoWords> {
    Context mContext;
    LayoutInflater mLayoutInflater;
    int mCheck;

    public ListDownLoadAdapter(Context context, int check) {
        //コンストラクタ
        super(context, check);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mCheck = check;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListDownLoadAdapter.ViewSetUp view_set_up;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(mCheck, parent, false);
            view_set_up = new ListDownLoadAdapter.ViewSetUp(convertView);
            convertView.setTag(view_set_up);
        } else {
            view_set_up = ((ListDownLoadAdapter.ViewSetUp) convertView.getTag());
        }
        GroupTwoWords item = getItem(position);
        if (item != null) {
            view_set_up.groupName.setText(item.getGroupName());
            view_set_up.groupMaker.setText(item.getMaker());
            view_set_up.groupTime.setText(item.getCalendar());
            StringBuffer buffer = new StringBuffer();
            buffer.append("(");
            buffer.append(item.getUserId());
            buffer.append(")");
            view_set_up.userId.setText(buffer.toString());
        }
        return convertView;
    }

    private class ViewSetUp {
        // Listviewのカスタマイズしている
        TextView groupName;
        TextView groupMaker;
        TextView groupTime;
        TextView userId;
        ImageView imageView;

        public ViewSetUp(View view) {
            groupName = (TextView) view.findViewById(R.id.group_name_textview);
            groupName.setTextSize(30);
            groupMaker = (TextView) view.findViewById(R.id.group_maker);
            groupTime = (TextView) view.findViewById(R.id.group_time);
            userId = (TextView) view.findViewById(R.id.textViewUserId);
            imageView = (ImageView) view.findViewById(R.id.imageViewHole);
        }
    }
}

