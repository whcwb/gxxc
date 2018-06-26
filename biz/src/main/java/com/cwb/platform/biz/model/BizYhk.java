package com.cwb.platform.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Table(name = "biz_yhk")
public class BizYhk implements Serializable {
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
     * 银行卡户主
     */
    @Column(name = "YHK_XM")
    private String yhkXm;

    /**
     * 用户提现 银行卡号
     */
    @Column(name = "YHK_KH")
    private String yhkKh;

    /**
     * 开户行
     */
    @Column(name = "YHK_KHH")
    private String yhkKhh;

    /**
     * 银行卡所属银行
     */
    @Column(name = "YHK_SSYH")
    private String yhkSsyh;

    /**
     * 银行卡上次使用时间
     */
    @Column(name = "YHK_SCSYSJ")
    private String yhkScsysj;
    /**
     * 手机号码
     */
    @Transient
    private String dn;

    private static final long serialVersionUID = 1L;

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

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
     * 获取银行卡户主
     *
     * @return YH_XM - 银行卡户主
     */
    public String getYhkXm() {
        return yhkXm;
    }

    /**
     * 设置银行卡户主
     *
     * @param yhkXm 银行卡户主
     */
    public void setYhkXm(String yhkXm) {
        this.yhkXm = yhkXm;
    }

    /**
     * 获取用户提现 银行卡号
     *
     * @return YHK_KH - 用户提现 银行卡号
     */
    public String getYhkKh() {
        return yhkKh;
    }

    /**
     * 设置用户提现 银行卡号
     *
     * @param yhkKh 用户提现 银行卡号
     */
    public void setYhkKh(String yhkKh) {
        this.yhkKh = yhkKh;
    }

    /**
     * 获取开户行
     *
     * @return YHK_KHH - 开户行
     */
    public String getYhkKhh() {
        return yhkKhh;
    }

    /**
     * 设置开户行
     *
     * @param yhkKhh 开户行
     */
    public void setYhkKhh(String yhkKhh) {
        this.yhkKhh = yhkKhh;
    }

    /**
     * 获取银行卡所属银行
     *
     * @return YHK_SSYH - 银行卡所属银行
     */
    public String getYhkSsyh() {
        return yhkSsyh;
    }

    /**
     * 设置银行卡所属银行
     *
     * @param yhkSsyh 银行卡所属银行
     */
    public void setYhkSsyh(String yhkSsyh) {
        this.yhkSsyh = yhkSsyh;
    }

    /**
     * 获取银行卡上次使用时间
     *
     * @return YHK_SCSYSJ - 银行卡上次使用时间
     */
    public String getYhkScsysj() {
        return yhkScsysj;
    }

    /**
     * 设置银行卡上次使用时间
     *
     * @param yhkScsysj 银行卡上次使用时间
     */
    public void setYhkScsysj(String yhkScsysj) {
        this.yhkScsysj = yhkScsysj;
    }

    public enum InnerColumn {
        id("ID"),
        yhId("YH_ID"),
        yhkXm("YHK_XM"),
        yhkKh("YHK_KH"),
        yhkKhh("YHK_KHH"),
        yhkSsyh("YHK_SSYH"),
        yhkScsysj("YHK_SCSYSJ");

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