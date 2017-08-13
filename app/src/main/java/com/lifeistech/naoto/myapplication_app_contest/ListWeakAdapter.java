package com.lifeistech.naoto.myapplication_app_contest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by naoto on 2017/08/13.
 */

public class ListWeakAdapter extends ArrayAdapter<TwoWordsWeak>{
    Context mContext;
    LayoutInflater mLayoutInflater;
    int mCheck;

    public ListWeakAdapter(Context context, int check){
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
            view_set_up = new ListWeakAdapter.ViewSetUp(convertView);
            convertView.setTag(view_set_up);
        } else {
            view_set_up = ((ListWeakAdapter.ViewSetUp) convertView.getTag());
        }
        TwoWordsWeak item = getItem(position);
        if(item != null){
            view_set_up.wordsJapanese.setText(item.getWORDS_JAPANESE());
            view_set_up.wordsEnglish.setText(item.getWRDS_ENGLISH());

        }
        return convertView;
    }

    private class ViewSetUp{
        // Listviewのカスタマイズしている
        TextView wordsJapanese;
        TextView wordsEnglish;
        public ViewSetUp(View view){
            wordsJapanese = (TextView)view.findViewById(R.id.textView_adapter_weak);
            wordsEnglish = (TextView)view.findViewById(R.id.textView_adapter_weak1);
        }
    }
}



