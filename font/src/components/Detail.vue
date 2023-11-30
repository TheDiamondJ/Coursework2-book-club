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
                                <span style="font-size: 16px">Welcome : </span><span
                                    style="font-size: 16px;margin-right: 20px">{{ this.user.name }}</span>

                                <el-button size="big" style="background-color: yellow" @click.native="logout"><h1>
                                    logout</h1></el-button>

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
            <el-menu-item index="2" disabled>List</el-menu-item>
        </el-menu>

        <br>
        <br>

        <div style="display: flex">
            <div style="width: 60%">
                <el-menu
                        :default-active="activeIndex"
                        class="el-menu-demo"
                        mode="horizontal"
                        text-color="black"
                        active-text-color="black">
                    <el-menu-item index="1" style="margin-left: 50px;margin-right: 50px">BOOK DETAIL</el-menu-item>
                </el-menu>
                <br>
                <div style="margin-left: 50px">
                    <h2>{{ detailData.book.title }}</h2>
                    <span class="el-icon-user"
                          style="float: right;margin-right: 50px">{{ detailData.book.author }}</span>
                    <br>
                    <el-image :src="detailData.book.image" :preview-src-list="detailData.book.image"
                              style="width: 100%;height: auto"></el-image>
                    <br>
                    <br>
                    <p>
                        {{ detailData.book.content }}
                    </p>
                </div>
                <br>

                <el-divider></el-divider>
                <div style="margin-left: 50px;margin-bottom: 50px">
                    <h2 style="margin-bottom: 50px">RATING ({{ detailData.commentCount }})</h2>
                    <div style="border: #B3C0D1 solid 1px">
                        <p style="font-size: 16px; margin: 20px ;color: gray">Comment</p>
                        <el-form :model="commentForm" status-icon ref="commentForm" label-width="100px"
                                 class="ruleForm">
                            <div style="width: 90%;margin: auto">


                                <el-input v-model="commentForm.content" type="textarea" placeholder="Comment"
                                          :rows="8"></el-input>
                                <div style="display: flex;margin: 50px">
                                    <span style="margin-right: 30px">Rating : </span>
                                    <el-rate v-model="commentForm.rate" show-score text-color="#ff9900"
                                             score-template="{value}">
                                    </el-rate>
                                    <el-button type="primary" @click="submitComment" style="margin-left: 30%">submit
                                    </el-button>
                                </div>

                            </div>
                            <div style="margin-left: 5%">

                            </div>

                        </el-form>

                        <el-card v-for="c in detailData.commentList" :key="c" class="box-card"
                                 style="margin: 20px 50px 20px 50px">
                                <p style="margin-bottom: 10px">{{ c.content }}</p>

                            <el-rate v-model="c.rating" disabled style="float: right;margin-bottom: 30px">

                            </el-rate>
                            <div style="display: flex;margin-top: 30px">
                                <el-avatar shape="square" :size="50" :src="defaultImg" ></el-avatar>
                                <div style="margin-left: 20px">
                                    <span style="font-size:12px;font-weight: lighter ;color: gray">{{ c.user.name }}</span>
                                    <br>
                                    <span style="font-size:12px;font-weight: lighter ;color: gray">Time: {{ c.time }}</span>
                                </div>

                            </div>



                        </el-card>

                    </div>
                </div>


            </div>
            <div style="width: 30%; margin-left: 60px">
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <span>OTHER BOOK</span>
                        <el-button style="float: right; padding: 3px 0" type="text">flush</el-button>
                    </div>
                    <div v-for="o in tableData" :key="o" class="text item" style="display: flex">
                        <img :src="o.image" style="width: 120px;height: 100px;margin-left: 20px">
                        <div style="margin-left:30px;margin-bottom: 50px;margin-top: 10px">
                            <h3>{{ o.title }}</h3>
                            <p style="font-weight: lighter;color: orange;margin-top: 5px">{{ o.author }}</p>
                            <p style="font-weight: lighter;color: gray;margin-top: 5px">{{ o.publication }}</p>
                        </div>
                    </div>
                </el-card>
            </div>
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
export default {
    name: "Detail",
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
            defaultImg: 'http://localhost:8888/upload/default.jpg',
            commentForm: {
                content: '',
                rate: 0
            },
            commentData: [],
            bookId: '',
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
            detailData: {},
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
        submitComment() {
            console.log(this.commentForm)
            if (this.user == null) {
                this.$message({
                    message: 'please login first',
                    type: 'error'
                })
                return
            }
            this.$axios.post(this.$httpUrl + '/comment/add', {
                content: this.commentForm.content,
                rating: this.commentForm.rate,
                bookId: this.bookId,
                userId: this.user.id
            }).then(res => res.data).then(res => {
                if (res.code == 200) {
                    this.$message({
                        message: 'comment successfully',
                        type: 'success'
                    })
                    this.commentForm.content = ''
                    this.commentForm.rate = 0
                    this.getComment()
                } else {
                    this.$message({
                        message: 'comment failed',
                        type: 'error'
                    })
                }
            })
        },
        getComment() {
            if (this.bookId) {
                this.$axios.get(this.$httpUrl + '/book/get?bookId=' + this.bookId).then(res => res.data).then(res => {
                    this.detailData = res.data
                })
            }
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
            this.activeIndex = '2'
            this.menuIndex = 2
            this.getBook()
        },
        getBook() {
            this.$axios.get(this.$httpUrl + '/book/list?keyword=' + this.searchValue).then(res => res.data).then(res => {
                console.log(res)
                this.tableData = res.data
            })

        },
        handleSelect(key, keyPath) {
            this.$router.push('/')
            console.log(key, keyPath);

        },

    },
    created() {
        const bookId = this.$route.params.bookId;
        this.bookId = bookId
        if (bookId) {
            this.$axios.get(this.$httpUrl + '/book/get?bookId=' + bookId).then(res => res.data).then(res => {
                this.detailData = res.data
            })
        }
        this.$axios.get(this.$httpUrl + '/book/list').then(res => res.data).then(res => {
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

.box-card {
    background-color: #F7F7F7;
}

.text {
    font-size: 14px;
}

.item {
    margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
    display: table;
    content: "";
}

.clearfix:after {
    clear: both
}


</style>