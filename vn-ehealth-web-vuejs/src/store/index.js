import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    emrDmTinhThanh: null,
    emrDmQuanHuyen: null,
    emrDmPhuongXa: null,
    emrDmMaBenhChandoannoiden: [],
    emrDmMaBenhChandoankkb: [],
    emrDmMaBenhChandoanbenhchinh: null,
    emrDmMaBenhChandoankemtheos: [],
    emrDmMaBenhChandoanravienchinh: [],
    emrDmMaBenhChandoanravienkemtheos: [],
    emrDmPhauThuThuat: null,
    emrDmMaBenhChandoantruocs: [],
    emrDmMaBenhChandoansaus: [],
    emrDmChanDoanHinhAnh: null,
    emrDmThamDoChucNang: null,
    emrDmGiaiPhauBenh: null,
    emrDmXetNghiem: null,
    emrDmChiSoXetNghiem: null,
    emrDmThuoc: null,
    emrDmTanXuatDungThuoc: null,
    emrDmDuongDungThuoc: null
  },
  mutations: {
    setValue: function (state, { key, value }) {
      state[key] = value;
    },
  },
  actions: {
  },
  modules: {
  }
})
