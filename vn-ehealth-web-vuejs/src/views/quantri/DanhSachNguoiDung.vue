<template>
    <div>
        <div class="font-weight-bold text-uppercase">
            <i class="far fa-file-alt"></i> DANH SÁCH NGƯỜI DÙNG
        </div>
        <!--    <div id="content" class="bg-white mt-2">Danh sách người dùng</div>-->
        <div class="col-6 float-left pl-0 pr-3">
            <form class="search-detail">
                <input v-model="keyword" class="form-control" placeholder="Search "/>
            </form>
        </div>
        <div class="col-6 float-left p-0 ">
            <select class="form-control float-left" style="width: 180px" v-model="roleId">
                <option v-for="role in roles" :value="role.id">{{role.ten}}</option>
            </select>
            <button v-on:click="search()" class="float-left ml-3 form-control" style="width: 150px; background-color: #C4C4C4">Tìm kiếm
            </button>
        </div>
        <div class="pr-4 pt-4">
            <div v-if="loading" style="position: absolute;left: 50%;top:50%" class="spinner-border"></div>
            <div id="app" v-if="!loading">
                <div v-if="totalRecords === 0">
                    <b>Không có dữ liệu</b>
                </div>
                <table v-if="totalRecords > 0" class="table table-bordered table-width-100 mt-4">
                    <thead>
                    <tr>
                        <th style="width:5%">STT</th>
                        <th style="width:20%">Họ tên</th>
                        <th style="width:20%">Email</th>
                        <th style="width:15%">Số điện thoại</th>
                        <th style="width:12%">Vai trò</th>
                        <th style="width:13%">Trạng thái</th>
                        <th style="width:15%">Thao tác</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="(item, index) in items">
                        <td align="center">{{index+1}}</td>
                        <td>{{item.emrPerson.tendaydu}}</td>
                        <td>{{item.emrPerson.email}}</td>
                        <td>{{item.emrPerson.dienthoai}}</td>
                        <td>
                            <span v-for="role in item.roles">{{role.ten}}</span>
                        </td>
                        <td></td>
                        <td align="center">
                            <router-link class="mr-2" title="Xem"
                                         :to="{name: 'themnguoidung', params: {userId: item.id.valueOf()}}">
                                    <i class="fa fa-eye"></i>
                            </router-link>

                            <a class="mr-2" href="#" title="Mở khóa">
                                <i class="fa fa-user"></i>
                            </a>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <router-link class="form-control" style="background-color: #C4C4C4; width: 200px; text-align: center"
                             to="/quantri/themnguoidung">
                    Thêm người dùng
                </router-link>
            </div>
        </div>
    </div>
</template>


<script>
    export default {
        data() {
            return {
                pageSize: 10,
                page: 1,
                items: [],
                totalPages: null,
                totalRecords: null,
                loading: false,
                roles: [],
                roleId: "",
                keyword: "",
            };
        },

        methods: {
            getAll: async function () {
                let result = await this.get("/api/user/findAll", {
                    page: this.page, pageSize: this.pageSize
                });
                this.items = result.listData;
                this.totalPages = result.totalPage;
                this.totalRecords = result.totalRow;
            },
            search: async function () {
                let result = await this.get("/api/user/search", {
                    page: this.page,
                    pageSize: this.pageSize,
                    keyword: this.keyword,
                    roleId: this.roleId
                });
                this.items = result.listData;
                this.totalPages = result.totalPage;
                this.totalRecords = result.totalRow;
            },
        },
        created: async function () {
            this.loading = true;
            this.roles = await this.get("/api/role/getAll");
            await this.getAll();
            this.loading = false;
        }
    }
</script>


<style scoped>

</style>
