<template>
  <div v-if="hsba" class="mt-3">
    <div class="row mt-3">
      <div class="col-12">
        <label class="label-title label-bold">I. VÀO VIỆN HOẶC VÀO KHOA KHÁM BỆNH</label>
      </div>
    </div>
    <div class="row mt-2">
      <div class="col-6">
        <label class="label-title">Chẩn đoán nơi đến</label>
        <input
          readonly
          data-toggle="modal"
          data-target="#dmChandoannoidenSelect"
          class="form-control"
          :value="getTextChanDoan(hsba.emrChanDoan.emrDmMaBenhChandoannoiden)"
        />
      </div>
      <div class="col-6">
        <label class="label-title">Chẩn đoán vào viện</label>
        <input
          readonly
          data-toggle="modal"
          data-target="#dmChandoankkbSelect"
          class="form-control"
          :value="getTextChanDoan(hsba.emrChanDoan.emrDmMaBenhChandoankkb)"
        />
      </div>
    </div>
    <div class="row mt-2">
      <div class="col-6">
        <label class="label-title">Mô tả chẩn đoán nơi đến</label>
        <textarea rows="3" class="form-control" v-model="hsba.emrChanDoan.motachandoannoiden"></textarea>
      </div>
      <div class="col-6">
        <label class="label-title">Mô tả chẩn đoán vào viện</label>
        <textarea rows="3" class="form-control" v-model="hsba.emrChanDoan.motachandoankkb"></textarea>
      </div>
    </div>
    <div class="row mt-3">
      <div class="col-12">
        <label class="label-title label-bold">II. VÀO KHOA ĐIỀU TRỊ</label>
      </div>
    </div>
    <div class="row mt-2">
      <div class="col-6">
        <label class="label-title">Bệnh chính</label>
        <input
          readonly
          data-toggle="modal"
          data-target="#dmChandoanbenhchinhSelect"
          class="form-control"
          :value="getTextChanDoan(hsba.emrBenhAn.emrDmMaBenhChandoanbenhchinh)"
        />
      </div>
      <div class="col-6">
        <label class="label-title">Bệnh kèm theo</label>
        <input
          readonly
          data-toggle="modal"
          data-target="#dmChandoankemtheosSelect"
          class="form-control"
          :value="getTextChanDoans(hsba.emrBenhAn.emrDmMaBenhChandoankemtheos)"
        />
      </div>
    </div>
    <div class="row mt-2">
      <div class="col-6">
        <label class="label-title">Mô tả chẩn đoán bệnh chính</label>
        <textarea rows="3" class="form-control" v-model="hsba.emrBenhAn.motachandoanbenhchinh"></textarea>
      </div>
      <div class="col-6">
        <label class="label-title">Mô tả chẩn đoán bệnh kèm theo</label>
        <textarea rows="3" class="form-control" v-model="hsba.emrBenhAn.motachandoankemtheo"></textarea>
      </div>
    </div>

    <div class="row mt-3">
      <div class="col-12">
        <label class="label-title label-bold">III. RA VIỆN</label>
      </div>
    </div>
    <div class="row mt-2">
      <div class="col-6">
        <label class="label-title">Bệnh chính</label>
        <input
          readonly
          class="form-control"
          data-toggle="modal"
          data-target="#dmChandoanravienchinhsSelect"
          :value="getTextChanDoan(hsba.emrChanDoan.emrDmMaBenhChandoanravienchinh)"
        />
      </div>
      <div class="col-6">
        <label class="label-title">Bệnh kèm theo</label>
        <input
          readonly
          class="form-control"
          data-toggle="modal"
          data-target="#dmChandoanravienkemtheosSelect"
          :value="getTextChanDoans(hsba.emrChanDoan.emrDmMaBenhChandoanravienkemtheos)"
        />
      </div>
    </div>
    <div class="row mt-2">
      <div class="col-6">
        <label class="label-title">Mô tả chẩn đoán bệnh chính</label>
        <textarea rows="3" class="form-control" v-model="hsba.emrChanDoan.motachandoanravienchinh"></textarea>
      </div>
      <div class="col-6">
        <label class="label-title">Mô tả chẩn đoán bệnh kèm theo</label>
        <textarea
          rows="3"
          class="form-control"
          v-model="hsba.emrChanDoan.motachandoanravienkemtheo"
        ></textarea>
      </div>
    </div>

    <div class="mt-3 mb-3">
      <button v-on:click="saveHsba()" class="btn btn-sm btn-primary">Lưu lại</button>
    </div>

    <DanhMucPopup
      id="dmChandoannoidenSelect"
      showCode="true"
      name="emrDmMaBenhChandoannoiden"
      :value="hsba.emrChanDoan.emrDmMaBenhChandoannoiden"
      title="Chẩn đoán nơi đến"
      dmType="DM_MA_BENH"
    ></DanhMucPopup>
    <DanhMucPopup
      id="dmChandoankkbSelect"
      showCode="true"
      name="emrDmMaBenhChandoankkb"
      :value="hsba.emrChanDoan.emrDmMaBenhChandoankkb"
      title="Chẩn đoán vào viện"
      dmType="DM_MA_BENH"
    ></DanhMucPopup>
    <DanhMucPopup
      id="dmChandoanbenhchinhSelect"
      showCode="true"
      name="emrDmMaBenhChandoanbenhchinh"
      :value="hsba.emrBenhAn.emrDmMaBenhChandoanbenhchinh"
      title="Chẩn đoán bệnh chính"
      dmType="DM_MA_BENH"
    ></DanhMucPopup>
    <DanhMucPopup
      id="dmChandoankemtheosSelect"
      multi="true"
      showCode="true"
      name="emrDmMaBenhChandoankemtheos"
      :value="hsba.emrBenhAn.emrDmMaBenhChandoankemtheos"
      title="Chẩn đoán bệnh kèm theo"
      dmType="DM_MA_BENH"
    ></DanhMucPopup>
    <DanhMucPopup
      id="dmChandoanravienchinhsSelect"
      showCode="true"
      name="emrDmMaBenhChandoanravienchinh"
      :value="hsba.emrChanDoan.emrDmMaBenhChandoanravienchinh"
      title="Chẩn đoán bệnh chính (ra viện)"
      dmType="DM_MA_BENH"
    ></DanhMucPopup>
    <DanhMucPopup
      id="dmChandoanravienkemtheosSelect"
      multi="true"
      showCode="true"
      name="emrDmMaBenhChandoanravienkemtheos"
      :value="hsba.emrChanDoan.emrDmMaBenhChandoanravienkemtheos"
      title="Chẩn đoán bệnh kèm theo (ra viện)"
      dmType="DM_MA_BENH"
    ></DanhMucPopup>
  </div>
</template>

<script>
import mixin from "./Mixin.vue";

export default {
  mixins: [mixin],

  computed: {
    emrDmMaBenhChandoannoiden: function() {
      return this.$store.state.emrDmMaBenhChandoannoiden;
    },
    emrDmMaBenhChandoankkb: function() {
      return this.$store.state.emrDmMaBenhChandoankkb;
    },
    emrDmMaBenhChandoanbenhchinh: function() {
      return this.$store.state.emrDmMaBenhChandoanbenhchinh;
    },
    emrDmMaBenhChandoankemtheos: function() {
      return this.$store.state.emrDmMaBenhChandoankemtheos;
    },
    emrDmMaBenhChandoanravienchinh: function() {
      return this.$store.state.emrDmMaBenhChandoanravienchinh;
    },
    emrDmMaBenhChandoanravienkemtheos: function() {
      return this.$store.state.emrDmMaBenhChandoanravienkemtheos;
    }
  },

  watch: {
    emrDmMaBenhChandoannoiden: function(val) {
      this.hsba.emrChanDoan.emrDmMaBenhChandoannoiden = val;
    },
    emrDmMaBenhChandoankkb: function(val) {
      this.hsba.emrChanDoan.emrDmMaBenhChandoankkb = val;
    },
    emrDmMaBenhChandoanbenhchinh: function(val) {
      this.hsba.emrBenhAn.emrDmMaBenhChandoanbenhchinh = val;
    },
    emrDmMaBenhChandoankemtheos: function(val) {
      this.hsba.emrBenhAn.emrDmMaBenhChandoankemtheos = val;
    },
    emrDmMaBenhChandoanravienchinh: function(val) {
      this.hsba.emrChanDoan.emrDmMaBenhChandoanravienchinh = val;
    },
    emrDmMaBenhChandoanravienkemtheos: function(val) {
      this.hsba.emrChanDoan.emrDmMaBenhChandoanravienkemtheos = val;
    }
  },

  methods: {
    getTextChanDoan: function(chandoan) {
      if (chandoan && chandoan.ma != "") {
        return chandoan.ma + " - " + chandoan.ten;
      }
      return "";
    },
    getTextChanDoans: function(chandoans) {
      if (chandoans && chandoans.length > 0) {
        return chandoans.map(x => x.ma + " - " + x.ten).join(" ; ");
      }
      return "";
    }
  }
};
</script>