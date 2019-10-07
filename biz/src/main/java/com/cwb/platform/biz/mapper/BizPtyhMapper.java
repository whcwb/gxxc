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

    @Select(" select count(id) from biz_ptyh p where yh_lx = '1' and ( hfsj is null or hfsj = '') ")
    int sumDhf();

    @Select(" select count(id) from biz_ptyh where yh_lx = '1' and yh_xy_sl_type != '4' ")
    int sumDsl();

    @Select(" select count(id) from biz_ptyh p where yh_xy_sl_type = '4' and yh_lx = '1' and (yh_xy_jf_type = '1' or  (yh_xy_jf_type = '2' and (yh_xy_yk_type ='11' or yh_xy_yk_type >='20'   ))  or (yh_xy_jf_type = '3'  and  k3_jfzt != 1 )) ")
    int sumDjfK();

    @Select(" select count(id) from biz_ptyh where yh_lx = '1' and yh_xy_sl_type = '4' and ( (yh_xy_jf_type != '1' and yh_xy_yk_type != '11') or (yh_xy_jf_type ='3' and yh_xy_yk_type != '21' ) or (k3_jfzt= 1 and  yh_xy_jf_type ='3' and yh_xy_yk_type != '31') )")
    int sumDlr();

    @Select(" select count(id) from biz_ptyh where yh_lx = '1' and yh_xy_sl_type = '4' and (  yh_xy_yk_type != '31' and yh_xy_yk_type <'40')")
    int sumDlrNew();

    @Select(" select count(id) from biz_ptyh where yh_lx = '1' and  (yh_k2_sub_id is null or yh_k2_sub_id ='' ) and (yh_xy_yk_type ='11' or yh_xy_yk_type >='20' ) ")
    int sumDfp();

    @Select(" select count(id) from biz_tx  where tt_zt ='0' ")
    int sunDfy();

    @Select(" select count(id) from biz_ptyh where yh_lx = '1' and (yh_k2_sub_id is not null or yh_k2_sub_id != '') and yh_k2_sub_sj is null ")
    int sumK2Pxf();

    @Select(" select count(id) from biz_ptyh where yh_lx = '1' and (yh_k3_sub_id is not null or yh_k3_sub_id != '') and yh_k3_sub_sj is null and (yh_xy_yk_type = '31' or yh_xy_yk_type >= '40' )  ")
    int sumK3Pxf();

    @Select(" select YH_K2_SUB_ID from biz_ptyh where ( yh_k2_sub_sj is null or yh_k2_sub_sj = '' )  and ( YH_K2_SUB_ID is not null or YH_K2_SUB_ID != '')  and YH_K2_SUB_NAME like '%${subName}%'   group by YH_K2_SUB_ID order by YH_K2_SUB_ID ")
    List<String> getK2Dp(@Param("subName") String subName);

    @Select(" select YH_K3_SUB_ID from biz_ptyh where ( yh_k3_sub_sj is null or yh_k3_sub_sj = '' )  and ( YH_K3_SUB_ID is not null or YH_K3_SUB_ID != '')  and ( yh_xy_yk_type = '31' or yh_xy_yk_type >= '40'  ) and YH_K3_SUB_NAME like '%${subName}%'   group by YH_K3_SUB_ID order by YH_K3_SUB_ID ")
    List<String> getK3Dp(@Param("subName") String subName);

    @Select(" select id from biz_ptyh where yh_lx ='1' and yh_xy_sl_type ='4' and id not in ( select yh_id from biz_ks_yk where (cj1 >= 90 or cj2 >= 90) and km_code = '1' )")
    List<String> getK1Lr();

    @Select(" select id from biz_ptyh where yh_lx = '1' and yh_xy_sl_type = '4' and id not in ( select yh_id from biz_ks_yk where (cj1 >= 80 or cj2 >= 80) and km_code = '2' ) and id in ( select yh_id from biz_ks_yk where (cj1 >= 90 or cj2 >= 90) and km_code ='1' )")
    List<String> getK2Lr();

    @Select(" select id from biz_ptyh where yh_lx = '1' and yh_xy_sl_type ='4' and id not in  ( select yh_id from biz_ks_yk where (cj1>=90 or cj2 >= 90) and km_code= '3')   and id in ( select yh_id from biz_ks_yk where (cj1 >= 80 or cj2 >= 80) and km_code = '2' ) and id in ( select yh_id from biz_ks_yk where (cj1 >= 90 or cj2 >= 90) and km_code ='1' )")
    List<String> getK3Lr();
}