package com.cwb.platform.sys.model;


import com.cwb.platform.sys.util.OrgUtil;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table(name = "SYS_PTJG")
public class SysJg implements Serializable {
    /**
     * 机构代码生成方式：
     * 一级机构只有3位（001，002，003），二级有6位（001001，001002，002001），以此类推
     */
    @Id
    @Column(name = "JGDM")
    private String jgdm;

    /**
     * 机构名称fv
     */
    @Column(name = "JGMC")
    private String jgmc;

    /**
     * 机构类型
     */
    @Column(name = "JGLX")
    private String jglx;

    /**
     * 状态
     */
    @Column(name = "ZT")
    private String zt;

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
     * 管理员
     */
    @Column(name = "GLY")
    private String gly;

    /**
     * 管理员姓名
     */
    @Column(name = "GLYXM")
    private String glyxm;

    /**
     * 父机构代码
     */
    @Column(name = "FJGDM")
    private String fjgdm;

    /**
     * 备注
     */
    @Column(name = "BZ")
    private String bz;

    /**
     * 机构等级
     */
    @Column(name = "JGDJ")
    private Integer jgdj;


    @Transient
    private List<SysJg> children;
    @Transient
    private String title;

    public String getTitle() {
        return jgmc;
    }
    

    public List<SysJg> getChildren() {
        return children == null ? new ArrayList<>() : children;
    }

    public void setChildren(List<SysJg> children) {
        this.children = children;
    }

    private static final long serialVersionUID = 1L;

    public Integer getLevel(){
        return OrgUtil.getLevel(jgdm);
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
     * 获取机构名称
     *
     * @return JGMC - 机构名称
     */
    public String getJgmc() {
        return jgmc;
    }

    /**
     * 设置机构名称
     *
     * @param jgmc 机构名称
     */
    public void setJgmc(String jgmc) {
        this.jgmc = jgmc;
    }

    /**
     * 获取机构类型
     *
     * @return JGLX - 机构类型
     */
    public String getJglx() {
        return jglx;
    }

    /**
     * 设置机构类型
     *
     * @param jglx 机构类型
     */
    public void setJglx(String jglx) {
        this.jglx = jglx;
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
     * 获取管理员
     *
     * @return GLY - 管理员
     */
    public String getGly() {
        return gly;
    }

    /**
     * 设置管理员
     *
     * @param gly 管理员
     */
    public void setGly(String gly) {
        this.gly = gly;
    }

    /**
     * 获取管理员姓名
     *
     * @return GLYXM - 管理员姓名
     */
    public String getGlyxm() {
        return glyxm;
    }

    /**
     * 设置管理员姓名
     *
     * @param glyxm 管理员姓名
     */
    public void setGlyxm(String glyxm) {
        this.glyxm = glyxm;
    }

    /**
     * 获取父机构代码
     *
     * @return FJGDM - 父机构代码
     */
    public String getFjgdm() {
        return fjgdm;
    }

    /**
     * 设置父机构代码
     *
     * @param fjgdm 父机构代码
     */
    public void setFjgdm(String fjgdm) {
        this.fjgdm = fjgdm;
    }

    /**
     * 获取备注
     *
     * @return BZ - 备注
     */
    public String getBz() {
        return bz;
    }

    /**
     * 设置备注
     *
     * @param bz 备注
     */
    public void setBz(String bz) {
        this.bz = bz;
    }

    /**
     * 获取机构等级
     *
     * @return JGDJ - 机构等级
     */
    public Integer getJgdj() {
        return jgdj;
    }

    /**
     * 设置机构等级
     *
     * @param jgdj 机构等级
     */
    public void setJgdj(Integer jgdj) {
        this.jgdj = jgdj;
    }

    public enum InnerColumn {
        jgdm("JGDM"),
        jgmc("JGMC"),
        jglx("JGLX"),
        zt("ZT"),
        cjsj("CJSJ"),
        cjr("CJR"),
        xgr("XGR"),
        xgsj("XGSJ"),
        gly("GLY"),
        glyxm("GLYXM"),
        fjgdm("FJGDM"),
        bz("BZ"),
        jgdj("JGDJ");

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