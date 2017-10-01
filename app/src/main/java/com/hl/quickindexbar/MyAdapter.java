package com.hl.quickindexbar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HL on 2017/9/28/0028.
 */

public class MyAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Friend> mdata;

    public MyAdapter(Context context, ArrayList<Friend> data) {
        mContext = context;
        mdata = data;
    }

    @Override
    public int getCount() {
        return mdata.size();
    }

    @Override
    public Friend getItem(int position) {
        return mdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.adapter_item, null);
        }
        ViewHolder holder = ViewHolder.getViewHolder(convertView);
        //当前item拼音的首字母
        String currentWord = mdata.get(position).getPinyin().charAt(0) + "";
        if (position > 0) {
            //获取上一个item拼音的首字母
            String lastWord = mdata.get(position - 1).getPinyin().charAt(0) + "";
            if (currentWord.equals(lastWord)) {
                //相同隐藏first_word条目
                holder.first_word.setVisibility(View.GONE);
            } else {
                //不同则显示当前首字母
                //由于条目复用，需将first_word设置为可见
                holder.first_word.setVisibility(View.VISIBLE);
                holder.first_word.setText(currentWord);
            }
        } else {
            holder.first_word.setVisibility(View.VISIBLE);
            holder.first_word.setText(currentWord);
        }
        holder.name.setText(getItem(position).getName());
        return convertView;
    }

    static class ViewHolder {

        TextView name;
        TextView first_word;

        public ViewHolder(View convertView) {
            name = (TextView) convertView.findViewById(R.id.tv_name);
            first_word = (TextView) convertView.findViewById(R.id.tv_first_word);
        }

        public static ViewHolder getViewHolder(View convertView) {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (holder == null) {
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            return holder;
        }
    }
}
