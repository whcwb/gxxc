package com.cwb.platform.biz.mapper;

import com.cwb.platform.biz.model.BizUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BizUserMapper extends Mapper<BizUser> {

    @Update("<script>" +
            " UPDATE BIZ_USER SET YH_JLID = #{jlId}" +
            " where YH_ID IN " +
            " <foreach collection='list' item='item' open='(' close=')' separator=','>" +
            " #{item} " +
            "</foreach>" +
            " AND YH_JLID IS NULL" +
            "</script>")
    void updateJlid(@Param("list") List<String> list,@Param("jlId") String jlId);

    @Select(" <script>" +
            " SELECT ID FROM biz_ptyh WHERE " +
            " YH_LX='1' AND YH_ZT='1' AND DD_SFJX='1' AND YH_IXY_SFFP='0' AND YH_SFSD='0' " +
            " AND ID IN " +
            "<foreach collection='list' item='item' open='(' close=')' separator=','>" +
            " #{item} " +
            "</foreach>" +
            "</script>")
    List<String> getYhIds(@Param("list") List<String> ids);
    @Update("<script>" +
            " UPDATE BIZ_USER SET YH_JLID1 = #{jlId}" +
            " where YH_ID IN " +
            " <foreach collection='list' item='item' open='(' close=')' separator=','>" +
            " #{item} " +
            "</foreach>" +
            " AND YH_JLID1 IS NULL" +
            "</script>")
    void updateJlid1(@Param("list")  List<String> list, @Param("jlId") String jlId);
    @Update("<script>" +
            " UPDATE BIZ_USER SET YH_JLID2 = #{jlId}" +
            " where YH_ID IN " +
            " <foreach collection='list' item='item' open='(' close=')' separator=','>" +
            " #{item} " +
            "</foreach>" +
            " AND YH_JLID2 IS NULL" +
            "</script>")
    void updateJlid2(@Param("list") List<String> list, @Param("jlId") String jlId);

    @Update("<script>" +
            " UPDATE BIZ_USER SET YH_JLID3 = #{jlId}" +
            " where YH_ID IN " +
            " <foreach collection='list' item='item' open='(' close=')' separator=','>" +
            " #{item} " +
            "</foreach>" +
            " AND YH_JLID3 IS NULL" +
            "</script>")
    void updateJlid3(@Param("list") List<String> list, @Param("jlId") String jlId);

    @Select(" <script> " +
            " SELECT u.YH_ID FROM BIZ_USER u , BIZ_PTYH p where " +
            " <choose>" +
            " <when test=\"grade == 1 \"> " +
            " u.YH_SJID = #{userid} " +
            " </when> " +
            " <when test=\"grade == 2 \"> " +
            " u.YH_SSJID = #{userid} " +
            " </when> " +
            " <otherwise > " +
            " (u.YH_SJID =#{userid} OR u.YH_SSJID =#{userid} ) " +
            " </otherwise> " +
            "</choose>" +
            " <if test=\"yhlx !=null  \"> " +
            " AND p.YH_LX = #{yhlx} " +
            " </if> " +
            " <if test=\"sfjf !=null   \"> " +
            " AND p.DD_SFJX = #{sfjf} " +
            " </if> " +
            " <if test= \"yhxm !=null  \"> " +
            " AND u.YH_XM like '%${yhxm}%' " +
            " </if> " +
            " AND u.YH_ID = p.ID ORDER BY u.CJSJ " +
            " </script> ")
    List<String> getYhIdByTerm(@Param("grade")String grade, @Param("userid") String userId, @Param("yhlx")String yhlx, @Param("sfjf") String sfjf, @Param("yhxm")String yhXm);

}