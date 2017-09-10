package com.lifeistech.naoto.myapplication_app_contest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lifeistech.naoto.myapplication_app_contest.R;
import com.lifeistech.naoto.myapplication_app_contest.sugar.TwoWords;

/**
 * Created by naoto on 2017/08/30.
 */

public class ListUploadAdapter extends ArrayAdapter<TwoWords> {
    Context mContext;
    LayoutInflater mLayoutInflater;
    int mCheck;

    public ListUploadAdapter(Context context, int check) {
        //コンストラクタ
        super(context, check);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mCheck = check;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListUploadAdapter.ViewSetUp view_set_up;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(mCheck, parent, false);
            view_set_up = new ListUploadAdapter.ViewSetUp(convertView);
            convertView.setTag(view_set_up);
        } else {
            view_set_up = ((ListUploadAdapter.ViewSetUp) convertView.getTag());
        }
        TwoWords item = getItem(position);
        if (item != null) {
            view_set_up.japanese.setText(item.getJapanese());
            view_set_up.englishes.setText(item.getEnglish());
        }
        return convertView;
    }

    private class ViewSetUp {
        // Listviewのカスタマイズしている
        TextView japanese;
        TextView englishes;


        public ViewSetUp(View view) {
            japanese = (TextView) view.findViewById(R.id.upload_text_japanese);
            englishes = (TextView) view.findViewById(R.id.upload_text_english);
        }
    }
}

