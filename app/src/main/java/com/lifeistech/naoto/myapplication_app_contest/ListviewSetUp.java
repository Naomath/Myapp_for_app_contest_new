package com.lifeistech.naoto.myapplication_app_contest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by naoto on 2017/06/04.
 */

public class ListviewSetUp extends ArrayAdapter<TwoWordsForSet> {
    Context mContext;
    LayoutInflater mLayoutInflater;
    int mCheck;

    public ListviewSetUp(Context context, int check){
        //コンストラクタ
        super(context, check);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mCheck = check;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewSetUp view_set_up;
        if(convertView == null){
            convertView = mLayoutInflater.inflate(mCheck, parent, false);
            view_set_up = new ViewSetUp(convertView);
            convertView.setTag(view_set_up);
        } else {
            view_set_up = ((ViewSetUp) convertView.getTag());
        }
        TwoWordsForSet item = getItem(position);
        if(item != null){
            view_set_up.japanese_textview.setText(item.getJapanese());
            view_set_up.english_textview.setText(item.getEnglish());
        }
        return convertView;
    }

    private class ViewSetUp{
        // Listviewのカスタマイズしている
        TextView japanese_textview;
        TextView english_textview;

        public ViewSetUp(View view){
            japanese_textview = ((TextView)view.findViewById(R.id.textlist));
            english_textview = ((TextView)view.findViewById(R.id.textlist2));
            japanese_textview.setTextSize(30);
            english_textview.setTextSize(30);
        }
    }
}
