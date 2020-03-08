<template>
  <div>
    <ul class="nav nav-tabs">
      <li v-for="tab in tabs" v-bind:key="tab.id" class="nav-item">
        <router-link
          class="nav-link"
          :class="{'active': tab.id==($route.meta.subTabId||1)}"
          :to="{name: tab.link, params: {hsbaId: hsbaId}}"
        >{{ tab.name }}</router-link>
      </li>
    </ul>
    <router-view />
  </div>
</template>

<script>

export default {
  props: ["hsbaId"],

  data() {
    return {      
      tabs: [
        {id: 1, name: 'THÔNG TIN HÀNH CHÍNH', link: 'chinhSuaTTHanhChinh'},
        {id: 2, name: 'Thông tin người bệnh' , link: 'chinhSuaTTNguoiBenh'},
        {id: 3, name:'Chẩn đoán', link: 'chinhSuaTTChanDoan'},
        {id: 4, name:'Tình trạng ra viện', link: 'chinhSuaTTTinhTrangRaVien'},
      ]
    };
  }
};
</script>