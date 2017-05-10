package com.sample.kg.main.adapters;

import com.sample.kg.beans.Song;

/**
 * Created by zhaoli on 2017/3/30.
 */
public interface ICurPlayItemCallback {
    void onLove(Song song);
    void onDownload(Song song);
    void onDelete(Song song);
}
