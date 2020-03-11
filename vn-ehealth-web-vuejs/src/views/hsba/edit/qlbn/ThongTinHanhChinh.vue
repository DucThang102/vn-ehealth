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
        <input class="form-control" v-model="hsba.bacsylambenhan.ten" />
      </div>
      <div class="col-4">
        <label class="label-title">Ngày làm bệnh án</label>
        <span style="color:red">(*)</span>
        <date-picker v-model="hsba.ngaykybenhan" :config="{format: 'YYYY-DD-MM HH:mm:ss'}" />
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
        <input class="form-control" v-model="hsba.emrBenhNhan.tendaydu" />
      </div>
      <div class="col-4">
        <label class="label-title">Sinh ngày</label>
        <date-picker v-model="hsba.emrBenhNhan.ngaysinh" :config="{format: 'YYYY-DD-MM HH:mm:ss'}" />
      </div>
      <div class="col-4">
        <label class="label-title">Giới tính</label>
        <select v-model="hsba.emrBenhNhan.emrDmGioiTinh.ma" class="form-control">
          <option value="M">Nam</option>
          <option value="F">Nữ</option>
          <option value="U">Không xác định</option>
        </select>
      </div>
    </div>
    <div class="row mt-2">
      <div class="col-4">
        <label class="label-title">Mã bệnh nhân</label>
        <input class="form-control" v-model="hsba.emrBenhNhan.iddinhdanhchinh" />
      </div>
      <div class="col-4">
        <label class="label-title">CMND/Hộ chiếu</label>
        <input class="form-control" v-model="hsba.emrBenhNhan.iddinhdanhphu" />
      </div>
      <div class="col-4">
        <label class="label-title">Nghề nghiệp</label>
        <select v-model="hsba.emrBenhNhan.emrDmNgheNghiep.ma" class="form-control">
          <option v-for="dm in dmNgheNghiepList" :key="dm.id" :value="dm.ma">{{dm.ten}}</option>
        </select>
      </div>
    </div>
    <div class="row mt-2">
      <div class="col-4">
        <label class="label-title">Quốc tịch</label>
        <select v-model="hsba.emrBenhNhan.emrDmQuocGia.ma" class="form-control">
          <option v-for="dm in dmQuocGiaList" :key="dm.id" :value="dm.ma">{{dm.ten}}</option>
        </select>
      </div>
      <div class="col-4">
        <label class="label-title">Dân tộc</label>
        <select v-model="hsba.emrBenhNhan.emrDmDanToc.ma" class="form-control">
          <option v-for="dm in dmDanTocList" :key="dm.id" :value="dm.ma">{{dm.ten}}</option>
        </select>
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
        <select
          @change="updateDmQuanHuyen"
          v-model="hsba.emrBenhNhan.emrDmTinhThanh.ma"
          class="form-control"
        >
          <option v-for="dm in dmTinhThanhList" :key="dm.id" :value="dm.ma">{{dm.ten}}</option>
        </select>
      </div>
      <div class="col-4">
        <label class="label-title">Quận/huyện</label>
        <select
          @change="updateDmXaPhuong"
          v-model="hsba.emrBenhNhan.emrDmQuanHuyen.ma"
          class="form-control"
        >
          <option v-for="dm in dmQuanHuyenList" :key="dm.id" :value="dm.ma">{{dm.ten}}</option>
        </select>
      </div>
      <div class="col-4">
        <label class="label-title">Phường/xã</label>
        <select v-model="hsba.emrBenhNhan.emrDmPhuongXa.ma" class="form-control">
          <option v-for="dm in dmPhuongXaList" :key="dm.id" :value="dm.ma">{{dm.ten}}</option>
        </select>
      </div>
    </div>
    <div class="row mt-2">
      <div class="col-12">
        <label class="label-title">Địa chỉ chi tiết</label>
        <input class="form-control" v-model="hsba.emrBenhNhan.diachi" />
      </div>
    </div>
    <div class="row mt-2">
      <div class="col-12">
        <label class="label-title">Nơi làm việc</label>
        <input class="form-control" v-model="hsba.emrBenhNhan.noilamviec" />
      </div>
    </div>
    <div class="row mt-2">
      <div class="col-4">
        <label class="label-title">Số thẻ BHYT</label>
        <input class="form-control" v-model="hsba.emrBenhNhan.sobhyt" />
      </div>
      <div class="col-4">
        <label class="label-title">BHYT giá trị đến</label>
        <date-picker v-model="hsba.emrBenhNhan.ngayhethanthebhyt" :config="{format: 'YYYY-DD-MM HH:mm:ss'}" />
      </div>
    </div>
    <div class="row mt-2">
      <div class="col-4">
        <label class="label-title">Họ tên người liên hệ</label>
        <input class="form-control" v-model="hsba.emrBenhNhan.tennguoibaotin" />
      </div>
      <div class="col-4">
        <label class="label-title">Số điện thoại liên hệ</label>
        <input class="form-control" v-model="hsba.emrBenhNhan.sodienthoainguoibaotin" />
      </div>
    </div>
    <div class="row mt-2">
      <div class="col-12">
        <label class="label-title">Thông tin người cần liên hệ</label>
        <textarea
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
export default {
  data: function() {
    return {
      hsba: null,
      dmNgheNghiepList: [],
      dmQuocGiaList: [],
      dmDanTocList: [],
      dmTinhThanhList: [],
      dmQuanHuyenList: [],
      dmPhuongXaList: []
    };
  },

  props: ["hsbaId"],

  methods: {
    updateDmQuanHuyen: async function() {
      this.dmQuanHuyenList = await this.get("/api/danhmuc/get_dm_list", {
        dm_type: "DM_DVHC",
        level: 2,
        parentCode: this.hsba.emrBenhNhan.emrDmTinhThanh.ma
      });
    },

    updateDmXaPhuong: async function() {
      this.dmPhuongXaList = await this.get("/api/danhmuc/get_dm_list", {
        dm_type: "DM_DVHC",
        level: 3,
        parentCode: this.hsba.emrBenhNhan.emrDmQuanHuyen.ma
      });
    },

    updateTenDm: function(dm, dmList) {
      var item = dmList.find(x => x.ma == dm.ma);
      if(item) dm.ten = item.ten;
    },

    saveHsba: async function() {
      this.updateTenDm(this.hsba.emrBenhNhan.emrDmNgheNghiep, this.dmNgheNghiepList);
      this.updateTenDm(this.hsba.emrBenhNhan.emrDmQuocGia, this.dmQuocGiaList);
      this.updateTenDm(this.hsba.emrBenhNhan.emrDmDanToc, this.dmDanTocList);
      this.updateTenDm(this.hsba.emrBenhNhan.emrDmTinhThanh, this.dmTinhThanhList);
      this.updateTenDm(this.hsba.emrBenhNhan.emrDmQuanHuyen, this.dmQuanHuyenList);
      this.updateTenDm(this.hsba.emrBenhNhan.emrDmPhuongXa, this.dmPhuongXaList);
      console.log(this.hsba);

      var result = await this.post("/api/hsba/update_hsba", this.hsba);
      if (result.success) {
        alert("Cập nhật thông tin thành công");
        sessionStorage.removeItem("dataChange");
      } else {
        alert("Lỗi xảy ra quá trình lưu thông tin");
      }
    }
  },

  created: async function() {
    this.hsba = await this.get("/api/hsba/get_hsba_by_id", {
      hsba_id: this.hsbaId
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
      parentCode: this.hsba.emrBenhNhan.emrDmTinhThanh.ma
    });

    this.dmPhuongXaList = await this.get("/api/danhmuc/get_dm_list", {
      dm_type: "DM_DVHC",
      level: 3,
      parentCode: this.hsba.emrBenhNhan.emrDmQuanHuyen.ma
    });
  }
};
</script>