<template>
  <div class="mt-3" v-if="hsba">
    <div class="row">
      <div class="col-12">
        <label class="label-title label-bold">I. THÔNG TIN HỒ SƠ:</label>
      </div>
    </div>
    <div class="row mt-2">
      <div class="col-4">
        <label class="label-title">Mã Y tế</label>
        <span style="color:red">(*)</span>
        <input class="form-control" v-model="hsba.mayte" />
      </div>
      <div class="col-4">
        <label class="label-title">Bác sĩ làm bệnh án</label>
        <span style="color:red">(*)</span>
        <input class="form-control" v-model="hsba.emrBenhAn.bacsylambenhan" />
      </div>
      <div class="col-4">
        <label class="label-title">Ngày làm bệnh án</label>
        <span style="color:red">(*)</span>
        <date-picker v-model="hsba.emrBenhAn.ngaykybenhan" :config="{format: 'DD/MM/YYYY HH:mm'}"></date-picker>
      </div>
    </div>
    <div class="row mt-3">
      <div class="col-12">
        <label class="label-title label-bold">I. HÀNH CHÍNH:</label>
      </div>
    </div>
    <div class="row mt-2">
      <div class="col-4">
        <label class="label-title">Họ và tên</label>
        <input readonly class="form-control" :value="hsba.emrBenhNhan.tendaydu" />
      </div>
      <div class="col-4">
        <label class="label-title">Sinh ngày</label>
        <input readonly class="form-control" :value="hsba.emrBenhNhan.ngaysinh" />
      </div>
      <div class="col-4">
        <label class="label-title">Giới tính</label>
        <input readonly class="form-control" :value="hsba.emrBenhNhan.emrDmGioiTinh.ten" />
      </div>
    </div>
    <div class="row mt-2">
      <div class="col-4">
        <label class="label-title">Mã bệnh nhân</label>
        <input readonly class="form-control" :value="hsba.emrBenhNhan.iddinhdanhchinh" />
      </div>
      <div class="col-4">
        <label class="label-title">CMND/Hộ chiếu</label>
        <input readonly class="form-control" :value="hsba.emrBenhNhan.iddinhdanhphu" />
      </div>
      <div class="col-4">
        <label class="label-title">Nghề nghiệp</label>
        <input readonly class="form-control" :value="hsba.emrBenhNhan.emrDmNgheNghiep.ten" />
      </div>
    </div>
    <div class="row mt-2">
      <div class="col-4">
        <label class="label-title">Quốc tịch</label>
        <input readonly class="form-control" :value="hsba.emrBenhNhan.emrDmQuocGia.ten" />
      </div>
      <div class="col-4">
        <label class="label-title">Dân tộc</label>
        <input readonly class="form-control" :value="hsba.emrBenhNhan.emrDmDanToc.ten" />
      </div>
    </div>
    <div class="row mt-2">
      <div class="col-12">
        <label class="label-title">Địa chỉ</label>
      </div>
    </div>
    <div class="row mt-2">
      <div class="col-4">
        <label class="label-title">Tỉnh/thành</label>
        <input
          readonly
          class="form-control"
          :value="hsba.emrBenhNhan.emrDmTinhThanh.ten"
        />
      </div>
      <div class="col-4">
        <label class="label-title">Quận/huyện</label>
        <input
          readonly
          class="form-control"
          :value="hsba.emrBenhNhan.emrDmQuanHuyen.ten"
        />
      </div>
      <div class="col-4">
        <label class="label-title">Phường/xã</label>
        <input
          readonly
          class="form-control"
          :value="hsba.emrBenhNhan.emrDmPhuongXa.ten"
        />
      </div>
    </div>
    <div class="row mt-2">
      <div class="col-12">
        <label class="label-title">Địa chỉ chi tiết</label>
        <input readonly class="form-control" :value="hsba.emrBenhNhan.diachi" />
      </div>
    </div>
    <div class="row mt-2">
      <div class="col-12">
        <label class="label-title">Nơi làm việc</label>
        <input readonly class="form-control" :value="hsba.emrBenhNhan.noilamviec" />
      </div>
    </div>
    <div class="row mt-2">
      <div class="col-4">
        <label class="label-title">Số thẻ BHYT</label>
        <input readonly class="form-control" :value="hsba.emrBenhNhan.sobhyt" />
      </div>
      <div class="col-4">
        <label class="label-title">BHYT giá trị đến</label>
        <input readonly class="form-control" :value="hsba.emrBenhNhan.ngayhethanthebhyt" />
      </div>
    </div>
    <div class="row mt-2">
      <div class="col-4">
        <label class="label-title">Họ tên người liên hệ</label>
        <input readonly class="form-control" :value="hsba.emrBenhNhan.tennguoibaotin" />
      </div>
      <div class="col-4">
        <label class="label-title">Số điện thoại liên hệ</label>
        <input readonly class="form-control" :value="hsba.emrBenhNhan.sodienthoainguoibaotin" />
      </div>
    </div>
    <div class="row mt-2">
      <div class="col-12">
        <label class="label-title">Thông tin người cần liên hệ</label>
        <textarea
          readonly
          rows="3"
          class="form-control"
          :value="hsba.emrBenhNhan.diachinguoibaotin"
        ></textarea>
      </div>
    </div>

    <div class="mt-3 mb-3">
      <button class="btn btn-sm btn-primary" v-on:click="saveHsba()">Lưu lại</button>
    </div>
  </div>
</template>

<script>
import mixin from "./Mixin.vue";

export default {
  mixins: [mixin],
};
</script>