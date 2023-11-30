<template>
    <div>
        <div style="margin-bottom: 5px;">
            <el-input v-model="keyword" placeholder="please input keyword" suffix-icon="el-icon-search" style="width: 200px;"
                      @keyup.enter.native="loadPost"></el-input>
            <el-button type="primary" style="margin-left: 10%;" @click="loadPost">search</el-button>
            <el-button type="success" @click="resetParam">reset</el-button>
        </div>
        <el-table :data="tableData"
                  :header-cell-style="{ background: '#f2f5fc', color: '#555555' }"
                  border
        >
            <el-table-column prop="id" label="ID" width="60">
            </el-table-column>
            <el-table-column prop="bookId" label="bookId" width="80">
            </el-table-column>
            <el-table-column prop="userId" label="userId" width="80">
            </el-table-column>
            <el-table-column prop="content" label="content" width="200">
            </el-table-column>
            <el-table-column prop="rating" label="rating" width="150">
                <template slot-scope="scope">
                    <el-rate :value="scope.row.rating" disabled></el-rate>
                </template>
            </el-table-column>
            <el-table-column prop="time" label="time" width="150">
            </el-table-column>

            <el-table-column prop="operate" label="operate">
                <template slot-scope="scope">
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

    </div></template>

<script>
    export default {
        name: "CommentManage",
        data() {

            return {
                tableData: [],
                pageSize: 10,
                pageNum: 1,
                total: 0,
                keyword: '',
                form: {
                    id: '',
                    bookId: '',
                    userId: '',
                    time: '',
                    rating: '',
                    content: '',


                }
            }
        },
        methods: {
            resetForm() {
                this.$refs.form.resetFields();
                this.keyword = ''
                this.loadPost();
            },
            del(id) {
                console.log(id)

                this.$axios.get(this.$httpUrl + '/comment/delete?commentId=' + id).then(res => res.data).then(res => {
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
                    this.form.time = row.time
                    this.form.rating = row.rating
                    this.form.content = row.content
                    this.form.bookId = row.bookId
                    this.form.userId = row.userId
                })
            },
            add() {
                this.centerDialogVisible = true
                this.$nextTick(() => {
                    this.resetForm()
                })

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
                this.$axios.get(this.$httpUrl + '/comment/list', {
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