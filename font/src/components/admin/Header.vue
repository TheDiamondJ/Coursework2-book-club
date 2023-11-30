<template>
    <div style="display: flex;line-height: 60px;">
        <div style="margin-top: 8px;">
            <i :class="icon" style="font-size: 20px;cursor: pointer;" @click="collapse"></i>
        </div>
        <div style="flex: 1;text-align: center;font-size: 34px;">
            <span>Book Club Backstage Management</span>
        </div>
        <el-dropdown>
            <span>admin</span><i class="el-icon-arrow-down" style="margin-left: 5px;"></i>
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="logout">logout</el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>

    </div>
</template>

<script>
    export default {
        name: "Header",
        data(){
            return {
                user : JSON.parse(sessionStorage.getItem('CurUser'))
            }
        },
        props:{
            icon:String
        },
        methods:{
            logout(){
                console.log('logout')

                this.$confirm('Are you sure you want to log out??', 'prompt', {
                    confirmButtonText: 'confirm',
                    cancelButtonText: 'cancel',
                    type: 'warning',
                    center: true,

                })
                    .then(() => {
                        this.$message({
                            type:'success',
                            message:'exit successfully'
                        })

                        this.$router.push("/")
                        sessionStorage.clear()
                    })
                    .catch(() => {
                        this.$message({
                            type:'info',
                            message:'cancel exit'
                        })
                    })

            },
            collapse(){
                this.$emit('doCollapse')
            }

        },
        created(){
            if (this.user === null){
                this.$message({
                    type:'error',
                    message:'Please log in first'
                })
                this.$router.push("/")
                return
            }
            if (this.user.type !== "admin"){
                this.$message({
                    type:'error',
                    message:'You are not an administrator'
                })
                this.$router.push("/")
                return;
            }
            this.$router.push("/admin/Home")
        }

    }
</script>

<style scoped>

</style>