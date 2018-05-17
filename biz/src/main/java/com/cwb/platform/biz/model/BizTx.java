package com.cwb.platform.biz.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 *  用户提现表
 *  当用户提现后，财务需要审核后，再能进行提现操作
 */
@Table(name = "biz_tx")
public class BizTx implements Serializable {
    /**
     * 主键id
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 用户ID
     */
    @Column(name = "YH_ID")
    private String yhId;

    /**
     * 提现方式(1、微信红包 2、人工转账)
     */
    @Column(name = "TT_FS")
    private String ttFs;

    /**
     * 提现金额(单位分)
     */
    @Column(name = "TT_JE")
    private Double ttJe;

    /**
     * 提现时间
     */
    @Column(name = "TT_SJ")
    private String ttSj;

    /**
     * 提现状态(0该红包待审核 1、红包已收取 2、红包已经发送  3、红包过期未收取到)
     */
    @Column(name = "TT_ZT")
    private String ttZt;

    /**
     * 红包发送的次数
     */
    @Column(name = "TT_HBCS")
    private String ttHbcs;

    /**
     * 提现红包凭证 (用于查询红包是否已经被领取)
     */
    @Column(name = "TT_HBPZ")
    private String ttHbpz;

    /**
     * 父级ID(红包重发时，需要将原始记录的订单ID放到这里。)
     */
    @Column(name = "TT_FJID")
    private String ttFjid;

    /**
     * 审核人
     */
    @Column(name = "TT_SHR")
    private String ttShr;

    /**
     * 提现审核状态(0、待审核 1、审核通过 2、审核拒绝)
     */
    @Column(name = "TT_SHZT")
    private String ttShzt;

    /**
     * 备注
     */
    @Column(name = "TT_BZ")
    private String ttBz;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键id
     *
     * @return ID - 主键id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键id
     *
     * @param id 主键id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取用户ID
     *
     * @return YH_ID - 用户ID
     */
    public String getYhId() {
        return yhId;
    }

    /**
     * 设置用户ID
     *
     * @param yhId 用户ID
     */
    public void setYhId(String yhId) {
        this.yhId = yhId;
    }

    /**
     * 获取提现方式(1、微信红包 2、人工转账)
     *
     * @return TT_FS - 提现方式(1、微信红包 2、人工转账)
     */
    public String getTtFs() {
        return ttFs;
    }

    /**
     * 设置提现方式(1、微信红包 2、人工转账)
     *
     * @param ttFs 提现方式(1、微信红包 2、人工转账)
     */
    public void setTtFs(String ttFs) {
        this.ttFs = ttFs;
    }

    /**
     * 获取提现金额(单位分)
     *
     * @return TT_JE - 提现金额(单位分)
     */
    public Double getTtJe() {
        return ttJe;
    }

    /**
     * 设置提现金额(单位分)
     *
     * @param ttJe 提现金额(单位分)
     */
    public void setTtJe(Double ttJe) {
        this.ttJe = ttJe;
    }

    /**
     * 获取提现时间
     *
     * @return TT_SJ - 提现时间
     */
    public String getTtSj() {
        return ttSj;
    }

    /**
     * 设置提现时间
     *
     * @param ttSj 提现时间
     */
    public void setTtSj(String ttSj) {
        this.ttSj = ttSj;
    }

    /**
     * 获取提现状态(0该红包待审核 1、红包已收取 2、红包已经发送  3、红包过期未收取到)
     *
     * @return TT_ZT - 提现状态(0该红包待审核 1、红包已收取 2、红包已经发送  3、红包过期未收取到)
     */
    public String getTtZt() {
        return ttZt;
    }

    /**
     * 设置提现状态(0该红包待审核 1、红包已收取 2、红包已经发送  3、红包过期未收取到)
     *
     * @param ttZt 提现状态(0该红包待审核 1、红包已收取 2、红包已经发送  3、红包过期未收取到)
     */
    public void setTtZt(String ttZt) {
        this.ttZt = ttZt;
    }

    /**
     * 获取红包发送的次数
     *
     * @return TT_HBCS - 红包发送的次数
     */
    public String getTtHbcs() {
        return ttHbcs;
    }

    /**
     * 设置红包发送的次数
     *
     * @param ttHbcs 红包发送的次数
     */
    public void setTtHbcs(String ttHbcs) {
        this.ttHbcs = ttHbcs;
    }

    /**
     * 获取提现红包凭证 (用于查询红包是否已经被领取)
     *
     * @return TT_HBPZ - 提现红包凭证 (用于查询红包是否已经被领取)
     */
    public String getTtHbpz() {
        return ttHbpz;
    }

    /**
     * 设置提现红包凭证 (用于查询红包是否已经被领取)
     *
     * @param ttHbpz 提现红包凭证 (用于查询红包是否已经被领取)
     */
    public void setTtHbpz(String ttHbpz) {
        this.ttHbpz = ttHbpz;
    }

    /**
     * 获取父级ID(红包重发时，需要将原始记录的订单ID放到这里。)
     *
     * @return TT_FJID - 父级ID(红包重发时，需要将原始记录的订单ID放到这里。)
     */
    public String getTtFjid() {
        return ttFjid;
    }

    /**
     * 设置父级ID(红包重发时，需要将原始记录的订单ID放到这里。)
     *
     * @param ttFjid 父级ID(红包重发时，需要将原始记录的订单ID放到这里。)
     */
    public void setTtFjid(String ttFjid) {
        this.ttFjid = ttFjid;
    }

    /**
     * 获取审核人
     *
     * @return TT_SHR - 审核人
     */
    public String getTtShr() {
        return ttShr;
    }

    /**
     * 设置审核人
     *
     * @param ttShr 审核人
     */
    public void setTtShr(String ttShr) {
        this.ttShr = ttShr;
    }

    /**
     * 获取提现审核状态(0、待审核 1、审核通过 2、审核拒绝)
     *
     * @return TT_SHZT - 提现审核状态(0、待审核 1、审核通过 2、审核拒绝)
     */
    public String getTtShzt() {
        return ttShzt;
    }

    /**
     * 设置提现审核状态(0、待审核 1、审核通过 2、审核拒绝)
     *
     * @param ttShzt 提现审核状态(0、待审核 1、审核通过 2、审核拒绝)
     */
    public void setTtShzt(String ttShzt) {
        this.ttShzt = ttShzt;
    }

    /**
     * 获取备注
     *
     * @return TT_BZ - 备注
     */
    public String getTtBz() {
        return ttBz;
    }

    /**
     * 设置备注
     *
     * @param ttBz 备注
     */
    public void setTtBz(String ttBz) {
        this.ttBz = ttBz;
    }

    public enum InnerColumn {
        id("ID"),
        yhId("YH_ID"),
        ttFs("TT_FS"),
        ttJe("TT_JE"),
        ttSj("TT_SJ"),
        ttZt("TT_ZT"),
        ttHbcs("TT_HBCS"),
        ttHbpz("TT_HBPZ"),
        ttFjid("TT_FJID"),
        ttShr("TT_SHR"),
        ttShzt("TT_SHZT"),
        ttBz("TT_BZ");

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