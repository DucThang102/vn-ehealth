<template>
  <div v-if="tdcnList">
    <table class="table table-bordered">
      <tr>
        <th style="width: 5%;" class="text-center">STT</th>
        <th style="width: 15%;" class="text-center">Thao tác</th>
        <th style="width: 30%;" class="text-center">Thăm dò chức năng</th>
        <th style="width: 30%;" class="text-center">Loại thăm dò chức năng</th>
        <th style="width: 20%;" class="text-center">Ngày thực hiện</th>
      </tr>
      <tr v-for="(tdcn, i) in tdcnList" :key="tdcn.id">
        <td class="text-center">{{ i + 1 }}</td>
        <td class="text-center">
          <a href="#" v-on:click="viewTdcn(tdcn)">
            <i class="fas fa-fw fa-binoculars"></i>
            Xem
          </a>
        </td>
        <td class="text-center">{{ tdcn.emrDmThamDoChucNang.ten }}</td>
        <td class="text-center">{{ tdcn.emrDmLoaiThamDoChucNang.ten }}</td>
        <td class="text-center">{{ formatDateTime(tdcn.ngaythuchien) }}</td>
      </tr>
      <tr v-if="tdcnList.length==0">
        <td colspan="5">Không có dữ liệu</td>
      </tr>
    </table>

    <div class="modal fade" id="tdcnModal">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">
              <strong>Thông tin thăm dò chức năng</strong>
            </h5>
            <button type="button" class="close" data-dismiss="modal">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>

          <div v-if="tdcn" class="modal-body">
            <TomTat :hsbaId="hsbaId" title="PHIẾU THĂM DÒ CHỨC NĂNG"></TomTat>
            <hr />
            <table border="1" cellpadding="10" class="table table-bordered">
              <div class="row">
                <div class="col-12">
                  <b>Thăm dò chức năng: {{ tdcn.emrDmThamDoChucNang.ten }}</b>
                  <br />
                  <span>- Loại thăm dò chức năng: {{ tdcn.emrDmLoaiThamDoChucNang.ten }}</span>
                  <br />
                  <span>- Ngày yêu cầu: {{ formatDateTime(tdcn.ngayyeucau) }}</span>
                  <br />
                  <span>- Bác sỹ yêu cầu: {{ attr(tdcn, 'bacsiyeucau.ten') }}</span>
                  <br />
                  <span>- Ngày thực hiện: {{ formatDateTime(tdcn.ngaythuchien) }}</span>
                  <br />
                  <span>- Bác sĩ thực hiện: {{ attr(tdcn, 'bacsichuyenkhoa.ten') }}</span>
                  <br />
                </div>
              </div>
              <hr />

              <div class="row">
                <div class="col-12">
                  <b>
                    <span>Kết quả</span>
                    <br />
                  </b>
                  <span>{{ tdcn.ketqua }}</span>
                </div>
              </div>
              <hr />

              <div class="row">
                <div class="col-12">
                  <b>
                    <span>Kết luận</span>
                    <br />
                  </b>
                  <span>{{ tdcn.ketluan }}</span>
                </div>
              </div>
              <hr />

              <div class="row">
                <div class="col-12">
                  <b>
                    <span>Lời dặn</span>
                    <br />
                  </b>
                  <span>{{ tdcn.loidan }}</span>
                </div>
              </div>

              <div v-if="tdcn.emrFileDinhKemTdcns.length > 0">
                <hr />
                <b>Danh sách file đính kèm:</b>
                <table class="table table-bordered mt-3">
                  <tr>
                    <th style="width:10%" class="text-center">STT</th>
                    <th style="width:90%" class="text-center">Tên file</th>
                  </tr>
                  <tr v-for="(file, i) in tdcn.emrFileDinhKemTdcns" :key="file.id">
                    <td>{{ i + 1 }}</td>
                    <td>
                      <a :href="file.url">{{ file.ten }}</a>
                    </td>
                  </tr>
                </table>
              </div>
            </table>
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
      tdcnList: null,
      tdcn: null
    };
  },

  methods: {
    viewTdcn: function(tdcn) {
      this.tdcn = tdcn;
      $("#tdcnModal").modal();
    }
  },

  created: async function() {
    this.tdcnList = await this.get("/api/tdcn/get_ds_tdcn", {
      hsba_id: this.hsbaId
    });
  }
};
</script>