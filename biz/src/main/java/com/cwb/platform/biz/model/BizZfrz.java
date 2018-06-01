package com.cwb.platform.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "biz_zfrz")
public class BizZfrz {
    /**
     * 主键id
     */
    @Id
    @Column(name = "ID")
    private String id;
    /**
     * 支付类型
     */
    @Column(name = "ZF_LX")
    private String zfLx;
    /**
     * 支付金额
     */
    @Column(name = "ZF_JE")
    private String zfJe;
    /**
     * 订单id
     */
    @Column(name = "DD_ID")
    private String ddId;
    /**
     * 支付凭证id
     */
    @Column(name = "ZF_PZ")
    private String zfPz;
    /**
     * 支付时间
     */
    @Column(name = "ZF_SJ")
    private String zfSj;
    /**
     * 支付原始报文
     */
    @Column(name = "ZF_BW")
    private String zfBw;
    /**
     * 创建时间
     */
    @Column(name = "CJSJ")
    private String cjsj;
    /**
     * 支付验证状态
     */
    @Column(name = "ZF_YZZT")
    private String zfYzzt;
    /**
     * 支付处理状态
     */
    @Column(name = "ZF_CLZT")
    private String zfClzt;

    /**
     * 获取主键id
     * @return 主键id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键id
     * @param id 主键id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取支付类型
     * @return
     */
    public String getZfLx() {
        return zfLx;
    }

    /**
     * 设置支付类型
     * @param zfLx
     */
    public void setZfLx(String zfLx) {
        this.zfLx = zfLx;
    }

    /**
     * 获取支付金额
     * @return
     */
    public String getZfJe() {
        return zfJe;
    }
    /**
     * 设置支付金额
     */
    public void setZfJe(String zfJe) {
        this.zfJe = zfJe;
    }

    /**
     * 获取订单id
     * @return
     */
    public String getDdId() {
        return ddId;
    }

    /**
     * 设置订单id
     * @param ddId
     */
    public void setDdId(String ddId) {
        this.ddId = ddId;
    }

    /**
     * 获取支付凭证
     * @return
     */
    public String getZfPz() {
        return zfPz;
    }
    /**
     * 设置支付凭证
     */
    public void setZfPz(String zfPz) {
        this.zfPz = zfPz;
    }

    /**
     * 获取支付时间
     * @return
     */
    public String getZfSj() {
        return zfSj;
    }

    /**
     * 设置支付时间
     * @param zfSj
     */
    public void setZfSj(String zfSj) {
        this.zfSj = zfSj;
    }

    /**
     * 获取支付报文
     * @return
     */
    public String getZfBw() {
        return zfBw;
    }

    /**
     * 设置支付报文
     * @param zfBw
     */
    public void setZfBw(String zfBw) {
        this.zfBw = zfBw;
    }

    /**
     * 获取创建时间
     * @return
     */
    public String getCjsj() {
        return cjsj;
    }

    /**
     * 设置创建时间
     * @param cjsj
     */
    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    /**
     * 获取支付验证状态
     * @return
     */
    public String getZfYzzt() {
        return zfYzzt;
    }

    /**
     * 设置支付验证状态
     * @param zfYzzt
     */
    public void setZfYzzt(String zfYzzt) {
        this.zfYzzt = zfYzzt;
    }

    /**
     * 获取支付处理状态
     * @return
     */
    public String getZfClzt() {
        return zfClzt;
    }

    /**
     * 设置处理状态
     * @param zfClzt
     */
    public void setZfClzt(String zfClzt) {
        this.zfClzt = zfClzt;
    }

    public enum InnerColumn {
        id("ID"),
        zfLx("ZF_LX"),
        zfJe("ZF_JE"),
        ddId("DD_ID"),
        zfPz("ZF_PZ"),
        zfSj("ZF_SJ"),
        zfBw("ZF_BW"),
        cjsj("CJSJ"),
        zfYzzt("ZF_YZZT"),
        zfClzt("ZF_CLZT");

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


