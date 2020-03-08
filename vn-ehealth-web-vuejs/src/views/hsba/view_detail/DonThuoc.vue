<template>
  <div v-if="donthuocList">
    <table class="table table-bordered">
      <tr>
        <th style="width: 5%;" class="text-center">STT</th>
        <th style="width: 15%;" class="text-center">Thao tác</th>
        <th style="width: 30%;" class="text-center">Số đơn thuốc</th>
        <th style="width: 30%;" class="text-center">Bác sĩ kê đơn</th>
        <th style="width: 20%;" class="text-center">Ngày kê đơn</th>
      </tr>
      <tr v-for="(donthuoc, i) in donthuocList" :key="donthuoc.id">
        <td class="text-center">{{ i + 1 }}</td>
        <td class="text-center">
          <a href="#" v-on:click="viewDonthuoc(donthuoc)">
            <i class="fas fa-fw fa-binoculars"></i> Xem
          </a>
        </td>
        <td class="text-center">{{ donthuoc.sodon }}</td>
        <td class="text-center">{{ attr(donthuoc, 'bacsikedon.ten') }}</td>
        <td class="text-center">{{ formatDate(donthuoc.ngaykedon) }}</td>
      </tr>

      <tr v-if="donthuocList.length==0">
        <td colspan="5">Không có dữ liệu</td>
      </tr>
    </table>

    <div class="modal fade" id="donthuocModal">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">
              <strong>Thông tin hình ảnh tổn thương</strong>
            </h5>
            <button type="button" class="close" data-dismiss="modal">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div v-if="donthuoc" class="modal-body">
            <TomTat :hsbaId="hsbaId" title="PHIẾU HÌNH ẢNH TỔN THƯƠNG"></TomTat>
            <hr />
            <div>
              <meta charset="UTF-8" />
              <table border="1" cellpadding="10" class="table table-bordered">
                <font size="2.5">
                  <div class="row">
                    <div class="col-12">
                      <b>Đơn thuốc số: {{ donthuoc.sodon }}</b>
                      <br />
                      <span>- Ngày kê đơn: {{ formatDate(donthuoc.ngaykedon) }}</span>
                      <br />
                      <span>- Bác sỹ kê đơn: {{ attr(donthuoc, 'bacsikedon.ten') }}</span>
                      <br />
                    </div>
                  </div>
                  <table class="table table-bordered mt-3">
                    <tr>
                      <th style="width:4%" class="text-center">STT</th>
                      <th style="width:12%" class="text-center">Biệt dược</th>
                      <th style="width:12%" class="text-center">Tên thuốc</th>
                      <th style="width:12%" class="text-center">Ngày bắt đầu</th>
                      <th style="width:12%" class="text-center">Ngày kết thúc</th>
                      <th style="width:12%" class="text-center">Đường dùng</th>
                      <th style="width:12%" class="text-center">Liều lượng</th>
                      <th style="width:12%" class="text-center">Tần suất</th>
                      <th style="width:12%" class="text-center">Chỉ dẫn</th>
                    </tr>
                    <tr v-for="(dtct, i) in donthuoc.emrDonThuocChiTiets" :key="dtct.id">
                      <td>{{ i + 1 }}</td>
                      <td>{{ dtct.bietduoc }}</td>
                      <td>{{ attr(dtct, 'emrDmThuoc.ten') }}</td>
                      <td>{{ formatDate(dtct.ngaybatdau) }}</td>
                      <td>{{ formatDate(dtct.ngayketthuc) }}</td>
                      <td>{{ attr(dtct, 'emrDmDuongDungThuoc.ten') }}</td>
                      <td>{{ dtct.lieuluongdung }}</td>
                      <td>{{ attr(dtct, 'emrDmTanXuatDungThuoc.ten') }}</td>
                      <td>
                        {{ attr(dtct, 'emrDmChiDanDungThuoc.ten') ||
                        dtct.chidandungthuoc }}
                      </td>
                    </tr>
                  </table>

                  <div v-if="donthuoc.emrFileDinhKemDonThuocs.length > 0">
                    <hr />
                    <b>Danh sách file đính kèm:</b>
                    <table class="table table-bordered mt-3">
                      <tr>
                        <th style="width:10%" class="text-center">STT</th>
                        <th style="width:90%" class="text-center">Tên file</th>
                      </tr>
                      <tr v-for="(file, i) in donthuoc.emrFileDinhKemDonThuocs" :key="file.id">
                        <td>{{ i + 1 }}</td>
                        <td>
                          <a :href="file.url">{{ file.ten }}</a>
                        </td>
                      </tr>
                    </table>
                  </div>
                </font>
              </table>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng lại</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import TomTat from "@/components/hsba/view_detail/TomTat.vue";
export default {
  components: {
    TomTat
  },

  props: ["hsbaId"],

  data() {
    return {
      donthuocList: null,
      donthuoc: null
    };
  },

  methods: {
    viewDonthuoc: function(donthuoc) {
      this.donthuoc = donthuoc;
      $("#donthuocModal").modal();
    }
  },

  created: async function() {
    this.donthuocList = await this.get("/api/donthuoc/get_ds_donthuoc", {
      hsba_id: this.hsbaId
    });
  }
};
</script>