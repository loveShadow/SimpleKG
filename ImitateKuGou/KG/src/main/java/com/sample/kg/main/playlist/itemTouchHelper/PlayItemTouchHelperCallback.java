package com.sample.kg.main.playlist.itemTouchHelper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.sample.kg.base.adapters.BaseAdapter;
import com.sample.kg.base.adapters.IPlayItemTouchCallback;

/**
 * Created by zhaoli on 2017/3/26.
 */
public class PlayItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final IPlayItemTouchCallback touchCallback;

    public PlayItemTouchHelperCallback(IPlayItemTouchCallback touchCallback) {
        this.touchCallback = touchCallback;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeFlag(ItemTouchHelper.ACTION_STATE_IDLE, dragFlags) |
                makeFlag(ItemTouchHelper.ACTION_STATE_DRAG, dragFlags);
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            if (viewHolder instanceof BaseAdapter.BaseViewHolder) {
                ((BaseAdapter.BaseViewHolder) viewHolder).onItemDrag();
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof BaseAdapter.BaseViewHolder) {
            ((BaseAdapter.BaseViewHolder) viewHolder).onItemRelease();
        }
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        touchCallback.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }
}
