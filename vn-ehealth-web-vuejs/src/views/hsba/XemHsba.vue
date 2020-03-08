<template>
  <div>    
    <div class="font-weight-bold text-uppercase"></div>
    <div id="content" class="bg-white mt-2">
      <div>
        <div>
          <div style="display:flex">
            <div class="ml-3" style="display: flex;">
              <div style="display: inline-block; align-self: flex-end;">
                <b>Xem thông tin hồ sơ bệnh án</b>
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
            <div style="flex-grow: 1.0">
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
        {id: 1, name: 'Tờ bệnh án', link: 'xemToBenhAn'},
        {id: 2, name: 'Hình ảnh tổn thương' , link: 'xemTTHinhAnhTonThuong'},
        {id: 3, name: 'Phẫu thuật thủ thuật' , link: 'xemTTPhauThuatThuThuat'},
        {id: 4, name: 'Xét nghiệm' , link: 'xemTTXetNghiem'},
        {id: 5, name: 'Chẩn đoán hình ảnh' , link: 'xemTTChanDoanHinhAnh'},
        {id: 6, name: 'Thăm dò chức năng' , link: 'xemTTThamDoChucNang'},
        {id: 7, name: 'Giải phẫu bệnh' , link: 'xemTTGiaiPhauBenh'},
        {id: 8, name: 'Hội chẩn' , link: 'xemTTHoiChan'},
        {id: 9, name: 'Điều trị' , link: 'xemTTDieuTri'},
        {id: 10, name: 'Chăm sóc' , link: 'xemTTChamSoc'},
        {id: 11, name: 'Theo dõi chức năng sống', link: 'xemTTChucNangSong'},
        {id: 12, name: 'Đơn thuốc', link: 'xemTTDonThuoc'},
        {id: 13, name: 'Các giấy tờ đi kèm khác' , link: 'xemTTGiayToKhac'}
      ],      
      hsba: null,
    };
  },

  props: ['hsbaId']

};
</script>