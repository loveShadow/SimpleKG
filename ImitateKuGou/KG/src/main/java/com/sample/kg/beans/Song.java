package com.sample.kg.beans;

/**
 * Created by zhaoli on 2017/3/16.
 * 歌曲
 */
public class Song {
    public enum  State {
        None,
        Select,
        Play
    }

    //歌曲名
    public String songName;
    //歌手名
    public String singerName;
    //歌曲图片
    public String songImage;
    //播放状态
    public State playState = State.None;
    //是否为我喜欢
    public boolean isLove = false;
}
