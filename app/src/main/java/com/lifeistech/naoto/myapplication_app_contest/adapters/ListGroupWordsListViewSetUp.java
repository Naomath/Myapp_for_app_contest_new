package com.lifeistech.naoto.myapplication_app_contest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lifeistech.naoto.myapplication_app_contest.R;
import com.lifeistech.naoto.myapplication_app_contest.Sugar.TwoWords;
import com.lifeistech.naoto.myapplication_app_contest.Sugar.TwoWordsSet;

/**
 * Created by naoto on 2017/07/27.
 */

public class ListGroupWordsListViewSetUp extends ArrayAdapter<TwoWordsSet> {
    Context mContext;
    LayoutInflater mLayoutInflater;
    int mCheck;

    public ListGroupWordsListViewSetUp(Context context, int check) {
        //コンストラクタ
        super(context, check);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mCheck = check;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListGroupWordsListViewSetUp.ViewSetUp3 view_set_up;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(mCheck, parent, false);
            view_set_up = new ListGroupWordsListViewSetUp.ViewSetUp3(convertView);
            convertView.setTag(view_set_up);
        } else {
            view_set_up = ((ListGroupWordsListViewSetUp.ViewSetUp3) convertView.getTag());
        }
        TwoWordsSet item = getItem(position);
        if (item != null) {
            view_set_up.japanese_textview.setText(item.getJapanese());
            view_set_up.english_textview.setText(item.getEnglish());
        }
        return convertView;
    }

    private class ViewSetUp3 {
        // Listviewのカスタマイズしている
        TextView japanese_textview;
        TextView english_textview;
        ImageView imageView;

        public ViewSetUp3(View view) {
            japanese_textview = ((TextView) view.findViewById(R.id.textViewLGW0));
            english_textview = ((TextView) view.findViewById(R.id.textViewLGW1));
            imageView = (ImageView) view.findViewById(R.id.imageView6);
        }
    }
}
