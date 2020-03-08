<template>
  <div v-if="hsba">
    <div class="row mt-3">
      <div class="col-12">
        <label>Tóm tắt bệnh án</label>
        <textarea rows="3" class="form-control" v-model="hsba.emrBenhAn.tomtat"></textarea>
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
  mixins: [mixin]
};
</script>
