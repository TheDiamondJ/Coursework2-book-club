<template>
    <el-menu
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b"
            style="height: 100%;"
            default-active="/admin/Home"
            :collapse="isCollapse"
            :collapse-transition="false"
            router
    >
       <el-menu-item index="/admin/Home">
           <i class="el-icon-s-home"></i>
           <span slot="title">Home</span>
       </el-menu-item>

        <el-menu-item :index="'/admin/User'">
            <i class="el-icon-user-solid"></i>
            <span slot="title">user</span>
        </el-menu-item>

        <el-menu-item :index="'/admin/Book'">
            <i class="el-icon-menu"></i>
            <span slot="title">book</span>
        </el-menu-item>

        <el-menu-item :index="'/admin/Comment'">
            <i class="el-icon-s-order"></i>
            <span slot="title">comment</span>
        </el-menu-item>


    </el-menu>
</template>

<script>
    export default {
        name: "Aside",
        data(){
            return {
            }
        },
        computed:{
        },
        props:{
            isCollapse:Boolean
        }
    }
</script>

<style scoped>

</style>