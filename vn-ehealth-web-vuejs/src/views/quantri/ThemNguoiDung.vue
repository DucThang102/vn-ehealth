<template>
    <div>
        <div class="font-weight-bold text-uppercase">
            <i class="far fa-file-alt"></i> THÊM NGƯỜI DÙNG
        </div>
        <div class="ml-5 mr-5">
            <form action="" class="row">
                <div class="col-4 float-left">
                    <label>Họ và tên:</label>
                    <input type="text" class="form-control" v-model="user.emrPerson.tendaydu"
                           placeholder="Nguyen Van A">
                    <ul v-if="errors.tendaydu">
                        <li v-for="(error,i) in errors.tendaydu" :key="i" style="color:red">
                            {{error}}
                        </li>
                    </ul>
                </div>
                <div class="col-4 float-left">
                    <label>Ngày sinh</label>
                    <date-picker v-model="user.emrPerson.ngaysinh" :config="{format: 'YYYY-DD-MM HH:mm:ss'}"/>
                </div>
                <div class="col-4 float-left">
                    <label>Giới tính</label>
                    <select v-model="user.emrPerson.emrDmGioiTinh.ma" class="form-control">
                        <option value="M">Nam</option>
                        <option value="F">Nữ</option>
                        <option value="U">Không xác định</option>
                    </select>
                </div>
                <div class="col-4 float-left mt-3">
                    <label>Email</label>
                    <input type="text" class="form-control" placeholder="nva@gmail.com" v-model="user.emrPerson.email">
                    <ul v-if="errors.email">
                        <li v-for="(error,i) in errors.email" :key="i" style="color:red">
                            {{error}}
                        </li>
                    </ul>
                </div>
                <div class="col-4 float-left mt-3">
                    <label>Số điện thoại</label>
                    <input type="number" class="form-control" placeholder="0978544446"
                           v-model="user.emrPerson.dienthoai">
                </div>
                <div class="col-4 float-left mt-3">
                    <label>CMNN/Hộ chiếu</label>
                    <input type="text" class="form-control" placeholder="121322231" v-model="user.emrPerson.cmnd">
                </div>
                <div class="col-4 float-left mt-3">
                    <label>Nghề nghiệp</label>
                    <select v-model="user.emrPerson.emrDmNgheNghiep.ma" class="form-control">
                        <option v-for="dm in dmNgheNghiepList" :key="dm.id" :value="dm.ma">{{dm.ten}}</option>
                    </select>
                </div>
                <div class="col-4 float-left mt-3">
                    <label>Quốc tịch</label>
                    <select v-model="user.emrPerson.emrDmQuocGia.ma" class="form-control">
                        <option v-for="dm in dmQuocGiaList" :key="dm.id" :value="dm.ma">{{dm.ten}}</option>
                    </select>
                </div>
                <div class="col-4 float-left mt-3">
                    <label>Dân tộc</label>
                    <select v-model="user.emrPerson.emrDmDanToc.ma" class="form-control">
                        <option v-for="dm in dmDanTocList" :key="dm.id" :value="dm.ma">{{dm.ten}}</option>
                    </select>
                </div>
                <div class="col-12 mt-3">
                    <label>Địa chỉ</label>
                    <input type="text" class="form-control" v-model="user.emrPerson.diachiChitiet" placeholder="123 KV, An Hưng">
                </div>
                <div class="col-4 float-left mt-3">
                    <label>Tỉnh</label>
                    <select
                            @change="updateDmQuanHuyen"
                            v-model="user.emrPerson.emrDmTinhThanh.ma"
                            class="form-control"
                    >
                        <option v-for="dm in dmTinhThanhList" :key="dm.id" :value="dm.ma">{{dm.ten}}</option>
                    </select>
                </div>
                <div class="col-4 float-left mt-3">
                    <label>Huyện</label>
                    <select
                            @change="updateDmXaPhuong"
                            v-model="user.emrPerson.emrDmQuanHuyen.ma"
                            class="form-control"
                    >
                        <option v-for="dm in dmQuanHuyenList" :key="dm.id" :value="dm.ma">{{dm.ten}}</option>
                    </select>
                </div>
                <div class="col-4 float-left mt-3">
                    <label>Xã</label>
                    <select v-model="user.emrPerson.emrDmPhuongXa.ma" class="form-control">
                        <option v-for="dm in dmPhuongXaList" :key="dm.id" :value="dm.ma">{{dm.ten}}</option>
                    </select>
                </div>
                <div class="col-12 mt-3">
                    <label>Nơi làm việc</label>
                    <input type="text" class="form-control" placeholder="Công ty CP VEIG"
                           v-model="user.emrPerson.noilamviec">
                </div>
                <div class="col mt-3">
                    <label>Vai trò người dùng</label><br>
                    <div v-for="role in roles" :key="role.id">
                        <input type="checkbox" :id="role.id" :value="role.id" v-model="user.roleIds" >
                        <label :for="role.id" class="mr-4">{{role.ten}}</label>
                    </div>
                </div>
                <input type="button" value="Lưu lại" v-on:click="createUser()" class="form-control mt-5"
                       style="background-color: #C4C4C4; width: 200px;">

            </form>
        </div>
    </div>
</template>

<script>
    export default {
        data: function () {
            return {
                user: {
                    roleIds: [],
                    emrPerson: {
                        tendaydu: "",
                        ngaysinh: "",
                        email: "",
                        dienthoai: "",
                        cmnd: "",
                        noilamviec: "",
                        diachiChitiet: "",
                        emrDmGioiTinh: {},
                        emrDmDanToc: {},
                        emrDmQuocGia: {},
                        emrDmNgheNghiep: {},
                        emrDmPhuongXa: {},
                        emrDmQuanHuyen: {},
                        emrDmTinhThanh: {},
                    },
                },
                errors: {
                    email: [],
                    tendaydu: []
                },

                dmNgheNghiepList: [],
                dmQuocGiaList: [],
                dmDanTocList: [],
                dmTinhThanhList: [],
                dmQuanHuyenList: [],
                dmPhuongXaList: [],
                roles: []
            };
        },


        props: ["userId"],

        methods: {
            updateDmQuanHuyen: async function () {
                this.dmQuanHuyenList = await this.get("/api/danhmuc/get_dm_list", {
                    dm_type: "DM_DVHC",
                    level: 2,
                    parentCode: this.user.emrPerson.emrDmTinhThanh.ma
                });
            },

            updateDmXaPhuong: async function () {
                this.dmPhuongXaList = await this.get("/api/danhmuc/get_dm_list", {
                    dm_type: "DM_DVHC",
                    level: 3,
                    parentCode: this.user.emrPerson.emrDmQuanHuyen.ma
                });
            },

            updateTenDm: function (dm, dmList) {
                var item = dmList.find(x => x.ma == dm.ma);
                if (item) dm.ten = item.ten;
            },

            createUser: async function () {
                this.updateTenDm(this.user.emrPerson.emrDmNgheNghiep, this.dmNgheNghiepList);
                this.updateTenDm(this.user.emrPerson.emrDmQuocGia, this.dmQuocGiaList);
                this.updateTenDm(this.user.emrPerson.emrDmDanToc, this.dmDanTocList);
                this.updateTenDm(this.user.emrPerson.emrDmTinhThanh, this.dmTinhThanhList);
                this.updateTenDm(this.user.emrPerson.emrDmQuanHuyen, this.dmQuanHuyenList);
                this.updateTenDm(this.user.emrPerson.emrDmPhuongXa, this.dmPhuongXaList);
                this.errors.email = [];
                this.errors.tendaydu = [];

                let result = await this.post("/api/user/create_user", this.user);
                console.log("result: " + JSON.stringify(result));
                if (result.success) {
                    alert("Thêm người dùng thành công");
                    sessionStorage.removeItem("dataChange");
                } else {
                    let errors = result.errors;
                    for(let i = 0; i < errors.length; i++){
                        let field = errors[i].field;
                        let message = errors[i].message;
                        this.errors[field].push(message);
                    }
                }
            }
        },

        created: async function () {
            this.roles = await  this.get("/api/user/getRolesByUsername", {
                username: localStorage.getItem("username")
            });
            this.dmNgheNghiepList = await this.get("/api/danhmuc/get_dm_list", {
                dm_type: "DM_NGHE_NGHIEP"
            });

            this.dmQuocGiaList = await this.get("/api/danhmuc/get_dm_list", {
                dm_type: "DM_QUOC_GIA"
            });

            this.dmDanTocList = await this.get("/api/danhmuc/get_dm_list", {
                dm_type: "DM_DAN_TOC"
            });

            this.dmTinhThanhList = await this.get("/api/danhmuc/get_dm_list", {
                dm_type: "DM_DVHC",
                level: 1
            });

            this.dmQuanHuyenList = await this.get("/api/danhmuc/get_dm_list", {
                dm_type: "DM_DVHC",
                level: 2,
                parentCode: this.user.emrPerson.emrDmTinhThanh.ma
            });

            this.dmPhuongXaList = await this.get("/api/danhmuc/get_dm_list", {
                dm_type: "DM_DVHC",
                level: 3,
                parentCode: this.user.emrPerson.emrDmQuanHuyen.ma
            });
        },
    };
</script>

<style scoped>
</style>
