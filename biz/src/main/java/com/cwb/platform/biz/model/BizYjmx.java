package com.cwb.platform.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户账户佣金变动明细表
 * 佣金新增、提现操作流水表
 */
@Table(name = "biz_yjmx")
public class BizYjmx implements Serializable {
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 用户ID
     */
    @Column(name = "YH_ID")
    private String yhId;

    /**
     * 金额(单位分)
     */
    @Column(name = "ZJ_JE")
    private Double zjJe;

    /**
     * 关联的订单
     */
    @Column(name = "ZJ_ID")
    private String zjId;

    /**
     * 费用方式 ZDCLK0053 (1 佣金 -1 提现)
     */
    @Column(name = "ZJ_FS")
    private String zjFs;

    /**
     * 创建时间
     */
    @Column(name = "CJSJ")
    private String cjsj;

    /**
     * 提现状态 ZDCLK0054 (0、提现冻结  1、 处理成功 2、提现失败) 提现操作默认0 佣金操作默认1  
     */
    @Column(name = "ZJ_ZT")
    private String zjZt;

    /**
     * 备注
     */
    @Column(name = "ZJ_BZ")
    private String zjBz;
    /**
     * 明细类型  ZDCLK0066 1、付款 2、分佣 3、消费 4、提现
     */
    @Column(name = "MX_LX")
    private String mxlx;



    private static final long serialVersionUID = 1L;

    public String getMxlx() {
        return mxlx;
    }

    public void setMxlx(String mxlx) {
        this.mxlx = mxlx;
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
     * 获取金额(单位分)
     *
     * @return ZJ_JE - 金额(单位分)
     */
    public Double getZjJe() {
        return zjJe;
    }

    /**
     * 设置金额(单位分)
     *
     * @param zjJe 金额(单位分)
     */
    public void setZjJe(Double zjJe) {
        this.zjJe = zjJe;
    }

    /**
     * 获取关联的订单
     *
     * @return ZJ_ID - 关联的订单
     */
    public String getZjId() {
        return zjId;
    }

    /**
     * 设置关联的订单
     *
     * @param zjId 关联的订单
     */
    public void setZjId(String zjId) {
        this.zjId = zjId;
    }

    /**
     * 获取费用方式 ZDCLK0053 (1 佣金 -1 提现)
     *
     * @return ZJ_FS - 费用方式 ZDCLK0053 (1 佣金 -1 提现)
     */
    public String getZjFs() {
        return zjFs;
    }

    /**
     * 设置费用方式 ZDCLK0053 (1 佣金 -1 提现)
     *
     * @param zjFs 费用方式 ZDCLK0053 (1 佣金 -1 提现)
     */
    public void setZjFs(String zjFs) {
        this.zjFs = zjFs;
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
     * 获取提现状态 ZDCLK0054 (0、提现冻结  1、 处理成功 2、提现失败) 提现操作默认0 佣金操作默认1  
     *
     * @return ZJ_ZT - 提现状态 ZDCLK0054 (0、提现冻结  1、 处理成功 2、提现失败) 提现操作默认0 佣金操作默认1  
     */
    public String getZjZt() {
        return zjZt;
    }

    /**
     * 设置提现状态 ZDCLK0054 (0、提现冻结  1、 处理成功 2、提现失败) 提现操作默认0 佣金操作默认1  
     *
     * @param zjZt 提现状态 ZDCLK0054 (0、提现冻结  1、 处理成功 2、提现失败) 提现操作默认0 佣金操作默认1  
     */
    public void setZjZt(String zjZt) {
        this.zjZt = zjZt;
    }

    /**
     * 获取备注
     *
     * @return ZJ_BZ - 备注
     */
    public String getZjBz() {
        return zjBz;
    }

    /**
     * 设置备注
     *
     * @param zjBz 备注
     */
    public void setZjBz(String zjBz) {
        this.zjBz = zjBz;
    }

    public enum InnerColumn {
        id("ID"),
        yhId("YH_ID"),
        zjJe("ZJ_JE"),
        zjId("ZJ_ID"),
        zjFs("ZJ_FS"),
        cjsj("CJSJ"),
        zjZt("ZJ_ZT"),
        zjBz("ZJ_BZ");

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