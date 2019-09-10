package com.cwb.platform.biz.model;

import com.cwb.platform.sys.model.BizPtyh;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Table(name = "BIZ_FP")
public class BizFp {

    @Id
    private String id;

    @Column(name = "YH_ID")
    private String yhId;

    private String fpms;

    @Column(name = "SUB_SCHOOL_ID")
    private String subSchoolId;

    @Column(name = "SUB_SCHOOL_NAME")
    private String subSchoolName;

    private String cjsj;

    private String cjr;

    private String sfdk;

    private String fpkm;

    @Transient
    private BizPtyh yh;

    public enum InnerColumn{
        id("ID"),
        yhId("YH_ID"),
        fpms("FPMS"),
        subSchoolId("SUB_SCHOOL_ID"),
        subSchoolName("SUB_SCHOOL_NAME"),
        cjsj("CJSJ"),
        cjr("CJR"),
        sfdk("SFDK"),
        fpkm("FPKM");

        public final String column;

        InnerColumn(String column) {
            this.column = column;
        }
        public String value(){
            return this.column;
        }
        public String getValue(){
            return this.column;
        }
        public String asc(){
            return this.column + " asc";
        }
        public String desc(){
            return this.column + " desc";
        }
    }


}
