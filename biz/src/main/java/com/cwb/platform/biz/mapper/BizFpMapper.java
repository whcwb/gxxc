package com.cwb.platform.biz.mapper;

import com.cwb.platform.biz.model.BizFp;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BizFpMapper extends Mapper<BizFp> {

    @Select("<script> select sub_school_id from biz_fp where sfdk = '0' and  fpkm = #{km} <if test='name != null'> and sub_school_name like '%${name}%'</if> group by sub_school_id order by sub_school_id </script>")
    List<String> getSubSchool(@Param("km") String km, @Param("name") String name);


}
