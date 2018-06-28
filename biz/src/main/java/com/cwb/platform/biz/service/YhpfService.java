package com.cwb.platform.biz.service;


import com.cwb.platform.biz.model.BizYhpf;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.util.bean.ApiResponse;

public interface YhpfService extends BaseService<BizYhpf, String> {


    ApiResponse<BizYhpf> getUserCoach(String yhId);
}
