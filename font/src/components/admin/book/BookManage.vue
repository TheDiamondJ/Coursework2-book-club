<template>
    <div>
        <div style="margin-bottom: 5px;">
            <el-input v-model="keyword" placeholder="please input keyword" suffix-icon="el-icon-search" style="width: 200px;"
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
            <el-table-column prop="image" label="image" width="150">
                <template slot-scope="scope">
                    <img :src="scope.row.image" alt="" style="width: 100px; height: 100px;">
                </template>
            </el-table-column>
            <el-table-column prop="title" label="title" width="150">
            </el-table-column>
            <el-table-column prop="author" label="author" width="150">
            </el-table-column>
            <el-table-column prop="publication" label="publication" width="150">
            </el-table-column>
            <el-table-column prop="rating" label="rating" width="150">
                <template slot-scope="scope">
                    <el-rate :value="scope.row.rating" disabled></el-rate>
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
                <el-form-item label="title" prop="title">
                    <el-col :span="20">
                        <el-input v-model="form.title"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="publication" prop="publication">
                    <el-col :span="20">
                        <el-input v-model="form.publication"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="author" prop="author">
                    <el-col :span="20">
                        <el-input v-model="form.author"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="release time" prop="releaseTime">
                    <el-col :span="20">
                        <el-input v-model="form.releaseTime"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="image" prop="image">
                    <el-col :span="20">
                        <el-upload action="http://localhost:8888/upload"
                                   list-type="picture-card"
                                    :on-preview="preview"
                                    :on-success="handleSuccess"
                                    :limit="1"
                                    :before-upload="beforeUpload">
                            <i class="el-icon-plus"></i>
                        </el-upload>
                    </el-col>
                </el-form-item>
                <el-form-item label="rating" prop="rating">
                    <el-col :span="20">
                        <el-rate v-model="form.rating"></el-rate>
                    </el-col>
                </el-form-item>
                <el-form-item label="content" prop="content">
                    <el-col :span="20">
                        <el-input type="textarea" v-model="form.content" rows="5"></el-input>
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
    name: "BookManage",
    data() {

        return {
            tableData: [],
            pageSize: 10,
            pageNum: 1,
            total: 0,
            keyword: '',
            centerDialogVisible: false,
            form: {
                id: '',
                title: '',
                publication: '',
                author: '',
                releaseTime: '',
                price: '',
                rating: '',
                content: '',
                image: ''

            },
            rules: {
                image: [
                    {required: true, message: 'Please upload a image', trigger: 'blur'},
                ],
                title: [
                    {required: true, message: 'Please enter title', trigger: 'blur'}
                ],
                publication: [
                    {required: true, message: 'Please enter publication', trigger: 'blur'},
                ],
                author: [
                    {required: true, message: 'Please enter author', trigger: 'blur'},
                ],
                releaseTime: [
                    {required: true, message: 'Please enter releaseTime', trigger: 'blur'},
                ],
                content: [
                    {required: true, message: 'Please enter content', trigger: 'blur'},
                ],
            }
        }
    },
    methods: {
        handleSuccess(res, file) {
            console.log(file.url)
            this.form.image = res.data
        },
        preview(file) {
            console.log(file)
        },
        beforeUpload(file) {
            const isJPG = file.type === 'image/jpeg';
            const isPNG = file.type === 'image/png';
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG && !isPNG) {
                this.$message.error('Upload pictures can only be in JPG or PNG format!');
            }
            if (!isLt2M) {
                this.$message.error('Upload pictures cannot exceed 2MB in size!');
            }
            return (isJPG || isPNG) && isLt2M;
        },
        resetForm() {
            this.$refs.form.resetFields();
            this.keyword = ''
            this.loadPost();
        },
        del(id) {
            console.log(id)

            this.$axios.get(this.$httpUrl + '/book/delete?bookId=' + id).then(res => res.data).then(res => {
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
                this.form.id = row.id
                this.form.title = row.title
                this.form.publication = row.publication
                this.form.author = row.author
                this.form.releaseTime = row.releaseTime
                this.form.rating = row.rating
                this.form.content = row.content
                this.form.image = row.image
            })
        },
        add() {

            this.centerDialogVisible = true
            this.$nextTick(() => {
                this.resetForm()
            })

        },
        doSave() {
            this.$axios.post(this.$httpUrl + '/book/add', this.form).then(res => res.data).then(res => {
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
            console.log(this.form)
        },
        doMod() {
            this.$axios.post(this.$httpUrl + '/book/update', this.form).then(res => res.data).then(res => {
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
            this.keyword = ''
            this.loadPost()
        },
        loadPost() {
            this.$axios.get(this.$httpUrl + '/book/list', {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    keyword: this.keyword
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