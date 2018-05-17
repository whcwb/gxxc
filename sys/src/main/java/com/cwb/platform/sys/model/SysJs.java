package com.cwb.platform.sys.model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sys_clk_ptjs")
public class SysJs implements Serializable {
    /**
     * 角色id
     */
    @Id
    @Column(name = "JS_ID")
    private String jsId;

    /**
     * 角色名称
     */
    @Column(name = "JSMC")
    private String jsmc;

    /**
     * 角色类型
     */
    @Column(name = "JSLX")
    private String jslx;

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
     * 说明
     */
    @Column(name = "SM")
    private String sm;

    private static final long serialVersionUID = 1L;
    @Transient
    private boolean _checked;


    public boolean is_checked() {
        return _checked;
    }

    public void set_checked(boolean _checked) {
        this._checked = _checked;
    }
    /**
     * 获取角色id
     *
     * @return JS_ID - 角色id
     */
    public String getJsId() {
        return jsId;
    }

    /**
     * 设置角色id
     *
     * @param jsId 角色id
     */
    public void setJsId(String jsId) {
        this.jsId = jsId;
    }

    /**
     * 获取角色名称
     *
     * @return JSMC - 角色名称
     */
    public String getJsmc() {
        return jsmc;
    }

    /**
     * 设置角色名称
     *
     * @param jsmc 角色名称
     */
    public void setJsmc(String jsmc) {
        this.jsmc = jsmc;
    }

    /**
     * 获取角色类型
     *
     * @return JSLX - 角色类型
     */
    public String getJslx() {
        return jslx;
    }

    /**
     * 设置角色类型
     *
     * @param jslx 角色类型
     */
    public void setJslx(String jslx) {
        this.jslx = jslx;
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

    public enum InnerColumn {
        jsId("JS_ID"),
        jsmc("JSMC"),
        jslx("JSLX"),
        cjsj("CJSJ"),
        cjr("CJR"),
        xgr("XGR"),
        xgsj("XGSJ"),
        zt("ZT"),
        jgdm("JGDM"),
        sm("SM");

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