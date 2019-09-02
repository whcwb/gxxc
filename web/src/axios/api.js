//网络请求地址前缀
let serverDomain='http:///www.520xclm.com';
// let BASE_URL = serverDomain + ':8080/biz/';
// let BASE_URL = 'http://127.0.0.1:9086';
// let BASE_URL = 'http://192.168.31.35:9086';
// let BASE_URL = 'http://192.168.31.228:9086'//洋

// let BASE_URL = 'http://192.168.123.69:9006';
let BASE_URL='http:///www.520xclm.com:8080/biz';

module.exports = {
    getImgUrl:'http://www.520xclm.com:8001/',
    url: BASE_URL,
    //图片上传地址
    UPLOAD: BASE_URL + '/upload',
    UPLOAD_PRIVATE: 'http://www.520xclm.com:8080/biz' + '/app/zjupload',
    STATIC_PATH: serverDomain + ':8001/',
    VIDEO_PATH:'',
    exportData:BASE_URL+'/pub/export',

    removeUserInfo:'/api/ptyh/removeUserInfo',

    getHandleStatus:'api/kssl/getHandleStatus', // 获取受理状态信息
    kssl:{
        QUERY:'/api/kssl/pager',
    },
    tk:{
        QUERY:'/api/tk/pager',
        tk:'/api/tk/tk',
    },
    ksyk:{
        QUERY:'/api/ksyk/pager',
    },
    USERROOT:{
        GET_MENU_LIST:'/api/gn/getUserFunctions',
        GET_MENU_TREE:'/api/gn/getMenuTree',
        INIT_MENU:'/api/gn/initMenu',
        MODIFY_PSD:'/api/yh/mdfPwd'
    },
    LOGIN:{
        QUERY:'login'
    },
    //文件控制
    FILE:{
        FINDBYPID:'/api/files/findByPId',//根据主ID查询文件信息
        UPLOAD:BASE_URL + '/api/files/upload',//根据主ID查询文件信息
    },
    USER:{
        QUERY:'/api/yh/pager',//用户管理
        ADD:'/api/yh/save',
        CHANGE:'/api/yh/update',
        GIVE:'/api/js/modifyUserRoles',
        DELE:'/api/yh/removeIds',
        SEARCHSOME:'/api/yh/query',//用户管理

    },
    examPlace:{
        QUERY:'/api/examPlace/pager',//考场
        ADD:'/api/examPlace/save',
        CHANGE:'/api/examPlace/update',
        DELE:'/api/examPlace/removeIds',
    },
    ksJf:{
        QUERY:'/api/ksjf/pager',//考试缴费管理
        ADD:'/api/ksjf/save',
        CHANGE:'/api/ksjf/update',
        DELE:'/api/ksjf/removeIds',
        DJF:'/api/ksjf/waitPaymentList',
        batchImport:'/api/ksjf/batchImport',
        EXPORT:'/pub/ksjf/export',
    },
    chat:{
        QUERY:'/api/msg/getUserList',
        USERMEASSAGE:'/api/msg/getUserMsg',
        REPLY:'/api/msg/reply'
    },
    ksjg:{
        QUERY:'/api/ksjg/pager',//考试结果记录
        ADD:'/api/ksjg/save',
        CHANGE:'/api/ksjg/update',
        DELE:'/api/ksjg/removeIds',
    },
    kssl:{
        QUERY:'/api/kssl/pager',//考试受理信息
        ADD:'/api/kssl/save',
        CHANGE:'/api/kssl/update',
        DELE:'/api/kssl/removeIds',
    },
    ksyk:{
        QUERY:'/api/ksyk/pager',//学员考试约考
        ADD:'/api/ksyk/save',
        CHANGE:'/api/ksyk/update',
        DELE:'/api/ksyk/removeIds',
    },
    hd:{
        QUERY:'/api/hd/pager',//活动管理
        ADD:'/api/hd/save',
        CHANGE:'/api/hd/update',
        DELE:'/api/hd/removeIds',
        hdtj:'/api/hd/hdtj',// 活动推荐
    },
    wj:{
        QUERY:'/api/wj/pager',//活动管理
        ADD:'/api/wj/save',
        CHANGE:'/api/wj/update',
        DELE:'/api/wj/removeIds',
        getByCondition:'/api/wj/getCondition',
    },
    cp:{
        QUERY:'/api/cp/pager',//产品管理
        ADD:'/api/cp/save',
        CHANGE:'/api/cp/update',
        DELE:'/api/cp/removeIds',
        getcplx:'/api/cp/getcplx',
        yzcpCode:'/api/cp/yzcpCode',
    },
    student:{
        QUERY:'/api/ptyh/pager',//平台用户管理
        ADD:'/api/ptyh/save',
        CHANGE:'/api/ptyh/update',
        DELE:'/api/ptyh/removeIds',
        getById:'/api/ptyh/',
        updateSffp:'/api/ptyh/updateSffp',
        assignStudents:'/api/ptyh/assignStudents', // 分配学员接口
        updateyhrz:'/api/ptyh/updateyhrz', // 更新用户认证状态
        updateSfsd:'/api/ptyh/updateSfsd',//用户管理
        getUserCoachEvaluate:'/api/ptyh/updateSfsd',//获取用户对教练的评分
        getPaymentRecord:'/api/ptyh/getPaymentRecord',//获取用户进度信息
        status_query:'/api/ptyh/status-query',//获取用户进度信息
        getZyList:'/api/ptyh/getZyList',//获取专员列表
    },
    teacher:{
        QUERY:'/api/jl/pager',//平台用户管理
        ADD:'/api/jl/save',
        CHANGE:'/api/jl/update',
        DELE:'/api/jl/removeIds',
        getById:'/api/jl/',
        updateyhrz:'/api/jl/updateyhrz',// 更新教练认证状态
    },
    user:{
        QUERY:'/api/user/pager',//用户实名表
        ADD:'/api/user/save',
        CHANGE:'/api/user/update',
        DELE:'/api/user/removeIds',
        getStudentList:'/api/user/getStudentList',
    },
    school:{
        QUERY:'/api/school/pager',//用户实名表
        ADD:'/api/school/save',
        CHANGE:'/api/school/update',
        DELE:'/api/school/removeIds',
    },
    trainPlace:{
        QUERY:'/api/trainPlace/pager',//用户实名表
        ADD:'/api/trainPlace/save',
        CHANGE:'/api/trainPlace/update',
        DELE:'/api/trainPlace/removeIds',
    },
    tx:{
        QUERY:'/api/tx/pager',//提现管理
        ADD:'/api/tx/save',
        CHANGE:'/api/tx/update',
        DELE:'/api/tx/removeIds',
        audit:'/api/tx/updateShzt',
        confirm:'/api/tx/updateTxzt',
        batchImport:'/api/tx/batchImport',
        dk:'/api/tx/wxEnterprisePay',
    },
    ROLE:{
        QUERY:'/api/js/pager',//角色管理
        ALL:'/api/js/getAll',//角色管理
        ADD:'/api/js/save',
        CHANGE:'/api/js/update',
        GIVE:'/api/js/modifyUserRoles',
        DELE:'/api/js/removeIds',
        MODIFY_USER_ROLES:'/api/js/modifyUserRoles',
        GET_USER_ROLES:'/api/js/getUserRoles'
    },
    FRAMEWORK:{
        QUERY:'api/jg/pager',//机构管理
        ADD:'/api/jg/save',
        CHANGE:'/api/jg/update',
        DELE:'/api/jg/removeIds',
        GET_TREE:'/api/jg/getOrgTree',
        GET_TREE_Node:'/api/jg/getTree',
        getSubOrgList:'/api/jg/getSubOrgList',
    },
    DICTIONARY:{
        QUERY:'/api/zd/pager',// 查询字典
        ADD:'/api/zd/save',// 新增字典
        CHANGE:'/api/zd/update',// 编辑字典
        DELE:'/api/zd/removeIds' // 删除字典
    },
    DICTIONARY_LIST:{
        QUERY:'/api/zdxm/pager',//查询字典项
        ADD:'/api/zdxm/save',// 新增字典项
        CHANGE:'/api/zdxm/update',// 编辑字典项
        DELE:'/api/zdxm/removeIds',// 删除字典项
        GET_BY_CONDITION:'/api/zdxm/getCondition', // 删除字典
    },
    ITMS:{
        QUERY:'api/fw/pager',//服务管理
        ADD:'/api/fw/save',
        CHANGE:'/api/fw/update',
        DELE:'/api/fw/removeIds'
    },
    FUNCTION:{
        QUERY:'api/gn/pager',//功能管理
        ADD:'/api/gn/save',
        CHANGE:'/api/gn/update',
        DELE:'/api/gn/removeIds',
        GET_ORG_PERMISSION_TREE:'/api/gn/getOrgPermissionTree',
        GET_ALL_PERMISSION_TREE:'/api/gn/getAllPermissionTree',
        GET_ROLE_PERMISSION_TREE:'/api/gn/getRolePermissionTree',
        SET_ROLE_FUNCTIONS:'/api/gn/setRoleFunctions',
        SET_ORG_FUNCTIONS:'/api/gn/setOrgFunctions',
        GET_ROLE_FUNCTIONS:'/api/gn/getRoleFunctions'
    },
    DAILY:{
        QUERY:'api/rz/pager',//日志管理
        ADD:'/api/rz/save',
        CHANGE:'/api/rz/update',
        DELE:'/api/rz/removeIds'
    },
    order:{
        QUERY:'api/order/pager',//订单管理
        ADD:'/api/order/save',
        CHANGE:'/api/order/update',
        DELE:'/api/order/removeIds',
    },
}
