<template>
  <div v-if="patient">
    <div id="content" class="bg-white mt-2">
      <div class="panel panel-default">
        <div class="text-right MB20">
          <a class="btn-info bo50 btn-pd" href="#">
            <i class="fa fa-print faR10" aria-hidden="true"></i>In
          </a>
        </div>
        <!--bt-->
        <div class="row row-eq-height">
          <div class="col-6 offset-3">
            <div class="box">
              <h2>
                <span class="text-ten">{{ patient.tendaydu}} || {{ patient.iddinhdanhchinh }}</span>
              </h2>
              <span class="text-label1">Giới tính:</span>
              {{
              patient.emrDmGioiTinh.ten }}
              <br />
              <span class="text-label1">Ngày sinh:</span>
              {{
              patient.ngaysinh }}
              <br />
              <span class="text-label2">Dân tộc:</span>
              {{
              patient.emrDmDanToc.ten }}
              <br />
              <span class="text-label2">Tôn giáo:</span>
              {{
              patient.religion }}
              <br />
              <span class="text-danger">
                <i class="MR5 fa fa-mobile" aria-hidden="true"></i>
                {{
                patient.sodienthoainguoibaotin}}
              </span>
              <span class="text-success">
                <i class="MR5 fa fa-map-marker" aria-hidden="true"></i>
                {{patient.diachi}}
              </span>
              <br />
              <span class="text-label2">Đối tượng:</span>
              <br />
              <span class="text-lable2">Số BHYT:</span>
              <span class="btn-info bo50 btn-pd1">{{ patient.sobhyt }}</span>
            </div>
          </div>
        </div>
      </div>
      <div class="panel panel-default">
        <ul class="nav nav-tabs">
          <li v-for="tab in tabs" v-bind:key="tab.id" class="nav-item ml-1">
            <router-link
              class="nav-link"
              :class="{'active': tab.id==activeTabId}"
              :style="{ backgroundColor: tab.id==activeTabId? '#1057ae' : '#eee', color: tab.id==activeTabId? '#fff' : '#000',
                            marginBottom : '2px', borderTop : tab.id==activeTabId? '0px' : ''}"
              :to="{name: tab.link, params: {patientId: patientId}}"
            >{{ tab.name }}</router-link>
          </li>
        </ul>
        <div style="border-top: 2px solid #1057ae">
          <router-view />
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: ["patientId"],
  data() {
    return {
      patient: null,
      tabs: [
        { id: 1, name: "Tổng quan", link: "ttBenhNhanTongQuan" },
        {
          id: 2,
          name: "Lịch sử dùng thuốc",
          link: "ttBenhNhanLichSuDungThuoc"
        },
        {
          id: 3,
          name: "Chẩn đoán hình ảnh",
          link: "ttBenhNhanChanDoanHinhAnh"
        },
        {
          id: 4,
          name: "Phẫu thuật thủ thuật",
          link: "ttBenhNhanPhauThuatThuThuat"
        },
        { id: 5, name: "Tài liệu lâm sàng", link: "ttBenhNhanTaiLieuLamSang" },
        { id: 6, name: "Chỉ số sinh tồn", link: "ttBenhNhanChiSoSinhTon" }
      ]
    };
  },
  computed: {
    activeTabId() {
      return this.$route.meta.tabId||1;
    }
  },
  created: async function() {
    this.patient = await this.get("/api/benh_nhan/get_benhnhan_by_id", {
      id: this.patientId
    });
  }
};
</script>