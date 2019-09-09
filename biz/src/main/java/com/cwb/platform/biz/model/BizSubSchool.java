package com.cwb.platform.biz.model;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "BIZ_SUB_SCHOOL")
public class BizSubSchool {

    @Id
    private String id;

    /**
     * 代培点名称
     */
    @Column(name = "SUB_NAME")
    private  String subName;

    /**
     * 负责人姓名
     */
    @Column(name = "SUB_FZ")
    private String subFz;

    /**
     * 负责人手机号码
     */
    @Column(name = "SUB_PHONE")
    private String subPhone;

    /**
     * 负责人 openID
     */
    @Column(name = "SUB_OPENID")
    private String subOpenid;

    /**
     *  用户id
     */
    @Column(name = "YH_ID")
    private String yhId;

    /**
     * 代培点经度
     */
    @Column(name = "SUB_LNG")
    private Double subLng;

    /**
     * 代培点纬度
     */
    @Column(name = "SUB_LAT")
    private Double subLat;

    /**
     * 代培点地址
     */
    @Column(name = "SUB_ADDr")
    private String subAddr;

    /**
     * 代培点图片
     */
    @Column(name = "SUB_IMG")
    private String subImg;

    private String cjsj;

    private String cjr;

    public enum  InnerColumn{
        id("ID"),
        subName("SUB_NAME"),
        subFz("SUB_FZ"),
        subPhone("SUB_PHONE"),
        subOpenid("SUB_OPENID"),
        yhId("YH_ID"),
        subLng("SUB_LNG"),
        subLat("SUB_Lat"),
        subAddr("SUB_ADDR"),
        subImg("SUB_IMG"),
        cjsj("CJSJ"),
        cjr("CJR");

        private final String column;

        InnerColumn(String column) {
            this.column = column;
        }

        public String value(){
            return this.column;
        }
        public String getValue(){
            return this.column;
        }
        public String asc() {
            return this.column + " asc";
        }
        public String desc(){
            return this.column + " desc";
        }






    }











}
