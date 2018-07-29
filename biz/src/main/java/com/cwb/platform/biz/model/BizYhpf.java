package com.cwb.platform.biz.model;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "biz_yhpf")
public class BizYhpf implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "JDBC")
    private String id;

    /**
     * 学员ID
     */
    @Column(name = "YH_ID")
    private String yhId;

    /**
     * 教练ID是biz_ptyh的主键ID
     */
    @Column(name = "JL_ID")
    private String jlId;

    /**
     * 用户评分的分值
     */
    @Column(name = "YH_FZ")
    private Integer yhFz;

    /**
     * 用户评论
     */
    @Column(name = "YH_PL")
    private String yhPl;

    /**
     * 审核状态 0未审核  1审核通过  2、审核失败
     */
    @Column(name = "AUDIT_TYPE")
    private Integer auditType;

    /**
     * 审核描述
     */
    @Column(name = "AUDIM_MSG")
    private String audimMsg;

    /**
     * 备注
     */
    @Column(name = "REMARKS")
    private String remarks;

    /**
     * 用户名称(用户别名)
     */
    @Column(name = "YH_BM")
    private String yhBm;

    /**
     * 教练姓名
     */
    @Column(name = "JL_XM")
    private String jlXm;
    /**
     * 用户的受理阶段
     * 用户的受理阶段(0:受理阶段  1：科目一阶段  2：科目二阶段 3：科目三阶段 4：科目四阶段)
     */
    @Column(name = "SL_TYPE")
    private String slType;


    /**
     * 当前学员
     */
    @Column(name = "SL_TYPE")
    private Integer slType;

    /**
     * 教练平均分
     */
    @Transient
    private String jlPjf;


    private static final long serialVersionUID = 1L;

    public String getSlType() {
        return slType;
    }

    public void setSlType(String slType) {
        this.slType = slType;
    }

    public String getJlPjf() {
        return jlPjf;
    }

    public void setJlPjf(String jlPjf) {
        this.jlPjf = jlPjf;
    }

    public Integer getSlType() {
        return slType;
    }

    public void setSlType(Integer slType) {
        this.slType = slType;
    }

    /**
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取学员ID
     *
     * @return YH_ID - 学员ID
     */
    public String getYhId() {
        return yhId;
    }

    /**
     * 设置学员ID
     *
     * @param yhId 学员ID
     */
    public void setYhId(String yhId) {
        this.yhId = yhId;
    }

    /**
     * 获取教练ID是biz_ptyh的主键ID
     *
     * @return JL_ID - 教练ID是biz_ptyh的主键ID
     */
    public String getJlId() {
        return jlId;
    }

    /**
     * 设置教练ID是biz_ptyh的主键ID
     *
     * @param jlId 教练ID是biz_ptyh的主键ID
     */
    public void setJlId(String jlId) {
        this.jlId = jlId;
    }

    /**
     * 获取用户评分的分值
     *
     * @return YH_FZ - 用户评分的分值
     */
    public Integer getYhFz() {
        return yhFz;
    }

    /**
     * 设置用户评分的分值
     *
     * @param yhFz 用户评分的分值
     */
    public void setYhFz(Integer yhFz) {
        this.yhFz = yhFz;
    }

    /**
     * 获取用户评论
     *
     * @return YH_PL - 用户评论
     */
    public String getYhPl() {
        return yhPl;
    }

    /**
     * 设置用户评论
     *
     * @param yhPl 用户评论
     */
    public void setYhPl(String yhPl) {
        this.yhPl = yhPl;
    }

    /**
     * 获取审核状态 0未审核  1审核通过  2、审核失败
     *
     * @return AUDIT_TYPE - 审核状态 0未审核  1审核通过  2、审核失败
     */
    public Integer getAuditType() {
        return auditType;
    }

    /**
     * 设置审核状态 0未审核  1审核通过  2、审核失败
     *
     * @param auditType 审核状态 0未审核  1审核通过  2、审核失败
     */
    public void setAuditType(Integer auditType) {
        this.auditType = auditType;
    }

    /**
     * 获取审核描述
     *
     * @return AUDIM_MSG - 审核描述
     */
    public String getAudimMsg() {
        return audimMsg;
    }

    /**
     * 设置审核描述
     *
     * @param audimMsg 审核描述
     */
    public void setAudimMsg(String audimMsg) {
        this.audimMsg = audimMsg;
    }

    /**
     * 获取备注
     *
     * @return REMARKS - 备注
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设置备注
     *
     * @param remarks 备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 获取用户名称(用户别名)
     *
     * @return YH_BM - 用户名称(用户别名)
     */
    public String getYhBm() {
        return yhBm;
    }

    /**
     * 设置用户名称(用户别名)
     *
     * @param yhBm 用户名称(用户别名)
     */
    public void setYhBm(String yhBm) {
        this.yhBm = yhBm;
    }

    /**
     * 获取教练姓名
     *
     * @return JL_XM - 教练姓名
     */
    public String getJlXm() {
        return jlXm;
    }

    /**
     * 设置教练姓名
     *
     * @param jlXm 教练姓名
     */
    public void setJlXm(String jlXm) {
        this.jlXm = jlXm;
    }

    public enum InnerColumn {
        id("ID"),
        yhId("YH_ID"),
        jlId("JL_ID"),
        yhFz("YH_FZ"),
        yhPl("YH_PL"),
        auditType("AUDIT_TYPE"),
        audimMsg("AUDIM_MSG"),
        remarks("REMARKS"),
        yhBm("YH_BM"),
        jlXm("JL_XM");

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