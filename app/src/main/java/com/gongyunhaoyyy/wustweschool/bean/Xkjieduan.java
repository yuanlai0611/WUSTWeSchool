package com.gongyunhaoyyy.wustweschool.bean;

/**
 * Created by acer on 2018/2/10.
 */

public class Xkjieduan {
    //选课开始时间(2018-01-26 14:30)，选课结束时间(2017-02-28 14:30)
    private String xkkssj,xkjzsj;
    //选课阶段(二选)，选课类别(公共选修课)，学年名称(2017-2018-2)
    private String xkjd,xklb,xnmc;
    private int xkfs,jx0502id,xklbname;

    public Xkjieduan(String xkkssj, String xkjzsj, String xkjd, String xklb, String xnmc, int xkfs, int jx0502id, int xklbname) {
        this.xkkssj = xkkssj;
        this.xkjzsj = xkjzsj;
        this.xkjd = xkjd;
        this.xklb = xklb;
        this.xnmc = xnmc;
        this.xkfs = xkfs;
        this.jx0502id = jx0502id;
        this.xklbname = xklbname;
    }

    public String getXkkssj() {
        return xkkssj;
    }

    public void setXkkssj(String xkkssj) {
        this.xkkssj = xkkssj;
    }

    public String getXkjzsj() {
        return xkjzsj;
    }

    public void setXkjzsj(String xkjzsj) {
        this.xkjzsj = xkjzsj;
    }

    public String getXkjd() {
        return xkjd;
    }

    public void setXkjd(String xkjd) {
        this.xkjd = xkjd;
    }

    public String getXklb() {
        return xklb;
    }

    public void setXklb(String xklb) {
        this.xklb = xklb;
    }

    public String getXnmc() {
        return xnmc;
    }

    public void setXnmc(String xnmc) {
        this.xnmc = xnmc;
    }

    public int getXkfs() {
        return xkfs;
    }

    public void setXkfs(int xkfs) {
        this.xkfs = xkfs;
    }

    public int getJx0502id() {
        return jx0502id;
    }

    public void setJx0502id(int jx0502id) {
        this.jx0502id = jx0502id;
    }

    public int getXklbname() {
        return xklbname;
    }

    public void setXklbname(int xklbname) {
        this.xklbname = xklbname;
    }
}
