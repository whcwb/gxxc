package com.cwb.platform.biz.mapper;

import com.cwb.platform.biz.model.BizJl;
import com.cwb.platform.biz.model.BizUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BizJlMapper extends Mapper<BizJl> {
    @Select({ "<script> " +
            " UPDATE BIZ_JL SET JL_PF=(SELECT IFNULL(ROUND(AVG(YH_FZ),0),5) FROM BIZ_YHPF WHERE JL_ID=#{item} AND AUDIT_TYPE='1') WHERE YH_ID = #{item}  " +
            " </script>"})
    void updateJlPf(String list);

    @Select("<script> select u.*,p.yh_xy_yk_type yhDqzt,p.yh_tx yhTx from biz_user u inner join biz_ptyh p on u.yh_id = p.id  <if test ='jz != null'> and p.yh_sfyjz = #{jz}</if> and substring(p.yh_xy_yk_type,1,1) in    <foreach collection='dqzts' index='index' item='item' open='(' separator=',' close=')' > #{item} </foreach>    where 1 = 1 <if test='xm != null'> and u.yh_xm like '%${xm}%'</if> and  (yh_jlid = #{jlid} or yh_jlid1= #{jlid} or yh_jlid2 = #{jlid} or yh_jlid3 = #{jlid} or yh_jlid4 = #{jlid}) " +
            "</script> ")
    List<BizUser> getMyStudent(@Param("jlid") String jlId,@Param("xm") String xm,@Param("jz") String jz, @Param("dqzts") List<String> dqzts);

}