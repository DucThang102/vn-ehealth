<template>
  <div v-if="hatt" class="mt-3">
    <div class="row">
      <div class="col-12">
        <label class="label-title">Ảnh tổn thương</label>
        <input class="form-control" v-model="hatt.anhtonthuong" />
      </div>
    </div>
    <div class="row mt-3">
      <div class="col-12">
        <label class="label-title">Mô tả tổn thương</label>
        <textarea rows="3" class="form-control" v-model="hatt.motatonthuong"></textarea>
      </div>
    </div>
    <div class="mt-3 mb-3">
      <button type="button" v-on:click="saveHatt()" class="btn btn-sm btn-primary mr-1">Lưu lại</button>
      <button type="button" v-on:click="back()" class="btn btn-sm btn-secondary">Quay lại</button>
    </div>
  </div>
</template>

<script>
export default {
  data(){
    return {
      hatt: null
    }
  },
  methods: {
    saveHatt : async function() {
      var result = await this.post("/api/hatt/create_or_update_hatt", this.hatt);
      if(result.success) {
        this.back();
      }else {
        alert('Lỗi xảy ra quá trình lưu thông tin');
      }
    },
    back: function() {
      this.$router.push({name: 'chinhSuaTTHinhAnTonThuongList', params: {hsbaId: this.hsbaId}});
    }
  },
  props: ["hsbaId", "hattId"],

  created: async function() {
    if(this.hattId == '_') {
      this.hatt = {emrHoSoBenhAnId: this.hsbaId};
    }else {
      this.hatt = await this.get("/api/hatt/get_hatt", {hatt_id: this.hattId});
    }
  }
};
</script>