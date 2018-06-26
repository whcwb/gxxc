import Main from '@/views/Main.vue';

// 不作为Main组件的子页面展示的页面单独写，如下
export const loginRouter = {
    path: '/login',
    name: 'login',
    meta: {
        title: '登录'
    },
    component: () => import('@/views/login.vue')
};

export const page404 = {
    path: '/*',
    name: 'error-404',
    meta: {
        title: '404-页面不存在'
    },
    component: () => import('@/views/error-page/404.vue')
};

export const page403 = {
    path: '/403',
    meta: {
        title: '403-权限不足'
    },
    name: 'error-403',
    component: () => import('@//views/error-page/403.vue')
};

export const page500 = {
    path: '/500',
    meta: {
        title: '500-服务端错误'
    },
    name: 'error-500',
    component: () => import('@/views/error-page/500.vue')
};
// 作为Main组件的子页面展示但是不在左侧菜单显示的路由写在otherRouter里
export const otherRouter = {
    path: '/',
    name: 'otherRouter',
    redirect: '/home',
    meta: { title: '首页' },
    component: Main,
    children: [
        {meta: { title: '首页' },path: 'home', title: {i18n: 'home'}, name: 'home_index', component: () => import('@/views/home/home.vue') }
    ]
};

// 作为Main组件的子页面展示并且在左侧菜单显示的路由写在appRouter里
export let appRouter = [
	{
		path: '/system',
        icon: 'android-car',
        name: 'system',
        meta: { title: '系统管理' },
        component: Main,
        children: [
			{
				path: 'system-user',
				icon: 'android-clipboard',
				name: 'system-user',
                meta: { title: '用户管理' },
				component: () => import('@/views/whdx/system/system-user')
			},
			{
				path: 'system-role',
				icon: 'android-clipboard',
				name: 'system-role',
                meta: { title: '角色管理' },
				component: () => import('@/views/whdx/system/system-role')
			},
			{
				path: 'system-framework',
				icon: 'android-clipboard',
				name: 'system-framework',
                meta: { title: '组织机构' },
				component: () => import('@/views/whdx/system/system-framework')
			},
			{
				path: 'system-dictionary',
				icon: 'android-clipboard',
				name: 'system-dictionary',
                meta: { title: '字典管理' },
				component: () => import('@/views/whdx/system/system-dictionary')
			},
			{
				path: 'system-ITSM',
				icon: 'android-clipboard',
				name: 'system-ITSM',
                meta: { title: '服务管理' },
				component: () => import('@/views/whdx/system/system-ITSM')
			},
			{
				path: 'system-function',
				icon: 'android-clipboard',
				name: 'system-function',
                meta: { title: '功能管理' },
				component: () => import('@/views/whdx/system/system-function')
			},
			{
				path: 'system-daily',
				icon: 'android-clipboard',
				name: 'system-daily',
                meta: { title: '日志管理' },
				component: () => import('@/views/whdx/system/system-daily')
			}
		]
	},
    {
        path: '/form',
        icon: 'android-checkbox',
        name: 'form',
        title: '表单编辑',
        component: Main,
        children: [
            { path: 'artical-publish', title: '文章发布', name: 'artical-publish', icon: 'compose', component: () => import('@/views/form/article-publish/article-publish.vue') },
            { path: 'workflow', title: '工作流', name: 'workflow', icon: 'arrow-swap', component: () => import('@/views/form/work-flow/work-flow.vue') }
        ]
    },
    {
        path: '/student',
        icon: 'android-car',
        name: 'student',
        meta: {title: '基础数据'},
        component: Main,
        children: [
            {
                path: 'school',
                icon: 'android-clipboard',
                name: 'school',
                meta: { title: '驾校管理' },
                component: () => import('@/views/whdx/school')
            },
            {
                path: 'examPlace',
                icon: 'android-clipboard',
                name: 'examPlace',
                meta: { title: '考场管理' },
                component: () => import('@/views/whdx/examPlace')
            },
            {
                path: 'trainPlace',
                icon: 'android-clipboard',
                name: 'trainPlace',
                meta: { title: '训练场管理' },
                component: () => import('@/views/whdx/trainPlace')
            },
            {
                path: 'news',
                icon: 'android-clipboard',
                name: 'news',
                meta: { title: '新闻资讯' },
                component: () => import('@/views/whdx/hd/list.vue')
            },
            {
                path: 'create_news',
                icon: 'android-clipboard',
                name: 'create_news',
                meta: { title: '新建资讯' },
                component: () => import('@/views/whdx/hd/create.vue')
            },
		]
    },
    {
        path: '/student',
        icon: 'android-car',
        name: 'student',
        meta: {title: '学员管理'},
        component: Main,
        children: [
            {
                path: 'student-list',
                icon: 'android-clipboard',
                name: 'student-list',
                meta: { title: '学员列表' },
                component: () => import('@/views/whdx/student/list')
            },
            {
                path: 'student-verify',
                icon: 'android-clipboard',
                name: 'student-verify',
                meta: { title: '学员认证' },
                component: () => import('@/views/whdx/student/verify')
            },
            {
                path: 'status',
                icon: 'android-clipboard',
                name: 'status',
                meta: { title: '学习进度' },
                component: () => import('@/views/whdx/student/status')
            },
		]
    },
    {
        path: '/commissioner',
        icon: 'android-car',
        name: 'commissioner',
        meta: {title: '专员管理'},
        component: Main,
        children: [
            {
                path: 'commissioner-list',
                icon: 'android-clipboard',
                name: 'commissioner-list',
                meta: { title: '专员列表' },
                component: () => import('@/views/whdx/commissioner')
            },
            {
                path: '/commissioner-asign',
                icon: 'android-car',
                name: 'commissioner-asign',
                meta: {title: '专员管理'},
                component: Main,
                children: [
                    {
                        path: 'commissioner-handle',
                        icon: 'android-clipboard',
                        name: 'commissioner-handle',
                        meta: { title: '受理专员' },
                        component: () => import('@/views/whdx/commissioner/handle')
                    },
                ]
            },

		]
    },
    {
        path: '/coach',
        icon: 'android-car',
        name: 'coach',
        meta: {title: '教练管理'},
        component: Main,
        children: [
            {
                path: 'teacher-list',
                icon: 'android-clipboard',
                name: 'teacher-list',
                meta: { title: '教练列表' },
                component: () => import('@/views/whdx/teacher/list')
            },
            {
                path: 'teacher-verify',
                icon: 'android-clipboard',
                name: 'teacher-verify',
                meta: { title: '教练审核' },
                component: () => import('@/views/whdx/teacher/verify')
            },
		]
    },
	{
		path: '/platform',
        icon: 'android-car',
        name: 'platform',
        meta: { title: '财务管理' },
        component: Main,
        children: [
            {
                path: 'product',
                icon: 'android-clipboard',
                name: 'product',
                meta: { title: '费用管理' },
                component: () => import('@/views/whdx/cp')
            },
			{
				path: 'order-list',
				icon: 'android-clipboard',
				name: 'order-list',
                meta: { title: '订单列表' },
				component: () => import('@/views/whdx/order')
			},
			{
				path: 'tx',
				icon: 'android-clipboard',
				name: 'tx',
                meta: { title: '提现管理' },
				component: () => import('@/views/whdx/tx/index.vue')
			},
		]
	},
    {
    	path: '/',
    	meta: {
    	    title: '500-服务端错误'
    	},
    	name: 'error-page-500',
    	component: Main,
        children: [
            { path: '500', title: '服务器繁忙', name: 'errorpage_500', component: () => import('@/views/error-page/500.vue') }
        ]
    }
];
// 所有上面定义的路由都要写在下面的routers里
export const routers = [
    loginRouter,
    otherRouter,
    ...appRouter,
    page500,
    page403,
    page404
];
