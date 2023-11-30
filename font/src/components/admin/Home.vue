<template>
    <div style="text-align: center;background-color: #f1f1f3;height: 100%;padding: 0px;margin: 0px;">
        <h1 style="font-size: 50px;">{{'manager : '+ this.name}}</h1>
            <el-card><span>Welcome to the library club background management system!</span>
                <br>
                <el-divider></el-divider>
                <div >
                    <h1>1. User Management:</h1>
                    <ul style="text-align: left">
                        <li>Add users: Administrators can add new users and set their user names, passwords, and permission levels.</li>
                        <li>Edit User: Administrators can edit the username, password, and permission level of an existing user.</li>
                        <li>Delete Users: Administrators can delete users that are no longer needed.</li>
                        <li>View user list: Administrators can view all current user lists and view their user names, permission levels, and more.</li>
                    </ul>
                </div>
                <el-divider></el-divider>
                <div >
                    <h1>2. Book Management:</h1>
                    <ul style="text-align: left">
                        <li>Add books: Administrators can add new books and fill in the book's name, author, publisher, ISBN number, etc.</li>
                        <li>Edit Books: Administrators can edit information about existing books.</li>
                        <li>Delete Books: Administrators can delete books that are no longer needed.</li>
                        <li>View Book List: Administrators can view all current book lists and view book details.</li>
                    </ul>
                </div>
                <el-divider></el-divider>
                <div >
                    <h1>3. Comments Management:</h1>
                    <ul style="text-align: left">
                        <li>Review lists: Administrators can view a list of user reviews of books and see information such as the content of the review, who reviewed it, and when it was reviewed.</li>
                        <li>Delete comments: Administrators can delete inappropriate or offending comments.</li>
                    </ul>
                </div>
            </el-card>


        <DateUtils></DateUtils>
    </div>
</template>

<script>
    import DateUtils from "./DateUtils.vue";
    export default {
        name: "Home",
        components: {DateUtils},
        data() {

            return {
                user:{},
                name: ''
            }
        },
        computed:{

        },
        methods:{
            init(){
                this.user = JSON.parse(sessionStorage.getItem('CurUser'))
                if (this.user == null){
                    this.$router.push("/")
                    return
                }
                this.name = this.user.name
            }
        },
        created(){
            this.init()
        }
    }
</script>

<style scoped>
    .el-descriptions{
        width:90%;

        margin: 0 auto;
        text-align: center;
    }
</style>