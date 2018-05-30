package com.cwb.platform.biz.mapper;

import com.cwb.platform.sys.model.BizPtyh;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BizPtyhMapper extends Mapper<BizPtyh> {

    @Select("<script> " +
            " SELECT * FROM BIZ_PTYH WHERE YH_ZT = '1' AND YH_LX = '2' " +
            "<if test='list !=null'>" +
            "AND " +
            "<foreach collection='list' item='item' open='(' close=')' separator='or'>" +
            "ID = #{item} " +
            "</foreach> " +
            "</if>" +
            "<if test='name != null'>" +
            " AND YH_XM LIKE '%${name}%' " +
            "</if>" +
            "<if test='phone !=null'> " +
            " AND YH_ZH LIKE '%${phone}%' " +
            "</if> " +
            "</script>")
    /*@Results({
            @Result(property = "yhZh", column = "YH_ZH"),
            @Result(property = "yhMm", column = "YH_MM"),
            @Result(property = "yhCjr",column = "YH_CJR"),
            @Result(property = "yhXgr",column = "YH_XGR"),
            @Result(property = "xgsj", column = "XGSJ"),
            @Result(property = "yhXm", column = "YH_XM"),
            @Result(property = "yhLx",column = "YH_LX"),
            @Result(property = "yhXb",column = "YH_XB"),
            @Result(property = "yhZjhm", column = "YH_ZJHM"),
            @Result(property = "yhMmyxq", column = "YH_MMYXQ"),
            @Result(property = "yhZt",column = "YH_ZT"),
            @Result(property = "ddSfjx",column = "DD_SFJX"),
            @Result(property = "yhOpenId", column = "YH_OPENID"),
            @Result(property = "yhAlipayId", column = "YH_ALIPAYID"),
            @Result(property = "yhTx",column = "YH_TX"),
            @Result(property = "yhBm",column = "YH_BM"),
            @Result(property = "yhZsyqm", column = "YH_ZSYQM"),
            @Result(property = "yhYyyqm", column = "YH_YYYQM"),
            @Result(property = "yhZsyqmImg",column = "YH_ZSYQM_IMG"),
            @Result(property = "yhIxySffp",column = "YH_IXY_SFFP"),
            @Result(property = "yhSfyjz", column = "YH_SFYJZ"),
            @Result(property = "yhFpms", column = "YH_FPMS"),
            @Result(property = "yhSfsd",column = "YH_SFSD"),
    })*/
    List<BizPtyh> getJls(@Param("name") String name, @Param("phone") String phone,@Param("list") List<String> list);
}