package com.cwb.platform.sys.model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sys_yh_js")
public class SysYhJs implements Serializable {
    /**
     * 用户角色关联表id
     */
    @Id
    @Column(name = "YHJS_ID")
    private String yhjsId;

    /**
     * 用户id
     */
    @Column(name = "YH_ID")
    private String yhId;

    /**
     * 角色id
     */
    @Column(name = "JS_ID")
    private String jsId;

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

    private static final long serialVersionUID = 1L;

    /**
     * 获取用户角色关联表id
     *
     * @return YHJS_ID - 用户角色关联表id
     */
    public String getYhjsId() {
        return yhjsId;
    }

    /**
     * 设置用户角色关联表id
     *
     * @param yhjsId 用户角色关联表id
     */
    public void setYhjsId(String yhjsId) {
        this.yhjsId = yhjsId;
    }

    /**
     * 获取用户id
     *
     * @return YH_ID - 用户id
     */
    public String getYhId() {
        return yhId;
    }

    /**
     * 设置用户id
     *
     * @param yhId 用户id
     */
    public void setYhId(String yhId) {
        this.yhId = yhId;
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

    public enum InnerColumn {
        yhjsId("YHJS_ID"),
        yhId("YH_ID"),
        jsId("JS_ID"),
        cjr("CJR"),
        cjsj("CJSJ");

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