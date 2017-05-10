package com.sample.kg.base.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoli on 2017/3/15.
 */
public abstract class BaseAdapter <D, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected final Context context;
    protected final List<D> dataList = new ArrayList<>();
    protected IAdapterCallback callback;

    public BaseAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<D> data) {
        dataList.clear();
        dataList.addAll(data);
    }

    public void setCallback(IAdapterCallback callback) {
        this.callback = callback;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public abstract class BaseViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnTouchListener {

        public BaseViewHolder(View itemView) {
            super(itemView);

            itemView.setOnTouchListener(this);
            itemView.setOnClickListener(this);
        }

        /**
         * 当Item被拖动
         */
        public void onItemDrag() {

        }

        /**
         * 当Item松开
         */
        public void onItemRelease() {

        }

        @Override
        public void onClick(View view) {
            if (itemView == view) {
                if (null != callback) {
                    callback.onClick(this);
                }
            }
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return false;
        }
    }
}
