package com.cwb.platform.sys.model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sys_js_fw")
public class SysJsFw implements Serializable {
    /**
     * id
     */
    @Id
    private String id;

    /**
     * 角色代码
     */
    @Column(name = "JSDM")
    private String jsdm;

    /**
     * 服务代码
     */
    @Column(name = "FWDM")
    private String fwdm;

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

    private static final long serialVersionUID = 1L;

    /**
     * 获取id
     *
     * @return id - id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取角色代码
     *
     * @return JSDM - 角色代码
     */
    public String getJsdm() {
        return jsdm;
    }

    /**
     * 设置角色代码
     *
     * @param jsdm 角色代码
     */
    public void setJsdm(String jsdm) {
        this.jsdm = jsdm;
    }

    /**
     * 获取服务代码
     *
     * @return FWDM - 服务代码
     */
    public String getFwdm() {
        return fwdm;
    }

    /**
     * 设置服务代码
     *
     * @param fwdm 服务代码
     */
    public void setFwdm(String fwdm) {
        this.fwdm = fwdm;
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

    public enum InnerColumn {
        id("id"),
        jsdm("JSDM"),
        fwdm("FWDM"),
        cjsj("CJSJ"),
        cjr("CJR");

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