package com.sample.kg.utils;

import com.sample.kg.beans.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoli on 2017/3/16.
 */
public class TestUtil {
    public static List<Song> getTestSongData() {
        List<Song> songList = new ArrayList<>();
        for (int i = 0; i < 10; i ++) {
            Song song = new Song();
            song.songName = "歌曲名";
            song.singerName = "歌手名";
            if (i % 3 == 0) {
                song.songName = "歌曲名歌曲名歌曲名歌曲名歌曲名歌曲名歌曲名歌曲名歌曲名歌曲名歌曲名";
                song.singerName = "歌手名歌手名歌手名歌手名歌手名歌手名歌手名歌手名歌手名歌手名歌手名";
            }
            if (i == 4) {
                song.playState = Song.State.Select;
            }
            songList.add(song);
        }
        return songList;
    }
}
