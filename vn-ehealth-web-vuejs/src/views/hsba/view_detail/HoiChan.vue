<template>
  <div v-if="hoichanList">
    <table class="table table-bordered">
      <tr>
        <th style="width: 5%;" class="text-center">STT</th>
        <th style="width: 15%;" class="text-center">Thao tác</th>
        <th style="width: 40%;" class="text-center">Ngày giờ hội chẩn</th>
        <th style="width: 40%;" class="text-center">Khoa điều trị</th>
      </tr>
      <tr v-for="(hoichan, i) in hoichanList" :key="hoichan.id">
        <td class="text-center">{{ i + 1 }}</td>
        <td class="text-center">
          <a href="#" v-on:click="viewHoichan(hoichan)">
            <i class="fas fa-fw fa-binoculars"></i> Xem
          </a>
        </td>
        <td class="text-center">{{ formateDateTime(hoichan.ngaythuchien) }}</td>
        <td class="text-center">{{ getTenKhoa(hoichan.emrVaoKhoa) }}</td>
      </tr>
      <tr v-if="hoichanList.length==0">
        <td colspan="4">Không có dữ liệu</td>
      </tr>
    </table>

    <div class="modal fade" id="hoichanModal">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">
              <strong>Thông tin hội chẩn</strong>
            </h5>
            <button type="button" class="close" data-dismiss="modal">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div v-if="hoichan" class="modal-body">
            <TomTat :hsbaId="hsbaId" title="PHIẾU HỘI CHẨN"></TomTat>
            <hr />
            <div>
              <meta charset="UTF-8" />
              <table border="1" cellpadding="10" class="table table-bordered">
                <font size="2.5">
                  <div class="row">
                    <div class="col-12">
                      <b>Hội chẩn lúc: {{ formateDateTime(hoichan.ngaythuchien) }}</b>
                      <br />
                      <span>- Tại khoa: {{ getTenKhoa(hoichan.emrVaoKhoa) }}</span>
                      <br />
                      <div class="row">
                        <div class="col-6">
                          <span>- Bác sĩ chủ tọa:</span>
                          <span>{{ bacsichutoa }}</span>
                        </div>
                        <div class="col-6">
                          <span>- Thư ký:</span>
                          <span>{{ thuky }}</span>
                        </div>
                        <div class="col-6"></div>
                      </div>
                      <span>- Thành viên tham gia:</span>
                      <ul>
                        <li
                          v-for="bs in hoichan.emrThanhVienHoiChans"
                          :key="bs.id"
                        >{{ attr(bs, 'bacsihoichan.ten') }}</li>
                      </ul>
                    </div>
                  </div>
                  <hr />

                  <div class="row">
                    <div class="col-12">
                      <b>
                        <span>Tóm tắt diễn biến</span>
                        <br />
                      </b>
                      <span>{{ hoichan.tomtatdienbien }}</span>
                    </div>
                  </div>
                  <hr />

                  <div class="row">
                    <div class="col-12">
                      <b>
                        <span>Kết luận</span>
                        <br />
                      </b>
                      <span>{{ hoichan.ketluanhoichan }}</span>
                    </div>
                  </div>
                  <hr />

                  <div class="row">
                    <div class="col-12">
                      <b>
                        <span>Hướng điều trị tiếp theo</span>
                        <br />
                      </b>
                      <span>{{ hoichan.huongdieutri }}</span>
                    </div>
                  </div>

                  <div v-if="hoichan.emrFileDinhKemHoiChans.length > 0">
                    <hr />
                    <b>Danh sách file đính kèm:</b>
                    <table class="table table-bordered mt-3">
                      <tr>
                        <th style="width:10%" class="text-center">STT</th>
                        <th style="width:90%" class="text-center">Tên file</th>
                      </tr>
                      <tr v-for="(file, i) in hoichan.emrFileDinhKemHoiChans" :key="file.id">
                        <td>{{ i + 1 }}</td>
                        <td>
                          <a :href="file.duongdan">{{ file.tenfile }}</a>
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
      hoichanList: null,
      hoichan: null
    };
  },

  methods: {
    viewHoichan: function(hoichan) {
      this.hoichan = hoichan;
      $("#hoichanModal").modal();
    },
    getTenKhoa: function(khoadieutri) {
      return (
        khoadieutri.tenkhoa || this.attr(khoadieutri, "emrDmKhoaDieuTri.ten")
      );
    }
  },
  
  computed: {
    bacsichutoa: function() {
      var bacsi = this.hoichan.emrThanhVienHoiChans.find(
        x => attr(x, "emrDmVaiTro.ma") == "01"
      );
      if (bacsi) {
        return attr(bacsi, 'bacsihoichan.ten');
      }
      return "";
    },

    thuky: function() {
      var bacsi = this.hoichan.emrThanhVienHoiChans.find(
        x => attr(x, "emrDmVaiTro.ma") == "02"
      );
      if (bacsi) {
        return attr(bacsi, 'bacsihoichan.ten');
      }
      return "";
    }
  },

  created: async function() {
    this.hoichanList = await this.get("/api/hoichan/get_ds_hoichan", {
      hsba_id: this.hsbaId
    });
  }
};
</script>