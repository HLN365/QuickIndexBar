package com.hl.quickindexbar;

/**
 * Created by HL on 2017/9/28/0028.
 */

public class Friend implements Comparable<Friend> {

    private String name;
    private String pinyin;

    public Friend(String name) {
        this.name = name;
        setPinyin(PinYinUtil.getPinyin(name));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public int compareTo(Friend another) {
        return getPinyin().compareTo(another.getPinyin());
    }
}
