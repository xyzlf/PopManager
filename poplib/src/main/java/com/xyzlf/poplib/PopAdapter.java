package com.xyzlf.poplib;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zhanglifeng on 2017/3/28.
 * Pop Adapter
 */
class PopAdapter extends BaseAdapter {

    private List<PopModel> popModels;

    public PopAdapter(List<PopModel> popModels) {
        this.popModels = popModels;
    }

    public void setPopModels(List<PopModel> popModels) {
        this.popModels = popModels;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return null == popModels ? 0 : popModels.size();
    }

    @Override
    public PopModel getItem(int position) {
        if (null == popModels || position >= popModels.size()) {
            return null;
        }
        return popModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pop_popwindow_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.pop_item_image);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.pop_item_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PopModel popModel = getItem(position);
        if (null != popModel) {
            if (popModel.getDrawableId() != 0) {
                viewHolder.imageView.setVisibility(View.VISIBLE);
                viewHolder.imageView.setImageResource(popModel.getDrawableId());
            } else {
                viewHolder.imageView.setVisibility(View.GONE);
            }
            viewHolder.textView.setText(popModel.getItemDesc());
        }
        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView textView;
    }

}
