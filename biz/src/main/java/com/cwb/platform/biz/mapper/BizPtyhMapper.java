package com.cwb.platform.biz.mapper;

import com.cwb.platform.sys.model.BizPtyh;
import org.apache.ibatis.annotations.*;
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
    List<BizPtyh> getJls(@Param("name") String name, @Param("phone") String phone,@Param("list") List<String> list);

    @Update("<script>" +
            " UPDATE BIZ_PTYH SET YH_IXY_SFFP = '1' , YH_FPMS = #{yhFpms} " +
            " where YH_ID IN " +
            " <foreach collection='list' item='item' open='(' close=')' separator=','>" +
            " #{item} " +
            "</foreach>" +
            "</script>")
    void updateJlFp(@Param("list") List<String> list,@Param("yhFpms") String yhFpms);

}