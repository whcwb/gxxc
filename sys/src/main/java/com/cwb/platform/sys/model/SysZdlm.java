package com.cwb.platform.sys.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Table(name = "sys_zdlm")
public class SysZdlm implements Serializable {
    /**
     * 类目代码
     */
    @Id
    @Column(name = "LMDM")
    private String lmdm;

    /**
     * 类目名称
     */
    @Column(name = "LMMC")
    private String lmmc;

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
     * 修改时间
     */
    @Column(name = "XGSJ")
    private String xgsj;

    /**
     * 修改人
     */
    @Column(name = "XGR")
    private String xgr;

    /**
     * 权重
     */
    @Column(name = "QZ")
    private Double qz;
    @Transient
    private List<SysZdxm> zdxmList;

    public List<SysZdxm> getZdxmList() {
        return zdxmList;
    }

    public void setZdxmList(List<SysZdxm> zdxmList) {
        this.zdxmList = zdxmList;
    }
    private static final long serialVersionUID = 1L;

    /**
     * 获取类目代码
     *
     * @return LMDM - 类目代码
     */
    public String getLmdm() {
        return lmdm;
    }

    /**
     * 设置类目代码
     *
     * @param lmdm 类目代码
     */
    public void setLmdm(String lmdm) {
        this.lmdm = lmdm;
    }

    /**
     * 获取类目名称
     *
     * @return LMMC - 类目名称
     */
    public String getLmmc() {
        return lmmc;
    }

    /**
     * 设置类目名称
     *
     * @param lmmc 类目名称
     */
    public void setLmmc(String lmmc) {
        this.lmmc = lmmc;
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
     * 获取权重
     *
     * @return QZ - 权重
     */
    public Double getQz() {
        return qz;
    }

    /**
     * 设置权重
     *
     * @param qz 权重
     */
    public void setQz(Double qz) {
        this.qz = qz;
    }

    public enum InnerColumn {
        lmdm("LMDM"),
        lmmc("LMMC"),
        cjsj("CJSJ"),
        cjr("CJR"),
        xgsj("XGSJ"),
        xgr("XGR"),
        qz("QZ");

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