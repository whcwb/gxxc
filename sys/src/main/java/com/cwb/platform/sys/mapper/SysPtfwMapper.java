package com.cwb.platform.sys.mapper;

import com.cwb.platform.sys.model.SysFw;
import com.cwb.platform.util.cache.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.cache.decorators.FifoCache;
import tk.mybatis.mapper.common.Mapper;

public interface SysPtfwMapper extends Mapper<SysFw> {
}