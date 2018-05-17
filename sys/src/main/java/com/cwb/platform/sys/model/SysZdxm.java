package com.cwb.platform.sys.model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sys_zdxm")
public class SysZdxm implements Serializable {
    /**
     * 字典id
     */
    @Id
    @Column(name = "ZD_ID")
    private String zdId;

    /**
     * 字典类目代码
     */
    @Column(name = "ZDLMDM")
    private String zdlmdm;

    /**
     * 字典代码
     */
    @Column(name = "ZDDM")
    private String zddm;

    /**
     * 字典名称
     */
    @Column(name = "ZDMC")
    private String zdmc;

    /**
     * 权重
     */
    @Column(name = "QZ")
    private Float qz;

    /**
     * 创建时间
     */
    @Column(name = "CJSJ")
    private String cjsj;

    /**
     * 创建人
     */
    @Column(name = "CJR")
    private String cjr;

    /**
     * 备用1
     */
    @Column(name = "BY1")
    private String by1;

    /**
     * 备用2
     */
    @Column(name = "BY2")
    private String by2;

    /**
     * 备用3
     */
    @Column(name = "BY3")
    private String by3;

    private static final long serialVersionUID = 1L;

    /**
     * 获取字典id
     *
     * @return ZD_ID - 字典id
     */
    public String getZdId() {
        return zdId;
    }

    /**
     * 设置字典id
     *
     * @param zdId 字典id
     */
    public void setZdId(String zdId) {
        this.zdId = zdId;
    }

    /**
     * 获取字典类目代码
     *
     * @return ZDLMDM - 字典类目代码
     */
    public String getZdlmdm() {
        return zdlmdm;
    }

    /**
     * 设置字典类目代码
     *
     * @param zdlmdm 字典类目代码
     */
    public void setZdlmdm(String zdlmdm) {
        this.zdlmdm = zdlmdm;
    }

    /**
     * 获取字典代码
     *
     * @return ZDDM - 字典代码
     */
    public String getZddm() {
        return zddm;
    }

    /**
     * 设置字典代码
     *
     * @param zddm 字典代码
     */
    public void setZddm(String zddm) {
        this.zddm = zddm;
    }

    /**
     * 获取字典名称
     *
     * @return ZDMC - 字典名称
     */
    public String getZdmc() {
        return zdmc;
    }

    /**
     * 设置字典名称
     *
     * @param zdmc 字典名称
     */
    public void setZdmc(String zdmc) {
        this.zdmc = zdmc;
    }

    /**
     * 获取权重
     *
     * @return QZ - 权重
     */
    public Float getQz() {
        return qz;
    }

    /**
     * 设置权重
     *
     * @param qz 权重
     */
    public void setQz(Float qz) {
        this.qz = qz;
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
     * 获取备用1
     *
     * @return BY1 - 备用1
     */
    public String getBy1() {
        return by1;
    }

    /**
     * 设置备用1
     *
     * @param by1 备用1
     */
    public void setBy1(String by1) {
        this.by1 = by1;
    }

    /**
     * 获取备用2
     *
     * @return BY2 - 备用2
     */
    public String getBy2() {
        return by2;
    }

    /**
     * 设置备用2
     *
     * @param by2 备用2
     */
    public void setBy2(String by2) {
        this.by2 = by2;
    }

    /**
     * 获取备用3
     *
     * @return BY3 - 备用3
     */
    public String getBy3() {
        return by3;
    }

    /**
     * 设置备用3
     *
     * @param by3 备用3
     */
    public void setBy3(String by3) {
        this.by3 = by3;
    }

    public enum InnerColumn {
        zdId("ZD_ID"),
        zdlmdm("ZDLMDM"),
        zddm("ZDDM"),
        zdmc("ZDMC"),
        qz("QZ"),
        cjsj("CJSJ"),
        cjr("CJR"),
        by1("BY1"),
        by2("BY2"),
        by3("BY3");

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