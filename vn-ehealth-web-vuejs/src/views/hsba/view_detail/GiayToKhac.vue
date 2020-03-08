<template>
  <div v-if="hsba" class="frame">
    <TomTat :hsbaId="hsbaId" title="CÁC GIẤY TỜ ĐI KÈM KHÁC"></TomTat>
    <hr />
    <table class="table table-bordered">
      <tr>
        <th style="width:10%" class="text-center">STT</th>
        <th style="width:15%" class="text-center">Thao tác</th>
        <th style="width:75%" class="text-center">Tên file</th>
      </tr>
      <tr v-for="(fileDinhKem,index) in hsba.emrFileDinhKems" :key="fileDinhKem.id">
        <td class="text-center">{{ index + 1 }}</td>
        <td class="text-center">
          <a href="#" title="Xem">
            <i class="fas fa-fw fa-lg fa-binoculars"></i>
          </a>
        </td>
        <td>
          <a :href="fileDinhKem.url">{{ fileDinhKem.name }}</a>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
import TomTat from "@/components/hsba/view_detail/TomTat.vue";

export default {
  components: {
    TomTat
  },

  data: function() {
    return {
      hsba: null
    };
  },

  props: ["hsbaId"],

  created: async function() {
    this.hsba = await this.get("/api/hsba/get_hsba_by_id", {
      hsba_id: this.hsbaId
    });
  }
};
</script>