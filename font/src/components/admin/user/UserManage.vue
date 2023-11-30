<template>
    <div>
        <div style="margin-bottom: 5px;">
            <el-input v-model="name" placeholder="please input name" suffix-icon="el-icon-search" style="width: 200px;"
                      @keyup.enter.native="loadPost"></el-input>
            <el-button type="primary" style="margin-left: 10%;" @click="loadPost">search</el-button>
            <el-button type="success" @click="resetParam">reset</el-button>

            <el-button type="primary" style="margin-left: 5px;" @click="add">add</el-button>
        </div>
        <el-table :data="tableData"
                  :header-cell-style="{ background: '#f2f5fc', color: '#555555' }"
                  border
        >
            <el-table-column prop="id" label="ID" width="60">
            </el-table-column>
            <el-table-column prop="username" label="username" width="180">
            </el-table-column>
            <el-table-column prop="name" label="name" width="180">
            </el-table-column>
            <el-table-column prop="dob" label="dob" width="80">
            </el-table-column>
            <el-table-column prop="type" label="role" width="120">
                <template slot-scope="scope">
                    <el-tag
                            disable-transitions>{{scope.row.type}}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="operate" label="operate">
                <template slot-scope="scope">
                    <el-button size="small" type="success" @click="mod(scope.row)">edit</el-button>
                    <el-popconfirm
                            title="Are you sure to delete this?"
                            @confirm="del(scope.row.id)"
                            style="margin-left: 5px;"
                    >
                        <el-button slot="reference" size="small" type="danger">delete</el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="pageNum"
                :page-sizes="[5, 10, 20,30]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
        </el-pagination>

        <el-dialog
                title="prompt"
                :visible.sync="centerDialogVisible"
                width="40%"
                center>

            <el-form ref="form" :rules="rules" :model="form" label-width="100px">
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
    <el-button type="primary" @click="save">confirm</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: "UserManage",
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
            tableData: [],
            pageSize: 10,
            pageNum: 1,
            total: 0,
            name: '',
            centerDialogVisible: false,
            form: {
                id: '',
                username: '',
                name: '',
                password: '',
                dob: ''
            },
            rules: {
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
            }
        }
    },
    methods: {
        resetForm() {
            this.$refs.form.resetFields();
            this.name = ''
            this.loadPost();
        },
        del(id) {
            console.log(id)

            this.$axios.get(this.$httpUrl + '/user/delete?userId=' + id).then(res => res.data).then(res => {
                console.log(res)
                if (res.code == 200) {

                    this.$message({
                        message: res.msg,
                        type: 'success'
                    });
                    this.loadPost()
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }

            })
        },
        mod(row) {
            console.log(row)

            this.centerDialogVisible = true
            this.$nextTick(() => {
                //赋值到表单
                this.form.id = row.id
                this.form.username = row.username
                this.form.name = row.name
                this.form.password = ''
                this.form.dob = row.dob
            })
        },
        add() {

            this.centerDialogVisible = true
            this.$nextTick(() => {
                this.resetForm()
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
                    this.loadPost()
                    this.resetForm()
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }

            })
        },
        doMod() {
            this.$axios.post(this.$httpUrl + '/user/update', this.form).then(res => res.data).then(res => {
                console.log(res)
                if (res.code == 200) {

                    this.$message({
                        message: res.msg,
                        type: 'success'
                    });
                    this.centerDialogVisible = false
                    this.loadPost()
                    this.resetForm()
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }

            })
        },
        save() {
            this.$refs.form.validate((valid) => {
                if (valid) {
                    if (this.form.id) {
                        this.doMod();
                    } else {
                        this.doSave();
                    }
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });

        },
        handleSizeChange(val) {
            this.pageNum = 1
            this.pageSize = val
            this.loadPost()
        },
        handleCurrentChange(val) {
            this.pageNum = val
            this.loadPost()
        },
        resetParam() {
            this.name = ''
            this.loadPost()
        },
        loadPost() {
            this.$axios.get(this.$httpUrl + '/user/find', {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    keyword: this.name
                }
            }).then(res => res.data).then(res => {
                console.log(res)
                if (res.code == 200) {
                    this.tableData = res.data
                } else {
                    alert('Failed to get data')
                }

            })
        }
    },
    beforeMount() {
        //this.loadGet();
        this.loadPost()
    }
}
</script>

<style scoped>

</style>