package com.cwb.platform.biz.model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "biz_exam_place")
public class BizExamPlace implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 地址
     */
    private String address;

    /**
     * 创建时间
     */
    private String cjsj;

    /**
     * 创建人
     */
    private String cjr;

    /**
     * 考试科目
     */
    private String kskm;

    /**
     * 经度
     */
    private Double lat;
    /**
     * 纬度
     */
    private Double lng;

    private static final long serialVersionUID = 1L;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    /**
     * @return id
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
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取地址
     *
     * @return address - 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取创建时间
     *
     * @return cjsj - 创建时间
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
     * @return cjr - 创建人
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
     * 获取考试科目
     *
     * @return kskm - 考试科目
     */
    public String getKskm() {
        return kskm;
    }

    /**
     * 设置考试科目
     *
     * @param kskm 考试科目
     */
    public void setKskm(String kskm) {
        this.kskm = kskm;
    }

    public enum InnerColumn {
        id("id"),
        name("name"),
        address("address"),
        cjsj("cjsj"),
        cjr("cjr"),
        kskm("kskm");

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