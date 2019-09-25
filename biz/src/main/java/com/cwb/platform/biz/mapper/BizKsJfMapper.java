package com.cwb.platform.biz.mapper;

import com.cwb.platform.biz.model.BizKsJf;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BizKsJfMapper extends Mapper<BizKsJf> {

    @Select(" select yh_id from biz_ks_yk k inner join biz_ks_jf f on f.yh_id = k.yh_id  and f.km_id = '1' and (k.cj1 >= 90 or k.cj2 >= 90 )")
    List<String> getUserids(String km);

}