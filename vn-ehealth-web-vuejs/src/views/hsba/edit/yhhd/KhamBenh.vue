<template>
  <div v-if="hsba">
    <div class="row mt-3">
      <div class="col-12">
        <b>
          <label>Chức năng sống</label>
        </b>
      </div>
    </div>

    <div class="row mt-2">
      <div class="col-2 text-center">
        <label>Mạch: (lần phút)</label>
      </div>
      <div class="col-2 text-center">
        <label>Nhiệt độ: (°C)</label>
      </div>
      <div class="col-2 text-center">
        <label>Huyết áp: (mmhg)</label>
      </div>
      <div class="col-2 text-center">
        <label>Nhịp thở: (lần phút)</label>
      </div>
      <div class="col-2 text-center">
        <label>Cân nặng: (kg)</label>
      </div>
      <div class="col-2 text-center">
        <label>Chiều cao: (cm)</label>
      </div>
    </div>
    <div class="row mt-2">
      <div class="col-2 text-center">
        <input class="form-control" v-model="hsba.emrBenhAn.mach" />
      </div>
      <div class="col-2 text-center">
        <input class="form-control" v-model="hsba.emrBenhAn.nhietdo" />
      </div>
      <div class="col-2 text-center">
        <div class="row">
          <div class="col-6">
            <input class="form-control" v-model="hsba.emrBenhAn.huyetapcao" />
          </div>
          <h5>/</h5>
          <div class="col-5">
            <input class="form-control" v-model="hsba.emrBenhAn.huyetapthap" />
          </div>
        </div>
      </div>
      <div class="col-2 text-center">
        <input class="form-control" v-model="hsba.emrBenhAn.nhiptho" />
      </div>
      <div class="col-2 text-center">
        <input class="form-control" v-model="hsba.emrBenhAn.cannang" />
      </div>
      <div class="col-2 text-center">
        <input class="form-control" v-model="hsba.emrBenhAn.chieucao" />
      </div>
    </div>

    <div class="row mt-4">
      <div class="col-12">
        <b>
          <label>Thông tin chuyên khoa</label>
        </b>
      </div>
    </div>
    <div class="row mt-2">
      <div class="col-12">
        <label>Khác</label>
        <textarea rows="3" class="form-control" v-model="hsba.emrBenhAn.coquankhac"></textarea>
      </div>
    </div>
    <div class="row mt-2">
      <div class="col-12">
        <label>Các xét nghiệm cận lâm sàng cần làm</label>
        <textarea rows="3" class="form-control" v-model="hsba.emrBenhAn.xetnghiemcanlamsang"></textarea>
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
};
</script>
