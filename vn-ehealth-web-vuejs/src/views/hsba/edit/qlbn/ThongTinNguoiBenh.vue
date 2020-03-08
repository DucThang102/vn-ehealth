<template>
  <div v-if="hsba" class="mt-3">
    <div class="row mt-3">
      <div class="col-12">
        <label class="label-title label-bold">I. VÀO VIỆN</label>
      </div>
    </div>
    <div class="row mt-2">
      <div class="col-4">
        <label class="label-title">Thời gian vào viện</label>
        <span style="color: red;">(*)</span>
        <date-picker
          v-model="hsba.emrQuanLyNguoiBenh.ngaygiovaovien"
          :config="{format: 'DD/MM/YYYY hh:mm', sideBySide:true}"
        ></date-picker>
      </div>
      <div class="col-4">
        <label class="label-title">Vào viện do bệnh này lần thứ</label>
        <input class="form-control" v-model="hsba.emrQuanLyNguoiBenh.vaovienlanthu" />
      </div>
      <div class="col-4">
        <label class="label-title">Nơi trực tiếp vào</label>
        <select class="form-control" v-model="hsba.emrQuanLyNguoiBenh.emrDmNoiTrucTiepVao.ma">
          <option value>------</option>
          <option
            v-for="emrDm in emrDmNoiTrucTiepVaos"
            :key="emrDm.id"
            :value="emrDm.ma"
          >{{emrDm.ten}}</option>
        </select>
      </div>
    </div>
    <div class="row mt-2">
      <div class="col-4">
        <label class="label-title">Nơi giới thiệu</label>
        <select class="form-control" v-model="hsba.emrQuanLyNguoiBenh.emrDmNoiGioiThieu.ma">
          <option value>------</option>
          <option
            v-for="emrDm in emrDmNoiGioiThieus"
            :key="emrDm.id"
            :value="emrDm.ma"
          >{{emrDm.ten}}</option>
        </select>
      </div>
      <div class="col-4">
        <label class="label-title">Đối tượng tài chính</label>
        <span style="color: red;">(*)</span>
        <select class="form-control" v-model="hsba.emrQuanLyNguoiBenh.emrDmLoaiDoiTuongTaiChinh.ma">
          <option value>------</option>
          <option
            v-for="emrDm in emrDmLoaiDoiTuongTaiChinhs"
            :key="emrDm.id"
            :value="emrDm.ma"
          >{{emrDm.ten}}</option>
        </select>
      </div>
      <div class="col-4">
        <label class="label-title">Loại vào viện</label>
        <span style="color: red;">(*)</span>
        <select class="form-control" v-model="hsba.emrQuanLyNguoiBenh.emrDmLoaiVaoVien.ma">
          <option value>------</option>
          <option v-for="emrDm in emrDmLoaiVaoViens" :key="emrDm.id" :value="emrDm.ma">{{emrDm.ten}}</option>
        </select>
      </div>
    </div>

    <div class="row mt-3">
      <div class="col-12">
        <label class="label-title label-bold">II. VÀO KHOA</label>
      </div>
    </div>

    <div class="mt-2" style="overflow-x:scroll;" v-if="emrVaoKhoas">
      <label class="label-title label-bold">Danh sách khoa điều trị</label>
      <a class="mt-1" v-on:click="addKhoaDieuTri()" href="javascript:void(0)" title="Thêm">
        <i class="fas fa-fw fa-lg fa-plus-circle"></i>
      </a>
      <table style="width:120% !important" class="table table-bordered">
        <tr>
          <th style="width: 6%;" class="text-center"></th>
          <th style="width: 15%;" class="text-center">Khoa điều trị</th>
          <th style="width: 15%;" class="text-center">Trưởng khoa</th>
          <th style="width: 15%;" class="text-center">Bác sỹ điều trị</th>
          <th style="width: 14%;" class="text-center">Ngày bắt đầu điều trị</th>
          <th style="width: 14%;" class="text-center">Ngày kết thúc điều trị</th>
          <th style="width: 10%;" class="text-center">Số phòng</th>
          <th style="width: 10%;" class="text-center">Số giường</th>
        </tr>
        <tr v-for="(khoa, i) in emrVaoKhoas" :key="khoa.id">
          <td class="text-center">
            <a href="javascript:void(0)" v-on:click="editKhoaDieuTri(khoa)">
              <i class="fas fa-fw fa-edit"></i>
            </a>
            <a v-on:click="deleteKhoaDieuTri(i)" href="javascript:void(0)">
              <i class="fas fa-fw fa-trash" style="color:red"></i>
            </a>
          </td>
          <td>{{ khoa.emrDmKhoaDieuTri.ten }}</td>
          <td>{{ khoa.tentruongkhoa }}</td>
          <td>{{ khoa.bacsidieutri }}</td>
          <td class="text-center">{{ khoa.ngaygiovaokhoa }}</td>
          <td class="text-center">{{ khoa.ngayketthucdieutri }}</td>
          <td class="text-center">{{ khoa.phong }}</td>
          <td class="text-center">{{ khoa.giuong }}</td>
        </tr>
        <tr v-if="emrVaoKhoas.length == 0">
          <td colspan="8">Không có khoa điều trị</td>
        </tr>
      </table>
    </div>

    <div class="row mt-4">
      <div class="col-12">
        <label class="label-title label-bold">III. RA VIỆN</label>
      </div>
    </div>

    <div class="row mt-2">
      <div class="col-4">
        <label class="label-title">Thời gian ra viện</label>
        <span style="color: red;">(*)</span>
        <date-picker
          v-model="hsba.emrQuanLyNguoiBenh.ngaygioravien"
          :config="{format: 'DD/MM/YYYY hh:mm', sideBySide:true}"
        ></date-picker>
      </div>
      <div class="col-4">
        <label class="label-title">Loại ra viện</label>
        <span style="color: red;">(*)</span>
        <select class="form-control" v-model="hsba.emrQuanLyNguoiBenh.emrDmLoaiRaVien.ma">
          <option>------</option>
          <option v-for="emrDm in emrDmLoaiRaViens" :key="emrDm.id" :value="emrDm.ma">{{emrDm.ten}}</option>
        </select>
      </div>
    </div>

    <div class="row mt-2">
      <div class="col-4">
        <label class="label-title">Bác sĩ cho ra viện</label>
        <input class="form-control" v-model="hsba.emrQuanLyNguoiBenh.tenbacsichoravien" />
      </div>
      <div class="col-4">
        <label class="label-title">Loại chuyển viện</label>
        <select class="form-control" v-model="hsba.emrQuanLyNguoiBenh.emrDmLoaiChuyenVien.ma">
          <option>------</option>
          <option
            v-for="emrDm in emrDmLoaiChuyenViens"
            :key="emrDm.id"
            :value="emrDm.ma"
          >{{emrDm.ten}}</option>
        </select>
      </div>
    </div>

    <div class="mt-3 mb-3">
      <button v-on:click="saveHsbaCustom()" class="btn btn-sm btn-primary">Lưu lại</button>
    </div>

    <div class="modal fade" id="vkModal" aria-hidden="true">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Thông tin vào khoa</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body" v-if="khoadieutri">
            <div class="row">
              <div class="col-6 form-group">
                <label class="label-title">Khoa điều trị</label>
                <span style="color: red;">(*)</span>
                <select class="form-control" v-model="khoadieutri.emrDmKhoaDieuTri.ma">
                  <option>------</option>
                  <option
                    v-for="emrDm in emrDmKhoaDieuTris"
                    :key="emrDm.id"
                    :value="emrDm.ma"
                  >{{emrDm.ten}}</option>
                </select>
              </div>
              <div class="col-6 form-group">
                <label class="label-title">Trưởng khoa</label>
                <input class="form-control" v-model="khoadieutri.tentruongkhoa" />
              </div>
            </div>
            <div class="row">
              <div class="col-12 form-group">
                <label class="label-title">Bác sỹ điều trị</label>
                <input class="form-control" v-model="khoadieutri.bacsidieutri" />
              </div>
            </div>
            <div class="row">
              <div class="col-6 form-group">
                <label class="label-title">Ngày bắt đầu điều trị</label>
                <span style="color: red;">(*)</span>
                <date-picker
                  v-model="khoadieutri.ngaygiovaokhoa"
                  :config="{format: 'DD/MM/YYYY HH:mm', sideBySide:true}"
                ></date-picker>
              </div>
              <div class="col-6 form-group">
                <label class="label-title">Ngày kết thúc điều trị</label>
                <span style="color: red;">(*)</span>
                <date-picker
                  v-model="khoadieutri.ngayketthucdieutri"
                  :config="{format: 'DD/MM/YYYY HH:mm', sideBySide:true}"
                ></date-picker>
              </div>
            </div>
            <div class="row">
              <div class="col-6 form-group">
                <label class="label-title">Số phòng</label>
                <input class="form-control" v-model="khoadieutri.phong" />
              </div>
              <div class="col-6 form-group">
                <label class="label-title">Số giường</label>
                <input class="form-control" v-model="khoadieutri.giuong" />
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button v-on:click="saveEmrVaoKhoa()" type="button" class="btn btn-primary">{{btnCloseAction}}</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import mixin from "./Mixin.vue";

export default {
  mixins: [mixin],

  data: function() {
    return {
      emrVaoKhoas: null,
      emrDmNoiTrucTiepVaos: null,
      emrDmNoiGioiThieus: null,
      emrDmLoaiDoiTuongTaiChinhs: null,
      emrDmLoaiVaoViens: null,
      emrDmLoaiRaViens: null,
      emrDmLoaiChuyenViens: null,
      emrDmKhoaDieuTris: null,
      khoadieutri: null,
      btnCloseAction: null
    };
  },

  created: async function() {
    this.emrVaoKhoas = await this.get("/api/vaokhoa/get_ds_vaokhoa", {
      hsba_id: this.hsbaId
    });
    this.emrDmNoiTrucTiepVaos = await this.get("/api/danhmuc/get_all_dm_list", {
      dm_type: "DM_NOI_TRUC_TIEP_VAO"
    });
    this.emrDmNoiGioiThieus = await this.get("/api/danhmuc/get_all_dm_list", {
      dm_type: "DM_NOI_GIOI_THIEU"
    });
    this.emrDmLoaiDoiTuongTaiChinhs = await this.get(
      "/api/danhmuc/get_all_dm_list",
      { dm_type: "DM_LOAI_DOI_TUONG_TAI_CHINH" }
    );
    this.emrDmLoaiVaoViens = await this.get("/api/danhmuc/get_all_dm_list", {
      dm_type: "DM_LOAI_VAO_VIEN"
    });
    this.emrDmLoaiRaViens = await this.get("/api/danhmuc/get_all_dm_list", {
      dm_type: "DM_LOAI_RA_VIEN"
    });
    this.emrDmLoaiChuyenViens = await this.get("/api/danhmuc/get_all_dm_list", {
      dm_type: "DM_LOAI_CHUYEN_VIEN"
    });
    this.emrDmKhoaDieuTris = await this.get("/api/danhmuc/get_all_dm_list", {
      dm_type: "DM_KHOA_DIEU_TRI"
    });
  },

  watch: {
    hsba: {
      handler: function(val, oldVal) {
        if (oldVal) {
          sessionStorage.setItem("dataChange", true);
        }
      },
      deep: true
    },

    khoadieutri: {
      handler: function(val) {
        this.updateEmrDmTen(val.emrDmKhoaDieuTri, this.emrDmKhoaDieuTris);
      },
      deep: true
    }
  },

  methods: {
    updateEmrDmTen: function(emrDm, list) {
      var item = list.find(x => x.ma == emrDm.ma);
      emrDm.ten = attr(item, "ten");
    },

    addKhoaDieuTri: function() {
      this.khoadieutri = {
        emrHoSoBenhAnId: this.hsba.id,
        emrDmKhoaDieuTri: { ma: "" }
      };
      this.btnCloseAction = "Thêm";
      $("#vkModal").modal();
    },

    editKhoaDieuTri: function(khoa) {
      this.khoadieutri = khoa;
      this.btnCloseAction = "Cập nhật";
      $("#vkModal").modal();
    },

    deleteKhoaDieuTri: function(index) {
      this.emrVaoKhoas.splice(index, 1);
    },

    saveEmrVaoKhoa: function() {
      if (this.khoadieutri.emrDmKhoaDieuTri.ma == "") {
        alert("Yêu cầu chọn khoa điều trị");
        return;
      }

      if (!this.khoadieutri.id) {
        this.emrVaoKhoas.push(this.khoadieutri);
      }
      $("#vkModal").modal("hide");
    },

    saveHsbaCustom: async function() {
      this.updateEmrDmTen(
        this.hsba.emrQuanLyNguoiBenh.emrDmNoiTrucTiepVao,
        this.emrDmNoiTrucTiepVaos
      );
      this.updateEmrDmTen(
        this.hsba.emrQuanLyNguoiBenh.emrDmNoiGioiThieu,
        this.emrDmNoiGioiThieus
      );
      this.updateEmrDmTen(
        this.hsba.emrQuanLyNguoiBenh.emrDmLoaiDoiTuongTaiChinh,
        this.emrDmLoaiDoiTuongTaiChinhs
      );
      this.updateEmrDmTen(
        this.hsba.emrQuanLyNguoiBenh.emrDmLoaiVaoVien,
        this.emrDmLoaiVaoViens
      );
      this.updateEmrDmTen(
        this.hsba.emrQuanLyNguoiBenh.emrDmLoaiRaVien,
        this.emrDmLoaiRaViens
      );
      this.updateEmrDmTen(
        this.hsba.emrQuanLyNguoiBenh.emrDmLoaiChuyenVien,
        this.emrDmLoaiChuyenViens
      );

      var result1 = await this.post("/api/hsba/update_hsba", this.hsba);
      var result2 = await this.post("/api/vaokhoa/save_ds_vaokhoa", {
        hsbaId: this.hsba.id,
        emrVaoKhoas: this.emrVaoKhoas
      });

      if (result1.success && result2.success) {
        alert("Cập nhật thông tin thành công");
        sessionStorage.removeItem("dataChange");
      } else {
        alert("Lỗi xảy ra quá trình lưu thông tin");
      }
    }
  }
};
</script>