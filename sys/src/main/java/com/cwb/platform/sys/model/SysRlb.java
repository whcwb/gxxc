package com.cwb.platform.sys.model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sys_rlb")
public class SysRlb implements Serializable {
    /**
     * 日期代码
     */
    @Id
    @Column(name = "RQDM")
    private String rqdm;

    /**
     * 星期
     */
    @Column(name = "XQ")
    private Integer xq;

    /**
     * 状态
     */
    @Column(name = "ZT")
    private String zt;

    /**
     * 日期
     */
    @Column(name = "RQ")
    private String rq;

    private static final long serialVersionUID = 1L;

    /**
     * 获取日期代码
     *
     * @return RQDM - 日期代码
     */
    public String getRqdm() {
        return rqdm;
    }

    /**
     * 设置日期代码
     *
     * @param rqdm 日期代码
     */
    public void setRqdm(String rqdm) {
        this.rqdm = rqdm;
    }

    /**
     * 获取星期
     *
     * @return XQ - 星期
     */
    public Integer getXq() {
        return xq;
    }

    /**
     * 设置星期
     *
     * @param xq 星期
     */
    public void setXq(Integer xq) {
        this.xq = xq;
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
     * 获取日期
     *
     * @return RQ - 日期
     */
    public String getRq() {
        return rq;
    }

    /**
     * 设置日期
     *
     * @param rq 日期
     */
    public void setRq(String rq) {
        this.rq = rq;
    }

    public enum InnerColumn {
        rqdm("RQDM"),
        xq("XQ"),
        zt("ZT"),
        rq("RQ");

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