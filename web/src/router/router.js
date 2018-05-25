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
		path: '/platform',
        icon: 'android-car',
        name: 'platform',
        meta: { title: '平台管理' },
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
				path: 'hd-jx',
				icon: 'android-clipboard',
				name: 'hd-jx',
                meta: { title: '驾校资料' },
				component: () => import('@/views/whdx/hd/jx')
			},
			{
				path: 'student-list',
				icon: 'android-clipboard',
				name: 'student-list',
                meta: { title: '训练场资料' },
				component: () => import('@/views/whdx/student/list')
			},
			{
				path: 'order-list',
				icon: 'android-clipboard',
				name: 'order-list',
                meta: { title: '订单列表' },
				component: () => import('@/views/whdx/order')
			},
			{
				path: 'create_news',
				icon: 'android-clipboard',
				name: 'create_news',
                meta: { title: 'text' },
				component: () => import('@/views/whdx/hd/create.vue')
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