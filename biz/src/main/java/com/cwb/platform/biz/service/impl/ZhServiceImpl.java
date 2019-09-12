package com.cwb.platform.biz.service.impl;


import com.cwb.platform.biz.mapper.BizZhMapper;
import com.cwb.platform.biz.model.BizUser;
import com.cwb.platform.biz.model.BizZh;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.biz.service.ZhService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.SimpleCondition;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZhServiceImpl extends BaseServiceImpl<BizZh, java.lang.String> implements ZhService {
    @Autowired
    private BizZhMapper entityMapper;
    @Autowired
    private PtyhService ptyhService;

    @Override
    protected Mapper<BizZh> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls() {
        return BizZh.class;
    }

    /**
     * 更新用户账户信息
     * 1、删除该账户信息
     * 2、从拥金明细表中统计出所有的金额
     *
     * 统计明细表中  提现状态 ZDCLK0054 (0、提现冻结  1、 处理成功 2、提现失败) 提现操作默认0 佣金操作默认1
     * @param userId
     * @return
     */
//    public boolean userAccountUpdate(List<String> userId) {
//        boolean ret;
//        if(userId!=null&& userId.size()>0){
//            entityMapper.deleteUserDetail(userId);
//            entityMapper.updatUserAccount(userId);
//        }
//        ret=true;
//        return ret;
//    }

    /**
     * 新 更新用户账户接口  计算 一级 和二级分佣 哪些可以提现 哪些需要冻结
     *
     * @param ids
     * @return
     */
    public boolean userAccountUpdate(List<String> ids) {
        // 开始对账户余额进行更新  , 以前的结构有点难
        // 首先查询当前用户 应该有多少余额 , 然后 在计算哪些是需要冻结
        ids.forEach(
                s -> {
                    BizPtyh ptyh = ptyhService.findById(s);
                    if (ptyh != null) {


                        // 总金额
                        double ye = entityMapper.sumYe(s);
                        // 已提现金额
                        double tx = entityMapper.sumTx(s);
                        // 提现冻结金额
                        double txDj = entityMapper.sumTxDj(s);

                        double txJe = 0;

                        if (StringUtils.isNotBlank(ptyh.getYhZsyqm())) {
                            // 查找下级学员
                            SimpleCondition condition = new SimpleCondition(BizPtyh.class);
                            condition.eq(BizPtyh.InnerColumn.yhYyyqm, ptyh.getYhZsyqm());
                            List<BizPtyh> ptyhs = ptyhService.findByCondition(condition);
                            if (CollectionUtils.isNotEmpty(ptyhs)) {
                                // 区分已经考过科目一 和 没考过的学员
                                List<String> collect =
                                        ptyhs.stream().filter(bizPtyh -> StringUtils.equals(bizPtyh.getYhLx(),
                                        "1")).filter(bizPtyh -> bizPtyh.getYhXyYkType().compareTo("10") >= 0).map(BizPtyh::getId).collect(Collectors.toList());
                        /*List<String> collect1 = ptyhs.stream().filter(bizPtyh -> StringUtils.equals(bizPtyh.getYhLx()
                                , "1")).filter(bizPtyh -> !((bizPtyh.getYhXyYkType().compareTo("10")) >= 0)).map
                                (BizPtyh::getId).collect(Collectors.toList());*/
                                // 计算这些学员 一级分佣的金额
                                if (CollectionUtils.isNotEmpty(collect)) {
                                    double oneJe = entityMapper.sumOneJe(collect);
                                    txJe += oneJe;
                                }
                                // 查询用户下面的二级用户 , 准备二级分佣
                                List<String> yqms =
                                        ptyhs.stream().map(BizPtyh::getYhZsyqm).collect(Collectors.toList());
                                condition = new SimpleCondition(BizPtyh.class);
                                condition.in(BizPtyh.InnerColumn.yhYyyqm, yqms);
                                condition.eq(BizPtyh.InnerColumn.yhLx, "1");
                                condition.gte(BizPtyh.InnerColumn.yhXyYkType, "10");
                                List<BizPtyh> rPtyhs = ptyhService.findByCondition(condition);
                                List<String> collect1 =
                                        rPtyhs.stream().map(BizPtyh::getId).collect(Collectors.toList());
                                if (CollectionUtils.isNotEmpty(collect1)) {
                                    double twoJe = entityMapper.sumTwoJe(collect1);
                                    txJe += twoJe;
                                }
                            }
                        }
                        //  总额度 减去 用户可提现额度 , 即 用户冻结的金额
                        BizZh zh = findById(s);
                        if (zh != null) {
                            zh.setYhTxdj(txDj);
                            zh.setYhYedj(ye - txJe);
                            zh.setYhZhye(txJe - tx - txDj);
                            // 如果有提现冻结的 或者 开始已经提现了的 , 这一部分直接从 djye中 提取出来
                            if (zh.getYhZhye() < 0) {
                                zh.setYhYedj(zh.getYhYedj() + zh.getYhZhye());
                                zh.setYhZhye(0.0);
                            }
                            zh.setYhYtx(tx);
                            update(zh);
                        } else {
                            zh = new BizZh();
                            zh.setYhId(s);
                            zh.setYhTxdj(txDj);
                            zh.setYhYedj(ye - txJe);
                            zh.setYhZhye(txJe - tx - txDj);
                            // 如果有提现冻结的 或者 开始已经提现了的 , 这一部分直接从 djye中 提取出来
                            if (zh.getYhZhye() < 0) {
                                zh.setYhYedj(zh.getYhYedj() + zh.getYhZhye());
                                zh.setYhZhye(0.0);
                            }
                            zh.setYhYtx(tx);
                            save(zh);
                        }
                    }
                }
        );
        return true;
    }


}
