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
            <select class="form-control float-left" style="width: 180px">
                <option value="Lọc theo vai trò">Lọc theo vai trò</option>
                <option value="Lọc theo vai trò">Lọc theo vai trò</option>
                <option value="Lọc theo vai trò">Lọc theo vai trò</option>
            </select>
            <button class="float-left ml-3 form-control" style="width: 150px; background-color: #C4C4C4">Tìm kiếm
            </button>
        </div>
        <div class="pr-4 pt-4">
            <table class="table table-bordered table-width-100 mt-4">
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
                <tr>
                    <td align="center">1</td>
                    <td>Nguyen Van A</td>
                    <td>ndff@gmail.com</td>
                    <td>0908664434</td>
                    <td>Bệnh nhân</td>
                    <td>Đã khóa</td>
                    <td align="center">
                        <a class="mr-2" href="#" title="Xem">
                            <i class="fa fa-eye"></i>
                        </a>
                        <a class="mr-2" href="#" title="Mở khóa">
                            <i class="fa fa-user"></i>
                        </a>
                    </td>
                </tr>
                <tr>
                    <td align="center">2</td>
                    <td>Tran Van B</td>
                    <td>trb@gmail.com</td>
                    <td>0907867533</td>
                    <td>Bác sỹ</td>
                    <td>Đang hoạt động</td>
                    <td align="center">
                        <a class="mr-2" href="#" title="Xem">
                            <i class="fa fa-eye"></i>
                        </a>
                        <a class="mr-2" href="#" title="Khóa">
                            <i class="fa fa-user-lock"></i>
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
</template>

<script>
</script>

<style scoped>

</style>
