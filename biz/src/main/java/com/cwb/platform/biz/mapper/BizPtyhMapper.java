package com.cwb.platform.biz.mapper;

import com.cwb.platform.sys.model.BizPtyh;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
            " where ID IN " +
            " <foreach collection='list' item='item' open='(' close=')' separator=','>" +
            " #{item} " +
            "</foreach>" +
            "</script>")
    void updateJlFp(@Param("list") List<String> list,@Param("yhFpms") String yhFpms);



    @Select("<script>" +
            " SELECT IFNULL(SUM(ZJ_JE*ZJ_FS),0) as fymoney FROM BIZ_YJMX WHERE MX_LX='2' AND ZJ_ID=#{ddId} " +
            "</script>")
    String fyMoney(@Param("ddId")  String ddId);
    @Update("<script>" +
            " UPDATE BIZ_PTYH SET YH_IXY_SFFP = '1' , YH_XY_FPZY_TYPE = #{type} " +
            " where ID IN " +
            " <foreach collection='list' item='item' open='(' close=')' separator=','>" +
            " #{item} " +
            "</foreach>" +
            "</script>")
    void updateXyfpType(@Param("list")  List<String> list, @Param("type") String type);

    @Update(" update biz_ptyh set YH_ZSYQM = #{qr} , YH_ZSYQM_IMG = #{path}")
    void updateQr(@Param("qr") String qrCode,@Param("path") String img);

    @Update(" update biz_jl set JL_JXSL = (JL_JXSL + #{size}) where YH_ID = #{jlId}")
    void updateJxsl(@Param("jlId") String jlId,@Param("size") int size);

    @Select(" select yh_open_id from biz_ptyh where id = (select yh_id from biz_sub_school where id = #{id})")
    String getOpenId(@Param("id") String id);

}