package com.gongyunhaoyyy.wustweschool.LitePal;

import com.gongyunhaoyyy.wustweschool.bean.Coursebean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by acer on 2017/10/14.
 */

public class Course extends DataSupport{
    private int id;
    private String dwmc,jsmc,kcxzmc,kcsj,ktmc,kcsxm,jsxm,xkjd,zxs,kkzc,kcmc,xf;
    private String kcsj1,kcsj2;
    private int kkzc1s,kkzc1e,kkzc2s,kkzc2e,kcxq1,kcxq2;
    private int kcjc1,kcjc2;

    public String getKkzc() {
        return kkzc;
    }

    public void setKkzc(String kkzc) {
        this.kkzc = kkzc;
    }

    public String getKcsj() {
        return kcsj;
    }

    public void setKcsj(String kcsj) {
        this.kcsj = kcsj;
    }

    public String getKcsj1() {
        return kcsj1;
    }

    public void setKcsj1(String kcsj1) {
        this.kcsj1 = kcsj1;
    }

    public String getKcsj2() {
        return kcsj2;
    }

    public void setKcsj2(String kcsj2) {
        this.kcsj2 = kcsj2;
    }

    public int getKkzc1s() {
        return kkzc1s;
    }

    public void setKkzc1s(int kkzc1s) {
        this.kkzc1s = kkzc1s;
    }

    public int getKkzc1e() {
        return kkzc1e;
    }

    public void setKkzc1e(int kkzc1e) {
        this.kkzc1e = kkzc1e;
    }

    public int getKkzc2s() {
        return kkzc2s;
    }

    public void setKkzc2s(int kkzc2s) {
        this.kkzc2s = kkzc2s;
    }

    public int getKkzc2e() {
        return kkzc2e;
    }

    public void setKkzc2e(int kkzc2e) {
        this.kkzc2e = kkzc2e;
    }

    public int getKcxq1() {
        return kcxq1;
    }

    public void setKcxq1(int kcxq1) {
        this.kcxq1 = kcxq1;
    }

    public int getKcxq2() {
        return kcxq2;
    }

    public void setKcxq2(int kcxq2) {
        this.kcxq2 = kcxq2;
    }

    public int getKcjc1() {
        return kcjc1;
    }

    public void setKcjc1(int kcjc1) {
        this.kcjc1 = kcjc1;
    }

    public int getKcjc2() {
        return kcjc2;
    }

    public void setKcjc2(int kcjc2) {
        this.kcjc2 = kcjc2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    public String getJsmc() {
        return jsmc;
    }

    public void setJsmc(String jsmc) {
        this.jsmc = jsmc;
    }

    public String getKcxzmc() {
        return kcxzmc;
    }

    public void setKcxzmc(String kcxzmc) {
        this.kcxzmc = kcxzmc;
    }


    public String getKtmc() {
        return ktmc;
    }

    public void setKtmc(String ktmc) {
        this.ktmc = ktmc;
    }

    public String getKcsxm() {
        return kcsxm;
    }

    public void setKcsxm(String kcsxm) {
        this.kcsxm = kcsxm;
    }

    public String getJsxm() {
        return jsxm;
    }

    public void setJsxm(String jsxm) {
        this.jsxm = jsxm;
    }

    public String getXkjd() {
        return xkjd;
    }

    public void setXkjd(String xkjd) {
        this.xkjd = xkjd;
    }

    public String getZxs() {
        return zxs;
    }

    public void setZxs(String zxs) {
        this.zxs = zxs;
    }


    public String getKcmc() {
        return kcmc;
    }

    public void setKcmc(String kcmc) {
        this.kcmc = kcmc;
    }

    public String getXf() {
        return xf;
    }

    public void setXf(String xf) {
        this.xf = xf;
    }
}
