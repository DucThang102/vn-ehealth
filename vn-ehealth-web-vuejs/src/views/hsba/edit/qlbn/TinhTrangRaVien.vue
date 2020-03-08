<template>
  <div v-if="hsba && emrDmKetQuaDieuTris">
    <div class="row mt-3">
      <div class="col-12">
        <div class="mb-2">
          <b>
            <label>Kết quả điều trị</label>
            <span style="color: red;">(*)</span>
          </b>
        </div>
        <div v-for="(emrDm,i) in emrDmKetQuaDieuTris" :key="i" class="radio">
          <input
            type="radio"
            :value="emrDm.ma"
            v-model="hsba.emrTinhTrangRaVien.emrDmKetQuaDieuTri.ma"
          />
          <label>{{i+1}}. {{emrDm.ten}}</label>
        </div>
      </div>
    </div>

    <div class="mt-3 mb-3">
      <button v-on:click="saveHsba()" class="btn btn-sm btn-primary">Lưu lại</button>
    </div>
  </div>
</template>

<script>
import mixin from "./Mixin.vue";

export default {
  mixins: [mixin],

  data: function() {
    return {
      emrDmKetQuaDieuTris: null
    };
  },

  created: async function() {
    this.emrDmKetQuaDieuTris = await this.get("/api/danhmuc/get_all_dm_list", {
      dm_type: "DM_KET_QUA_DIEU_TRI"
    });
  }
};
</script>