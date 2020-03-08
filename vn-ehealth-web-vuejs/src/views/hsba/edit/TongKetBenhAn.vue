<template>
  <div v-if="hsba">
    <div class="row mt-3">
      <div class="col-12">
        <label class="label-title label-bold">
          <label>TỔNG KẾT BỆNH ÁN</label>
        </label>
      </div>
    </div>
    <div class="row mt-3">
      <div class="col-12">
        <label class="label-title">Diễn biến lâm sàng</label>
        <textarea rows="3" class="form-control" v-model="hsba.emrTongKetRaVien.dienbienlamsang"></textarea>
      </div>
    </div>
    <div class="row mt-3">
      <div class="col-12">
        <label class="label-title">Kết quả cận lâm sàng</label>
        <textarea rows="3" class="form-control" v-model="hsba.emrTongKetRaVien.canlamsang"></textarea>
      </div>
    </div>
    <div class="row mt-3">
      <div class="col-12">
        <label class="label-title">Phương pháp điều trị YHHĐ</label>
        <textarea rows="3" class="form-control" v-model="hsba.emrTongKetRaVien.phuongphapdieutri"></textarea>
      </div>
    </div>
    <div class="row mt-3">
      <div class="col-12">
        <label class="label-title">Tình trạng ra viện</label>
        <textarea rows="3" class="form-control" v-model="hsba.emrTongKetRaVien.tinhtrangnguoibenh"></textarea>
      </div>
    </div>
    <div class="row mt-3">
      <div class="col-12">
        <label class="label-title">Chỉ dẫn điều trị tiếp theo</label>
        <textarea rows="3" class="form-control" v-model="hsba.emrTongKetRaVien.chidandieutri"></textarea>
      </div>
    </div>
    <div class="row mt-3">
      <div class="col-6">
        <label class="label-title">Người giao hồ sơ</label>
        <input class="form-control" v-model="hsba.emrTongKetRaVien.nguoigiaohoso" />
      </div>

      <div class="col-6">
        <label class="label-title">Người nhận hồ sơ</label>
        <input class="form-control" v-model="hsba.emrTongKetRaVien.nguoinhanhoso" />
      </div>
    </div>
    <div class="row mt-3">
      <div class="col-6">
        <table>
          <tr>
            <th style="width: 80%;">Loại hồ sơ</th>
            <th style="width: 20%;">Số tờ</th>
          </tr>
          <tr>
            <td>- X-quang</td>
            <td>
              <input
                type="number"
                min="0"
                class="form-control"
                v-model="hsba.emrTongKetRaVien.soToXQuang"
              />
            </td>
          </tr>
          <tr>
            <td>- CT Scanner</td>
            <td>
              <input
                type="number"
                min="0"
                class="form-control"
                v-model="hsba.emrTongKetRaVien.soToCTScanner"
              />
            </td>
          </tr>
          <tr>
            <td>- Siêu âm</td>
            <td>
              <input
                type="number"
                min="0"
                class="form-control"
                v-model="hsba.emrTongKetRaVien.soToSieuAm"
              />
            </td>
          </tr>
          <tr>
            <td>- Xét nghiệm</td>
            <td>
              <input
                type="number"
                min="0"
                class="form-control"
                v-model="hsba.emrTongKetRaVien.soToXetNghiem"
              />
            </td>
          </tr>
          <tr>
            <td>- Khác</td>
            <td>
              <input
                type="number"
                min="0"
                class="form-control"
                v-model="hsba.emrTongKetRaVien.soToKhac"
              />
            </td>
          </tr>
        </table>
      </div>
    </div>

    <div class="mt-3 mb-3">
      <button v-on:click="saveHsba()" class="btn btn-sm btn-primary">Lưu lại</button>
    </div>
  </div>
</template>

<script>
export default {
  data: function() {
    return {
      hsba: null
    };
  },

  props: ["hsbaId"],

  methods: {
    saveHsba: async function() {
      var result = await this.post("/api/hsba/update_hsba", this.hsba);
      if (result.success) {
        alert("Cập nhật thông tin thành công");
        sessionStorage.removeItem("dataChange");
      } else {
        alert("Lỗi xảy ra quá trình lưu thông tin");
      }
    }
  },

  watch: {
    hsba: {
      handler: function(val, oldVal) {
        if (oldVal) {
          sessionStorage.setItem("dataChange", true);
        }
      },
      deep: true
    }
  },

  created: async function() {
    this.hsba = await this.get("/api/hsba/get_hsba_by_id", {
      hsba_id: this.hsbaId
    });
  }
};
</script>