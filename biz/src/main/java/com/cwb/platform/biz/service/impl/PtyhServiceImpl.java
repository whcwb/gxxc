package com.cwb.platform.biz.service.impl;


import com.cwb.platform.biz.bean.StatusModel;
import com.cwb.platform.biz.mapper.*;
import com.cwb.platform.biz.model.*;
import com.cwb.platform.biz.service.*;
import com.cwb.platform.biz.util.IDNameIdentify;
import com.cwb.platform.biz.util.ShoreCode;
import com.cwb.platform.biz.wxpkg.service.WechatService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.base.LimitedCondition;
import com.cwb.platform.sys.bean.AccessToken;
import com.cwb.platform.sys.mapper.SysYhJsMapper;
import com.cwb.platform.sys.model.*;
import com.cwb.platform.sys.service.JsService;
import com.cwb.platform.sys.service.YhService;
import com.cwb.platform.sys.service.ZdxmService;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.bean.SimpleCondition;
import com.cwb.platform.util.commonUtil.*;
import com.cwb.platform.util.exception.RuntimeCheck;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class PtyhServiceImpl extends BaseServiceImpl<BizPtyh, java.lang.String> implements PtyhService {
    Logger payInfo = LoggerFactory.getLogger("access_info");


    @Autowired
    private TxService txService;
    @Autowired
    private StringRedisTemplate redisDao;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CpService cpService;

    @Value("${img_url}")
    private String imgUrl;

    @Value("${debug_test}")
    private String debugTest;

    @Value("${wxDomain}")
    private String domain;


    @Value("${logo_file_url}")
    private String logoFileUrl;
    @Value("${qr_code_file_url}")
    private String qrCodeFileUrl;

    @Autowired
    private BizFpMapper fpMapper;


    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private TrainPlaceService placeService;


    // 忽略当接收json字符串中没有bean结构中的字段时抛出异常问题
    private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
            false);
    @Autowired
    private PtyhService ptyhService;
    @Autowired
    private BizPtyhMapper entityMapper;
    @Autowired
    private BizKsYkMapper ksYkMapper;
    @Autowired
    private BizWjMapper wjMapper;
    @Autowired
    private BizJlMapper jlMapper;
    @Autowired
    private JlService jlService;
    @Autowired
    private UserService userService;
    @Autowired
    private BizUserMapper userMapper;
    @Autowired
    private YhpfService yhpfService;
    @Autowired
    private KsSlService ksSlService;
    @Autowired
    private KsYkService ksYkService;
    @Autowired
    private KsjfService ksjfService;
    @Autowired
    private YhService yhService;
    @Autowired
    private ZdxmService zdxmService;

    @Autowired
    private SysYhJsMapper userRoleMapper;

    @Autowired
    private YjmxService yjmxService;
    @Autowired
    private ZhService zhService;


    @PersistenceContext
    private EntityManager entityManager;


    AsyncEventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(1));

    public PtyhServiceImpl() {
        eventBus.register(this);
    }

    //
    @Value("${appSendSMSRegister:app_sendSMS_register}")
    private String appSendSMSRegister;

    @Value("${appSendSMSResetting:app_sendSMS_resetting}")
    private String appSendSMSResetting;

    //分配学员的微信消息模板
    @Value("${wxMsgTemplate.assignXy}")
    private String examMsgIdXy;
    //分配教练的微信消息模板
    @Value("${wxMsgTemplate.assignJl}")
    private String examMsgIdJl;
    //分配学员学员链接地址
    @Value("${wxassignXyDomain}")
    private String wxXyDomain;
    //分配学员教练链接地址
    @Value("${wxassignJlDomain}")
    private String wxJlDomain;

    @Autowired
    private WechatService wechatService;
    @Autowired
    private JsService jsService;
    private ExecutorService service = Executors.newSingleThreadExecutor();


    @Override
    protected Mapper<BizPtyh> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls() {
        return BizPtyh.class;
    }


    @Override
    public List<String> getSpecialCols() {
        return Arrays.asList("ddSfjx", "yhSfyjz", "yhZt", "jlxm", "jldh");
    }

    @Override
    public List<Map<String, String>> getSpecialVals(List<BizPtyh> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(this::afterReturn);
        }
        List<Map<String, String>> data = new ArrayList<>(list.size());
        for (BizPtyh row : list) {
            Map<String, String> map = new HashMap<>();
            map.put("ddSfjx", "1".equals(row.getDdSfjx()) ? "已缴费" : "未缴费");
            map.put("yhSfyjz", "1".equals(row.getYhSfyjz()) ? "有" : "无");
            map.put("yhZt", "1".equals(row.getYhZt()) ? "已认证" : "未认证");
            map.put("jlxm", row.getJlXm());
            map.put("jldh", row.getSjhm());
            data.add(map);
        }
        return data;
    }


    @Override
    public boolean fillPagerCondition(LimitedCondition condition) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String rz = request.getParameter("rz");
        String zylx = request.getParameter("zylx");//查询用户受理状态
        String zylxPager = request.getParameter("zylxPager");

        String cond = request.getParameter("cond");
        if (StringUtils.isNotBlank(cond)) {
            condition.and().andCondition("YH_ZH like '%" + cond + "%' or YH_XM like '%" + cond + "%' or YH_ZJHM like " +
                    "'%" + cond + "%' or YH_ZSYQM like '%" + cond + "%' or YH_LSH like '%" + cond + "%'");
        }

        if (StringUtils.isNotBlank(rz)) { // 若不为空则为学员认证列表
            condition.and().andIsNotNull("yhZjhm");
        }

        if (StringUtils.isNotEmpty(zylx)) {
            SysYh user = getCurrentUser();
            String type = user.getLx();
            SimpleCondition condition1 = new SimpleCondition(BizUser.class);
            switch (type) {
                case "slzy":
                    condition1.eq(BizUser.InnerColumn.yhJlid, user.getYhid());
                    break;
                case "k1":
                    condition1.eq(BizUser.InnerColumn.yhJlid1, user.getYhid());
                    break;
                case "k2":
                    condition1.eq(BizUser.InnerColumn.yhJlid2, user.getYhid());
                    break;
                case "k3":
                    condition1.eq(BizUser.InnerColumn.yhJlid3, user.getYhid());
                case "k4":
                    condition1.eq(BizUser.InnerColumn.yhjlid4, user.getYhid());
                    break;
            }
            List<BizUser> userList = userMapper.selectByExample(condition1);
            if (userList.size() == 0) {
                return false;
            }
            List<String> yhIds = userList.stream().map(BizUser::getYhId).collect(Collectors.toList());
            condition.in(BizPtyh.InnerColumn.id, yhIds);
        }
        if (StringUtils.isNotEmpty(zylxPager)) {
            List<SysYh> sysYhs = yhService.getByRoleIds(Arrays.asList(zylxPager));
            List<String> zjhms = sysYhs.stream().map(SysYh::getZjhm).collect(Collectors.toList());
            condition.in(BizUser.InnerColumn.yhZjhm, zjhms);
        }

        //这一批用户是种子用户，不在用户列表显示。
        List<String> notUrsrId = new ArrayList<>();
//        notUrsrId.add("460418231002202112");//李总
//        notUrsrId.add("461211447838375937");//张总
//        notUrsrId.add("461211447838375939");//刘显斌
//        notUrsrId.add("461221447838375937");//谢涛

        if (CollectionUtils.isNotEmpty(notUrsrId)) {
            condition.notIn(BizPtyh.InnerColumn.id, notUrsrId);
        }
        return true;
    }

    @Override
    public boolean fillQueryCondition(LimitedCondition condition) {
        String cond = getRequestParameterAsString("cond");
        if (StringUtils.isNotBlank(cond)) {
            condition.and().andCondition(" yh_zh like '%" + cond + "%' or yh_xm like '%" + cond + "%'");
        }
        return true;
    }

    @Override
    protected void afterPager(PageInfo<BizPtyh> resultPage) {
        List<BizPtyh> list = resultPage.getList();
        if (list.size() == 0) {
            return;
        }

        // 获取用户角色
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String showRoles = request.getParameter("showRoles");
        if ("true".equals(showRoles)) {
            List<String> idCardList = list.stream().map(BizPtyh::getYhZjhm).collect(Collectors.toList());
            List<SysYh> sysUserList = yhService.findIn(SysYh.InnerColumn.zjhm, idCardList);
            if (sysUserList.size() == 0) return;
            Map<String, String> idCardSysUserIdMap = sysUserList.stream().collect(Collectors.toMap(SysYh::getZjhm,
                    SysYh::getYhid));
            for (BizPtyh ptyh : list) {
                String idCard = ptyh.getYhZjhm();
                String yhId = idCardSysUserIdMap.get(idCard);
                if (StringUtils.isNotEmpty(yhId)) {
                    ptyh.setSysUserId(yhId);
                }
            }

            List<String> sysUserIds = sysUserList.stream().map(SysYh::getYhid).collect(Collectors.toList());
            SimpleCondition condition = new SimpleCondition(SysYhJs.class);
            condition.in(SysYhJs.InnerColumn.yhId, sysUserIds);
            List<SysYhJs> userRoleList = userRoleMapper.selectByExample(condition);
            if (userRoleList.size() == 0) return;
            Map<String, List<String>> sysIdRoleIdMap = new HashMap<>();
            for (SysYhJs sysYhJs : userRoleList) {
                String yhId = sysYhJs.getYhId();
                if (sysIdRoleIdMap.containsKey(yhId)) {
                    sysIdRoleIdMap.get(yhId).add(sysYhJs.getJsId());
                } else {
                    List<String> roleIdList = new ArrayList<>();
                    roleIdList.add(sysYhJs.getJsId());
                    sysIdRoleIdMap.put(yhId, roleIdList);
                }
            }

            List<String> roleIds = userRoleList.stream().map(SysYhJs::getJsId).collect(Collectors.toList());
            List<SysJs> roleList = jsService.findIn(SysJs.InnerColumn.jsId, roleIds);
            if (roleList.size() == 0) return;
            Map<String, SysJs> roleIdRoleMap = roleList.stream().collect(Collectors.toMap(SysJs::getJsId, p -> p));
            Map<String, List<String>> sysIdRoleNameMap = new HashMap<>();
            for (Map.Entry<String, List<String>> entry : sysIdRoleIdMap.entrySet()) {
                List<String> roleIdList = entry.getValue();
                List<String> roleNames = new ArrayList<>(roleIdList.size());
                for (String s : roleIdList) {
                    SysJs role = roleIdRoleMap.get(s);
                    if (role != null) {
                        roleNames.add(role.getJsmc());
                    }
                }
                sysIdRoleNameMap.put(entry.getKey(), roleNames);
            }

            for (BizPtyh ptyh : list) {
                String sysId = ptyh.getSysUserId();
                if (StringUtils.isEmpty(sysId)) continue;
                List<String> roleNames = sysIdRoleNameMap.get(sysId);
                String roleNameStr = "";
                for (String name : roleNames) {
                    roleNameStr += name + ",";
                }
                if (roleNameStr.length() != 0) {
                    roleNameStr = roleNameStr.substring(0, roleNameStr.length() - 1);
                }
                ptyh.setRoleNames(roleNameStr);
            }
        }

        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().forEach(bizPtyh -> afterReturn(bizPtyh));
        }

        return;
    }

    /**
     * 重置学员部分信息 ， 不对外展示
     *
     * @param bizPtyh
     */
    private void afterReturn(BizPtyh bizPtyh) {
        if (bizPtyh != null) {
            bizPtyh.setYhMm("");
            bizPtyh.setYhOpenId("");
            bizPtyh.setYhAlipayId("");
            bizPtyh.setYhYyyqm("");

            if (StringUtils.isNotBlank(bizPtyh.getYhTx()) && !StringUtils.containsNone(bizPtyh.getYhTx(), "http")) {
                bizPtyh.setYhTx(imgUrl + bizPtyh.getYhTx());
            }

            if (StringUtils.isNotBlank(bizPtyh.getYhZsyqm()) && StringUtils.isNotBlank(bizPtyh.getYhZsyqmImg()) && !StringUtils.containsNone(bizPtyh.getYhZsyqmImg(), "http")) {
                bizPtyh.setYhZsyqmImg(imgUrl + bizPtyh.getYhZsyqmImg());
            }

//            if (StringUtils.isNotBlank(bizPtyh.getYhZsyqm()) && StringUtils.isNotBlank(bizPtyh.getYhZsyqmImg())) {
//                String qrcodesFile="";
//                try {
//                    qrcodesFile = redisDao.boundValueOps("user_qrcode_"+bizPtyh.getId()).get();
//                    if(StringUtils.isEmpty(qrcodesFile)){
//                        WxMpQrcodeService wx = wxMpService.getQrcodeService();
//                        WxMpQrCodeTicket wxMpQrCodeTicket = wx.qrCodeCreateTmpTicket(bizPtyh.getId(), 2592000);
//                        String ticket=wxMpQrCodeTicket.getTicket();
//                        String url=wxMpQrCodeTicket.getUrl();
//                        redisDao.boundValueOps("url"+url).set(bizPtyh.getYhZsyqm(), 29, TimeUnit.DAYS);
//                        qrcodesFile=wx.qrCodePictureUrl(ticket);
//                        redisDao.boundValueOps("user_qrcode_"+bizPtyh.getId()).set(qrcodesFile, 29, TimeUnit.DAYS);
//                    }
//                }catch (WxErrorException e){
//                    e.printStackTrace();
//                }
//                if(StringUtils.isNotEmpty(qrcodesFile)){
//                    bizPtyh.setYhZsyqmImg(qrcodesFile);
//                }else{
//                    bizPtyh.setYhZsyqmImg("");
//                }
//
//            }

//            if (StringUtils.isNotBlank(bizPtyh.getYhZh())) {
//                bizPtyh.setYhZh(bizPtyh.getYhZh().replaceAll("(?<=[\\d]{3})\\d(?=[\\d]{4})", "*"));
//            }

            // 查询该用户是否有所属教练
            BizUser bizUser = userService.findById(bizPtyh.getId());
            if (!ObjectUtils.isEmpty(bizUser)) {
                // 通过用户实名表查询教练属性
                BizJl bizJl = jlService.findById(bizUser.getYhJlid());
                if (!ObjectUtils.isEmpty(bizJl)) {
                    bizPtyh.setJlXm(bizJl.getYhXm()); // 教练姓名
                    bizPtyh.setSjhm(bizJl.getYhSjhm()); // 手机号码
                    bizPtyh.setJlId(bizJl.getYhId());//教练员ID

                }
            }
        }
    }

    public BizPtyh afterReturns(BizPtyh bizPtyh) {
        this.afterReturn(bizPtyh);
        return bizPtyh;
    }

    @Override
    public List<BizPtyh> getByRoleIds(List<String> roleIds) {

        return null;
    }

    /**
     * 更新用户是否锁定状态 0 否 1 是
     *
     * @param bizPtyh
     * @return
     */
    @Override
    public ApiResponse<String> updateSfsd(BizPtyh bizPtyh) {
        SysYh sysYh = getCurrentUser();
        RuntimeCheck.ifBlank(bizPtyh.getId(), "用户Id不能为空");
        BizPtyh ptyh = findById(bizPtyh.getId());
        if (ObjectUtils.isEmpty(ptyh)) {
            return ApiResponse.fail("用户不存在");
        }
        RuntimeCheck.ifBlank(bizPtyh.getYhSfsd(), "用户锁定状态不能为空");
        if (StringUtils.containsNone(bizPtyh.getYhSfsd(), new char[]{'0', '1'})) {
            return ApiResponse.fail("请输入正确的状态");
        }
        BizPtyh newEntity = new BizPtyh();
        newEntity.setId(bizPtyh.getId());
        newEntity.setYhSfsd(bizPtyh.getYhSfsd());//用户是否锁定 ZDCLK0046 (0 否  1 是)
        newEntity.setXgsj(DateUtils.getNowTime());
        newEntity.setYhXgr(sysYh.getYhid());
        int i = update(newEntity);
        return i == 1 ? ApiResponse.success() : ApiResponse.fail();
    }

    /**
     * 更新用户是否分配信息
     *
     * @param bizPtyh
     * @return
     */
    @Override
    public ApiResponse<String> updateSffp(BizPtyh bizPtyh) {
        SysYh sysYh = getCurrentUser();
        RuntimeCheck.ifBlank(bizPtyh.getId(), "用户Id不能为空");
        BizPtyh ptyh = findById(bizPtyh.getId());
        if (ObjectUtils.isEmpty(ptyh)) {
            return ApiResponse.fail("用户不存在");
        }
        RuntimeCheck.ifBlank(bizPtyh.getYhIxySffp(), "用户是否分配不能为空");
        if (StringUtils.equals(bizPtyh.getYhIxySffp(), "0")) {
            bizPtyh.setYhFpms("");
        } else if (StringUtils.equals(bizPtyh.getYhIxySffp(), "1")) {
            if (StringUtils.isBlank(bizPtyh.getYhFpms())) {
                return ApiResponse.fail("用户分配描述不能为空");
            }
        }
        BizPtyh newEntity = new BizPtyh();
        newEntity.setId(bizPtyh.getId());
        newEntity.setYhIxySffp(bizPtyh.getYhIxySffp());
        if (StringUtils.equals(bizPtyh.getYhIxySffp(), "0")) {
            newEntity.setYhFpms("");
        } else if (StringUtils.equals(bizPtyh.getYhIxySffp(), "1")) {
            if (StringUtils.isBlank(bizPtyh.getYhFpms())) {
                return ApiResponse.fail("用户分配描述不能为空");
            }
            newEntity.setYhFpms(bizPtyh.getYhFpms());
        }
        newEntity.setXgsj(DateUtils.getNowTime());
        newEntity.setYhXgr(sysYh.getYhid());
        int i = update(newEntity);
        return i == 1 ? ApiResponse.success() : ApiResponse.fail();
    }

    /**
     * 更新学员认证状态
     *
     * @param bizPtyh
     * @return
     */
    @Override
    public ApiResponse<String> updateYhRz(BizPtyh bizPtyh) {
        SysYh sysYh = getCurrentUser();
        BizPtyh user = entityMapper.selectByPrimaryKey(bizPtyh.getId());
        if (user == null) return ApiResponse.fail("用户不存在");
        RuntimeCheck.ifTrue(!StringUtils.equals(user.getYhLx(), "1"), "操作失败，只有学员才能进行认证操作");
        RuntimeCheck.ifTrue(StringUtils.equals(user.getYhZt(), "1"), "操作失败，该学员已认证无需再次认证");
        RuntimeCheck.ifTrue(StringUtils.equals(user.getYhSfsd(), "1"), "操作失败，该学员已锁定无法进行认证操作");

        RuntimeCheck.ifBlank(bizPtyh.getYhZt(), "审核状态不能为空");
        if (StringUtils.containsNone(bizPtyh.getYhZt(), new char[]{'1', '2'})) {
            return ApiResponse.fail("请输入正确审核状态");
        }
        BizPtyh newEntity = new BizPtyh();
        newEntity.setId(user.getId());


        if (StringUtils.equals(bizPtyh.getYhZt(), "1")) {//认证成功
            //      获取用户父级ID
            String yhSjid = "";//设置上级ID
            String yhSsjid = "";//上上级ID

            String yhYyyqm = user.getYhYyyqm();//该用户的父级ID
            SimpleCondition newCondition = new SimpleCondition(BizPtyh.class);
            newCondition.eq(BizPtyh.InnerColumn.yhZsyqm.name(), yhYyyqm);
            List<BizPtyh> bizPtyhsList = ptyhService.findByCondition(newCondition);
            if (bizPtyhsList == null) return ApiResponse.fail("用户资料存在异常，请联系管理处理!");
            if (bizPtyhsList.size() != 1) return ApiResponse.fail("用户资料存在异常，请联系管理处理!");
            String pUserId = bizPtyhsList.get(0).getId();//获取出父级ID
            yhSjid = pUserId;
            BizUser pBizUser = userMapper.selectByPrimaryKey(yhSjid);//获取出上上级ID
            if (pBizUser != null) {
                yhSsjid = pBizUser.getYhSjid();
            }

            //修改用户实名表  biz_user
            BizUser bizUser = new BizUser();
            bizUser.setYhId(user.getId());//用户ID
            bizUser.setYhZjhm(user.getYhZjhm());//用户证件号码
            bizUser.setYhSjhm(user.getYhZh());//用户账户
            bizUser.setYhSfjsz(user.getYhSfyjz());//设置是否有驾驶证(1:有 2:没有)
            bizUser.setYhXm(user.getYhXm());//姓名
            bizUser.setCjsj(DateUtils.getNowTime());//创建时间
            bizUser.setYhSjid(yhSjid);//设置上级ID
            bizUser.setYhSsjid(yhSsjid);//上上级ID
            int i = userMapper.updateByPrimaryKey(bizUser);
            RuntimeCheck.ifTrue(i != 1, "操作失败，请重新尝试");
            newEntity.setYhZt("1");
            newEntity.setYhZtMs("");
        } else {
            String yhZtMs = bizPtyh.getYhZtMs();
            RuntimeCheck.ifBlank(yhZtMs, "请填写审核失败原因。");
            newEntity.setYhZt("2");
            newEntity.setYhZtMs(yhZtMs);
        }

        int k = update(newEntity);
        return k == 1 ? ApiResponse.success() : ApiResponse.fail();

    }

    @Override
    public BizPtyh findByIdSelect(String userid) {
        BizPtyh obj = this.findById(userid);
        this.afterReturn(obj);
        return obj;
    }

//==============================================================APP端  开始====================

    /**
     * 用户注册操作
     *
     * @param entity
     * @return
     */
    @Override
    public ApiResponse<String> userEnroll(BizPtyh entity, HttpServletRequest request) throws IOException,
            WxErrorException {


        RuntimeCheck.ifBlank(entity.getYhZh(), "用户账户不能为空");
        String s = redisDao.boundValueOps(entity.getYhZh()).get();
        if (StringUtils.isBlank(s)) {
            redisDao.boundValueOps(entity.getYhZh()).set("1", 5, TimeUnit.SECONDS);
        } else {
            return ApiResponse.fail("操作频繁, 请稍后再试");
        }
        String telIdentifying = entity.getTelIdentifying();//短信验证码
        RuntimeCheck.ifBlank(telIdentifying, "短信验证码不能为空");
        RuntimeCheck.ifBlank(entity.getYhYyyqm(), "用户应邀邀请码不能为空");
        entity.setYhYyyqm(entity.getYhYyyqm().toUpperCase());
        SimpleCondition newCondition = new SimpleCondition(BizPtyh.class);
        newCondition.eq(BizPtyh.InnerColumn.yhZsyqm.name(), entity.getYhYyyqm());
        newCondition.eq(BizPtyh.InnerColumn.yhSfsd.name(), "0");//用户没有锁定
//        newCondition.eq(BizPtyh.InnerColumn.ddSfjx.name(), "1");//是否缴费 ZDCLK0045 (0 未缴费 1 已缴费)

        // 判断用户邀请码是否过期 todo
        List<BizPtyh> bizPtyhs = ptyhService.findByCondition(newCondition);
        RuntimeCheck.ifEmpty(bizPtyhs, "用户邀请码失效!");
        RuntimeCheck.ifBlank(bizPtyhs.get(0).getYhYqmgqsj(), "用户邀请码失效");
        if (bizPtyhs.get(0).getYhYqmgqsj().compareTo(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM" +
                "-dd HH:mm:ss"))) < 0) {
            return ApiResponse.fail("用户邀请码已过期");
        }


        String yhZh = entity.getYhZh();
        ApiResponse<String> validate = this.validateSms(yhZh, telIdentifying, "1");
        RuntimeCheck.ifTrue(validate.getCode() != 200, validate.getMessage());

//      用户应邀邀请码存在造假的可能。是否需要验证,这里的验证是注册下发短信时，已经查了数据库
        String app_sendSMS_yyyqm = redisDao.boundValueOps(appSendSMSRegister + "zh" + yhZh).get();
        RuntimeCheck.ifFalse(StringUtils.equals(entity.getYhZh(), app_sendSMS_yyyqm), "账号错误，请重新发送短信验证码");

        RuntimeCheck.ifBlank(entity.getYhMm(), "用户密码不能为空");
        RuntimeCheck.ifTrue(entity.getYhMm().length() < 6, "用户密码不能小于6位");
//        RuntimeCheck.ifBlank(entity.getYhXm(),"用户姓名不能为空");
//        RuntimeCheck.ifBlank(entity.getYhZjhm(),"用户证件号码不能为空");

        RuntimeCheck.ifBlank(entity.getYhLx(), "用户类型不能为空");//类型 ZDCLK0041(2、教练、1、学员)
        if (StringUtils.containsNone(entity.getYhLx(), new char[]{'1', '2', '3'})) {
            return ApiResponse.fail("请输入正确用户类型");
        }
        String yhEncrypt = "";
        yhEncrypt = EncryptUtil.encryptUserPwd(entity.getYhMm());
        RuntimeCheck.ifBlank(yhEncrypt, "用户密码加密失败，用户注册失败");

        SimpleCondition condition = new SimpleCondition(BizPtyh.class);
        condition.eq(BizPtyh.InnerColumn.yhZh.name(), entity.getYhZh());
        Integer count = this.countByCondition(condition);
        RuntimeCheck.ifTrue(count > 0, "账号已存在，请更换别的登陆账号！");

//        注册类型  1、微信注册  2、支付宝注册 3、web页面注册
        String addType = entity.getAddType();
        RuntimeCheck.ifBlank(addType, "注册类型不能为空");
        if (StringUtils.containsNone(addType, new char[]{'1', '2', '3'})) {
            return ApiResponse.fail("请输入正确注册类型");
        }
        // TODO: 2018/5/19 一定要确定，注册来源
        String yhOpenId = entity.getYhOpenId();//微信OPEN_ID
        String yhAlipayId = "";//支付宝ID
        if (StringUtils.equals(addType, "1")) {
            yhOpenId = ""; // TODO: 2018/5/19 请求微信的OPEN_ID
            RuntimeCheck.ifBlank(yhOpenId, "微信唯一编号不能为空");
        } else if (StringUtils.equals(addType, "2")) {
            yhAlipayId = ""; // TODO: 2018/5/19 请求支付宝的ID
            RuntimeCheck.ifBlank(yhAlipayId, "支付宝唯一编号不能为空");
        }
        if (StringUtils.isEmpty(yhOpenId)) {
            yhOpenId = request.getHeader("openid");
        }
        Map<String, String> requestMap = new HashMap<>();
        //遍历 header 头部信息

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            requestMap.put(key, value);
        }
        String headerString = "";
        try {
            headerString = mapper.writeValueAsString(requestMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        payInfo.debug("------ptyhService.openid=" + yhOpenId);
        payInfo.debug("------request.getHeader(\"openid\")=" + request.getHeader("openid"));
        payInfo.debug("------request.getHeader(\"openId\")=" + request.getHeader("openId"));
        payInfo.debug("------request.getHeader=" + headerString);
        //没有填写别名的时候，将手机码后四位填写到别名中去
        if (StringUtils.isEmpty(entity.getYhBm())) {
            String yhbm = entity.getYhZh();
            if (StringUtils.isNotEmpty(yhbm) && yhbm.length() > 4) {
                yhbm = yhbm.substring(yhbm.length() - 4);
            }
            entity.setYhBm(yhbm);
        }

        BizPtyh newEntity = new BizPtyh();
        newEntity.setId(genId());//获取ID
        newEntity.setYhZh(entity.getYhZh());//用户账户
        newEntity.setYhMm(yhEncrypt);//用户密码
        newEntity.setCjsj(DateUtils.getNowTime());//创建时间
        newEntity.setYhXm(entity.getYhXm());//姓名
        newEntity.setYhLx(entity.getYhLx());//用户类型
        newEntity.setYhXb(entity.getYhXb());//用户性别
        newEntity.setYhZjhm(entity.getYhZjhm());//用户证件号码
        newEntity.setYhZt("0");//认证状态 ZDCLK0043(0 未认证、1 已认证)
        newEntity.setDdSfjx("0");//是否缴费 ZDCLK0045 (0 未缴费 1 已缴费)
        newEntity.setYhOpenId(yhOpenId);//微信OPEN_ID
        newEntity.setYhAlipayId(yhAlipayId);//支付宝ID
        newEntity.setYhTx(entity.getYhTx());//用户头像
        newEntity.setYhBm(entity.getYhBm());//用户别名
        newEntity.setYhYyyqm(entity.getYhYyyqm());//用户应邀邀请码f
        newEntity.setYhIxySffp("0");//学员是否已分配
        newEntity.setYhSfyjz(entity.getYhSfyjz());//学员是否有驾照
        newEntity.setYhSfsd("0");//用户是否锁定 ZDCLK0046 (0 否  1 是)
        newEntity.setYhTx("temp/192.png");

        String bizptyhlog = "";
        try {
            bizptyhlog = mapper.writeValueAsString(newEntity);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        //      获取用户父级ID
        String yhSjid = "";//设置上级ID
        String yhSsjid = "";//上上级ID

        String yhYyyqm = entity.getYhYyyqm();//该用户的父级ID
        newCondition = new SimpleCondition(BizPtyh.class);
        newCondition.eq(BizPtyh.InnerColumn.yhZsyqm.name(), yhYyyqm);
        List<BizPtyh> bizPtyhsList = ptyhService.findByCondition(newCondition);
        if (bizPtyhsList == null) return ApiResponse.fail("用户资料存在异常，请联系管理处理!");
        if (bizPtyhsList.size() != 1) return ApiResponse.fail("用户资料存在异常，请联系管理处理!");
        String pUserId = bizPtyhsList.get(0).getId();//获取出父级ID
        yhSjid = pUserId;
        BizUser pBizUser = userMapper.selectByPrimaryKey(yhSjid);//获取出上上级ID
        if (pBizUser != null) {
            yhSsjid = pBizUser.getYhSjid();
        }
        int i = 0;
        BizUser bizUser = new BizUser();
        if (StringUtils.isNotBlank(entity.getYhZjhm()) && StringUtils.isNotBlank(entity.getYhXm())) {
            String identify = IDNameIdentify.indentifyIdCard(entity.getYhZjhm(), entity.getYhXm());
            RuntimeCheck.ifFalse(StringUtils.equals(identify, "200"), identify);
            newEntity.setYhXm(entity.getYhXm());//用户姓名
            newEntity.setYhBm(entity.getYhBm());//用户别名
            newEntity.setYhZjhm(entity.getYhZjhm());//用户证件号码
            newEntity.setYhXb(entity.getYhXb());//用户性别
            String yhSfyjz = "0";
            if (StringUtils.isNotEmpty(entity.getYhSfyjz())) {
                yhSfyjz = entity.getYhSfyjz();
            }
            newEntity.setYhSfyjz(yhSfyjz);//用户驾照状态不能为空
            newEntity.setYhZt("1");//学员认证状态 ZDCLK0043(0 未认证、1 已认证)
            newEntity.setYhZtMs("");//用户驾照状态不能为空

            bizUser.setYhId(newEntity.getId());//用户ID
            bizUser.setYhZjhm(entity.getYhZjhm());//用户证件号码
            bizUser.setYhSjhm(newEntity.getYhZh());//用户账户
            bizUser.setYhSfjsz(newEntity.getYhSfyjz());//设置是否有驾驶证(1:有 2:没有)
            bizUser.setYhXm(entity.getYhXm());//姓名
            bizUser.setCjsj(DateUtils.getNowTime());//创建时间
            bizUser.setYhSjid(yhSjid);//设置上级ID
            bizUser.setYhSsjid(yhSsjid);//上上级ID
        } else {
            //插入用户实名表  biz_user
            bizUser.setYhId(newEntity.getId());//用户ID
            bizUser.setYhZjhm(newEntity.getYhZjhm());//用户证件号码
            bizUser.setYhSjhm(newEntity.getYhZh());//用户账户
            bizUser.setYhSfjsz(newEntity.getYhSfyjz());//设置是否有驾驶证(1:有 2:没有)
            bizUser.setYhXm(newEntity.getYhXm());//姓名
            bizUser.setCjsj(DateUtils.getNowTime());//创建时间
            bizUser.setYhSjid(yhSjid);//设置上级ID
            bizUser.setYhSsjid(yhSsjid);//上上级ID
        }
        //  用户注册生成邀请码
        String yhZsyqm = "";
        boolean flag = true;
        while (flag) {
            yhZsyqm = ShoreCode.createShareCode();
            List<BizPtyh> ptyhs = ptyhService.findEq(BizPtyh.InnerColumn.yhZsyqm, yhZsyqm);
            if (CollectionUtils.isEmpty(ptyhs)) {
                flag = false;
            }
        }
        String filepath = "/QRCode/" + DateTime.now().toString("yyyyMMdd") + "/" + yhZsyqm + ".png";
        WxMpQrCodeTicket ticket = wxMpService.getQrcodeService().qrCodeCreateLastTicket(newEntity.getId());
        String qrCode = ticket.getTicket();
        String pictureUrl = wxMpService.getQrcodeService().qrCodePictureUrl(qrCode);
        URL u = new URL(pictureUrl);

        FileUtils.copyURLToFile(u, new File(qrCodeFileUrl + filepath), 5000, 5000);
        newEntity.setQrcode(ticket.getUrl());
        newEntity.setYhZsyqm(yhZsyqm);
        newEntity.setYhZsyqmImg(filepath);
        newEntity.setYhYqmgqsj(DateTime.now().plusYears(1).toString("yyyy-MM-dd HH:mm:ss"));
        newEntity.setYhYqmkssj(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        newEntity.setYhLx("3");

        entityMapper.insertSelective(newEntity);

        i = userMapper.insert(bizUser);
        RuntimeCheck.ifTrue(i != 1, "操作失败，请重新尝试");

        redisDao.delete(appSendSMSRegister + "yyyqm" + yhZh);
        redisDao.delete(appSendSMSRegister + yhZh);

        BizYjmx yjmx = new BizYjmx();
        yjmx.setId(genId());
        yjmx.setZjFs("-1");
        yjmx.setZjZt("1");
        yjmx.setZjJe(20000d);
        yjmx.setYhId(newEntity.getId());
        yjmx.setTxShZt("1");
        yjmx.setMxlx("3");
        yjmx.setCjsj(DateUtils.getNowTime());
        yjmxService.save(yjmx);

        service.execute(() -> zhService.userAccountUpdate(Arrays.asList(newEntity.getId())));


        /*BizZh bizZh = new BizZh();
        bizZh.setYhId(newEntity.getId());
        bizZh.setYhTxdj(0d);
        bizZh.setYhZhye(-20000d);
        zhService.save(bizZh);*/
        return i == 1 ? ApiResponse.success() : ApiResponse.fail();
    }

    /**
     * 修改登录密码
     *
     * @param userId 用户id
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @return 操作结果
     */
    @Override
    public ApiResponse<String> mdfPwd(String userId, String oldPwd, String newPwd) {

        BizPtyh user = entityMapper.selectByPrimaryKey(userId);
        String newEncrypt;
        if (user == null) return ApiResponse.fail("用户不存在");
        try {
            String encrypt = Des.encrypt(oldPwd);
            if (!encrypt.equals(user.getYhMm())) {
                return ApiResponse.fail("原始密码错误");
            }
            newEncrypt = Des.encrypt(newPwd);
        } catch (Exception e) {
            return ApiResponse.fail("加密失败");
        }
        user.setYhMm(newEncrypt);
        entityMapper.updateByPrimaryKeySelective(user);
        return ApiResponse.success();
    }

    /**
     * 微信登录
     *
     * @param openId 获得用户的OPEN_ID
     */
    @Override
    public ApiResponse<Map<String, Object>> wxlogin(String openId) {

        Example condition = new Example(BizPtyh.class);
        condition.and().andEqualTo(BizPtyh.InnerColumn.yhOpenId.name(), openId);
        List<BizPtyh> existUser = this.findByCondition(condition);
        Map<String, Object> rMap = new HashMap<>(2);
        ApiResponse<Map<String, Object>> result = new ApiResponse<>();
        if (existUser != null && existUser.size() > 0) {
            BizPtyh item = existUser.get(0);
            RuntimeCheck.ifTrue(!"1".equals(item.getYhSfsd()), "用户已禁用！");
            try {
                String token = JwtUtil.createToken(item.getId(), item.getYhXm());
                redisDao.boundValueOps(item.getId()).set(token, 1, TimeUnit.DAYS);
                redisDao.boundValueOps(item.getId() + "-appUserInfo").set(mapper.writeValueAsString(item), 1,
                        TimeUnit.DAYS);
                AccessToken aToken = new AccessToken();
                aToken.setUserId(item.getId());
                aToken.setUsername(item.getYhBm());//用户别名
                aToken.setToken(token);

                rMap.put("accessToken", aToken);
                result.setResult(rMap);
            } catch (Exception e) {
                result.setCode(ApiResponse.FAILED);
                result.setMessage("用户登陆失败，请重试！");
            }
        } else {
            result.setCode(203);
            result.setMessage("该微信用户未注册，请您注册后使用系统！");
        }

        return result;
    }

    /**
     * 用户头像，别名 修改
     *
     * @param entity
     * @return
     */
    @Override
    public ApiResponse<String> updateUserInfo(BizPtyh entity) {
        BizPtyh user = getAppCurrentUser();
        if (user == null) return ApiResponse.fail("用户不存在");
        BizPtyh newEntity = new BizPtyh();
        newEntity.setId(user.getId());
        if (StringUtils.isNotEmpty(entity.getYhTx())) {
            newEntity.setYhTx(entity.getYhTx());
        }
        if (StringUtils.isNotEmpty(entity.getYhBm())) {
            newEntity.setYhBm(entity.getYhBm());
        }
        int i = update(newEntity);
        return i == 1 ? ApiResponse.success() : ApiResponse.fail();
    }

    /**
     * 用户实名操作
     *
     * @param entity yhXm     用户姓名
     *               yhZjhm     用户证件号码
     *               yhXb     用户性别
     *               yhSfyjz     用户驾照状态不能为空
     *               imgList 以,进行分隔
     *               imgTypeList 以,进行分隔
     *               <p>
     *               1、证件照片上传文件表  biz_wj
     *               2、修改用户表 biz_ptyh
     *               <p>
     *               下面两步是在后台审核时操作的。
     *               3、上传实名表 biz_user
     *               4、上传定单表  biz_order
     * @return
     */
    @Override
    public ApiResponse<String> updateUserReal(BizPtyh entity) throws WxErrorException, IOException {
        BizPtyh userRequest = getAppCurrentUser();
        if (userRequest == null) return ApiResponse.fail("用户不存在");

        BizPtyh user = entityMapper.selectByPrimaryKey(userRequest.getId());
        if (user == null) {
            return ApiResponse.fail("用户不存在");
        }
        if (StringUtils.equals(user.getYhZt(), "1")) {
            return ApiResponse.fail("用户已实名认证成功，无需此操作");
        }
        if (StringUtils.equals(user.getYhSfsd(), "1")) {
            return ApiResponse.fail("用户已锁定，无法进行操作");
        }
        if (StringUtils.isEmpty(entity.getImgList()) && StringUtils.isBlank(entity.getYhZjhm())) {
            return ApiResponse.fail("请上传证件照片");
        }


//        如果有识别信息的情况下，走自动审核业务
        String ocrRecognitionJson = redisDao.boundValueOps(userRequest.getId() + "-ocrRecognition-map").get();
        if (StringUtils.isNotBlank(ocrRecognitionJson)) {
            return updateUserRealAuditing(entity, ocrRecognitionJson, user);
        }

        RuntimeCheck.ifBlank(entity.getYhXm(), "用户姓名不能为空");
        RuntimeCheck.ifBlank(entity.getYhZjhm(), "用户证件号码不能为空");
        RuntimeCheck.ifTrue(entity.getYhZjhm().length() != 18, "证件号码格式不正确");
        String CardCode = entity.getYhZjhm();
        String sex;//获取性别 ZDCLK0042(1、男;2、女)
        if (Integer.parseInt(CardCode.substring(16).substring(0, 1)) % 2 == 0) {// 判断性别
            sex = "2";
        } else {
            sex = "1";
        }
        entity.setYhXb(sex);
        if (StringUtils.indexOf(user.getYhZh(), user.getYhBm()) > -1) {
            entity.setYhBm(entity.getYhXm());
        }

        String yhzjhm = entity.getYhZjhm();
        SimpleCondition condition = new SimpleCondition(BizPtyh.class);
        condition.eq(BizPtyh.InnerColumn.yhZjhm.name(), yhzjhm);
        condition.and().andNotEqualTo(BizPtyh.InnerColumn.id.name(), userRequest.getId());
        List<BizPtyh> listCount = this.findByCondition(condition);
        if (listCount != null && listCount.size() > 0) {
            RuntimeCheck.ifTrue(true, "该证件号已与手机号" + listCount.get(0).getYhZh() + "关联，请更换新的证件号！");
        }
        String[] imgList = StringUtils.split(entity.getImgList(), ",");
        String yhSfyjz = "0";//设置是否有驾照 ZDCLK0046 (0 否  1 是)

        List<BizWj> wjList = new ArrayList<>();
        List<String> wjSxList = new ArrayList<>();
        if (imgList != null && imgList.length > 0) {
            if (StringUtils.trimToNull(imgList[2]) != null && !StringUtils.equals(imgList[2], "-")) {
                yhSfyjz = "1";
            }
            for (int i = 0; i < imgList.length; i++) {
                if (StringUtils.trimToNull(imgList[i]) != null && !StringUtils.equals(imgList[i], "-")) {
                    BizWj wj = new BizWj();
                    wj.setId(genId());
                    wj.setYhId(user.getId());//
                    wj.setWjTpdz(imgList[i]);//

                    //ZDCLK0050 (0 10、 身份证正面 1 11、 身份证反面  2 20、 驾照正面 3 21、 驾照背面…………)
                    switch (i) {
                        case 0:
                            wj.setWjSx("10");
                            break;
                        case 1:
                            wj.setWjSx("11");
                            break;
                        case 2:
                            wj.setWjSx("20");
                            break;
                        case 3:
                            wj.setWjSx("21");
                            break;
                    }

                    wj.setWjSbzt("0");
                    wj.setCjsj(DateUtils.getNowTime());
                    wj.setWjSfyx("1");
                    wjList.add(wj);
                    wjSxList.add(wj.getWjSx());
                }
            }
        }
        //
        if (wjList.size() > 0) {
            wjMapper.deleteBatch(user.getId(), wjSxList);
            wjMapper.insertBatch(wjList);
        }
        String identify = IDNameIdentify.indentifyIdCard(CardCode, entity.getYhXm());
        RuntimeCheck.ifFalse(StringUtils.equals(identify, "200"), identify);
        BizPtyh newEntity = new BizPtyh();
        newEntity.setId(user.getId());
        newEntity.setYhXm(entity.getYhXm());//用户姓名
        newEntity.setYhBm(entity.getYhBm());//用户别名
        newEntity.setYhZjhm(entity.getYhZjhm());//用户证件号码
        newEntity.setYhXb(entity.getYhXb());//用户性别
        yhSfyjz = "0";
        if (StringUtils.isNotEmpty(entity.getYhSfyjz())) {
            yhSfyjz = entity.getYhSfyjz();
        }
        newEntity.setYhSfyjz(yhSfyjz);//用户驾照状态不能为空
        newEntity.setYhZt("1");//学员认证状态 ZDCLK0043(0 未认证、1 已认证)
        newEntity.setYhZtMs("");//用户驾照状态不能为空
        newEntity.setYhYqmgqsj(DateTime.now().plusYears(1).toString("yyyy-MM-dd HH:mm:ss"));
        newEntity.setYhYqmkssj(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        newEntity.setYhLx("3");
        if (StringUtils.isBlank(user.getYhZsyqm())) {
            String yhZsyqm = "";
            boolean flag = true;
            while (flag) {
                yhZsyqm = ShoreCode.createShareCode();
                List<BizPtyh> ptyhs = ptyhService.findEq(BizPtyh.InnerColumn.yhZsyqm, yhZsyqm);
                if (CollectionUtils.isEmpty(ptyhs)) {
                    flag = false;
                }
            }
            String filepath = "/QRCode/" + DateTime.now().toString("yyyyMMdd") + "/" + yhZsyqm + ".png";
            WxMpQrCodeTicket ticket = wxMpService.getQrcodeService().qrCodeCreateLastTicket(user.getId());
            String qrCode = ticket.getTicket();
            String pictureUrl = wxMpService.getQrcodeService().qrCodePictureUrl(qrCode);
            URL u = new URL(pictureUrl);

            FileUtils.copyURLToFile(u, new File(qrCodeFileUrl + filepath), 5000, 5000);
            newEntity.setQrcode(ticket.getUrl());
            newEntity.setYhZsyqm(yhZsyqm);
            newEntity.setYhZsyqmImg(filepath);
        }


        int i = update(newEntity);
        if (i > 0) {
            String yhSjid = "";//设置上级ID
            String yhSsjid = "";//上上级ID

            String yhYyyqm = user.getYhYyyqm();//该用户的父级ID
            SimpleCondition newCondition = new SimpleCondition(BizPtyh.class);
            newCondition.eq(BizPtyh.InnerColumn.yhZsyqm.name(), yhYyyqm);
            List<BizPtyh> bizPtyhsList = ptyhService.findByCondition(newCondition);
            if (bizPtyhsList == null) return ApiResponse.fail("用户资料存在异常，请联系管理处理!");
            if (bizPtyhsList.size() != 1) return ApiResponse.fail("用户资料存在异常，请联系管理处理!");
            String pUserId = bizPtyhsList.get(0).getId();//获取出父级ID
            yhSjid = pUserId;
            BizUser pBizUser = userMapper.selectByPrimaryKey(yhSjid);//获取出上上级ID
            if (pBizUser != null) {
                yhSsjid = pBizUser.getYhSjid();
            }
            yhSfyjz = "0";
            if (StringUtils.isNotEmpty(entity.getYhSfyjz())) {
                yhSfyjz = entity.getYhSfyjz();
            }

            //修改用户实名表  biz_user
            BizUser bizUser = new BizUser();
            bizUser.setYhId(user.getId());//用户ID
            bizUser.setYhZjhm(entity.getYhZjhm());//用户证件号码
            bizUser.setYhSjhm(user.getYhZh());//用户账户
            bizUser.setYhSfjsz(yhSfyjz);//设置是否有驾驶证(1:有 2:没有)
            bizUser.setYhXm(entity.getYhXm());//姓名
            bizUser.setCjsj(DateUtils.getNowTime());//创建时间
            bizUser.setYhSjid(yhSjid);//设置上级ID
            bizUser.setYhSsjid(yhSsjid);//上上级ID
            userMapper.updateByPrimaryKey(bizUser);
        }
        return i == 1 ? ApiResponse.success() : ApiResponse.fail();
    }

    /**
     * 用ocr识别的用户，直接进行审核
     *
     * @param entity
     * @param ocrRecognitionJson 识别出来的文本
     * @param user               用户的登录信息
     * @return
     */
    private ApiResponse<String> updateUserRealAuditing(BizPtyh entity, String ocrRecognitionJson, BizPtyh user) throws WxErrorException, IOException {
//        1、解析识别出来的报文
        Map<String, String> map = new HashMap<>();
        JSONObject jsonObject = new JSONObject(ocrRecognitionJson);
        String xm = jsonObject.getString("xm");
        String xb = jsonObject.getString("xb");
        String cfzh = jsonObject.getString("cfzh");
        String filePath = jsonObject.getString("filePath");
//        2、处理性别
        String sex = "2";//获取性别 ZDCLK0042(1、男;2、女)
        if (StringUtils.equals(xb, "男")) {// 判断性别
            sex = "1";
        }
        entity.setYhXb(sex);
        entity.setYhXm(xm);
        entity.setYhZjhm(cfzh);

        //默认将姓名填充到别名中去
        if (StringUtils.indexOf(user.getYhZh(), user.getYhBm()) > -1) {
            entity.setYhBm(xm);
        }


//        3、检查证件和手机号码是否有绑定
        String yhzjhm = entity.getYhZjhm();
        SimpleCondition condition = new SimpleCondition(BizPtyh.class);
        condition.eq(BizPtyh.InnerColumn.yhZjhm.name(), yhzjhm);
        condition.and().andNotEqualTo(BizPtyh.InnerColumn.id.name(), user.getId());
        List<BizPtyh> listCount = this.findByCondition(condition);
        if (listCount != null && listCount.size() > 0) {
            RuntimeCheck.ifTrue(true, "该证件号已与手机号" + listCount.get(0).getYhZh() + "关联，请更换新的证件号！");
        }
//        4、回写文件表
        String[] imgList = StringUtils.split(entity.getImgList(), ",");
        String yhSfyjz = "0";//设置是否有驾照 ZDCLK0046 (0 否  1 是)

        List<BizWj> wjList = new ArrayList<BizWj>();
        List<String> wjSxList = new ArrayList<String>();
        String zjFilePath = "";
        if (imgList != null && imgList.length > 0) {
            if (StringUtils.trimToNull(imgList[2]) != null && !StringUtils.equals(imgList[2], "-")) {
                yhSfyjz = "1";
            }
            for (int i = 0; i < imgList.length; i++) {
                if (StringUtils.trimToNull(imgList[i]) != null && !StringUtils.equals(imgList[i], "-")) {
                    BizWj wj = new BizWj();
                    wj.setId(genId());
                    wj.setYhId(user.getId());//
                    wj.setWjTpdz(imgList[i]);//

                    //ZDCLK0050 (0 10、 身份证正面 1 11、 身份证反面  2 20、 驾照正面 3 21、 驾照背面…………)
                    switch (i) {
                        case 0:
                            wj.setWjSx("10");
                            wj.setWjSbzt("1");
                            wj.setWjBw(ocrRecognitionJson);
                            zjFilePath = imgList[i];
                            break;
                        case 1:
                            wj.setWjSx("11");
                            break;
                        case 2:
                            wj.setWjSx("20");
                            break;
                        case 3:
                            wj.setWjSx("21");
                            break;
                    }
                    wj.setWjSbzt("0");
                    wj.setCjsj(DateUtils.getNowTime());
                    wj.setWjSfyx("1");
                    wjList.add(wj);
                    wjSxList.add(wj.getWjSx());
                }
            }
        }

        RuntimeCheck.ifFalse(StringUtils.equals(filePath, zjFilePath), "提交的文件名称不符，请重新上传！");
        //
        if (wjList.size() > 0) {
            wjMapper.deleteBatch(user.getId(), wjSxList);
            wjMapper.insertBatch(wjList);
        }
//        5、新增实名表
        String yhSjid = "";//设置上级ID
        String yhSsjid = "";//上上级ID

        String yhYyyqm = user.getYhYyyqm();//该用户的父级ID
        SimpleCondition newCondition = new SimpleCondition(BizPtyh.class);
        newCondition.eq(BizPtyh.InnerColumn.yhZsyqm.name(), yhYyyqm);
        List<BizPtyh> bizPtyhsList = ptyhService.findByCondition(newCondition);
        if (bizPtyhsList == null) return ApiResponse.fail("用户资料存在异常，请联系管理处理!");
        if (bizPtyhsList.size() != 1) return ApiResponse.fail("用户资料存在异常，请联系管理处理!");
        String pUserId = bizPtyhsList.get(0).getId();//获取出父级ID
        yhSjid = pUserId;
        BizUser pBizUser = userMapper.selectByPrimaryKey(yhSjid);//获取出上上级ID
        if (pBizUser != null) {
            yhSsjid = pBizUser.getYhSjid();
        }
        yhSfyjz = "0";
        if (StringUtils.isNotEmpty(entity.getYhSfyjz())) {
            yhSfyjz = entity.getYhSfyjz();
        }

        String identify = IDNameIdentify.indentifyIdCard(cfzh, xm);
        RuntimeCheck.ifFalse(StringUtils.equals("200", identify), identify);

        //修改用户实名表  biz_user
        BizUser bizUser = new BizUser();
        bizUser.setYhId(user.getId());//用户ID
        bizUser.setYhZjhm(entity.getYhZjhm());//用户证件号码
        bizUser.setYhSjhm(user.getYhZh());//用户账户
        bizUser.setYhSfjsz(yhSfyjz);//设置是否有驾驶证(1:有 2:没有)
        bizUser.setYhXm(entity.getYhXm());//姓名
        bizUser.setCjsj(DateUtils.getNowTime());//创建时间
        bizUser.setYhSjid(yhSjid);//设置上级ID
        bizUser.setYhSsjid(yhSsjid);//上上级ID
        int i = userMapper.updateByPrimaryKey(bizUser);
        RuntimeCheck.ifTrue(i != 1, "操作失败，请重新尝试");

//        6、修改用户主表
        BizPtyh newEntity = new BizPtyh();
        newEntity.setId(user.getId());
        newEntity.setYhXm(entity.getYhXm());//用户姓名
        newEntity.setYhZjhm(entity.getYhZjhm());//用户证件号码
        newEntity.setYhXb(entity.getYhXb());//用户性别
        newEntity.setYhSfyjz(yhSfyjz);//用户驾照状态不能为空
        newEntity.setYhZt("1");//学员认证状态 ZDCLK0043(0 未认证、1 已认证)
        newEntity.setYhBm(entity.getYhBm());//用户别名
        newEntity.setYhYqmgqsj(DateTime.now().plusYears(1).toString("yyyy-MM-dd HH:mm:ss"));
        newEntity.setYhYqmkssj(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        newEntity.setYhLx("3");
        if (StringUtils.isBlank(user.getYhZsyqm())) {
            String yhZsyqm = "";
            boolean flag = true;
            while (flag) {
                yhZsyqm = ShoreCode.createShareCode();
                List<BizPtyh> ptyhs = ptyhService.findEq(BizPtyh.InnerColumn.yhZsyqm, yhZsyqm);
                if (CollectionUtils.isEmpty(ptyhs)) {
                    flag = false;
                }
            }
            String filepath = "/QRCode/" + DateTime.now().toString("yyyyMMdd") + "/" + yhZsyqm + ".png";
            WxMpQrCodeTicket ticket = wxMpService.getQrcodeService().qrCodeCreateLastTicket(user.getId());
            String qrCode = ticket.getTicket();
            String pictureUrl = wxMpService.getQrcodeService().qrCodePictureUrl(qrCode);
            URL u = new URL(pictureUrl);

            FileUtils.copyURLToFile(u, new File(qrCodeFileUrl + filepath), 5000, 5000);
            newEntity.setQrcode(ticket.getUrl());
            newEntity.setYhZsyqm(yhZsyqm);
            newEntity.setYhZsyqmImg(filepath);
        }


        i = update(newEntity);
        /*BizZh bizZh = new BizZh();
        bizZh.setYhId(user.getId());
        bizZh.setYhTxdj(0d);
        bizZh.setYhZhye(-20000d);
        zhService.save(bizZh);*/
        return i == 1 ? ApiResponse.success() : ApiResponse.fail();
    }

    /**
     * 我的邀请码
     * 用户缴费成功后，为用户生成邀请码，未缴费引导用户缴费。
     *
     * @return
     */
    public BizPtyh getUserInvitationCode(String id) {
        BizPtyh user = this.findByIdSelect(id);
        RuntimeCheck.ifTrue(user == null, "用户资料有误！");
        RuntimeCheck.ifTrue(StringUtils.equals(user.getYhZt(), "0"), "用户还未认证，请您认证！");//认证状态 ZDCLK0043(0 未认证、1 已认证)
//        RuntimeCheck.ifTrue(StringUtils.equals(user.getDdSfjx(),"0"),"用户还未缴费，请您缴费！");//是否缴费 ZDCLK0045 (0 未缴费 1 已缴费)
        return user;
    }

    /**
     * 用户申请成为教练
     *
     * @param bizJl imgList
     * @return
     */
    @Override
    public ApiResponse<String> updatelx(BizJl bizJl) {
        if (StringUtils.isEmpty(bizJl.getImgList())) {
            return ApiResponse.fail("请上传证件照片");
        }

        BizPtyh bizPtyh = getAppCurrentUser();
        BizPtyh user = this.findById(bizPtyh.getId());
        String yhZjhm = bizJl.getYhZjhm();

        // 查看用户是否已经实名认证
        if (StringUtils.equals(user.getYhZt(), "1") || (StringUtils.indexOf(yhZjhm, "*") > -1)) {  // 用户已认证 ，会写部分信息
            bizJl.setYhXm(user.getYhXm());
            bizJl.setYhZjhm(user.getYhZjhm());
        }
        RuntimeCheck.ifTrue(StringUtils.equals(user.getYhJlsh(), "1"), "该用户已经提交申请");
        RuntimeCheck.ifTrue(StringUtils.equals(bizPtyh.getYhLx(), "2"), "该用户已经是教练");
        RuntimeCheck.ifBlank(bizJl.getYhXm(), "用户姓名不能为空");
        RuntimeCheck.ifBlank(bizJl.getYhZjhm(), "用户身份号码不能为空");

        SimpleCondition condition = new SimpleCondition(BizJl.class);
        condition.eq(BizJl.InnerColumn.yhZjhm.name(), bizJl.getYhZjhm());
        List<BizJl> bizJls = jlService.findByCondition(condition);
        if (CollectionUtils.isNotEmpty(bizJls) && StringUtils.equals(bizJls.get(0).getYhId(), bizPtyh.getId())) {
            jlMapper.deleteByPrimaryKey(bizPtyh.getId());
        } else if (CollectionUtils.isNotEmpty(bizJls) && !StringUtils.equals(bizJls.get(0).getYhId(),
                bizPtyh.getId())) {
            return ApiResponse.fail("该身份证已经与其他用户关联");
        }

        bizJl.setYhSjhm(bizPtyh.getYhZh());
        RuntimeCheck.ifBlank(bizJl.getJlJl(), "教练驾龄不能为空");
        RuntimeCheck.ifBlank(bizJl.getJlQu(), "教练所属区域不能为空");
//        RuntimeCheck.ifBlank(bizJl.getJlZml(), "教练证明人不能为空");
        RuntimeCheck.ifBlank(bizJl.getJlJjlxr(), "教练紧急联系人不能为空");
        RuntimeCheck.ifBlank(bizJl.getJlJjlxrdh(), "教练紧急联系人电话不能为空");
        RuntimeCheck.ifBlank(bizJl.getJlZz(), "住址不能为空");

        // 更新用户信息为教练 ，未认证
        BizPtyh ptyh = new BizPtyh();
        ptyh.setId(bizPtyh.getId());
//        ptyh.setYhLx("2"); // 2 为教练 1 为学员
//        ptyh.setYhZt("0"); // 0 为 未认证  1 为已认证
        ptyh.setYhJlsh("0");
        ptyh.setYhSfyjz("1"); // 0 没有驾照 1 有驾照
        update(ptyh);

        bizJl.setYhId(bizPtyh.getId());
        bizJl.setJlShZt(ptyh.getYhJlsh());


        jlService.save(bizJl);

        String[] imgList = StringUtils.split(bizJl.getImgList(), ",");
        String yhSfyjz = "0";//设置是否有驾照 ZDCLK0046 (0 否  1 是)

        List<BizWj> wjList = new ArrayList<BizWj>();
        List<String> wjSxList = new ArrayList<String>();
        if (imgList != null && imgList.length > 0) {
            if (StringUtils.trimToNull(imgList[2]) != null && !StringUtils.equals(imgList[2], "-")) {
                yhSfyjz = "1";
            }
            for (int i = 0; i < imgList.length; i++) {
                if (StringUtils.trimToNull(imgList[i]) != null && !StringUtils.equals(imgList[i], "-")) {
                    BizWj wj = new BizWj();
                    wj.setId(genId());
                    wj.setYhId(bizPtyh.getId());//
                    wj.setWjTpdz(imgList[i]);//

                    //ZDCLK0050 (0 10、 身份证正面 1 11、 身份证反面  2 20、 驾照正面 3 21、 驾照背面…………)
                    switch (i) {
                        case 0:
                            wj.setWjSx("10");
                            break;
                        case 1:
                            wj.setWjSx("11");
                            break;
                        case 2:
                            wj.setWjSx("20");
                            break;
                        case 3:
                            wj.setWjSx("21");
                            break;
                    }

                    wj.setWjSbzt("0");
                    wj.setCjsj(DateUtils.getNowTime());
                    wj.setWjSfyx("1");
                    wjList.add(wj);
                    wjSxList.add(wj.getWjSx());
                }
            }
        }
        //
        if (wjList.size() > 0) {
            wjMapper.deleteBatch(bizPtyh.getId(), wjSxList);
            wjMapper.insertBatch(wjList);
        }


        return ApiResponse.success();
    }

    /**
     * 根据条件分页搜索已经认证的教练
     *
     * @param name
     * @param phone
     * @param area
     * @param
     * @return
     */
    @Override
    public ApiResponse<List<BizPtyh>> getCoaches(String name, String phone, String area, int pageNum, int pageSize) {
        List<BizPtyh> list = new ArrayList<>();
        // 若三个条件都为空 分页查询所有已经认证的教练
        SimpleCondition condition = new SimpleCondition(BizPtyh.class);
        condition.eq(BizPtyh.InnerColumn.yhZt.name(), "1"); // 0 未认证 1 已认证
        condition.eq(BizPtyh.InnerColumn.yhLx.name(), "2"); // 1 学员 2 教练
        PageHelper.startPage(pageNum, pageSize);

        if (StringUtils.isEmpty(name) && StringUtils.isEmpty(phone) && StringUtils.isEmpty(area)) {

            List<BizPtyh> bizPtyhs = this.findByCondition(condition);
            bizPtyhs.stream().forEach(bizPtyh -> {
                list.add(afterReturns(bizPtyh));
            });
            return ApiResponse.success(list);
        }

        if (StringUtils.isNotBlank(name)) {
            condition.like(BizPtyh.InnerColumn.yhXm.name(), "%" + name + "%");
        }
        if (StringUtils.isNotBlank(phone)) {
            condition.like(BizPtyh.InnerColumn.yhZh.name(), "%" + phone + "%");
        }
        if (StringUtils.isNotBlank(area)) {

            SimpleCondition jlCondition = new SimpleCondition(BizJl.class);
            //  根据区域查出用户 id
            jlCondition.like(BizJl.InnerColumn.jlQu.name(), "%" + area + "");
            List<BizJl> bizJls = jlService.findByCondition(jlCondition);
            // 拿到所在区域的所有教练的 id
            List<String> jlIds = bizJls.stream().map(BizJl::getYhId).collect(Collectors.toList());
            List<BizPtyh> bizPtyhList = entityMapper.getJls(name, phone, jlIds);
            bizPtyhList.stream().forEach(bizPtyh -> {
                list.add(afterReturns(bizPtyh));
            });
            return ApiResponse.success(list);
        }

        List<BizPtyh> ptyhs = this.findByCondition(condition);
        ptyhs.stream().forEach(bizPtyh -> {
            list.add(afterReturns(bizPtyh));
        });
        return ApiResponse.success(list);
    }

    @Override
    public ApiResponse<List<String>> assignStudents(String yhId, String jlId, String jlType) {
        SysYh user = getCurrentUser();
        BizPtyh users = this.findById(jlId);
        RuntimeCheck.ifTrue(ObjectUtils.isEmpty(users), "该用户不存在");
        RuntimeCheck.ifBlank(yhId, "请选择学员");
//        RuntimeCheck.ifFalse(StringUtils.equals(users.getYhLx(),"2"),"教练信息有误，请核实后再操作");
//        RuntimeCheck.ifFalse(StringUtils.equals(users.getYhJlsh(),"1"),"该教练未进行实名认证");

        // 将多个学员id 分开
        List<String> ids = Arrays.asList(yhId.split(","));
        // 可以分配的用户 id
//        List<String> ids = userService.getYhIds(sIds);

        // 进行分配操作
        if (CollectionUtils.isNotEmpty(ids)) {
            // 如果是分配的科目二教练 需要将 科目三一同分配
            entityMapper.updateJxsl(jlId, ids.size());

            userService.updateJlId(ids, jlId, jlType);
            entityMapper.updateJlFp(ids, "该学员于：" + DateUtils.getNowTime() + " 分配给受理专员：" + users.getYhXm() + "");

            if (StringUtils.equals(jlType, "2")) {

                userService.updateJlId(ids, jlId, "3");
                BizJl jl = jlService.findById(jlId);
                RuntimeCheck.ifBlank(jl.getSubSchoolId(), "教练尚未绑定代培点, 请先绑定代培点");
                List<BizPtyh> ptyhs = ptyhService.findByIds(ids);

                // 从字典中获取
                List<SysZdxm> zdxms = zdxmService.findEq(SysZdxm.InnerColumn.zdlmdm, "subFee");
                RuntimeCheck.ifEmpty(zdxms, "请设置代培费");
                Map<String, String> map = zdxms.stream().collect(Collectors.toMap(SysZdxm::getZddm, SysZdxm::getZdmc));
                // 需要生成 代培费纪录
                ptyhs.forEach(p -> {
                    if(StringUtils.equals(p.getYhXyYkType(), "11") || p.getYhXyYkType().compareTo("20") >=0){
                        BizPtyh ptyh = new BizPtyh();
                        ptyh.setYhK2SubId(jl.getSubSchoolId());
                        ptyh.setYhK2SubJe(Integer.parseInt(map.get("k2")));
                        ptyh.setYhK2SubName(jl.getSubSchoolName());
                        BizFp fp = new BizFp();
                        fp.setId(genId());
                        fp.setCjsj(DateUtils.getNowTime());
                        fp.setFpkm("2");
                        fp.setFpms("该学员于" + DateUtils.getNowTime() + " 分配给 " + jl.getYhXm());
                        fp.setSfdk("0");
                        fp.setSubSchoolId(jl.getSubSchoolId());
                        fp.setSubSchoolName(jl.getSubSchoolName());
                        fp.setYhId(p.getId());
                        fpMapper.insert(fp);
                        ptyh.setYhK3SubId(jl.getSubSchoolId());
                        ptyh.setYhK3SubJe(Integer.parseInt(map.get("k3")));
                        ptyh.setYhK3SubName(jl.getSubSchoolName());
                        ptyh.setYhK2Sh("0");
                        ptyh.setYhK3Sh("0");
                        fp.setId(genId());
                        fp.setFpkm("3");
                        fpMapper.insert(fp);
                        ptyh.setId(p.getId());
                        update(ptyh);
                    }else {
                        RuntimeCheck.ifTrue(true, "学员科目一成绩合格才能分配教练");
                    }

                });
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        map.put("jlId", jlId);
        map.put("jlType", jlType);
        map.put("user", user);
        eventBus.post(map);
        return ApiResponse.success(ids);
    }

    @Subscribe
    public void sendObject(Map<String, Object> map) {
        payInfo.debug("进入异步通知开始 Async begin---");
        try {
            List<String> ids = (List<String>) map.get("ids");
            String jlId = (String) map.get("jlId");
            String jlType = (String) map.get("jlType");
            SysYh user = (SysYh) map.get("user");
            this.wxSendMessage(ids, jlId, jlType, user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        payInfo.debug("进入异步通知开始 Async END---");
    }

    /**
     * 分配学员-下发微信消息
     *
     * @param ids
     * @param jlId
     */
    public void wxSendMessage(List<String> ids, String jlId, String jlType, SysYh user) {
        String jlMessage = "专员您好，为您分配了" + ids.size() + "位学员。请您及时联系！";//给教练下发的记录
        payInfo.debug("分配学员-下发消息--------------------");
        System.out.println(
                "------------------------------------------------------------------------------------------");
        try {
            BizJl jlMsage = jlService.findById(jlId);
            BizPtyh appJlUser = ptyhService.findById(jlId);
            payInfo.debug("appJlUser.getYhOpenId():" + appJlUser.getYhOpenId());

            List<WxMpTemplateData> data = new ArrayList<>();
            data.add(new WxMpTemplateData("first", "专员您好，已为您分配到了新的学员"));
            data.add(new WxMpTemplateData("keyword1", jlMsage.getYhXm()));
            data.add(new WxMpTemplateData("keyword2", jlMsage.getYhSjhm()));//教练电话
            data.add(new WxMpTemplateData("keyword3", user.getXm()));
            data.add(new WxMpTemplateData("remark", jlMessage));
            WxMpTemplateMessage msg = new WxMpTemplateMessage();
            msg.setToUser(appJlUser.getYhOpenId());
            msg.setTemplateId(examMsgIdJl);
            msg.setUrl(wxJlDomain);
            msg.setData(data);
            String res = "";

            Map<String, String> jlSmsMap = new HashMap<String, String>();
            jlSmsMap.put("phoneNumbers", appJlUser.getYhZh());//电话号码
            jlSmsMap.put("templateType", "3");//给专员下发短信
//                ${userName}专员，你好！平台为您分配了一位新学员（${studentUser}），电话号码是（${studentTel}），请您及时与他联系并安排培训！
            jlSmsMap.put("userName", jlMsage.getYhXm());//专员姓名

            try {
                res = wechatService.sendTemplateMsg(msg, null);
            } catch (Exception e) {
            }
            payInfo.debug("sendMsg result :", res);

            SimpleCondition condition = new SimpleCondition(BizPtyh.class);
            condition.in(BizPtyh.InnerColumn.id.name(), ids);
            List<BizPtyh> userList = entityMapper.selectByExample(condition);
            String first = "";
            if (StringUtils.equals("0", jlType)) {
                first = "受理";
            } else if (StringUtils.equals("1", jlType)) {
                first = "第一";
            } else if (StringUtils.equals("2", jlType)) {
                first = "第二";
            } else if (StringUtils.equals("3", jlType)) {
                first = "第三";
            }
            Map<String, String> xbMap = new HashMap<>();
            xbMap.put("1", "男");
            xbMap.put("2", "女");
            for (BizPtyh u : userList) {
                data = new ArrayList<>();

                data.add(new WxMpTemplateData("first", first + "分配成功"));
                data.add(new WxMpTemplateData("keyword1", jlMsage.getYhXm()));
                data.add(new WxMpTemplateData("keyword2", jlMsage.getYhSjhm()));//教练电话
                data.add(new WxMpTemplateData("keyword3", user.getXm()));
                data.add(new WxMpTemplateData("remark",
                        "学员" + u.getYhXm() + "您好，" + first + "分配成功。" + first + "：" + jlMsage.getYhXm() + "，性别" + xbMap.get(appJlUser.getYhXb())));
                payInfo.debug("u.getYhOpenId():" + u.getYhOpenId());
                msg = new WxMpTemplateMessage();
                msg.setToUser(u.getYhOpenId());
                msg.setTemplateId(examMsgIdXy);
                msg.setUrl(wxXyDomain);
                msg.setData(data);
                try {
                    //尊敬的${userName}学员，您好！平台为您分配了${stage}的培训专员（${user}），电话号码是(${tel})，请保持手机畅通，以便专员与您联系！
                    Map<String, String> xlSmsMap = new HashMap<String, String>();
                    xlSmsMap.put("phoneNumbers", u.getYhZh());//电话号码
                    xlSmsMap.put("templateType", "4");//给专员下发短信
                    xlSmsMap.put("userName", u.getYhXm());//学员姓名
                    xlSmsMap.put("stage", first);
                    xlSmsMap.put("user", jlMsage.getYhXm());
                    xlSmsMap.put("tel", jlMsage.getYhSjhm());

                    jlSmsMap.put("studentUser", u.getYhXm());//学员姓名
                    jlSmsMap.put("studentTel", u.getYhZh());//学员电话
                    List<Map<String, String>> smsMapList = new ArrayList<Map<String, String>>();
                    smsMapList.add(xlSmsMap);
                    smsMapList.add(jlSmsMap);
                    res = wechatService.sendTemplateMsg(msg, smsMapList);
                } catch (Exception e) {
                }
                payInfo.debug("sendMsg result :", res);
            }
        } catch (Exception e) {
            payInfo.debug(String.valueOf(e));
        }
        payInfo.debug("分配学员-下发消息 END--------------------");
    }

    /**
     * 下发短信
     *
     * @param tel             手机号码
     * @param type            1、注册  2、重置密码
     * @param identifyingCode 验证码
     * @return
     */
    @Override
    public boolean sendSMS(String tel, int type, String identifyingCode) {
        boolean ret = false;
        if (StringUtils.isEmpty(identifyingCode)) {
            identifyingCode = StringDivUtils.getSix();//获取验证码
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("phoneNumbers", tel);//电话号码
        map.put("templateParam", "{\"code\":\"" + identifyingCode + "\"}");//短信验证码
        String redisKey = "";
        if (type == 1) {
            redisKey = appSendSMSRegister;
            //使用注册模板下发
            map.put("templateCode", "SMS_136430180");//用户注册验证码
        } else if (type == 2) {
            redisKey = appSendSMSResetting;
            //使用重置密码模板进行下发
            map.put("templateCode", "SMS_141935095");//密码重置
        } else {
            //类型不存在，不能下发
            return false;
        }
//        查询当前KEY过期时间还有多少秒 超过120秒后，可以再次下发短信
        long identifying = redisDao.getExpire(redisKey + tel, TimeUnit.SECONDS);
        if (identifying != -1 && 24 * 60 * 60 - identifying < 300) {
            return true;
        }
        // TODO: 2018/5/19 调试模式。
        if (debugTest != null && debugTest.equals("1")) {//调试
            ret = true;
        } else {
            //短信下发
            ret = SendSmsUtil.sendSms(map);
        }
        if (ret) {
            redisDao.boundValueOps(redisKey + tel).set(identifyingCode, 1, TimeUnit.DAYS);//设备验证码，为一天过期
        }
        return ret;
    }


    /**
     * 根据当前用户显示相关人员的列表
     *
     * @return
     */
    @Override
    public ApiResponse<List<BizPtyh>> getBizPtyhList(Page<BizPtyh> ptyhPage) {

        ApiResponse<List<BizPtyh>> result = new ApiResponse<>();

        PageInfo<BizPtyh> pageInfo = new PageInfo<>();
        // 获取当前登录用户
        BizPtyh user = getAppCurrentUser();
        // 鉴定该用户为 教练还是学员
        if (StringUtils.equals(user.getYhLx(), "1")) { // 用户为学员 展示其教练信息

            BizUser bizUser = userService.findById(user.getId());
            RuntimeCheck.ifTrue(ObjectUtils.isEmpty(bizUser), "学员信息不存在");
            SimpleCondition condition = new SimpleCondition(BizPtyh.class);
            condition.eq(BizPtyh.InnerColumn.id.name(), bizUser.getYhJlid());
            pageInfo = findPage(ptyhPage, condition);

            BizJl bizJl = jlService.findById(bizUser.getYhJlid());
            RuntimeCheck.ifTrue(ObjectUtils.isEmpty(bizJl), "该用户的教练未进行认证");
            if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
                pageInfo.getList().stream().forEach(
                        bizPtyh -> {
                            BizPtyh ptyh = afterReturns(bizPtyh);
                            ptyh.setYhMm(bizJl.getJlMs()); // 用户表中没有教练简介 ， 将 密码字段设置为 教练的简介
                            ptyh.setJljj(bizJl.getJlMs()); // 教练简介
                        }
                );
            }
            afterPager(pageInfo);
            result.setPage(pageInfo);
            return result;

        } else if (StringUtils.equals(user.getYhLx(), "2")) { // 用户为教练 ， 需要展示其学员列表

            SimpleCondition condition = new SimpleCondition(BizUser.class);
            condition.eq(BizUser.InnerColumn.yhJlid.name(), user.getId());
            List<BizUser> bizUsers = userService.findByCondition(condition);

            // 获取所有学员的 id
            List<String> yhIds = bizUsers.stream().map(BizUser::getYhId).collect(Collectors.toList());
            SimpleCondition yhCondition = new SimpleCondition(BizPtyh.class);
            yhCondition.in(BizPtyh.InnerColumn.id.name(), yhIds);
            yhCondition.eq(BizPtyh.InnerColumn.yhSfyjz.name(), "0"); // 查询学员中无驾照的
            pageInfo = findPage(ptyhPage, yhCondition);

            if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
                pageInfo.getList().stream().forEach(bizPtyh -> {
                    afterReturns(bizPtyh);
                });
            }
            afterPager(pageInfo);
            result.setPage(pageInfo);
            return result;
        }

        return result;
    }

    /**
     * 用户重置密码
     *
     * @param tel
     * @param code
     * @param newPwd
     * @return
     */
    @Override
    public ApiResponse<String> resetPwd(String tel, String code, String newPwd) {

        SimpleCondition condition = new SimpleCondition(BizPtyh.class);
        condition.eq(BizPtyh.InnerColumn.yhZh.name(), tel);
        List<BizPtyh> ptyhList = findByCondition(condition);
        RuntimeCheck.ifEmpty(ptyhList, "该账号尚未注册");

        // 验证短信验证码是否正确
        ApiResponse<String> sms = validateSms(tel, code, "2");

        RuntimeCheck.ifTrue(sms.getCode() != 200, sms.getMessage());


        BizPtyh newEntity = new BizPtyh();
        newEntity.setId(ptyhList.get(0).getId());

        // 重置密码加密
        String userPwd = EncryptUtil.encryptUserPwd(newPwd);
        RuntimeCheck.ifBlank(userPwd, "重置密码出错，请重新申请");
        newEntity.setYhMm(userPwd);
        int i = update(newEntity);
        return i == 1 ? ApiResponse.success() : ApiResponse.fail("重置失败");


    }

    /**
     * 当前登录学员修改驾照信息
     *
     * @return
     */
    @Override
    public ApiResponse<String> updateJz(BizPtyh ptyh) {

        BizPtyh bizPtyh = getAppCurrentUser();
        RuntimeCheck.ifTrue(StringUtils.containsNone(ptyh.getYhSfyjz(), new char[]{'0', '1'}), "学员驾照信息输入有误");
        RuntimeCheck.ifTrue(StringUtils.equals(bizPtyh.getYhSfyjz(), ptyh.getYhSfyjz()), "没有修改信息");
        BizPtyh entity = new BizPtyh();
        entity.setId(bizPtyh.getId());
        entity.setYhSfyjz(ptyh.getYhSfyjz());
        int i = update(entity);

        return i == 1 ? ApiResponse.success() : ApiResponse.fail("更新失败");
    }


    /**
     * 短信验证
     *
     * @param tel             手机号码
     * @param identifyingCode 验证码
     * @param type            1、注册  2、重置密码
     * @return
     */
    @Override
    public ApiResponse<String> validateSms(String tel, String identifyingCode, String type) {
        if (StringUtils.isEmpty(identifyingCode)) {
            return ApiResponse.fail("验证码不能为空");
        }
        if (StringUtils.isEmpty(tel)) {
            return ApiResponse.fail("手机号码不能为空");
        }
        String redisKey = "";
        if (StringUtils.equals(type, "1")) {//
            redisKey = appSendSMSRegister;
        } else if (StringUtils.equals(type, "2")) {//重置密码
            redisKey = appSendSMSResetting;
        } else {
            return ApiResponse.fail("验证失败");
        }

        String identifying = redisDao.boundValueOps(redisKey + tel).get();
        if (StringUtils.equals(identifying, identifyingCode)) {
            return ApiResponse.success();
        } else {
            return ApiResponse.fail("验证码有误");
        }
    }

    //
    /*public static void main(String[] args) {
        List<String> sids = new ArrayList<>();
        sids.add("1");
        sids.remove("1");
    }*/
    @Override
    public ApiResponse<String> validateCode(String code) {
        SimpleCondition newCondition = new SimpleCondition(BizPtyh.class);
        newCondition.eq(BizPtyh.InnerColumn.yhZsyqm.name(), code);
        List<BizPtyh> bizPtyhsList = ptyhService.findByCondition(newCondition);
        if (bizPtyhsList == null) return ApiResponse.fail("邀请码不存在!");
        if (bizPtyhsList.size() != 1) return ApiResponse.fail("邀请码不存在!");
        if (!StringUtils.equals(bizPtyhsList.get(0).getYhZt(), "1")) return ApiResponse.fail("邀请码不存在!");
        if (!StringUtils.equals(bizPtyhsList.get(0).getDdSfjx(), "1")) return ApiResponse.fail("邀请码不存在!");
        if (!StringUtils.equals(bizPtyhsList.get(0).getYhSfsd(), "0")) return ApiResponse.fail("邀请码已经锁定，不能邀请您!");
        return ApiResponse.success("验证成功");
    }

    @Override
    public ApiResponse<BizJl> getStudentCoach(String yhId) {
        BizPtyh users = getAppCurrentUser();
        yhId = users.getId();
        List<BizUser> userList = userService.findEq(BizUser.InnerColumn.yhId, yhId);
        if (userList.size() == 0) {
            return ApiResponse.success(new BizJl());
        }


        BizUser user = userList.get(0);
        RuntimeCheck.ifBlank(user.getYhJlid(), "专员暂未分配");
        BizJl coach = jlService.findById(user.getYhJlid());
        RuntimeCheck.ifNull(coach, "未找到教练");
        return ApiResponse.success(coach);
    }

    /**
     * 管理员给用户创建二维码
     *
     * @param userId
     * @return
     */
    public ApiResponse<String> creatorUserQRCode(String userId) {
        SysYh sysUser = getCurrentUser();
        if (StringUtils.equals(sysUser.getLx(), "su")) {
            //检查本人是否有权限操作此接口
            SysYhJs yhJs = new SysYhJs();
            yhJs.setYhId(sysUser.getYhid());
            List<SysYhJs> userJsList = userRoleMapper.select(yhJs);
            RuntimeCheck.ifNull(userJsList, "本人无限制进行此操作");
            List<String> userJsLis = userJsList.stream().map(SysYhJs::getJsId).collect(Collectors.toList());
            RuntimeCheck.ifFalse(userJsLis.contains("000000"), "本人无限制进行此操作");
        }

        RuntimeCheck.ifBlank(userId, "请选择用户");
        BizPtyh ptyh = entityMapper.selectByPrimaryKey(userId);
        if (ptyh == null) return ApiResponse.fail("学员不存在!");
        String yhZsyqm = ptyh.getYhZsyqm();
        String yhZsyqmImg = ptyh.getYhZsyqmImg();
        String userName = ptyh.getYhXm();
        if (StringUtils.isEmpty(yhZsyqm)) {
            yhZsyqm = genId();
        }
        if (StringUtils.isEmpty(yhZsyqmImg)) {
            yhZsyqmImg = "QRCode/" + DateUtils.getToday("yyyyMMdd") + "/" + yhZsyqm + ".png";
        }
        try {
            payInfo.debug("手工生成邀请码，手工生成邀请图片---");
            File logoFile = new File(logoFileUrl);

            File file = new File(qrCodeFileUrl + yhZsyqmImg);
            if (!file.exists() && !file.isDirectory()) {
                file.mkdirs();
            }
            String note = "您的好友：" + userName + " 邀请您";
            note = "";//经理说，生成的图片不需要增加文件。所以这行去掉
            ZXingCode.drawLogoQRCode(logoFile, new File(qrCodeFileUrl + yhZsyqmImg), yhZsyqm, note);
            payInfo.debug("用户：" + userName + "。生成邀请码成功。异步---");
        } catch (Exception e) {
            return ApiResponse.fail();
        }

        return ApiResponse.success();

    }

    public ApiResponse<String> removeUserInfo(String userId) {
        SysYh sysUser = getCurrentUser();
        if (!StringUtils.equals(sysUser.getLx(), "su")) {
            //检查本人是否有权限操作此接口
            SysYhJs yhJs = new SysYhJs();
            yhJs.setYhId(sysUser.getYhid());
            List<SysYhJs> userJsList = userRoleMapper.select(yhJs);
            RuntimeCheck.ifNull(userJsList, "本人无限制进行此操作");
            List<String> userJsLis = userJsList.stream().map(SysYhJs::getJsId).collect(Collectors.toList());
            RuntimeCheck.ifFalse(userJsLis.contains("000000"), "本人无限制进行此操作");
        }


        RuntimeCheck.ifBlank(userId, "请选择用户");
        BizPtyh ptyh = entityMapper.selectByPrimaryKey(userId);
        if (ptyh == null) return ApiResponse.fail("学员不存在!");
        RuntimeCheck.ifTrue(StringUtils.equals(ptyh.getDdSfjx(), "1"), "用户已缴费，不能进行此操作");

        BizWj wj = new BizWj();
        wj.setYhId(userId);
        wjMapper.delete(wj);

        BizUser bizUser = new BizUser();
        bizUser.setYhId(userId);
        userMapper.delete(bizUser);

        entityMapper.deleteByPrimaryKey(userId);

        return ApiResponse.success();
    }

    @Override
    public ApiResponse<List<BizJl>> getJls(String yhId) {
        BizPtyh currentUser;
        if (StringUtils.isBlank(yhId)) {
            currentUser = getAppCurrentUser();
        } else {
            currentUser = ptyhService.findById(yhId);
        }

        List<BizJl> bizJls = new ArrayList<>();
        BizUser bizUser = userService.findById(currentUser.getId());
        BizYhpf yhpf = new BizYhpf();
        yhpf.setYhId(bizUser.getYhId());
        addBizjls(bizJls, yhpf, bizUser.getYhJlid(), "0");  // 受理专员
        addBizjls(bizJls, yhpf, bizUser.getYhJlid1(), "1");  // 科目一
        addBizjls(bizJls, yhpf, bizUser.getYhJlid2(), "2");// 科目二
        addBizjls(bizJls, yhpf, bizUser.getYhJlid3(), "3");// 科目三
        addBizjls(bizJls, yhpf, bizUser.getYhJlid4(), "4");// 科目四
        return ApiResponse.success(bizJls);
    }

    private void addBizjls(List<BizJl> bizJls, BizYhpf yhpf, String yhJlid, String slType) {
        if (StringUtils.isNotBlank(yhJlid)) { //
            BizJl bizJl = jlService.findById(yhJlid);
            BizJl newBizJl = new BizJl();
//            DeepStudent s2 = (DeepStudent) s1.clone();
            yhpf.setJlId(bizJl.getYhId());
            yhpf.setSlType(slType);
            List<BizYhpf> entity = yhpfService.findByEntity(yhpf);
            if (CollectionUtils.isNotEmpty(entity)) {
                newBizJl.setYhFz(entity.get(0).getYhFz());
            } else {
                newBizJl.setYhFz(null);
            }
            newBizJl.setYhZjhm(bizJl.getYhZjhm().replaceAll("(\\d{3})\\d*(\\d{4})", "$1******$2"));
            newBizJl.setYhId(bizJl.getYhId());
            newBizJl.setYhXm(bizJl.getYhXm());
            newBizJl.setYhSjhm(bizJl.getYhSjhm());
            newBizJl.setCjsj(bizJl.getCjsj());
            newBizJl.setJlJl(bizJl.getJlJl());
            newBizJl.setJlQu(bizJl.getJlQu());
            newBizJl.setJlZml(bizJl.getJlZml());
            newBizJl.setJlJjlxr(bizJl.getJlJjlxr());
            newBizJl.setJlJjlxrdh(bizJl.getJlJjlxrdh());
            newBizJl.setJlZz(bizJl.getJlZz());
            newBizJl.setJlPf(bizJl.getJlPf());
            newBizJl.setJlImg(bizJl.getJlImg());
            newBizJl.setJlMs(bizJl.getJlMs());
            newBizJl.setJlShZt(bizJl.getJlShZt());
            newBizJl.setJlShMs(bizJl.getJlShMs());
            newBizJl.setJlJxsl(bizJl.getJlJxsl());
            // 查询教练的训练场信息
            BizTrainPlace place = placeService.findById(bizJl.getTrainId());
            newBizJl.setTrainPlace(place);
            bizJls.add(newBizJl);
        } else {
            bizJls.add(null);
        }
    }


    @Override
    public ApiResponse<List<List>> getPaymentRecord(String yhId) {

        List<List> lists = new ArrayList<>();
        RuntimeCheck.ifBlank(yhId, "用户id不能为空");

        // 查询 受理 记录
        SimpleCondition slCondition = new SimpleCondition(BizKsSl.class);
        slCondition.eq(BizKsSl.InnerColumn.yhId.name(), yhId);
        slCondition.setOrderByClause(" CJSJ desc ");
        List<BizKsSl> ksSlList = ksSlService.findByCondition(slCondition);
        // 查询缴费记录
        SimpleCondition jfCondition = new SimpleCondition(BizKsYk.class);
        jfCondition.eq(BizKsYk.InnerColumn.yhId.name(), yhId);
        jfCondition.setOrderByClause(" CJSJ desc");
        List<BizKsJf> ksJfList = ksjfService.findByCondition(jfCondition);
        // 查询约考记录
        SimpleCondition ykCondition = new SimpleCondition(BizKsYk.class);
        ykCondition.eq(BizKsYk.InnerColumn.yhId.name(), yhId);
        ykCondition.setOrderByClause(" CJSJ DESC ");
        List<BizKsYk> ksYkList = ksYkService.findByCondition(ykCondition);

        lists.add(ksSlList);
        lists.add(ksJfList);
        lists.add(ksYkList);


        return ApiResponse.success(lists);
    }

    @Override
    public ApiResponse<List<StatusModel>> statusQuery(BizPtyh entity, Page<BizPtyh> pager) {
        ApiResponse<List<BizPtyh>> res = pager(pager);
        ApiResponse<List<StatusModel>> result = new ApiResponse<>();
        result.setPage(res.getPage());
        List<BizPtyh> list = res.getPage().getList();
        if (list == null || list.size() == 0) {
            return result;
        }
        List<String> userIds = list.stream().map(BizPtyh::getId).collect(Collectors.toList());
        List<BizUser> userList = userService.findIn(BizUser.InnerColumn.yhId, userIds);
        Map<String, BizUser> userMap = userList.stream().collect(Collectors.toMap(BizUser::getYhId, p -> p));

        List<StatusModel> models = new ArrayList<>();
        for (BizPtyh ptyh : list) {
            BizUser user = userMap.get(ptyh.getId());
            if (user == null) continue;
            StatusModel model = new StatusModel(ptyh);
            BizPtyh zy0 = ptyhService.findById(user.getYhJlid());
            model.setZy0(zy0);

            BizPtyh zy1 = ptyhService.findById(user.getYhJlid1());
            model.setZy1(zy1);

            BizPtyh zy2 = ptyhService.findById(user.getYhJlid2());
            model.setZy0(zy2);

            BizPtyh zy3 = ptyhService.findById(user.getYhJlid3());
            model.setZy0(zy3);

            BizPtyh zy4 = ptyhService.findById(user.getYhJlid4());
            model.setZy0(zy4);

            BizKsYk km1 = getYk(ptyh, "1");
            model.setKm1(km1);

            BizKsYk km2 = getYk(ptyh, "2");
            model.setKm2(km2);

            BizKsYk km3 = getYk(ptyh, "3");
            model.setKm3(km3);

            BizKsYk km4 = getYk(ptyh, "4");
            model.setKm4(km4);
            models.add(model);
        }
        PageInfo pageInfo = res.getPage();
        pageInfo.setList(models);
        result.setPage(pageInfo);
        return result;
    }

    @Override
    public ApiResponse<List<BizPtyh>> getZyList(String type) {
        SimpleCondition condition = new SimpleCondition(SysYhJs.class);
        condition.eq(SysYhJs.InnerColumn.jsId, type);
        List<SysYhJs> userRoleList = userRoleMapper.selectByExample(condition);
        if (userRoleList.size() == 0) {
            return new ApiResponse<>();
        }

        List<String> sysUserIds = userRoleList.stream().map(SysYhJs::getYhId).collect(Collectors.toList());
        List<SysYh> sysUserList = yhService.findIn(SysYh.InnerColumn.yhid, sysUserIds);
        if (sysUserList.size() == 0) {
            return new ApiResponse<>();
        }
        List<String> idCardList = sysUserList.stream().map(SysYh::getZjhm).collect(Collectors.toList());
        List<BizPtyh> bizUserList = findIn(BizPtyh.InnerColumn.yhZjhm, idCardList);
        ApiResponse<List<BizPtyh>> res = new ApiResponse<>();
        res.setResult(bizUserList);
        return res;
    }

    private BizKsYk getYk(BizPtyh ptyh, String km) {
        SimpleCondition condition = new SimpleCondition(BizKsYk.class);
        condition.eq(BizKsYk.InnerColumn.yhId, ptyh.getId());
        condition.eq(BizKsYk.InnerColumn.kmCode, km);
        condition.setOrderByClause(BizKsYk.InnerColumn.cjsj.desc());
        List<BizKsYk> yks = ksYkMapper.selectByExampleAndRowBounds(condition, new RowBounds(0, 1));
        if (yks.size() != 0) {
            return yks.get(0);
        }
        return null;
    }

    /**
     * 注册邀请消息推送
     *
     * @param userId 邀请人ID
     * @param openId 注册人的OPEN_ID
     * @return
     */
    @Override
    public void sendRegisterInvite(String userId, String openId) throws UnsupportedEncodingException {
        payInfo.debug("时间" + DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
        try {
            Thread thread = Thread.currentThread();
            thread.sleep(2 * 1000);//暂停2秒后程序继续执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        BizPtyh user = this.findById(userId);
        String first = "";
        List<WxMpTemplateData> data = new ArrayList<>();
        data.add(new WxMpTemplateData("first", first));
        data.add(new WxMpTemplateData("keyword1", DateUtils.getNowTime()));
        data.add(new WxMpTemplateData("keyword2", user.getYhXm()));
//        data.add(new WxMpTemplateData("keyword3", DateUtils.getNowTime()));
        data.add(new WxMpTemplateData("remark", "点击进入注册页面"));

        WxMpTemplateMessage msg = new WxMpTemplateMessage();
        msg.setToUser(openId);
        msg.setTemplateId("4VIWC4ezWgotUv1w4t7VHon1grV7OVu04C8mtkwBOP4");

        String url = domain + "?page=reg&id=" + user.getYhZsyqm();
        String encode = URLEncoder.encode(url, "UTF-8");
        msg.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb01394ea85904296" +
                "&redirect_uri=" + encode + "&response_type=code&scope=snsapi_userinfo&state=debug&connect_redirect=1" +
                "#wechat_redirect");//
        msg.setData(data);
        try {
            wechatService.sendTemplateMsg(msg, null);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
//        Map<String,String> ret=new HashMap<>();
//        ret.put("yhtx",user.getYhTx());
//        ret.put("yhZsyqm",user.getYhZsyqm());
//        return ret;
    }

    @Override
    public ApiResponse<String> bindOpenId(String openid) {
        RuntimeCheck.ifBlank(openid, "请上传openid");
        BizPtyh user = getAppCurrentUser();
        BizPtyh ptyh = findById(user.getId());
        ptyh.setYhOpenId(openid);
        update(ptyh);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<String> updateAssignStudent(String id, String jlId, String km) {
        String flag = getRequestParameterAsString("flag");
        RuntimeCheck.ifBlank(id, "请选择学员");
        RuntimeCheck.ifBlank(jlId, "请选择教练");
        RuntimeCheck.ifBlank(km, "请选择要分配的科目");
        // 查询当前学员的代培点与修改之后的代培点是否一致 , 如果不一致 需要提醒是否修改
        BizJl jl = jlService.findById(jlId);
        BizPtyh user = ptyhService.findById(id);
        RuntimeCheck.ifTrue(StringUtils.equals(km, "2") && StringUtils.isBlank(user.getYhK2SubId()), "此学员尚未分配教练 , " +
                "不能修改教练");
        RuntimeCheck.ifTrue(StringUtils.equals(km, "3") && StringUtils.isBlank(user.getYhK3SubId()), "此学员尚未分配教练 , " +
                "不能修改教练");
        RuntimeCheck.ifBlank(jl.getSubSchoolId(), "请为当前教练绑定代培点");
        if (StringUtils.isBlank(flag)) {
            BizPtyh ptyh = findById(id);
            if (!StringUtils.equals(jl.getSubSchoolId(), ptyh.getYhK2SubId())) {
                ApiResponse<String> res = new ApiResponse<>();
                res.setMessage("当前所选教练 不是学员所在代培点的教练 , 请确认是否修改");
                res.setCode(555);
                return res;
            }
        }
        Map<String, String> map = new HashMap<>();
        map.put("0", "受理");
        map.put("1", "科一");
        map.put("2", "科二");
        map.put("3", "科三");
        map.put("4", "科四");

        userService.updateJlId(Arrays.asList(id), jlId, km);
        entityMapper.updateJlFp(Arrays.asList(id),
                "该学员于：" + DateUtils.getNowTime() + " 分配给" + map.get(km) + "专员：" + jl.getYhXm() + "");
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<String> updateSubFee(String ids, String km) {
        RuntimeCheck.ifBlank(ids, "请选择已交代培费");


        String time = DateUtils.getNowTime();
        BizPtyh ptyhs = ptyhService.findById(ids);

        if (StringUtils.equals(km, "2")) {

            RuntimeCheck.ifFalse(StringUtils.equals(ptyhs.getYhK2Sh(), "1"), "请先审核通过");
            RuntimeCheck.ifFalse(StringUtils.isBlank(ptyhs.getYhK2SubSj()), "此代培费已打款");
            ptyhs.setYhK2SubSj(time);
        } else if (StringUtils.equals(km, "3")) {
            RuntimeCheck.ifFalse(StringUtils.equals(ptyhs.getYhK3Sh(), "1"), "请先审核通过");
            RuntimeCheck.ifFalse(StringUtils.isBlank(ptyhs.getYhK3SubSj()), "此代培费已打款");
            ptyhs.setYhK3SubSj(time);
        }

        ptyhService.update(ptyhs);
        // 更新日志
        SimpleCondition condition = new SimpleCondition(BizFp.class);
        condition.eq(BizFp.InnerColumn.yhId, ptyhs.getId());
        condition.eq(BizFp.InnerColumn.fpkm, km);
        List<BizFp> fps = fpMapper.selectByExample(condition);
        BizFp fp;
        if (CollectionUtils.isNotEmpty(fps)) {
            fp = fps.get(0);
            fp.setSfdk("1");
            fpMapper.updateByPrimaryKey(fp);
        } else {
            fp = new BizFp();
            fp.setId(genId());
            fp.setYhId(ptyhs.getId());
            fp.setFpkm(km);
            fp.setSfdk("1");
            if (km.equals("2")) {
                fp.setSubSchoolName(ptyhs.getYhK2SubName());
                fp.setSubSchoolId(ptyhs.getYhK2SubId());
            } else {
                fp.setSubSchoolName(ptyhs.getYhK3SubName());
                fp.setSubSchoolId(ptyhs.getYhK3SubId());
            }
            fp.setCjsj(DateUtils.getNowTime());
            fpMapper.insert(fp);
        }
        // 找到当前负责人的 openid
        String openId = null;
        int amount = 0;
        if (km.equals("2")) {
            openId = entityMapper.getOpenId(ptyhs.getYhK2SubId());
            amount = ptyhs.getYhK2SubJe();
        } else if (km.equals("3")) {
            openId = entityMapper.getOpenId(ptyhs.getYhK3SubId());
            amount = ptyhs.getYhK3SubJe();
        }
        RuntimeCheck.ifNull(openId, "当前代培点负责人未绑定微信, 请先绑定微信");
        List<BizPtyh> list = ptyhService.findEq(BizPtyh.InnerColumn.yhOpenId, openId);
        RuntimeCheck.ifEmpty(list, "未找到平台负责人信息");
        ApiResponse<String> response = txService.wxEnterprisePayRealize(fp.getId(), openId, amount,
                list.get(0).getYhXm(), "平台系统打款：订单号" + fp.getId());
        RuntimeCheck.ifFalse(response.getCode() == 200, response.getMessage());
        return response;
    }

    @Override
    public ApiResponse<String> getSubFee(String km, int pageNum, int pageSize) {

        List<Map<String, Object>> result = new ArrayList<>();
        String name = getRequestParameterAsString("name");
        if (StringUtils.isBlank(name)) {
            name = null;
        }
        String finalName = name;
        PageInfo<String> info =
                PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> fpMapper.getSubSchool(km, finalName));
        if (CollectionUtils.isNotEmpty(info.getList())) {
            SimpleCondition condition = new SimpleCondition(BizFp.class);
            condition.eq(BizFp.InnerColumn.fpkm, km);
            condition.eq(BizFp.InnerColumn.sfdk, "0");
            condition.in(BizFp.InnerColumn.subSchoolId, info.getList());
            List<BizFp> fps = fpMapper.selectByExample(condition);
            Set<String> collect = fps.stream().map(BizFp::getYhId).collect(Collectors.toSet());
            List<BizPtyh> ptyhs = ptyhService.findByIds(collect);
            Map<String, BizPtyh> ptyhMap = ptyhs.stream().collect(Collectors.toMap(BizPtyh::getId, p -> p));
            Map<String, List<BizFp>> map = fps.stream().collect(Collectors.groupingBy(BizFp::getSubSchoolId));
            for (Map.Entry<String, List<BizFp>> entry : map.entrySet()) {
                Map<String, Object> objectMap = new HashMap<>();
                List<BizFp> value = entry.getValue();
                String schoolName = value.get(0).getSubSchoolName();
                value.forEach(bizFp -> bizFp.setYh(ptyhMap.get(bizFp.getYhId())));
                double zj = 0.0;
                if (StringUtils.equals(km, "2")) {
                    zj =
                            value.stream().map(BizFp::getYh).map(BizPtyh::getYhK2SubJe).mapToDouble(value1 -> value1).sum();
                } else if (StringUtils.equals(km, "3")) {
                    zj =
                            value.stream().map(BizFp::getYh).map(BizPtyh::getYhK3SubJe).mapToDouble(value1 -> value1).sum();
                }
                objectMap.put("subSchoolName", schoolName);
                objectMap.put("yhList", value);
                objectMap.put("km", km);
                objectMap.put("zj", zj);
                objectMap.put("total", value.size());
                result.add(objectMap);
            }
        }
        ApiResponse<String> res = new ApiResponse<>();
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>();
        pageInfo.setTotal(info.getTotal());
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        pageInfo.setList(result);
        res.setPage(pageInfo);
        return res;
    }

    @Override
    public ApiResponse<String> getSubStudent(String subId, int pageNum, int pageSize) {
        RuntimeCheck.ifBlank(subId, "请选择代培点");
        LimitedCondition condition = getQueryCondition();
        condition.and().andCondition(" YH_K2_SUB_ID = '" + subId + "' or YH_K3_SUB_ID = '" + subId + "'");
        PageInfo<BizPtyh> info =
                PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> findByCondition(condition));
        ApiResponse<String> res = new ApiResponse<>();
        res.setPage(info);
        return res;
    }

    @Override
    public ApiResponse<String> shSubFee(String id, String km) {
        BizPtyh ptyh = findById(id);
        if (StringUtils.equals(km, "2")) {
            ptyh.setYhK2Sh("1");
        } else if (StringUtils.equals("3", km)) {
            ptyh.setYhK3Sh("1");
        }
        update(ptyh);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<String> confrimSubFee(String id, String km) {
        RuntimeCheck.ifBlank(id, "请选择记录");
        BizPtyh ptyh = findById(id);
        String time = DateUtils.getNowTime();
        if (StringUtils.equals(km, "2")) {
            RuntimeCheck.ifFalse(StringUtils.equals(ptyh.getYhK2Sh(), "1"), "请先审核通过");
            RuntimeCheck.ifFalse(StringUtils.isBlank(ptyh.getYhK2SubSj()), "此代培费已打款");
            ptyh.setYhK2SubSj(time);
        } else if (StringUtils.equals(km, "3")) {
            RuntimeCheck.ifFalse(StringUtils.equals(ptyh.getYhK3Sh(), "1"), "请先审核通过");
            RuntimeCheck.ifFalse(StringUtils.isBlank(ptyh.getYhK3SubSj()), "此代培费已打款");
            ptyh.setYhK3SubSj(time);
        }
        update(ptyh);
        // 更新日志
        SimpleCondition condition = new SimpleCondition(BizFp.class);
        condition.eq(BizFp.InnerColumn.yhId, ptyh.getId());
        condition.eq(BizFp.InnerColumn.fpkm, km);
        List<BizFp> fps = fpMapper.selectByExample(condition);
        BizFp fp;
        if (CollectionUtils.isNotEmpty(fps)) {
            fp = fps.get(0);
            fp.setSfdk("1");
            fpMapper.updateByPrimaryKey(fp);
        } else {
            fp = new BizFp();
            fp.setId(genId());
            fp.setYhId(ptyh.getId());
            fp.setFpkm(km);
            fp.setSfdk("1");
            if (km.equals("2")) {
                fp.setSubSchoolName(ptyh.getYhK2SubName());
                fp.setSubSchoolId(ptyh.getYhK2SubId());
            } else {
                fp.setSubSchoolName(ptyh.getYhK3SubName());
                fp.setSubSchoolId(ptyh.getYhK3SubId());
            }
            fp.setCjsj(DateUtils.getNowTime());
            fpMapper.insert(fp);
        }
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<String> getDhfYh(int pageNum, int pageSize) {
        SimpleCondition condition = new SimpleCondition(BizPtyh.class);
        String hf = getRequestParameterAsString("hf");
        if (StringUtils.equals(hf, "1")) {
            condition.and().andIsNotNull(BizPtyh.InnerColumn.hfsj.name());
        } else {
            condition.and().andIsNull(BizPtyh.InnerColumn.hfsj.name());
        }
        condition.setOrderByClause(" cjsj desc  ");
        condition.eq(BizPtyh.InnerColumn.yhLx, "1");
        String cond = getRequestParameterAsString("cond");
        if (StringUtils.isNotBlank(cond)) {
            condition.and().andCondition(" yh_zh like '%" + cond + "%' or yh_xm like '%" + cond + "%' or yh_zjhm like" +
                    " '%" + cond + "%'");
        }
        PageInfo<BizPtyh> info =
                PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> findByCondition(condition));
        if (CollectionUtils.isNotEmpty(info.getList())) {
            Set<String> set = info.getList().stream().map(BizPtyh::getId).collect(Collectors.toSet());
            SimpleCondition condition1 = new SimpleCondition(BizOrder.class);
            condition1.in(BizOrder.InnerColumn.yhId, set);
            condition1.eq(BizOrder.InnerColumn.ddZfzt, "1");
            condition1.setOrderByClause(" cjsj desc ");
            List<BizOrder> orders = orderService.findByCondition(condition1);
            Map<String, List<BizOrder>> map = orders.stream().collect(Collectors.groupingBy(BizOrder::getYhId));
            info.getList().forEach(bizPtyh -> {

                List<BizOrder> value = map.get(bizPtyh.getId());
                if (CollectionUtils.isNotEmpty(value)) {
                    BizOrder order =
                            value.stream().sorted(Comparator.comparing(BizOrder::getCjsj).reversed()).collect(Collectors.toList()).get(0);
                    BizCp cp = cpService.findById(order.getCpId());
                    bizPtyh.setCpje(cp.getCpJl());
                    bizPtyh.setCpmc(cp.getCpMc());
                }
            });

        }
        ApiResponse<String> res = new ApiResponse<>();
        res.setPage(info);
        return res;
    }

    @Override
    public ApiResponse<String> updateHf(String id) {

        RuntimeCheck.ifBlank(id, "请选择已回访学员");
        SysYh yh = getCurrentUser();
        BizPtyh ptyh = findById(id);
        ptyh.setHfsj(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        ptyh.setHfr(yh.getXm());
        update(ptyh);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<String> getDslYh(int pageNum, int pageSize) {
        SimpleCondition condition = new SimpleCondition(BizPtyh.class);
        String cond = getRequestParameterAsString("cond");
        condition.eq(BizPtyh.InnerColumn.yhLx, "1");
        if (StringUtils.isNotBlank(cond)) {
            condition.and().andCondition(" yh_zh like '%" + cond + "%' or yh_xm like '%" + cond + "%' or yh_zjhm like" +
                    " '%" + cond + "%'");
        }
        String sl = getRequestParameterAsString("sl");
        if(StringUtils.equals(sl, "1")){
            condition.eq(BizPtyh.InnerColumn.yhXySlType, "4");
            SimpleCondition simpleCondition = new SimpleCondition(BizKsSl.class);
            simpleCondition.eq(BizKsSl.InnerColumn.slType, "4");
            simpleCondition.setOrderByClause(" sl_sj desc ");
            PageInfo<BizKsSl> slinfo =
                    PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> ksSlService.findByCondition(simpleCondition));
            if(CollectionUtils.isNotEmpty(slinfo.getList())){
                List<String> list = slinfo.getList().stream().map(BizKsSl::getYhId).collect(Collectors.toList());
                condition.in(BizPtyh.InnerColumn.id, list);
            }
        }else{
            condition.and().andNotEqualTo(BizPtyh.InnerColumn.yhXySlType.name(), "4");
        }
        PageInfo<BizPtyh> info =
                PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> findByCondition(condition));
        ApiResponse<String> res = new ApiResponse<>();
        res.setPage(info);
        if (CollectionUtils.isNotEmpty(info.getList())) {
            Set<String> set = info.getList().stream().map(BizPtyh::getId).collect(Collectors.toSet());
            SimpleCondition condition1 = new SimpleCondition(BizOrder.class);
            condition1.in(BizOrder.InnerColumn.yhId, set);
            condition1.eq(BizOrder.InnerColumn.ddZfzt, "1");
            condition1.setOrderByClause(" cjsj desc ");
            List<BizOrder> orders = orderService.findByCondition(condition1);
            Map<String, List<BizOrder>> map = orders.stream().collect(Collectors.groupingBy(BizOrder::getYhId));
            info.getList().forEach(bizPtyh -> {

                List<BizOrder> value = map.get(bizPtyh.getId());
                if (CollectionUtils.isNotEmpty(value)) {
                    BizOrder order =
                            value.stream().sorted(Comparator.comparing(BizOrder::getCjsj).reversed()).collect(Collectors.toList()).get(0);
                    BizCp cp = cpService.findById(order.getCpId());
                    bizPtyh.setCpje(cp.getCpJl());
                    bizPtyh.setCpmc(cp.getCpMc());
                }
            });
        }
        return res;
    }

    @Override
    public ApiResponse<String> getDjfYh(String km, int pageNum, int pageSize) {
        RuntimeCheck.ifBlank(km, "请选择科目");

        SimpleCondition condition = new SimpleCondition(BizPtyh.class);
        condition.eq(BizPtyh.InnerColumn.yhLx, "1");
        String cond = getRequestParameterAsString("cond");
        List<String> yhIds;
        if (StringUtils.equals(km, "1")) {
            condition.eq(BizPtyh.InnerColumn.yhXySlType, "4");
        } else if (StringUtils.equals(km, "2")) {
            SimpleCondition simpleCondition = new SimpleCondition(BizKsYk.class);
            simpleCondition.eq(BizKsYk.InnerColumn.kmCode, "1");
            simpleCondition.and().andCondition(" cj1 >=90 or cj2 >=90");
            List<BizKsYk> yks = ksYkService.findByCondition(simpleCondition);
            if (CollectionUtils.isNotEmpty(yks)) {
                yhIds = yks.stream().map(BizKsYk::getYhId).collect(Collectors.toList());
                condition.in(BizPtyh.InnerColumn.id, yhIds);
            }
        } else if (StringUtils.equals(km, "3")) {
            SimpleCondition simpleCondition = new SimpleCondition(BizKsYk.class);
            simpleCondition.eq(BizKsYk.InnerColumn.kmCode, "2");
            simpleCondition.and().andCondition(" cj1 >=80 or cj2 >=80");
            List<BizKsYk> yks = ksYkService.findByCondition(simpleCondition);
            if (CollectionUtils.isNotEmpty(yks)) {
                yhIds = yks.stream().map(BizKsYk::getYhId).collect(Collectors.toList());
                condition.in(BizPtyh.InnerColumn.id, yhIds);
            }
        }


        if (StringUtils.isNotBlank(cond)) {
            condition.and().andCondition(" yh_zh like '%" + cond + "%' or yh_xm like '%" + cond + "%' or yh_zjhm like" +
                    " '%" + cond + "%' or yh_lsh like '%" + cond + "%'");
        }
        condition.eq(BizPtyh.InnerColumn.yhXyJfType, km);
        if (StringUtils.equals("3", km)) {
            condition.and().andNotEqualTo(BizPtyh.InnerColumn.k3jfzt.name(), 1);
        }
        PageInfo<BizPtyh> info =
                PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> findByCondition(condition));

        if (km.equals("1")) {
            if (CollectionUtils.isNotEmpty(info.getList())) {
                org.joda.time.format.DateTimeFormatter pattern = DateTimeFormat.forPattern("yyyy-MM-dd");
                info.getList().forEach(bizPtyh -> {
                    SimpleCondition condition1 = new SimpleCondition(BizKsSl.class);
                    condition1.eq(BizKsSl.InnerColumn.yhId, bizPtyh.getId());
                    condition1.eq(BizKsSl.InnerColumn.slType, "4");
                    List<BizKsSl> ksSls = ksSlService.findByCondition(condition1);
                    if (CollectionUtils.isNotEmpty(ksSls)) {
                        BizKsSl sl = ksSls.get(0);
                        bizPtyh.setSlsj(sl.getSlSj());
                        long time = DateTime.parse(sl.getSlSj(), pattern).getMillis();
                        long millis = DateTime.now().getMillis();
                        long l = (millis - time) / (24 * 60 * 60 * 1000);
                        bizPtyh.setDay(l);
                    }
                });

            }
        } else {
            if (CollectionUtils.isNotEmpty(info.getList())) {
                org.joda.time.format.DateTimeFormatter pattern = DateTimeFormat.forPattern("yyyy-MM-dd");
                // 计算从科一考试及格到现在的天数
                info.getList().forEach(bizPtyh -> {
                    SimpleCondition condition1 = new SimpleCondition(BizKsYk.class);
                    condition1.eq(BizKsYk.InnerColumn.yhId, bizPtyh.getId());
                    condition1.eq(BizKsYk.InnerColumn.kmCode, "1");
                    condition1.and().andCondition(" cj1 >= 90 or cj2 >= 90");
                    condition1.setOrderByClause(" yk_sj desc ");
                    List<BizKsYk> yks = ksYkService.findByCondition(condition1);
                    if (CollectionUtils.isNotEmpty(yks) && yks.size() > 0) {
                        BizKsYk yk = yks.get(0);
                        String sj = yk.getYkSj();
                        bizPtyh.setSlsj(sj);
                        long time = DateTime.parse(sj, pattern).getMillis();
                        long millis = DateTime.now().getMillis();
                        long l = (millis - time) / (24 * 60 * 60 * 1000);
                        bizPtyh.setDay(l);
                    }
                });
            }
        }
        ApiResponse<String> res = new ApiResponse<>();
        res.setPage(info);
        return res;
    }

    @Override
    public ApiResponse<String> getDfpYh(String km, int pageNum, int pageSize) {

        RuntimeCheck.ifBlank(km, "请选择要分配科目");

        SimpleCondition condition = new SimpleCondition(BizPtyh.class);
        condition.eq(BizPtyh.InnerColumn.yhLx, "1");
        String cond = getRequestParameterAsString("cond");
        if (StringUtils.isNotBlank(cond)) {
            condition.and().andCondition(" yh_zh like '%" + cond + "%' or yh_xm like '%" + cond + "%' or yh_zjhm like" +
                    " '%" + cond + "%' or yh_lsh like '%" + cond + "%'");
        }
        SimpleCondition condition1 = new SimpleCondition(BizUser.class);

        if (StringUtils.equals(km, "0")) {
            // 受理专员只要缴费即可分配
            condition1.and().andCondition(" YH_JLID is null or YH_JLID = ''");
        } else if (StringUtils.equals(km, "1")) {
            // 分配科一 需要受理成功
            condition.eq(BizPtyh.InnerColumn.yhXySlType, "4");
            condition1.and().andCondition("YH_JLID1 is null or YH_JLID1 = ''");
        } else if (StringUtils.equals(km, "2")) {
            // 科目一考试合格即可分配科二科三
            condition.and().andCondition(" YH_XY_YK_TYPE = '11' or YH_XY_YK_TYPE >= '20'");
            condition1.and().andCondition("YH_JLID2 is null or YH_JLID2 = ''");
        }
        List<BizUser> users = userService.findByCondition(condition1);
        ApiResponse<String> res = new ApiResponse<>();
        if (CollectionUtils.isNotEmpty(users)) {
            Set<String> yhids = users.stream().map(BizUser::getYhId).collect(Collectors.toSet());
            condition.in(BizPtyh.InnerColumn.id, yhids);
            PageInfo<BizPtyh> info =
                    PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> findByCondition(condition));
            if (CollectionUtils.isNotEmpty(info.getList())) {
                info.getList().forEach(bizPtyh -> {
                    SimpleCondition simpleCondition = new SimpleCondition(BizKsYk.class);
                    simpleCondition.eq(BizKsYk.InnerColumn.kmCode, "1");
                    simpleCondition.setOrderByClause(" yk_sj desc ");
                    simpleCondition.eq(BizKsYk.InnerColumn.yhId, bizPtyh.getId());
                    List<BizKsYk> yks = ksYkService.findByCondition(simpleCondition);
                    if (CollectionUtils.isNotEmpty(yks)) {
                        Integer cj1 = yks.get(0).getCj1();
                        Integer cj2 = yks.get(0).getCj2();
                        if (cj1 >= 90 || cj2 >= 90) {
                            bizPtyh.setHg("1");
                        } else {
                            bizPtyh.setHg("0");
                        }
                        bizPtyh.setKssj(yks.get(0).getYkSj());
                    }
                });
            }
            res.setPage(info);
        }
        return res;
    }

    @Override
    public ApiResponse<String> getDlrYh(String km, int pageNum, int pageSize) {
        // 根据科目查询符合条件的学员
        RuntimeCheck.ifBlank(km, "请选择科目");
        SimpleCondition condition = new SimpleCondition(BizPtyh.class);
        condition.eq(BizPtyh.InnerColumn.yhLx, "1");
        String cond = getRequestParameterAsString("cond");
        if (StringUtils.isNotBlank(cond)) {
            condition.and().andCondition(" yh_zh like '%" + cond + "%' or yh_xm like '%" + cond + "%' or yh_zjhm like" +
                    " '%" + cond + "%' or yh_lsh like '%" + cond + "%'");
        }
        condition.eq(BizPtyh.InnerColumn.yhXySlType, "4");
        // 成绩录入处 改成即使没有缴费 也可以录入成绩 , 二者不做控制
        if (StringUtils.equals(km, "1")) {
            // 科目一需要录入条件 ,  科一成绩未合格 , 且 用户已经受理成功
            List<String> k1ids = entityMapper.getK1Lr();
            condition.in(BizPtyh.InnerColumn.id, k1ids);

            // 需要录入科目一的条件 ,   首先科目一已缴费 其次 科目一成绩不合格 或 未录入
            condition.and().andNotEqualTo(BizPtyh.InnerColumn.yhXyYkType.name(), "11");
//            condition.eq(BizPtyh.InnerColumn.yhXyJfType, "2");
        } else if (StringUtils.equals(km, "2")) {

            List<String> k2Lr = entityMapper.getK2Lr();
            condition.in(BizPtyh.InnerColumn.id, k2Lr);
            condition.and().andNotEqualTo(BizPtyh.InnerColumn.yhXyYkType.name(), "21");
//            condition.eq(BizPtyh.InnerColumn.yhXyJfType, "3");
        } else if (StringUtils.equals(km, "3")) {
            List<String> k3Lr = entityMapper.getK3Lr();
            condition.in(BizPtyh.InnerColumn.id, k3Lr);
            condition.and().andNotEqualTo(BizPtyh.InnerColumn.yhXyYkType.name(), "31");
//            condition.eq(BizPtyh.InnerColumn.yhXyJfType, "3");
//            condition.eq(BizPtyh.InnerColumn.k3jfzt, 1);
        }
        PageInfo<BizPtyh> info =
                PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> findByCondition(condition));
        ApiResponse<String> res = new ApiResponse<>();
        res.setPage(info);
        if(CollectionUtils.isNotEmpty(info.getList())){
            info.getList().forEach(bizPtyh -> {
                if(StringUtils.equals(km, "1")){
                    if(bizPtyh.getYhXyJfType().equals("1")){
                        bizPtyh.setJfzt("0");
                    }else{
                        bizPtyh.setJfzt("1");
                    }
                }else if(StringUtils.equals(km, "2")){
                    List<BizKsJf> jfs = ksjfService.findEq(BizKsJf.InnerColumn.kmId, "2");
                    if(CollectionUtils.isNotEmpty(jfs)){
                        bizPtyh.setJfzt("1");
                    }else{
                        bizPtyh.setJfzt("0");
                    }
                }else{
                    if(bizPtyh.getK3jfzt() == 1){
                        bizPtyh.setJfzt("1");
                    }else{
                        bizPtyh.setJfzt("0");
                    }
                }
            });
        }
        return res;
    }

    @Override
    public ApiResponse<Map<String, Integer>> sytj() {
        // 首页统计主要统计每个流程中需要操作的记录数  按照每个流程来查询人数
        // ①待回访的   人数  判定条件:  回访时间为空
        int dhf = entityMapper.sumDhf();
        // ②待受理人数   判定条件   受理状态没有成功的状态
        int dsl = entityMapper.sumDsl();
        // ③ 待缴考试费人数     条件   科一为受理之后 , 科二为 可以成绩合格  科三为科二成绩合格
        int djf = entityMapper.sumDjfK();
        // ④ 待录入成绩    科一: 费用已交 且 成绩不合格  科二 同理  , 科三 同理
        int dlr = entityMapper.sumDlrNew();
        // ⑤ 待分配专员学员数量  (当前只分配 科二科三 )
        int dfp = entityMapper.sumDfp();
        // ⑥ 待确认分佣    查询提现表中还未提现的用户记录
        int dfy = entityMapper.sunDfy();
        // ⑦ 待付培训费
        int k2Pxf = entityMapper.sumK2Pxf();
        int k3Pxf = entityMapper.sumK3Pxf();

        Map<String,Integer> map = new HashMap<>();
        map.put("hf", dhf);
        map.put("dsl",dsl);
        map.put("djf",djf);
        map.put("dlr",dlr);
        map.put("dfp",dfp);
        map.put("dfy",dfy);
        map.put("pxf", k2Pxf + k3Pxf);
        return ApiResponse.success(map);
    }

    @Override
    public ApiResponse<String> getPxfyh(String km, int pageNum, int pageSize) {
        RuntimeCheck.ifBlank(km, "请选择科目");
        SimpleCondition condition = new SimpleCondition(BizPtyh.class);
        String cond = getRequestParameterAsString("cond");
        if (StringUtils.isNotBlank(cond)) {
            condition.and().andCondition(" yh_zh like '%" + cond + "%' or yh_xm like '%" + cond + "%' or yh_zjhm like" +
                    " '%" + cond + "%' or yh_lsh like '%" + cond + "%'");
        }
        condition.eq(BizPtyh.InnerColumn.yhLx, "1");
        if(StringUtils.equals(km, "2")){
            condition.and().andCondition(" yh_k2_sub_sj is null or yh_k2_sub_sj is null = ''  ");
            condition.and().andCondition(" yh_k2_sub_id is not null and yh_k2_sub_id is not null != '' ");
        }else if (StringUtils.equals(km,"3")){
            // 查询已经考试合格但是 科目三还未缴培训费的学员
            condition.and().andCondition(" yh_k3_sub_id is not null and yh_k3_sub_id  != '' ");
            condition.and().andCondition(" yh_k3_sub_sj is null or yh_k3_sub_sj  = ''  ");
            condition.and().andCondition(" yh_xy_yk_type = '31' or yh_xy_yk_type >= '40'  ");
        }else {
            return ApiResponse.success();
        }
        PageInfo<BizPtyh> info =
                PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> findByCondition(condition));
        ApiResponse<String> res = new ApiResponse<>();
        res.setPage(info);
        return res;
    }

    @Override
    public ApiResponse<String> getDfPxf(String km, int pageNum, int pageSize) {
        RuntimeCheck.ifBlank(km, "请选择科目");
        // 根据各代培点分组 , 可以先查询代培点的分组
        String subName = getRequestParameterAsString("subName");
        if(StringUtils.isBlank(subName)){
            subName = null;
        }

        // 根据传入科目查询有多少代培点需要分组
        PageInfo<String> dp;
        String finalSubName = subName;
        dp = StringUtils.equals(km, "2") ? PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> entityMapper.getK2Dp(finalSubName)) : PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> entityMapper.getK3Dp(finalSubName));
        if(CollectionUtils.isEmpty(dp.getList())){
            return ApiResponse.success();
        }
        List<Map<String, Object>> pageList = new ArrayList<>();
        SimpleCondition condition = new SimpleCondition(BizPtyh.class);
        if (StringUtils.equals(km, "2")) {
            condition.in(BizPtyh.InnerColumn.yhK2SubId, dp.getList());
            condition.and().andCondition(" yh_k2_sub_sj is null or yh_k2_sub_sj is null = ''  ");
            condition.and().andCondition(" yh_k2_sub_id is not null and yh_k2_sub_id is not null != '' ");
            List<BizPtyh> ptyhs = findByCondition(condition);
            Map<String, List<BizPtyh>> map = ptyhs.stream().collect(Collectors.groupingBy(BizPtyh::getYhK2SubId));
            for (String s : dp.getList()) {
                Map<String, Object> m = new HashMap<>();
                List<BizPtyh> list = map.get(s);
                String name = list.get(0).getYhK2SubName();
                m.put("subCode",s);
                m.put("subName", name);
                m.put("students", list);
                m.put("price", list.stream().mapToDouble(BizPtyh::getYhK2SubJe).sum());
                pageList.add(m);
            }
        } else {
            condition.in(BizPtyh.InnerColumn.yhK3SubId, dp.getList());
            // 查询已经考试合格但是 科目三还未缴培训费的学员
            condition.and().andCondition(" yh_k3_sub_id is not null and yh_k3_sub_id  != '' ");
            condition.and().andCondition(" yh_k3_sub_sj is null or yh_k3_sub_sj  = ''  ");
            condition.and().andCondition(" yh_xy_yk_type = '31' or yh_xy_yk_type >= '40'  ");
            List<BizPtyh> ptyhs = findByCondition(condition);
            Map<String, List<BizPtyh>> map = ptyhs.stream().collect(Collectors.groupingBy(BizPtyh::getYhK3SubId));
            for (String s : dp.getList()) {
                Map<String, Object> m = new HashMap<>();
                List<BizPtyh> list = map.get(s);
                String name = list.get(0).getYhK3SubName();
                m.put("subCode",s);
                m.put("subName", name);
                m.put("students", list);
                m.put("price", list.stream().mapToDouble(BizPtyh::getYhK3SubJe).sum());
                pageList.add(m);
            }
        }
        PageInfo<Map<String,Object>> info = new PageInfo<>();
        BeanUtils.copyProperties(dp,info, "list");
        info.setList(pageList);
        ApiResponse<String> res = new ApiResponse<>();
        res.setPage(info);
        return res;
    }


}
