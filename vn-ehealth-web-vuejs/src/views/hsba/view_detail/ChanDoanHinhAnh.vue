<template>
  <div v-if="cdhaList">
    <table class="table table-bordered">
      <tr>
        <th style="width: 5%;" class="text-center">STT</th>
        <th style="width: 15%;" class="text-center">Thao tác</th>
        <th style="width: 15%;" class="text-center">Loại chẩn đoán hình ảnh</th>
        <th style="width: 15%;" class="text-center">Chẩn đoán hình ảnh</th>
        <th style="width: 15%;" class="text-center">Ngày thực hiện</th>
        <th style="width: 20%;" class="text-center">Kết quả</th>
      </tr>
      <tr v-for="(cdha, i) in cdhaList" :key="cdha.id">
        <td class="text-center">{{ i + 1 }}</td>
        <td class="text-center">
          <a href="javascript:void(0)" v-on:click="viewCdha(cdha)">
            <i class="fas fa-fw fa-binoculars"></i> Xem
          </a>
        </td>
        <td class="text-center">{{ cdha.emrDmLoaiChanDoanHinhAnh.ten }}</td>
        <td class="text-center">{{ cdha.emrDmChanDoanHinhAnh.ten }}</td>
        <td class="text-center">{{ formatDateTime(cdha.ngaythuchien) }}</td>
        <td class="text-center">{{cdha.ketqua}}</td>
      </tr>
      <tr v-if="cdhaList.length==0">
        <td colspan="7">Không có dữ liệu</td>
      </tr>
    </table>

    <div class="modal fade" id="cdhaModal">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">
              <strong>Thông tin chẩn đoán hình ảnh</strong>
            </h5>
            <button type="button" class="close" data-dismiss="modal">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div v-if="cdha" class="modal-body">
            <TomTat :hsbaId="hsbaId" title="PHIẾU CHẨN ĐOÁN HÌNH ẢNH"></TomTat>
            <hr />
            <div>
              <meta charset="UTF-8" />
              <table border="1" cellpadding="10" class="table table-bordered">
                <font size="2.5">
                  <div class="row">
                    <div class="col-12">
                      <b>Chẩn đoán hình ảnh: {{ cdha.emrDmChanDoanHinhAnh.ten }}</b>
                      <br />
                      <span>- Loại chẩn đoán: {{ cdha.emrDmLoaiChanDoanHinhAnh.ten }}</span>
                      <br />
                      <span>- Ngày yêu cầu: {{ formatDateTime(cdha.ngayyeucau) }}</span>
                      <br />
                      <span>- Bác sĩ yêu cầu: {{ attr(cdha, 'bacsiyeucau.ten') }}</span>
                      <br />
                      <span>- Ngày thực hiện: {{ formatDateTime(cdha.ngaythuchien) }}</span>
                      <br />
                      <span>- Bác sĩ thực hiện: {{ attr(cdha, 'bacsichuyenkhoa.ten') }}</span>
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
                      <span>{{ cdha.ketqua }}</span>
                    </div>
                  </div>
                  <hr />

                  <div class="row">
                    <div class="col-12">
                      <b>
                        <span>Kết luận</span>
                        <br />
                      </b>
                      <span>{{ cdha.ketluan }}</span>
                    </div>
                  </div>
                  <hr />

                  <div class="row">
                    <div class="col-12">
                      <b>
                        <span>Lời dặn</span>
                        <br />
                      </b>
                      <span>{{ cdha.loidan }}</span>
                    </div>
                  </div>

                  <div v-if="cdha.emrFileDinhKemCdhas.length > 0">
                    <hr />
                    <b>Danh sách file đính kèm:</b>
                    <table class="table table-bordered mt-3">
                      <tr>
                        <th style="width:10%" class="text-center">STT</th>
                        <th style="width:90%" class="text-center">Tên file</th>
                      </tr>
                      <tr v-for="(file, i) in cdha.emrFileDinhKemCdhas" :key="file.id">
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
      cdhaList: null,
      cdha: null
    };
  },

  methods: {
    viewCdha: function(cdha) {
      this.cdha = cdha;
      $("#cdhaModal").modal();
    }
  },

  created: async function() {
    this.cdhaList = await this.get("/api/cdha/get_ds_cdha", {
      hsba_id: this.hsbaId
    });
  }
};
</script>