package com.cwb.platform.biz.app.service.impl;

import com.cwb.platform.biz.app.service.AppUserService;
import com.cwb.platform.biz.mapper.BizPtyhMapper;
import com.cwb.platform.biz.mapper.BizUserMapper;
import com.cwb.platform.biz.model.BizUser;
import com.cwb.platform.biz.service.UserService;
import com.cwb.platform.biz.service.impl.PtyhServiceImpl;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.base.LimitedCondition;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.bean.SimpleCondition;
import com.cwb.platform.util.exception.RuntimeCheck;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.common.Mapper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AppUserServiceImpl extends BaseServiceImpl<BizUser, String> implements AppUserService {
    @Autowired
    private BizUserMapper entityMapper;

    @Autowired
    private UserService userService;
    @Autowired
    private BizPtyhMapper ptyhMapper;

    @Autowired
    private PtyhServiceImpl ptyhService;
    @Autowired
    private BizUserMapper userMapper;

    @Override
    protected Mapper<BizUser> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls() {
        return BizUser.class;
    }

    /**
     * 分页补充   按全部、已付款、待付款 来进行查询
     *
     * @param condition
     * @return
     */
    public boolean fillPagerCondition(LimitedCondition condition) {
        BizPtyh user = getAppCurrentUser();
        RuntimeCheck.ifNull(user, "用户不存在");
        String userId = user.getId();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userGrade = request.getParameter("userGrade");//用户等级  1、一级用户   2、二级用户。不写查全部
        if (StringUtils.equals(userGrade, "1")) {//一级用户
            condition.and().andEqualTo(BizUser.InnerColumn.yhSjid.name(), userId);//上级ID
        } else if (StringUtils.equals(userGrade, "2")) {//二级用户
            condition.and().andEqualTo(BizUser.InnerColumn.yhSsjid.name(), userId);//上上级ID
        } else {
//            condition.and().andEqualTo(BizUser.InnerColumn.yhSjid.name(),userId);//上级ID
//            condition.or().andEqualTo(BizUser.InnerColumn.yhSsjid.name(),userId);//上上级ID
//             上面这样写有问题。SQL会是 (YH_SJID='1') or (YH_SSJID='')    而我要的是：(YH_SJID='1' or YH_SSJID='') 所以不能满足我的需求
            condition.and().andCondition(" ( YH_SJID='" + userId + "' OR YH_SSJID='" + userId + "') ");

        }
        return true;
    }

    @Override
    protected void afterPager(PageInfo<BizUser> resultPage) {
        BizPtyh user = getAppCurrentUser();
        RuntimeCheck.ifNull(user, "用户不存在");
        String userId = user.getId();

        List<BizUser> list = resultPage.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            List<String> ids = list.stream().map(BizUser::getYhId).collect(Collectors.toList());

            SimpleCondition condition = new SimpleCondition(BizPtyh.class);
            condition.in(BizPtyh.InnerColumn.id.name(), ids);
            List<BizPtyh> userwjs = ptyhMapper.selectByExample(condition);

            Map<String, BizPtyh> userMap = userwjs.stream().collect(Collectors.toMap(BizPtyh::getId, p -> p));

            for (BizUser order : list) {
                String orderUserId = order.getYhId();
                if (!userMap.containsKey(orderUserId)) continue;
                BizPtyh zdlm = userMap.get(orderUserId);
                order.setUserDetail(ptyhService.afterReturns(zdlm));
//                用户等级 * 1、一级用户   2、二级用户
                if (StringUtils.equals(userId, order.getYhSjid())) {
                    order.setUserGrade("1");
                } else {
                    order.setUserGrade("2");
                }

                if (StringUtils.equals(zdlm.getYhXySlType(), "0") ||
                        StringUtils.equals(zdlm.getYhXySlType(), "1") ||
                        StringUtils.equals(zdlm.getYhXySlType(), "2") ||
                        StringUtils.equals(zdlm.getYhXySlType(), "3") ||
                        StringUtils.equals(zdlm.getYhXySlType(), "4")) {
                    order.setYhDqzt("0");
                }
                if (zdlm.getYhXyYkType().charAt(0) == '0') {
                    order.setYhDqzt("0");
                } else if (zdlm.getYhXyYkType().charAt(0) == '1') {
                    order.setYhDqzt("1");
                } else if (zdlm.getYhXyYkType().charAt(0) == '2') {
                    order.setYhDqzt("2");
                } else if (zdlm.getYhXyYkType().charAt(0) == '3') {
                    order.setYhDqzt("3");
                } else {
                    order.setYhDqzt("4");
                }
//                待受理 受理中 受理成功
                String yhzt="";
                //00表示未受理 01受理成功 02表示受理中
                if(StringUtils.equals(zdlm.getYhXySlType(), "0") ){
                    yhzt="00";
                }else if( StringUtils.equals(zdlm.getYhXySlType(), "1") ||
                            StringUtils.equals(zdlm.getYhXySlType(), "2") ||
                            StringUtils.equals(zdlm.getYhXySlType(), "3") ){
                    yhzt="02";
                }else if(StringUtils.equals(zdlm.getYhXySlType(), "4")&& (StringUtils.isEmpty(zdlm.getYhXyYkType())||StringUtils.equals(zdlm.getYhXyYkType(),"0"))){
                    yhzt="01";
                }else if(StringUtils.isNotEmpty(zdlm.getYhXyYkType())){
                    yhzt=zdlm.getYhXyYkType();
                }
                order.setYhslZt(yhzt);

                if (StringUtils.isNotBlank(order.getYhZjhm())) {
                    order.setYhZjhm(order.getYhZjhm().replaceAll("(\\d{3})\\d*(\\d{4})", "$1******$2"));
                }
            }
        }
        return;
    }

    @Override
    public ApiResponse<List<BizUser>> myTeam(String grade, String yhlx, String sfjf, String yhXm, Page<BizUser> page) {
        ApiResponse<List<BizUser>> result = new ApiResponse<>();

        // 获取当前用户
        BizPtyh currentUser = getAppCurrentUser();
        RuntimeCheck.ifNull(currentUser, "当前登录用户不存在");
        String userId = currentUser.getId();

        if (StringUtils.isBlank(grade)) {

            grade = null;

        }
        if (StringUtils.isBlank(yhlx)) {
            yhlx = null;
        }
        if (StringUtils.isBlank(sfjf)) {
            sfjf = null;
        }
        if (StringUtils.isBlank(yhXm)) {
            yhXm = null;
        }
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<BizUser> users = userMapper.getYhIdByTerm(grade, userId, yhlx, sfjf, yhXm);
        PageInfo<BizUser> pageInfo = new PageInfo<>(users);

        afterPager(pageInfo);
        result.setPage(pageInfo);

        return result;
    }

    public static void main(String[] args) {
        System.out.println(StringUtils.isBlank("   "));
    }
}
