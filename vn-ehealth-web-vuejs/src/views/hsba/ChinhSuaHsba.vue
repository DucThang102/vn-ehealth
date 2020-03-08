<template>
  <div>    
    <div class="font-weight-bold text-uppercase"></div>
    <div id="content" class="bg-white mt-2">
      <div>
        <div>
          <div style="display:flex">
            <div class="ml-3" style="display: flex;">
              <div style="display: inline-block; align-self: flex-end;">
                <b>Chỉnh sửa thông tin hồ sơ bệnh án</b>
              </div>
            </div>
          </div>

          <div class="d-flex mt-2">
            <ul style="min-width:220px" class="nav nav-tabs flex-column ml-3 mr-3">
              <li v-for="tab in tabs" v-bind:key="tab.id" class="nav-item">
                <router-link class="nav-link" :class="{'active': tab.id==($route.meta.tabId||1)}" 
                    :to="{name: tab.link, params: {hsbaId: hsbaId}}">
                  {{ tab.name }}
                </router-link>
              </li>
            </ul>
            <div style="flex-grow: 0.8">
              <router-view />
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
      tabs: [
        {id: 1, name: 'Quản lý bệnh nhân', link: 'chinhSuaTTHanhChinh'},
        {id: 2, name: 'Y học hiện đại' , link: 'chinhSuaTTHoiBenh'},
        {id: 3, name: 'Tổng kết bệnh án' , link: 'chinhSuaTTTongKetBenhAn'},
        {id: 4, name: 'Hình ảnh tổn thương' , link: 'chinhSuaTTHinhAnTonThuongList'},
        {id: 5, name: 'Phẫu thuật thủ thuật' , link: 'chinhSuaTTPhauThuatThuThuat'},
        {id: 6, name: 'Xét nghiệm' , link: 'chinhSuaTTXetNghiem'},
        {id: 7, name: 'Chẩn đoán hình ảnh' , link: 'chinhSuaTTChanDoanHinhAnh'},
        {id: 8, name: 'Thăm dò chức năng' , link: 'chinhSuaTTThamDoChucNang'},
        {id: 9, name: 'Giải phẫu bệnh' , link: 'chinhSuaTTGiaiPhauBenh'},
        {id: 10, name: 'Hội chẩn' , link: 'chinhSuaTTHoiChan'},
        {id: 11, name: 'Điều trị' , link: 'chinhSuaTTDieuTri'},
        {id: 12, name: 'Chăm sóc' , link: 'chinhSuaTTChamSoc'},
        {id: 13, name: 'Theo dõi chức năng sống', link: 'chinhSuaTTChucNangSong'},
        {id: 14, name: 'Đơn thuốc', link: 'chinhSuaTTDonThuoc'},
        {id: 15, name: 'Các giấy tờ đi kèm khác' , link: 'chinhSuaTTGiayToKhac'}
      ],      
      hsba: null,
    };
  },

  props: ['hsbaId']

};
</script>