<template>
  <div v-if="hattList">
    <table class="table table-bordered">
      <tr>
        <th style="width: 5%;" class="text-center">STT</th>
        <th style="width: 15%;" class="text-center">Thao tác</th>
        <th style="width: 30%;" class="text-center">Ảnh tổn thương</th>
        <th style="width: 50%;" class="text-center">Mô tả tổn thương</th>
      </tr>
      <tr v-for="(hatt, i) in hattList" :key="hatt.id">
        <td class="text-center">{{ i + 1 }}</td>
        <td class="text-center">
          <a href="javascript:void(0)" v-on:click="viewHatt(hatt)">
            <i class="fas fa-fw fa-binoculars"></i> Xem
          </a>
        </td>
        <td class="text-center">{{ hatt.anhtonthuong }}</td>
        <td class="text-center">{{ hatt.motatonthuong }}</td>
      </tr>
      <tr v-if="hattList.length==0">
        <td colspan="4">Không có dữ liệu</td>
      </tr>
    </table>

    <div class="modal fade" id="hattModal">
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
          <div v-if="hatt" class="modal-body">
            <TomTat :hsbaId="hsbaId" title="PHIẾU HÌNH ẢNH TỔN THƯƠNG"></TomTat>
            <hr />
            <div>
              <meta charset="UTF-8" />
              <table border="1" cellpadding="10" class="table table-bordered">
                <div class="col-12">
                  <b>
                    <font size="3">Hình ảnh tổn thương: {{ hatt.anhtonthuong }}</font>
                  </b>
                  <br />
                  <span>
                    <font size="2.5">- Mô tả: {{ hatt.motatonthuong }}</font>
                  </span>
                  <br />

                  <div v-if="hatt.emrFileDinhKemHatts.length > 0">
                    <hr />
                    <b>Danh sách file đính kèm:</b>
                    <table class="table table-bordered mt-3">
                      <tr>
                        <th style="width:10%" class="text-center">STT</th>
                        <th style="width:90%" class="text-center">Tên file</th>
                      </tr>
                      <tr v-for="(file, i) in hatt.emrFileDinhKemHatts" :key="file.id">
                        <td>{{ i + 1 }}</td>
                        <td>
                          <a :href="file.url">{{ file.ten }}</a>
                        </td>
                      </tr>
                    </table>
                  </div>
                </div>
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
import TomTat from '@/components/hsba/view_detail/TomTat.vue';

export default {
  components: {
    TomTat
  },

  props: ["hsbaId"],
  data() {
    return {
      hattList: null,
      hatt: null
    }
  },
  
  methods: {
    viewHatt: function(hatt) {
      this.hatt = hatt;
      $("#hattModal").modal();
    }
  },
  
  created: async function() {
    this.hattList = await this.get("/api/hatt/get_ds_hatt", {
        hsba_id: this.hsbaId
    });
  }
};
</script>