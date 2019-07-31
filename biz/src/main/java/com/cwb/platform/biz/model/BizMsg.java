package com.cwb.platform.biz.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Table(name="BIZ_MSG")
public class BizMsg {

    @Id
    private String id;

    private String cjsj;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name")
    private String userName;

    private String content;

    private String type;

    public enum InnerColumn{
        id("ID"),
        cjsj("CJSJ"),
        userId("user_id"),
        userName("user_name"),
        content("content"),
        type("type");

        private final String column;

        InnerColumn( String column) {
            this.column = column;
        }

        public String getValue(){
            return this.column;
        }

        public String value(){
            return this.column;
        }

        public String desc(){
            return this.column + " DESC";
        }

        public String asc(){
            return this.column + " ASC";
        }


    }



}
