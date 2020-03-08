<template>
  <div v-if="hsba">
    <body id="benh_an">
      <BenhAnTruyenNhiem :hsba="hsba" v-if="hsba.emrDmLoaiBenhAn.ma=='03'"/>
      <!--
      <BenhAnNoiKhoa :hsba="hsba" v-if="hsba.emrDmLoaiBenhAn.ma=='01'"/>
      <BenhAnNhi :hsba="hsba" v-else-if="hsba.emrDmLoaiBenhAn.ma=='02'"/>      
      <BenhAnPhuKhoa :hsba="hsba" v-else-if="hsba.emrDmLoaiBenhAn.ma=='04'"/>
      <BenhAnSanKhoa :hsba="hsba" v-else-if="hsba.emrDmLoaiBenhAn.ma=='05'"/>
      <BenhAnSoSinh :hsba="hsba" v-else-if="hsba.emrDmLoaiBenhAn.ma=='06'"/>
      <BenhAnTamThan :hsba="hsba" v-else-if="hsba.emrDmLoaiBenhAn.ma=='07'"/>  
      <BenhAnDaLieu :hsba="hsba" v-else-if="hsba.emrDmLoaiBenhAn.ma=='08'"/>
      <BenhAnDD :hsba="hsba" v-else-if="hsba.emrDmLoaiBenhAn.ma=='09'"/>  
      <BenhAnHHTM :hsba="hsba" v-else-if="hsba.emrDmLoaiBenhAn.ma=='10'"/>  
      <BenhAnNgoaiKhoa :hsba="hsba" v-else-if="hsba.emrDmLoaiBenhAn.ma=='11'"/>
      <BenhAnRHM :hsba="hsba" v-else-if="hsba.emrDmLoaiBenhAn.ma=='14'"/>  
      <BenhAnTMH :hsba="hsba" v-else-if="hsba.emrDmLoaiBenhAn.ma=='15'"/>  
      <BenhAnNgoaiTru :hsba="hsba" v-else-if="hsba.emrDmLoaiBenhAn.ma=='20'"/>      
      <BenhAnNgoaiTruYhct :hsba="hsba" v-else-if="hsba.emrDmLoaiBenhAn.ma=='34'"/>  
      <BenhAnNoiTruYhct :hsba="hsba" v-else-if="hsba.emrDmLoaiBenhAn.ma=='35'"/> 
      -->     
    </body>
    <button class="btn btn-primary btn-sm" v-on:click="printToPdf()">In tờ bệnh án</button>
  </div>  
</template>

<script>
import BenhAnNoiKhoa from "./benh_an/BenhAnNoiKhoa.vue";
import BenhAnNhi from "./benh_an/BenhAnNhi.vue";
import BenhAnTruyenNhiem from "./benh_an/BenhAnTruyenNhiem.vue";
import BenhAnPhuKhoa from "./benh_an/BenhAnPhuKhoa.vue";
import BenhAnSanKhoa from "./benh_an/BenhAnSanKhoa.vue";
import BenhAnSoSinh from "./benh_an/BenhAnSoSinh.vue";
import BenhAnTamThan from "./benh_an/BenhAnTamThan.vue";
import BenhAnDaLieu from "./benh_an/BenhAnDaLieu.vue";
import BenhAnDD from "./benh_an/BenhAnDD.vue";
import BenhAnHHTM from "./benh_an/BenhAnHHTM.vue";
import BenhAnNgoaiKhoa from "./benh_an/BenhAnNgoaiKhoa.vue";
import BenhAnRHM from "./benh_an/BenhAnRHM.vue";
import BenhAnTMH from "./benh_an/BenhAnTMH.vue";
import BenhAnNgoaiTru from "./benh_an/BenhAnNgoaiTru.vue";
//import BenhAnNgoaiTruYhct from "./benh_an/BenhAnNgoaiTruYhct.vue";
import BenhAnNoiTruYhct from "./benh_an/BenhAnNoiTruYhct.vue";

export default {
  components: {
    BenhAnNoiKhoa,
    BenhAnNhi,
    BenhAnTruyenNhiem,
    BenhAnPhuKhoa,
    BenhAnSanKhoa,
    BenhAnSoSinh,
    BenhAnTamThan,
    BenhAnDaLieu,
    BenhAnDD,
    BenhAnHHTM,
    BenhAnNgoaiKhoa,
    BenhAnRHM,
    BenhAnTMH,
    BenhAnNgoaiTru,
    //BenhAnNgoaiTruYhct,
    BenhAnNoiTruYhct
  },
  data: function() {
    return {
      hsba: null
    };
  },
  props: ["hsbaId"],
  computed: {
    pdfURL: function() {
      return this.API_URL + "/api/hsba/view_pdf?hsba_id=" + this.hsbaId;
    }
  },
  methods: {
    printToPdf: function() {
      $("#benh_an input").each(function() {
        $(this).attr("value", $(this).val());
      });

      $("#benh_an input[type=checkbox]").each(function() {
        if ($(this).prop("checked")) {
          $(this).attr("checked", "checked");
        }
      });

      var body = $("#benh_an").html();
      var wnd = window.open("", ""); //window.open('', '', 'width=1024');
      wnd.document.write("");

      wnd.document.write("</head><body >");
      wnd.document.write(body);
      wnd.document.write("</body></html>");
      wnd.document.close();
    }
  },
  created: async function() {
    this.hsba = await this.get("/api/hsba/get_hsba_by_id", {
      hsba_id: this.hsbaId
    });
  }
}
</script>