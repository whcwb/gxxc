package com.cwb.platform.sys.model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sys_clk_ptyh")
public class SysYh implements Serializable {
    /**
     * 用户id
     */
    @Id
    @Column(name = "YHID")
    private String yhid;

    /**
     * 账号
     */
    @Column(name = "ZH")
    private String zh;

    /**
     * 密码
     */
    @Column(name = "MM")
    private String mm;

    /**
     * 手机号
     */
    @Column(name = "SJH")
    private String sjh;

    /**
     * 创建人
     */
    @Column(name = "CJR")
    private String cjr;

    /**
     * 创建时间
     */
    @Column(name = "CJSJ")
    private String cjsj;

    /**
     * 修改人
     */
    @Column(name = "XGR")
    private String xgr;

    /**
     * 修改时间
     */
    @Column(name = "XGSJ")
    private String xgsj;

    /**
     * 状态
     */
    @Column(name = "ZT")
    private String zt;

    /**
     * 机构代码
     */
    @Column(name = "JGDM")
    private String jgdm;

    /**
     * 姓名
     */
    @Column(name = "XM")
    private String xm;

    /**
     * 类型
     */
    @Column(name = "LX")
    private String lx;

    /**
     * 性别
     */
    @Column(name = "XB")
    private String xb;

    /**
     * 证件号码
     */
    @Column(name = "ZJHM")
    private String zjhm;

    /**
     * 密码有效期
     */
    @Column(name = "MMYXQ")
    private String mmyxq;

    /**
     * 职务
     */
    @Column(name = "ZW")
    private String zw;

    private static final long serialVersionUID = 1L;

    /**
     * 获取用户id
     *
     * @return YHID - 用户id
     */
    public String getYhid() {
        return yhid;
    }

    /**
     * 设置用户id
     *
     * @param yhid 用户id
     */
    public void setYhid(String yhid) {
        this.yhid = yhid;
    }

    /**
     * 获取账号
     *
     * @return ZH - 账号
     */
    public String getZh() {
        return zh;
    }

    /**
     * 设置账号
     *
     * @param zh 账号
     */
    public void setZh(String zh) {
        this.zh = zh;
    }

    /**
     * 获取密码
     *
     * @return MM - 密码
     */
    public String getMm() {
        return mm;
    }

    /**
     * 设置密码
     *
     * @param mm 密码
     */
    public void setMm(String mm) {
        this.mm = mm;
    }

    /**
     * 获取手机号
     *
     * @return SJH - 手机号
     */
    public String getSjh() {
        return sjh;
    }

    /**
     * 设置手机号
     *
     * @param sjh 手机号
     */
    public void setSjh(String sjh) {
        this.sjh = sjh;
    }

    /**
     * 获取创建人
     *
     * @return CJR - 创建人
     */
    public String getCjr() {
        return cjr;
    }

    /**
     * 设置创建人
     *
     * @param cjr 创建人
     */
    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    /**
     * 获取创建时间
     *
     * @return CJSJ - 创建时间
     */
    public String getCjsj() {
        return cjsj;
    }

    /**
     * 设置创建时间
     *
     * @param cjsj 创建时间
     */
    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    /**
     * 获取修改人
     *
     * @return XGR - 修改人
     */
    public String getXgr() {
        return xgr;
    }

    /**
     * 设置修改人
     *
     * @param xgr 修改人
     */
    public void setXgr(String xgr) {
        this.xgr = xgr;
    }

    /**
     * 获取修改时间
     *
     * @return XGSJ - 修改时间
     */
    public String getXgsj() {
        return xgsj;
    }

    /**
     * 设置修改时间
     *
     * @param xgsj 修改时间
     */
    public void setXgsj(String xgsj) {
        this.xgsj = xgsj;
    }

    /**
     * 获取状态
     *
     * @return ZT - 状态
     */
    public String getZt() {
        return zt;
    }

    /**
     * 设置状态
     *
     * @param zt 状态
     */
    public void setZt(String zt) {
        this.zt = zt;
    }

    /**
     * 获取机构代码
     *
     * @return JGDM - 机构代码
     */
    public String getJgdm() {
        return jgdm;
    }

    /**
     * 设置机构代码
     *
     * @param jgdm 机构代码
     */
    public void setJgdm(String jgdm) {
        this.jgdm = jgdm;
    }

    /**
     * 获取姓名
     *
     * @return XM - 姓名
     */
    public String getXm() {
        return xm;
    }

    /**
     * 设置姓名
     *
     * @param xm 姓名
     */
    public void setXm(String xm) {
        this.xm = xm;
    }

    /**
     * 获取类型
     *
     * @return LX - 类型
     */
    public String getLx() {
        return lx;
    }

    /**
     * 设置类型
     *
     * @param lx 类型
     */
    public void setLx(String lx) {
        this.lx = lx;
    }

    /**
     * 获取性别
     *
     * @return XB - 性别
     */
    public String getXb() {
        return xb;
    }

    /**
     * 设置性别
     *
     * @param xb 性别
     */
    public void setXb(String xb) {
        this.xb = xb;
    }

    /**
     * 获取证件号码
     *
     * @return ZJHM - 证件号码
     */
    public String getZjhm() {
        return zjhm;
    }

    /**
     * 设置证件号码
     *
     * @param zjhm 证件号码
     */
    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }

    /**
     * 获取密码有效期
     *
     * @return MMYXQ - 密码有效期
     */
    public String getMmyxq() {
        return mmyxq;
    }

    /**
     * 设置密码有效期
     *
     * @param mmyxq 密码有效期
     */
    public void setMmyxq(String mmyxq) {
        this.mmyxq = mmyxq;
    }

    /**
     * 获取职务
     *
     * @return ZW - 职务
     */
    public String getZw() {
        return zw;
    }

    /**
     * 设置职务
     *
     * @param zw 职务
     */
    public void setZw(String zw) {
        this.zw = zw;
    }

    public enum InnerColumn {
        yhid("YHID"),
        zh("ZH"),
        mm("MM"),
        sjh("SJH"),
        cjr("CJR"),
        cjsj("CJSJ"),
        xgr("XGR"),
        xgsj("XGSJ"),
        zt("ZT"),
        jgdm("JGDM"),
        xm("XM"),
        lx("LX"),
        xb("XB"),
        zjhm("ZJHM"),
        mmyxq("MMYXQ"),
        zw("ZW");

        private final String column;

        public String value() {
            return this.column;
        }

        public String getValue() {
            return this.column;
        }

        InnerColumn(String column) {
            this.column = column;
        }

        public String desc() {
            return this.column + " DESC";
        }

        public String asc() {
            return this.column + " ASC";
        }
    }
}