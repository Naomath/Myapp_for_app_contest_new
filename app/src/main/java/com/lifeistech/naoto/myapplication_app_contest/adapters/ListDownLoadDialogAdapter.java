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
 * Created by naoto on 2017/08/31.
 */

public class ListDownLoadDialogAdapter extends ArrayAdapter<TwoWords> {
    Context mContext;
    LayoutInflater mLayoutInflater;
    int mCheck;

    public ListDownLoadDialogAdapter(Context context, int check) {
        //コンストラクタ
        super(context, check);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mCheck = check;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ListDownLoadDialogAdapter.ViewSetUp view_set_up;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(mCheck, parent, false);
            view_set_up = new ListDownLoadDialogAdapter.ViewSetUp(convertView);
            convertView.setTag(view_set_up);
        } else {
            view_set_up = ((ListDownLoadDialogAdapter.ViewSetUp) convertView.getTag());
        }
        TwoWords item = getItem(position);
        if (item != null) {
            view_set_up.japanese_textview.setText(item.getJapanese());
            view_set_up.english_textview.setText(item.getEnglish());
        }
        return convertView;
    }

    private class ViewSetUp {
        // Listviewのカスタマイズしている
        TextView japanese_textview;
        TextView english_textview;

        public ViewSetUp(View view) {
            japanese_textview = ((TextView) view.findViewById(R.id.upload_text_japanese));
            english_textview = ((TextView) view.findViewById(R.id.upload_text_english));
        }
    }
}

