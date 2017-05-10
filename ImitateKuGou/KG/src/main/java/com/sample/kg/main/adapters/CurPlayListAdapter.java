package com.sample.kg.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sample.kg.R;
import com.sample.kg.base.adapters.BaseAdapter;
import com.sample.kg.base.adapters.IPlayItemTouchCallback;
import com.sample.kg.beans.Song;
import com.sample.kg.utils.ResourceUtil;
import com.sample.kg.utils.SongIDUtil;

import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zhaoli on 2017/3/15.
 */
public class CurPlayListAdapter extends BaseAdapter<Song, CurPlayListAdapter.CurPlayListViewHolder>
        implements IPlayItemTouchCallback {

    public CurPlayListAdapter(Context context) {
        super(context);
    }

    private boolean moveState = false;
    private ICurPlayItemCallback itemCallback;

    public void setMoveState(boolean moveState) {
        this.moveState = moveState;
    }

    public void setItemCallback(ICurPlayItemCallback itemCallback) {
        this.itemCallback = itemCallback;
    }

    @Override
    public CurPlayListAdapter.CurPlayListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        CurPlayListViewHolder holder = new CurPlayListViewHolder(
                inflater.inflate(R.layout.bottom_cur_playlist_item_layout, parent, false)
        );
        return holder;
    }

    @Override
    public void onBindViewHolder(CurPlayListAdapter.CurPlayListViewHolder holder, int position) {
        Song song = dataList.get(position);
        holder.song = song;
        holder.songNameView.setText(song.songName);
        holder.singerNameView.setText(song.singerName);
        holder.menuLayout.setPadding(0, 0, 0, 0);
        holder.moveIcon.setVisibility(View.GONE);
        holder.headLayout.setVisibility(View.VISIBLE);
        holder.deleteBtn.setVisibility(View.VISIBLE);
        holder.intervalLineView.setVisibility(View.VISIBLE);
        holder.loveBtn.setBackgroundResource(song.isLove ?
                R.drawable.bottom_playlist_menu_love_selected :
                R.drawable.bottom_playlist_menu_love_normal);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) holder.menuLayout.getLayoutParams();
        if (null == lp) {
            lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        switch (song.playState) {
            case None:
                holder.songHeadImage.setVisibility(View.GONE);
                holder.songHeadId.setVisibility(View.VISIBLE);
                holder.downloadBtn.setVisibility(View.GONE);
                holder.loveBtn.setVisibility(View.GONE);
                holder.songHeadId.setText(SongIDUtil.toString(getItemCount(), position + 1));
                holder.songNameView.setTextColor(ResourceUtil.getColor(context, R.color.playlist_normal_song_text));
                holder.singerNameView.setTextColor(ResourceUtil.getColor(context, R.color.playlist_normal_singer_text));
                lp.addRule(RelativeLayout.LEFT_OF, R.id.deleteBtn);
                break;
            case Select:
            case Play:
                holder.songHeadImage.setVisibility(View.VISIBLE);
                holder.songHeadId.setVisibility(View.GONE);
                holder.downloadBtn.setVisibility(View.VISIBLE);
                holder.loveBtn.setVisibility(View.VISIBLE);
                holder.songNameView.setTextColor(ResourceUtil.getColor(context, R.color.playlist_select_text));
                holder.singerNameView.setTextColor(ResourceUtil.getColor(context, R.color.playlist_select_text));
                lp.addRule(RelativeLayout.LEFT_OF, R.id.downloadBtn);
                break;
        }
        holder.menuLayout.setLayoutParams(lp);
        if (moveState) {
            holder.changeToMoveState();
        }
    }

    @Override
    public void onItemMove(int fromPos, int toPos) {
        Collections.swap(dataList, fromPos, toPos);
        notifyItemMoved(fromPos, toPos);
    }

    public class CurPlayListViewHolder extends BaseAdapter.BaseViewHolder
            implements View.OnLongClickListener, View.OnClickListener {

        public View headLayout;
        public LinearLayout menuLayout;
        public CircleImageView songHeadImage;
        public TextView songHeadId;
        public TextView songNameView;
        public TextView singerNameView;
        public Button downloadBtn;
        public Button loveBtn;
        public Button deleteBtn;
        public View moveIcon;
        public View intervalLineView;
        public Song song;

        public CurPlayListViewHolder(View itemView) {
            super(itemView);
            headLayout = itemView.findViewById(R.id.headLayout);
            moveIcon = itemView.findViewById(R.id.moveIcon);
            intervalLineView = itemView.findViewById(R.id.intervalLineView);
            menuLayout = (LinearLayout) itemView.findViewById(R.id.menuLayout);
            songHeadImage = (CircleImageView) itemView.findViewById(R.id.songHeadImage);
            songHeadId = (TextView) itemView.findViewById(R.id.songId);
            songNameView = (TextView) itemView.findViewById(R.id.songNameView);
            singerNameView = (TextView) itemView.findViewById(R.id.singerNameView);
            downloadBtn = (Button) itemView.findViewById(R.id.downloadBtn);
            loveBtn = (Button) itemView.findViewById(R.id.loveBtn);
            deleteBtn = (Button) itemView.findViewById(R.id.deleteBtn);

            itemView.setOnLongClickListener(this);
            downloadBtn.setOnClickListener(this);
            loveBtn.setOnClickListener(this);
            deleteBtn.setOnClickListener(this);
        }

        /**
         * 切换到移动状态
         */
        public void changeToMoveState() {
            headLayout.setVisibility(View.GONE);
            deleteBtn.setVisibility(View.INVISIBLE);
            downloadBtn.setVisibility(View.GONE);
            loveBtn.setVisibility(View.GONE);
            moveIcon.setVisibility(View.VISIBLE);
            intervalLineView.setVisibility(View.INVISIBLE);
            menuLayout.setPadding(context.getResources().getDimensionPixelSize(R.dimen.fhd_50), 0, 0, 0);
        }

        @Override
        public void onItemDrag() {
            super.onItemDrag();
            itemView.setBackgroundColor(ResourceUtil.getColor(context, R.color.playlist_item_moving));
        }

        @Override
        public void onItemRelease() {
            super.onItemRelease();
            itemView.setBackgroundColor(ResourceUtil.getColor(context, R.color.colorTransparent));
        }

        @Override
        public void onClick(View view) {
            super.onClick(view);
            if (null != itemCallback) {
                if (loveBtn == view) {
                    loveBtn.setBackgroundResource(song.isLove ?
                            R.drawable.bottom_playlist_menu_love_normal :
                            R.drawable.bottom_playlist_menu_love_selected);
                    song.isLove = !song.isLove;
                    itemCallback.onLove(song);
                } else if (downloadBtn == view) {
                    itemCallback.onDownload(song);
                } else if (deleteBtn == view) {
                    final int index = dataList.indexOf(song);
                    dataList.remove(index);
                    notifyItemRemoved(index);
                    itemCallback.onDelete(song);
                }
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (itemView == view) {
                if (null != callback) {
                    callback.onLongTouch(this);
                }
            }
            return false;
        }
    }
}
