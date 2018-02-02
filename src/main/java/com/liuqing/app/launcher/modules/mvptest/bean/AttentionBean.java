package com.liuqing.app.launcher.modules.mvptest.bean;

import java.util.List;

/**
 * 作者: Dream on 16/9/29 20:43
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

public class AttentionBean {


    /**
     * last_flag : top_list
     * last_coord : 18757415
     */

    private InfoBean info;
    /**
     * uid : 6904791
     * screen_name : 我是由小藜
     * social_name :
     * header : http://wimg.spriteapp.cn/profile/large/2016/05/18/573c74f0213fb_mini.jpg
     * gender : f
     * fans_count : 217407
     * introduction : 这个用户很懒，什么也没有留下！
     * plat_flag : 2
     * is_follow : 0
     * id : 6904791
     * tiezi_count : 0
     * is_vip : false
     */

    private List<TopListBean> top_list;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public List<TopListBean> getTop_list() {
        return top_list;
    }

    public void setTop_list(List<TopListBean> top_list) {
        this.top_list = top_list;
    }

    public static class InfoBean {
        private String last_flag;
        private String last_coord;

        public String getLast_flag() {
            return last_flag;
        }

        public void setLast_flag(String last_flag) {
            this.last_flag = last_flag;
        }

        public String getLast_coord() {
            return last_coord;
        }

        public void setLast_coord(String last_coord) {
            this.last_coord = last_coord;
        }

        @Override
        public String toString() {
            return "InfoBean{" +
                    "last_flag='" + last_flag + '\'' +
                    ", last_coord='" + last_coord + '\'' +
                    '}';
        }
    }

    public static class TopListBean {
        private String uid;
        private String screen_name;
        private String social_name;
        private String header;
        private String gender;
        private String fans_count;
        private String introduction;
        private int plat_flag;
        private int is_follow;
        private String id;
        private int tiezi_count;
        private boolean is_vip;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getScreen_name() {
            return screen_name;
        }

        public void setScreen_name(String screen_name) {
            this.screen_name = screen_name;
        }

        public String getSocial_name() {
            return social_name;
        }

        public void setSocial_name(String social_name) {
            this.social_name = social_name;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getFans_count() {
            return fans_count;
        }

        public void setFans_count(String fans_count) {
            this.fans_count = fans_count;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public int getPlat_flag() {
            return plat_flag;
        }

        public void setPlat_flag(int plat_flag) {
            this.plat_flag = plat_flag;
        }

        public int getIs_follow() {
            return is_follow;
        }

        public void setIs_follow(int is_follow) {
            this.is_follow = is_follow;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getTiezi_count() {
            return tiezi_count;
        }

        public void setTiezi_count(int tiezi_count) {
            this.tiezi_count = tiezi_count;
        }

        public boolean isIs_vip() {
            return is_vip;
        }

        public void setIs_vip(boolean is_vip) {
            this.is_vip = is_vip;
        }

        @Override
        public String toString() {
            return "TopListBean{" +
                    "uid='" + uid + '\'' +
                    ", screen_name='" + screen_name + '\'' +
                    ", social_name='" + social_name + '\'' +
                    ", header='" + header + '\'' +
                    ", gender='" + gender + '\'' +
                    ", fans_count='" + fans_count + '\'' +
                    ", introduction='" + introduction + '\'' +
                    ", plat_flag=" + plat_flag +
                    ", is_follow=" + is_follow +
                    ", id='" + id + '\'' +
                    ", tiezi_count=" + tiezi_count +
                    ", is_vip=" + is_vip +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AttentionBean{" +
                "info=" + info +
                ", top_list=" + top_list +
                '}';
    }
}
