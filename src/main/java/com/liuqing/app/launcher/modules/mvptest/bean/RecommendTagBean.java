package com.liuqing.app.launcher.modules.mvptest.bean;

import java.util.List;

/**
 * 作者: Dream on 16/9/29 22:32
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

public class RecommendTagBean {


    /**
     * count : 9
     * np : 11
     */

    private InfoBean info;
    /**
     * info : {"count":9,"np":11}
     * total : 9
     * list : [{"count":63,"id":35,"name":"网红"},{"count":6,"id":36,"name":"精品"},{"count":17,"id":38,"name":"搞笑"},{"count":56,"id":37,"name":"创意"},{"count":29,"id":40,"name":"视频"},{"count":27,"id":39,"name":"图文"},{"count":11,"id":41,"name":"潜力"},{"count":5,"id":43,"name":"生活"},{"count":170,"id":16,"name":"原创"}]
     * size : 9
     */

    private int total;
    private int size;
    /**
     * count : 63
     * id : 35
     * name : 网红
     */

    private List<ListBean> list;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class InfoBean {
        private int count;
        private int np;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getNp() {
            return np;
        }

        public void setNp(int np) {
            this.np = np;
        }
    }

    public static class ListBean {
        private int count;
        private int id;
        private String name;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
