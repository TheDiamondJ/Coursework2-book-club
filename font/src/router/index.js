
import VueRouter from 'vue-router';

const routes = [
    {
        path:'/detail/:bookId',
        name:'detail',
        component:()=>import('../components/Detail.vue')
    },
    {
        path:'/',
        name:'index',
        component:()=>import('../components/Index.vue')
    },
    {
        path:'/admin/Index',
        name:'index',
        component:()=>import('../components/admin/Index.vue'),
        children:[
            {
                path:'/admin/Home',
                name:'home',
                meta:{
                    title:'首页'
                },
                component:()=>import('../components/admin/Home.vue')
            },
            {
                path:'/admin/User',
                name:'user',
                meta:{
                    title:'用户管理'
                },
                component:()=>import('../components/admin/user/UserManage.vue')
            },
            {
                path:'/admin/Book',
                name:'book',
                meta:{
                    title:'图书管理'
                },
                component:()=>import('../components/admin/book/BookManage.vue')
            },
            {
                path:'/admin/Comment',
                name:'comment',
                meta:{
                    title:'评论管理'
                },
                component:()=>import('../components/admin/comment/CommentManage.vue')
            }
        ]
    }
]

const router = new VueRouter({
    mode:'history',
    routes
})

export function resetRouter() {
    router.matcher = new VueRouter({
        mode:'history',
        routes: []
    }).matcher
}
const VueRouterPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (to) {
    return VueRouterPush.call(this, to).catch(err => err)
}
export  default router;