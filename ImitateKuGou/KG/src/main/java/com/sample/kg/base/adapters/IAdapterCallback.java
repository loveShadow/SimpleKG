package com.sample.kg.base.adapters;

/**
 * Created by zhaoli on 2017/3/27.
 */
public interface IAdapterCallback {
    void onClick(BaseAdapter.BaseViewHolder viewHolder);
    void onTouch(BaseAdapter.BaseViewHolder viewHolder);
    void onLongTouch(BaseAdapter.BaseViewHolder viewHolder);
}
