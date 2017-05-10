package com.sample.kg.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sample.kg.R;
import com.sample.kg.base.adapters.BaseAdapter;
import com.sample.kg.beans.Song;
import com.sample.kg.utils.SongIDUtil;


/**
 * Created by zhaoli on 2017/3/15.
 */
public class LastPlayListAdapter extends BaseAdapter<Song, LastPlayListAdapter.LastPlayListViewHolder> {

    public LastPlayListAdapter(Context context) {
        super(context);
    }

    @Override
    public LastPlayListAdapter.LastPlayListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LastPlayListViewHolder holder = new LastPlayListViewHolder(
                inflater.inflate(R.layout.bottom_last_playlist_item_layout, parent, false)
        );
        return holder;
    }

    @Override
    public void onBindViewHolder(LastPlayListAdapter.LastPlayListViewHolder holder, int position) {
        Song song = dataList.get(position);
        holder.songNameView.setText(song.songName);
        holder.singerNameView.setText(song.singerName);
        holder.songHeadId.setText(SongIDUtil.toString(getItemCount(), position + 1));
    }

    public class LastPlayListViewHolder extends BaseAdapter.BaseViewHolder {
        public TextView songHeadId;
        public TextView songNameView;
        public TextView singerNameView;

        public LastPlayListViewHolder(View itemView) {
            super(itemView);
            songHeadId = (TextView) itemView.findViewById(R.id.songId);
            songNameView = (TextView) itemView.findViewById(R.id.songNameView);
            singerNameView = (TextView) itemView.findViewById(R.id.singerNameView);
        }
    }
}
