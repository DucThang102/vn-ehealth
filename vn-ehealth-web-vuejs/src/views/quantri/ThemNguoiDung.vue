<template>
    <div>
        <div class="font-weight-bold text-uppercase">
            <i class="far fa-file-alt"></i> THÊM NGƯỜI DÙNG
        </div>
        <div class="ml-5 mr-5">
            <form action="">
                <div class="col-4 float-left">
                    <label>Họ và tên</label>
                    <input type="text" class="form-control" placeholder="Nguyen Van A">
                </div>
                <div class="col-4 float-left">
                    <label>Ngày sinh</label>
                    <input type="date" class="form-control">
                </div>
                <div class="col-4 float-left">
                    <label>Giới tính</label>
                    <select class="form-control">
                        <option>Nam</option>
                        <option>Nữ</option>
                        <option>Thứ 3</option>
                        <option>Thứ 4</option>
                    </select>
                </div>
                <div class="col-4 float-left mt-3">
                    <label>Email</label>
                    <input type="text" class="form-control" placeholder="nva@gmail.com">
                </div>
                <div class="col-4 float-left mt-3">
                    <label>Số điện thoại</label>
                    <input type="number" class="form-control" placeholder="0978544446">
                </div>
                <div class="col-4 float-left mt-3">
                    <label>CMNN/Hộ chiếu</label>
                    <input type="text" class="form-control" placeholder="121322231">
                </div>
              <div class="col-4 float-left mt-3">
                <label>Nghề nghiệp</label>
                <select class="form-control">
                  <option>Nông dân</option>
                  <option>Giáo viên</option>
                </select>
              </div>
              <div class="col-4 float-left mt-3">
                <label>Quốc tịch</label>
                <select class="form-control">
                  <option>Việt Nam</option>
                  <option>Mỹ</option>
                </select>
              </div>
              <div class="col-4 float-left mt-3">
                <label>Dân tộc</label>
                <select class="form-control">
                  <option>Kinh</option>
                  <option>Tày </option>
                  <option>Giao</option>
                </select>
              </div>
              <div class="col mt-3">
                <label>Địa chỉ</label>
                <input type="text" class="form-control" placeholder="123 KV, An Hưng">
              </div>
              <div class="col-4 float-left mt-3">
                <label>Tỉnh</label>
                <select class="form-control">
                  <option>Nam Định</option>
                  <option>Ba Vì</option>
                </select>
              </div>
              <div class="col-4 float-left mt-3">
                <label>Huyện</label>
                <select class="form-control">
                  <option>Xuân Trường</option>
                  <option>Giao Thủy</option>
                </select>
              </div>
              <div class="col-4 float-left mt-3">
                <label>Xã</label>
                <select class="form-control">
                  <option>Xuân Ninh</option>
                  <option>Xuân Vinh </option>
                </select>
              </div>
              <div class="col mt-3">
                <label>Nơi làm việc</label>
                <input type="text" class="form-control" placeholder="Công ty CP VEIG">
              </div>
              <div class="col mt-3">
                <label>Vai trò người dùng</label>
                <b-form-radio-group>
                  <b-form-radio>Bệnh nhân</b-form-radio>
                  <b-form-radio>Bác sỹ</b-form-radio>
                  <b-form-radio>Admin</b-form-radio>
                  <b-form-radio>Super admin</b-form-radio>
                </b-form-radio-group>
              </div>
              <input type="submit" value="Lưu lại" class="form-control mt-5" style="background-color: #C4C4C4; width: 200px;">
            </form>
        </div>
    </div>
</template>

<script>
</script>

<style scoped>
</style>
