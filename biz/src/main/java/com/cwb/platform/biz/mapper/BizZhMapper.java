package com.cwb.platform.biz.mapper;

import com.cwb.platform.biz.model.BizZh;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BizZhMapper extends Mapper<BizZh> {

    @Select({ "<script> " +
            " DELETE FROM BIZ_ZH  WHERE YH_ID IN "+
            " <foreach collection='list' item='item' open='(' close=')' separator=','> " +
            "  #{item} " +
            " </foreach> " +
    " </script>"})
    void deleteUserDetail(@Param("list") List<String> list);

    @Select({ "<script> " +
            " INSERT INTO BIZ_ZH (YH_ID, YH_ZHYE, YH_TXDJ,YH_YEDJ) " +
            " SELECT U.ID AS YH_ID " +
            "  ,(SELECT SUM(W.ZJ_JE*W.ZJ_FS) FROM BIZ_YJMX W WHERE W.YH_ID=U.ID AND W.ZJ_ZT!='2' AND U.YH_XY_YK_TYPE &gt;= '10') AS YH_ZHYE " +
            "  ,(SELECT SUM(ZJ_JE) FROM BIZ_YJMX W WHERE W.YH_ID=U.ID AND W.ZJ_ZT='0' AND W.ZJ_FS='-1') AS YH_TXDJ " +
            " ,(SELECT SUM(W.ZJ_JE*W.ZJ_FS) FROM BIZ_YJMX W WHERE W.YH_ID=U.ID AND W.ZJ_ZT!='2' AND U.YH_XY_YK_TYPE &lt; '10')  AS YH_YEDJ " +
            " FROM BIZ_PTYH U WHERE U.ID IN  "+
            " <foreach collection='list' item='item' open='(' close=')' separator=','> " +
            "  #{item} " +
            " </foreach> " +
            " </script>"})
    void updatUserAccount(@Param("list") List<String> list);

    @Select(" SELECT IFNULL(SUM(W.ZJ_JE*W.ZJ_FS),0) FROM BIZ_YJMX W WHERE  W.YH_ID = #{id} AND W.ZJ_ZT!='2' AND MX_LX != '4' ")
    double sumYe(@Param("id") String id);

    @Select("SELECT IFNULL(SUM(ZJ_JE),0) FROM BIZ_YJMX W WHERE W.YH_ID = #{id} AND W.ZJ_ZT='1' AND W.ZJ_FS='-1'  AND MX_LX ='4' ")
    double sumTx(@Param("id")String id);

    /**
     * 查询学员一级分佣金额
     */
    @Select(" <script>SELECT IFNULL(SUM(CP_YJYJ),0) from biz_cp p INNER JOIN  (SELECT cp_id from biz_order where yh_id IN <foreach collection='ids' item='item' open='(' separator=',' close=')'>" +
            " #{item} " +
            "</foreach> ) c  on p.id = c.cp_id " +
            "</script>")
    double sumOneJe(@Param("ids") List<String> ids);

    @Select(" <script>SELECT IFNULL(SUM(CP_RJYJ),0) from biz_cp p INNER JOIN  (SELECT cp_id from biz_order where yh_id IN <foreach collection='ids' item='item' open='(' separator=',' close=')'>" +
            " #{item} " +
            "</foreach> ) c  on p.id = c.cp_id " +
            "</script>")
    double sumTwoJe(@Param("ids") List<String> ids);

    @Select("SELECT IFNULL(SUM(ZJ_JE),0) FROM BIZ_YJMX W WHERE W.YH_ID = #{id} AND W.ZJ_ZT='0' AND W.ZJ_FS='-1'")
    double sumTxDj(@Param("id") String id);

}