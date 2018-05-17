package com.cwb.platform.sys.model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sys_ptrz")
public class SysRz implements Serializable {
    /**
     * 日志id
     */
    @Id
    @Column(name = "RZ_ID")
    private String rzId;

    /**
     * 操作类型
     */
    @Column(name = "CZLX")
    private String czlx;

    /**
     * 操作时间
     */
    @Column(name = "CZSJ")
    private String czsj;

    /**
     * 操作人
     */
    @Column(name = "CZR")
    private String czr;

    /**
     * 对象id
     */
    @Column(name = "DX_ID")
    private String dxId;

    /**
     * 对象类型
     */
    @Column(name = "DXLX")
    private String dxlx;

    /**
     * 参数
     */
    @Column(name = "CS")
    private String cs;

    /**
     * 结果
     */
    @Column(name = "JG")
    private String jg;

    /**
     * 执行时间
     */
    @Column(name = "ZXSJ")
    private Integer zxsj;

    /**
     * 说明
     */
    @Column(name = "SM")
    private String sm;

    /**
     * 方法
     */
    @Column(name = "FF")
    private String ff;

    private static final long serialVersionUID = 1L;

    /**
     * 获取日志id
     *
     * @return RZ_ID - 日志id
     */
    public String getRzId() {
        return rzId;
    }

    /**
     * 设置日志id
     *
     * @param rzId 日志id
     */
    public void setRzId(String rzId) {
        this.rzId = rzId;
    }

    /**
     * 获取操作类型
     *
     * @return CZLX - 操作类型
     */
    public String getCzlx() {
        return czlx;
    }

    /**
     * 设置操作类型
     *
     * @param czlx 操作类型
     */
    public void setCzlx(String czlx) {
        this.czlx = czlx;
    }

    /**
     * 获取操作时间
     *
     * @return CZSJ - 操作时间
     */
    public String getCzsj() {
        return czsj;
    }

    /**
     * 设置操作时间
     *
     * @param czsj 操作时间
     */
    public void setCzsj(String czsj) {
        this.czsj = czsj;
    }

    /**
     * 获取操作人
     *
     * @return CZR - 操作人
     */
    public String getCzr() {
        return czr;
    }

    /**
     * 设置操作人
     *
     * @param czr 操作人
     */
    public void setCzr(String czr) {
        this.czr = czr;
    }

    /**
     * 获取对象id
     *
     * @return DX_ID - 对象id
     */
    public String getDxId() {
        return dxId;
    }

    /**
     * 设置对象id
     *
     * @param dxId 对象id
     */
    public void setDxId(String dxId) {
        this.dxId = dxId;
    }

    /**
     * 获取对象类型
     *
     * @return DXLX - 对象类型
     */
    public String getDxlx() {
        return dxlx;
    }

    /**
     * 设置对象类型
     *
     * @param dxlx 对象类型
     */
    public void setDxlx(String dxlx) {
        this.dxlx = dxlx;
    }

    /**
     * 获取参数
     *
     * @return CS - 参数
     */
    public String getCs() {
        return cs;
    }

    /**
     * 设置参数
     *
     * @param cs 参数
     */
    public void setCs(String cs) {
        this.cs = cs;
    }

    /**
     * 获取结果
     *
     * @return JG - 结果
     */
    public String getJg() {
        return jg;
    }

    /**
     * 设置结果
     *
     * @param jg 结果
     */
    public void setJg(String jg) {
        this.jg = jg;
    }

    /**
     * 获取执行时间
     *
     * @return ZXSJ - 执行时间
     */
    public Integer getZxsj() {
        return zxsj;
    }

    /**
     * 设置执行时间
     *
     * @param zxsj 执行时间
     */
    public void setZxsj(Integer zxsj) {
        this.zxsj = zxsj;
    }

    /**
     * 获取说明
     *
     * @return SM - 说明
     */
    public String getSm() {
        return sm;
    }

    /**
     * 设置说明
     *
     * @param sm 说明
     */
    public void setSm(String sm) {
        this.sm = sm;
    }

    /**
     * 获取方法
     *
     * @return FF - 方法
     */
    public String getFf() {
        return ff;
    }

    /**
     * 设置方法
     *
     * @param ff 方法
     */
    public void setFf(String ff) {
        this.ff = ff;
    }

    public enum InnerColumn {
        rzId("RZ_ID"),
        czlx("CZLX"),
        czsj("CZSJ"),
        czr("CZR"),
        dxId("DX_ID"),
        dxlx("DXLX"),
        cs("CS"),
        jg("JG"),
        zxsj("ZXSJ"),
        sm("SM"),
        ff("FF");

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