package com.cwb.platform.sys.mapper;

import com.cwb.platform.sys.model.SysZdxm;
import com.cwb.platform.util.cache.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.cache.decorators.FifoCache;
import tk.mybatis.mapper.common.Mapper;

@CacheNamespace(implementation=MybatisRedisCache.class, eviction=FifoCache.class)
public interface SysZdxmMapper extends Mapper<SysZdxm> {
}