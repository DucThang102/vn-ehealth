<template>
  <div>
    <div class="font-weight-bold text-uppercase">
      <i class="far fa-file-alt"></i> DANH SÁCH HỒ SƠ BỆNH ÁN CHƯA XỬ LÝ
    </div>
    <div id="content" class="bg-white mt-2">
      <form @submit.prevent="timKiemHoso()" class="mb-3">
        <div class="row">
          <div class="col-12">
            <input v-model="mayte" class="form-control" placeholder="Tìm theo mã y tế" />
          </div>
        </div>
      </form>
      <div v-if="loading" style="position: absolute;left: 50%;top:50%" class="spinner-border"></div>
      <div id="app" v-if="!loading">
        <div v-if="total == 0">
          <b>Không có dữ liệu</b>
        </div>

        <table style="width:150%" class="table table-bordered" v-if="total > 0">
          <thead>
            <tr>
              <th rowspan="2" style="width:2%">Thao tác</th>
              <th rowspan="2" style="width:2%">STT</th>
              <th rowspan="2" style="width:8%">Số bệnh án</th>
              <th rowspan="2" style="width:12%">Tên bệnh nhân</th>
              <th rowspan="2" style="width:10%">Địa chỉ</th>
              <th rowspan="2" style="width:7%">Loại bệnh án</th>
              <th rowspan="2" style="width:6%">Mã y tế</th>
              <th colspan="2">Chẩn đoán vào viện</th>
              <th colspan="2">Chẩn đoán ra viện</th>
              <th rowspan="2" style="width:8%">Khoa ra viện</th>
              <th rowspan="2" style="width:5%">Ngày vào viện</th>
              <th rowspan="2" style="width:5%">Ngày ra viện</th>
              <th rowspan="2" style="width:5%">Ngày tiếp nhận</th>
              <th rowspan="2" style="width:6%">Cán bộ tiếp nhận</th>
              <th rowspan="2" style="width:5%">Nguồn dữ liệu</th>
            </tr>
            <tr>
              <th style="width:5%">Mã Bệnh</th>
              <th style="width:5%">Mô tả</th>
              <th style="width:5%">Mã Bệnh</th>
              <th style="width:5%">Mô tả</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(hs, index) in items" v-bind:key="hs.id">
              <td class="text-center">
                <button
                  class="btn btn-small dropdown-toggle"
                  type="button"
                  data-toggle="dropdown"
                  aria-haspopup="true"
                  aria-expanded="false"
                >
                  <i class="fas fa-fw fa-tasks"></i>
                </button>
                <div class="dropdown-menu">
                  <router-link class="dropdown-item" :to="{name: 'xemToBenhAn', params: {hsbaId: hs.id}}">Xem HSBA</router-link>
                  <a class="dropdown-item">Xem lịch sử</a>
                  <router-link class="dropdown-item" 
                      :to="{name: 'chinhSuaTTHanhChinh', params: {hsbaId: hs.id}}">Chỉnh sửa</router-link>
                  <a
                    class="dropdown-item"
                    v-on:click="luuTruHoSo(hs.id)"
                    href="javascript:void(0)"
                  >Lưu trữ</a>
                  <a
                    class="dropdown-item"
                    v-on:click="xemHoSoGoc(hs.id)"
                    href="javascript:void(0)"
                  >Thông tin gốc</a>
                  <a class="dropdown-item">Xuất ra CDA</a>
                  <a class="dropdown-item" v-on:click="xoaHoSo(hs.id)" href="javascript:void(0)">Xóa</a>
                </div>
              </td>
              <td class="text-center">{{ index + offset + 1 }}</td>
              <td class="text-center">{{ attr(hs, 'matraodoi') }}</td>
              <td class="text-center">{{ attr(hs, 'emrBenhNhan.tendaydu') }}</td>
              <td>{{ attr(hs, 'emrBenhNhan.diachi') }}</td>
              <td class="text-center">{{ attr(hs, 'emrDmLoaiBenhAn.ten') }}</td>
              <td class="text-center">{{ hs.mayte }}</td>
              <td class="text-center">{{ getTextChanDoan(hs.emrChanDoan.emrDmMaBenhChandoankkb) }}</td>
              <td>{{ hs.emrChanDoan.motachandoankkb }}</td>
              <td
                class="text-center"
              >{{ getTextChanDoan(hs.emrChanDoan.emrDmMaBenhChandoanravienchinh) }}</td>
              <td>{{ attr(hs, 'emrChanDoan.motachandoanravienchinh') }}</td>
              <td class="text-center">{{ hs.khoaRaVien }}</td>
              <td class="text-center">{{ attr(hs, 'emrQuanLyNguoiBenh.ngaygiovaovien') }}</td>
              <td class="text-center">{{ attr(hs, 'emrQuanLyNguoiBenh.ngaygioravien') }}</td>
              <td class="text-center">{{ hs.ngaytiepnhan }}</td>
              <td class="text-center">{{ hs.nguoitiepnhan }}</td>
              <td class="text-center">{{ hs.nguonDuLieu }}</td>
            </tr>
          </tbody>
        </table>
      </div>

      <b-pagination
        v-if="total > perPage"
        size="sm"
        v-model="page"
        :total-rows="total"
        :per-page="perPage"
      ></b-pagination>
      <label class="label">Tổng số : {{ total }} bản ghi</label>

      <div class="modal fade" id="hsGocModal" aria-hidden="true">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title">Thông tin hồ sơ gốc</h5>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div v-if="hsGocJson" class="modal-body">
              <textarea readonly rows="15" class="form-control" v-model="hsGocJson"></textarea>
            </div>
            <div class="modal-footer">
              <button data-dismiss="modal" type="button" class="btn btn-primary">Đóng lại</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      perPage: 10,
      page: null,
      items: [],
      total: null,
      mayte: "",
      loading: false,
      hsGocJson: null,
      hsba_id: null
    };
  },

  methods: {
    getTotal: async function() {
      this.total = await this.get("/api/hsba/count_ds_hs", {
        trangthai: 0,
        mayte: this.mayte
      });
    },

    getTextChanDoan: function(chandoan) {
      if (chandoan && chandoan.ma != "") {
        return chandoan.ma + " - " + chandoan.ten;
      }
      return "";
    },

    getNguonDuLieuStr: function(nguonDuLieu) {
      var m = { 1: "Từ HIS" };
      return m[nguonDuLieu];
    },

    getItems: async function() {
      this.items = await this.get("/api/hsba/get_ds_hs", {
        trangthai: 0,
        start: this.offset,
        count: this.perPage,
        mayte: this.mayte
      });

      this.items.forEach(x => {
        x.nguonDuLieu = this.getNguonDuLieuStr(x.nguonDuLieu);
      });
    },

    timKiemHoso: async function() {
      this.page = 1;
      this.reload();
    },

    xemHoSoGoc: async function(id) {
      var result = await this.get("/api/hsba/get_hs_goc", { hsba_id: id });
      this.hsGocJson = result["hsGoc"];
      $("#hsGocModal").modal();
    },

    luuTruHoSo: async function(id) {
      if (!confirm("Bạn có muốn lưu trữ hồ sơ này")) {
        return;
      }

      var result = await this.get("/api/hsba/archive_hsba", { hsba_id: id });
      if (result.success) {
        this.reload();
      } else {
        alert("Lưu trữ hồ sơ không thành công");
      }
    },

    xoaHoSo: async function(id) {
      if (!confirm("Bạn có muốn xóa hồ sơ này")) {
        return;
      }

      var result = await this.get("/api/hsba/delete_hsba", { hsba_id: id });
      if (result.success) {
        this.reload();
      } else {
        alert("Xóa hồ sơ không thành công");
      }
    },

    reload: function() {
      var url = "/?" + this.serialize({page: this.page, mayte: this.mayte});
      location.href = url;
    }
  },

  computed: {
    offset() {
      return (this.page - 1) * this.perPage;
    }    
  },

  watch: {
    page: function(_, oldVal) {
      if (oldVal) {        
        this.reload();
      }
    }
  },

  created: async function() {
    this.page = this.$route.query.page || 1;
    this.mayte = this.$route.query.mayte || "";
    this.loading = true;      
    await this.getTotal();
    await this.getItems();
    this.loading = false;
  }
};
</script>

<style scoped>
#app {
  overflow: scroll;
  width: 100%;
}
</style>
