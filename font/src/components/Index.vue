<template>
    <el-container style="height: 100%; margin: 0">
        <el-header
                style=" font-size: 12px;height: 100%;border-bottom: rgba(168,168,168,0.3) 1px solid; margin: 0;padding: 0">
            <div class="header">
                <div class="header-left">
                    <img src="../assets/logo.png" alt="">
                    <h1>BOOK CLUB</h1>
                </div>
                <div class="header-right">
                    <div style="">
                        <div class="r_input">
                            <el-input placeholder="Search" v-model="searchValue" style="width: 300px;"></el-input>
                            <el-button type="primary" @click="search">go</el-button>
                        </div>
                        <div class="r_button">
                            <el-button type="success" @click="login" v-if="this.user == null">Sign in</el-button>
                            <el-button type="success" @click="register" v-if="this.user == null">Register</el-button>
                            <div v-if="this.user != null">
                                <span style="font-size: 16px">Welcome : </span><span style="font-size: 16px;margin-right: 20px">{{ this.user.name }}</span>

                                <el-button size="big" style="background-color: yellow" @click.native="logout"><h1>logout</h1></el-button>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </el-header>

        <el-menu
            :default-active="activeIndex"
            class="el-menu-demo"
            mode="horizontal"
            @select="handleSelect"
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b">
            <el-menu-item index="1" style="margin-left: 50px;margin-right: 50px">Home</el-menu-item>
            <el-menu-item index="2" >List</el-menu-item>
        </el-menu>

        <br>
        <br>
        <div style="width: 100%" v-if="menuIndex==1">
            <el-menu
                :default-active="activeIndex"
                class="el-menu-demo"
                mode="horizontal"
                text-color="black"
                active-text-color="black">
                <el-menu-item index="1" style="margin-left: 50px;margin-right: 50px">RECOMMEND</el-menu-item></el-menu>

            <br>
            <div style="margin-left: 60px;">
                <el-row v-for="(item,index) in tableData" :key="index" @click.native="routTo(item.id)">
                    <el-card :body-style="{display: 'flex'}" >
                        <img :src="item.image" class="image">
                        <div style="padding: 14px;width: 320px ; background-color: bisque;">
                            <br>
                            <span style="margin: 20px ;font-weight: bold ; font-size: larger">{{item.title}}</span>
                            <br>
                            <br>
                            <span style="margin: 16px ;font-weight: bold ; font-size: 16px">Publication </span>
                            <span style="margin: 16px ;font-weight: bold ; font-size: 16px">: {{item.publication}}</span>
                            <br>
                            <br>
                            <span style="margin: 16px ;font-weight: bold ; font-size: 16px">Author </span>
                            <span style="margin-left: 48px ;font-weight: bold ; font-size: 16px">: {{item.author}}</span>
                            <br>
                            <br>
                            <span style="margin: 16px ;font-weight: bold ; font-size: 16px">Release time </span>
                            <span style="margin-left: 2px ;font-weight: bold ; font-size: 16px">: {{item.releaseTime}}</span>
                            <br>
                            <div style="display: flex">
                                <span style="margin: 16px ;font-weight: bold ; font-size: 16px">Rating </span>

                                <span style="margin: 16px 16px 16px 56px;font-weight: bold ; font-size: 16px">
                            <el-rate
                                v-model="item.rating"
                                disabled
                                show-score
                                score-template="{value}">
                            </el-rate>
                        </span>
                            </div>

                        </div>
                    </el-card>

                </el-row>
            </div>
        </div>

        <div style="width: 100%" v-if="menuIndex==2">
            <el-menu
                :default-active="2"
                class="el-menu-demo"
                mode="horizontal">
                <el-menu-item index="1" style="margin-left: 50px;margin-right: 30px;color: #333333" disabled="disabled" >SEARCH</el-menu-item>
            <el-menu-item index="2" style="margin-left: 30px;margin-right: 50px">BOOK LIST</el-menu-item></el-menu>
            <br>

            <el-table :data="tableData"
                      :header-cell-style="{ background: '#f2f5fc', color: '#555555' }"
                      style="margin-left: 50px">
            >
                <el-table-column prop="id" label="ID" width="160">
                </el-table-column>
                <el-table-column prop="image" label="image" width="200">
                    <template slot-scope="scope">
                        <img :src="scope.row.image" alt="" style="width: 150px; height: 150px;">
                    </template>
                </el-table-column>
                <el-table-column prop="title" label="title" width="200">
                    <template slot-scope="scope">
                        <router-link :to="{name: 'detail', params: {bookId: scope.row.id}}">{{scope.row.title}}</router-link>
                    </template>
                </el-table-column>
                <el-table-column prop="author" label="author" width="200">
                </el-table-column>
                <el-table-column prop="publication" label="publication" width="200">
                </el-table-column>
                <el-table-column prop="rating" label="rating" width="200">
                    <template slot-scope="scope">
                        <el-rate :value="scope.row.rating" disabled></el-rate>
                    </template>
                </el-table-column>

            </el-table>
        </div>



        <el-dialog title="Sign in" :visible.sync="dialogFormVisible" center>

            <el-form :model="loginForm" status-icon :rules="rules" ref="loginForm" label-width="100px" class="ruleForm">

                <el-form-item label="username" prop="username">
                    <el-input v-model="loginForm.username"></el-input>
                </el-form-item>

                <el-form-item label="password" prop="password">
                    <el-input type="password" v-model="loginForm.password" auto-complete="off"></el-input>
                </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false; resetForm('loginForm')">cancel</el-button>
                <el-button type="primary" @click="confirm">sign in</el-button>
            </div>
        </el-dialog>

        <el-dialog
                title="Register"
                :visible.sync="centerDialogVisible"
                width="40%"
                center>

            <el-form ref="form" :rules="rules1" :model="form" label-width="100px">
                <el-form-item label="username" prop="username">
                    <el-col :span="20">
                        <el-input v-model="form.username"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="password" prop="password">
                    <el-col :span="20">
                        <el-input v-model="form.password"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="name" prop="name">
                    <el-col :span="20">
                        <el-input v-model="form.name"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="dob" prop="dob">
                    <el-col :span="20">
                        <el-input v-model="form.dob"></el-input>
                    </el-col>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="centerDialogVisible = false">cancel</el-button>
                <el-button type="primary" @click="doSave">confirm</el-button>
            </span>
        </el-dialog>
    </el-container>
</template>


<script>

import it from "element-ui/src/locale/lang/it";

export default {
    name: "index",
    computed: {
        it() {
            return it
        }
    },
    data() {
        let checkDuplicate = (rule, value, callback) => {
            if (this.form.id) {
                return callback();
            }
            this.$axios.get(this.$httpUrl + "/user/find/username?username=" + this.form.username).then(res => res.data).then(res => {
                if (res.code != 200) {
                    callback()
                } else {
                    callback(new Error('username already exists'));
                }
            })
        };
        return {
            menuIndex: 1,
            tableData: [],
            activeIndex: '1',
            user: null,
            dialogFormVisible: false,
            centerDialogVisible: false,
            loginForm: {
                username: '',
                password: ''
            },
            rules: {
                username: [
                    {required: true, message: 'please enter username', trigger: 'blur'}
                ],
                password: [
                    {required: true, message: 'please enter password', trigger: 'blur'}
                ]
            },
            form: {
                id: '',
                username: '',
                name: '',
                password: '',
                dob: ''
            },
            rules1: {
                username: [
                    {required: true, message: 'Please enter username', trigger: 'blur'},
                    {min: 3, max: 8, message: '3 to 8 characters long', trigger: 'blur'},
                    {validator: checkDuplicate, trigger: 'blur'}
                ],
                name: [
                    {required: true, message: 'Please enter name', trigger: 'blur'}
                ],
                password: [
                    {required: true, message: 'Please enter password', trigger: 'blur'},
                    {min: 3, max: 8, message: '3 to 8 characters long', trigger: 'blur'}
                ],
            },
            searchValue: ''
        }
    },
    methods: {
        routTo(bookId){
            this.$router.push({
                name: 'detail',
                params: {
                    bookId: bookId
                }
            })
        },
        logout() {
            console.log('logout')

            this.$confirm('Are you sure you want to log out??', 'prompt', {
                confirmButtonText: 'confirm',
                cancelButtonText: 'cancel',
                type: 'warning',
                center: true,

            })
                .then(() => {
                    this.$message({
                        type: 'success',
                        message: 'exit successfully'
                    })

                    this.$router.push("/")
                    this.user = null
                    this.resetForm('loginForm')
                    sessionStorage.clear()
                })
                .catch(() => {
                    this.$message({
                        type: 'info',
                        message: 'cancel exit'
                    })
                })
        },
        doSave() {
            this.$axios.post(this.$httpUrl + '/user/register', this.form).then(res => res.data).then(res => {
                console.log(res)
                if (res.code == 200) {

                    this.$message({
                        message: res.msg,
                        type: 'success'
                    });
                    this.centerDialogVisible = false
                    this.resetForm()
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }

            })
        },
        register() {
            this.centerDialogVisible = true;
        },
        login() {
            this.dialogFormVisible = true;
        },
        confirm() {
            this.$refs.loginForm.validate(() => {
                this.$axios.post(this.$httpUrl + '/user/login', this.loginForm).then(res => res.data).then(res => {
                    if (res.code == 200) {
                        this.user = res.data
                        sessionStorage.setItem("CurUser", JSON.stringify(res.data))
                        if (res.data.type == "admin") {
                            this.$router.replace('/admin/Index');
                        }
                        this.$message({
                            message: res.msg,
                            type: 'success'
                        });
                        this.dialogFormVisible = false;
                    } else {
                        this.$message({
                            message: res.msg,
                            type: 'error'
                        });
                    }
                });

            });

        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },
        search() {
            this.activeIndex = "2"
            this.menuIndex = 2
            this.getBook()
        },
        getBook() {
            this.$axios.get(this.$httpUrl + '/book/list?keyword='+this.searchValue).then(res => res.data).then(res => {
                console.log(res)
                this.tableData = res.data
            })

        },
        handleSelect(key, keyPath) {
            this.menuIndex = key
            if (this.menuIndex == 1){
                this.searchValue = ''
            }
            console.log(key, keyPath);
            this.getBook();
        },

    },
    created() {
       this.$axios.get(this.$httpUrl + '/book/list').then(res => res.data).then(res => {
            console.log(res)
            this.tableData = res.data
        })

        this.user = JSON.parse(sessionStorage.getItem("CurUser"))

    }
}
</script>

<style scoped>
.header {
    width: 100%;
    height: 180px;
    display: flex;
    margin: 0;
    padding: 0;
}

.header img {
    width: 100px;
    height: 100px;
}

.header-left {
    display: flex;
    align-items: center;
    justify-content: center;
}

.header-right {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-grow: 1;
}

.r_button {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-grow: 1;
    margin-top: 20px;
}

.image {
    width: 330px;
    height: 300px;
}

</style>