VueAsyncComponent('qlybn', '/pages/hsba/edit/qly_benhnhan/qlybn.html', {
    data: function() {
      return {
        tab: 0,
      }
    },

    props: ["hsba_id"],
  
    methods: {
      changeTab(tab){
        if(tab != this.tab && sessionStorage.getItem('dataChange')) {
          if(!confirm('Bạn sẽ mất dữ liệu đang sửa nếu chuyển tab, tiếp tục?')){
            return;
          }          
        }
        this.tab = tab;
        sessionStorage.removeItem('dataChange');
      }
    },
});

var mixin = {
  data: function() {
    return {
      hsba: null
    }
  },

  props: ["hsba_id"],

  methods : {
    saveHsba : async function() {
      var result = await this.post("/api/hsba/update_hsba", this.hsba);
      if(result.success) {
        alert('Cập nhật thông tin thành công');
        sessionStorage.removeItem('dataChange');
      }else {
        alert('Lỗi xảy ra quá trình lưu thông tin');
      }
    }
  },

  watch: {
    hsba: {
      handler: function (val, oldVal) {
        if (oldVal) {
          sessionStorage.setItem('dataChange', true);
        }
      },
      deep: true
    }
  },

  created: async function() {
    this.hsba = await this.get("/api/hsba/get_hsba_by_id", {"hsba_id": this.hsba_id});
  }
};

VueAsyncComponent('tthc', '/pages/hsba/edit/qly_benhnhan/thongtin_hanhchinh.html', {
  mixins: [mixin],

  data: function() {
    return {
    }
  },

  computed: {    
    emrDmTinhThanh: function() {
      return store.state.emrDmTinhThanh;
    },
    emrDmQuanHuyen: function() {
      return store.state.emrDmQuanHuyen;
    },
    emrDmPhuongXa: function() {
      return store.state.emrDmPhuongXa;
    },
  },

  watch: {
    emrDmTinhThanh: function(val) {
      this.hsba.emrBenhNhan.emrDmTinhThanh = val;
    },
    emrDmQuanHuyen: function(val) {
      this.hsba.emrBenhNhan.emrDmQuanHuyen = val;
    },
    emrDmPhuongXa: function(val) {
      this.hsba.emrBenhNhan.emrDmPhuongXa = val;
    },
  }
});

VueAsyncComponent('ttnb', '/pages/hsba/edit/qly_benhnhan/thongtin_nguoibenh.html', {
  mixins: [mixin],

  data: function() {
    return {
      emrVaoKhoas: null,
      emrDmNoiTrucTiepVaos: null,
      emrDmNoiGioiThieus: null,
      emrDmLoaiDoiTuongTaiChinhs:null,
      emrDmLoaiVaoViens: null,
      emrDmLoaiRaViens: null,
      emrDmLoaiChuyenViens: null,
      khoadieutri: null,
    }
  },

  created: async function() {
    this.emrVaoKhoas = await this.get('/api/vaokhoa/get_ds_vaokhoa', { hsba_id: this.hsba_id, detail: false });
    this.emrDmNoiTrucTiepVaos = await this.get('/api/danhmuc/get_all_dm_list', {dm_type: 'DM_NOI_TRUC_TIEP_VAO'});
    this.emrDmNoiGioiThieus = await this.get('/api/danhmuc/get_all_dm_list', {dm_type: 'DM_NOI_GIOI_THIEU'});
    this.emrDmLoaiDoiTuongTaiChinhs = await this.get('/api/danhmuc/get_all_dm_list', {dm_type: 'DM_LOAI_DOI_TUONG_TAI_CHINH'});
    this.emrDmLoaiVaoViens = await this.get('/api/danhmuc/get_all_dm_list', {dm_type: 'DM_LOAI_VAO_VIEN'});
    this.emrDmLoaiRaViens = await this.get('/api/danhmuc/get_all_dm_list', {dm_type: 'DM_LOAI_RA_VIEN'});
    this.emrDmLoaiChuyenViens = await this.get('/api/danhmuc/get_all_dm_list', {dm_type: 'DM_LOAI_CHUYEN_VIEN'});        
  },

  watch: {
    hsba: {
      handler: function (val, oldVal) {
        if (oldVal) {
          sessionStorage.setItem('dataChange', true);
        }        
      },
      deep: true
    },
  },

  methods: {
    updateEmrDmTen(emrDm, list) {
      emrDm.ten = attr(list.find(x => x.ma == emrDm.ma), 'ten');
    },

    saveHsbaCustom : async function() {
      this.updateEmrDmTen(this.hsba.emrQuanLyNguoiBenh.emrDmNoiTrucTiepVao, this.emrDmNoiTrucTiepVaos);
      this.updateEmrDmTen(this.hsba.emrQuanLyNguoiBenh.emrDmNoiGioiThieu, this.emrDmNoiGioiThieus);
      this.updateEmrDmTen(this.hsba.emrQuanLyNguoiBenh.emrDmLoaiDoiTuongTaiChinh, this.emrDmLoaiDoiTuongTaiChinhs);
      this.updateEmrDmTen(this.hsba.emrQuanLyNguoiBenh.emrDmLoaiVaoVien, this.emrDmLoaiVaoViens);
      this.updateEmrDmTen(this.hsba.emrQuanLyNguoiBenh.emrDmLoaiRaVien, this.emrDmLoaiRaViens);
      this.updateEmrDmTen(this.hsba.emrQuanLyNguoiBenh.emrDmLoaiChuyenVien, this.emrDmLoaiChuyenViens);

      var result = await this.post("/api/hsba/update_hsba", this.hsba);
      if(result.success) {
        alert('Cập nhật thông tin thành công 1');
        sessionStorage.removeItem('dataChange');
      }else {
        alert('Lỗi xảy ra quá trình lưu thông tin');
      }
    }
  },
});

VueAsyncComponent('chandoan', '/pages/hsba/edit/qly_benhnhan/chandoan.html', {
  mixins: [mixin],

  data: function() {
    return {
    }
  },

  computed: {
    emrDmMaBenhChandoannoidens: function() {
      return store.state.emrDmMaBenhChandoannoidens;
    },
    emrDmMaBenhChandoankkbs: function() {
      return store.state.emrDmMaBenhChandoankkbs;
    },
    emrDmMaBenhChandoanbenhchinhs: function() {
      return store.state.emrDmMaBenhChandoanbenhchinhs;
    },
    emrDmMaBenhChandoankemtheos: function() {
      return store.state.emrDmMaBenhChandoankemtheos;
    },
    emrDmMaBenhChandoanravienchinhs: function() {
      return store.state.emrDmMaBenhChandoanravienchinhs;
    },
    emrDmMaBenhChandoanravienkemtheos: function() {
      return store.state.emrDmMaBenhChandoanravienkemtheos;
    },
  },

  watch: {
    emrDmMaBenhChandoannoidens: function(val) {
      this.hsba.emrChanDoan.emrDmMaBenhChandoannoidens = val;
    },
    emrDmMaBenhChandoankkbs: function(val) {
      this.hsba.emrChanDoan.emrDmMaBenhChandoankkbs = val;
    },
    emrDmMaBenhChandoanbenhchinhs: function(val) {
      this.hsba.emrBenhAn.emrDmMaBenhChandoanbenhchinhs = val;
    },
    emrDmMaBenhChandoankemtheos: function(val) {
      this.hsba.emrBenhAn.emrDmMaBenhChandoankemtheos = val;
    },
    emrDmMaBenhChandoanravienchinhs: function(val) {
      this.hsba.emrChanDoan.emrDmMaBenhChandoanravienchinhs = val;
    },
    emrDmMaBenhChandoanravienkemtheos: function(val) {
      this.hsba.emrChanDoan.emrDmMaBenhChandoanravienkemtheos = val;
    },
  },

  methods: {
    getTextChanDoan: function(chandoans){
      if(chandoans){
        return chandoans.map(x => x.ma + " - " + x.ten).join(' ; ');
      }
      return '';
    },
  },
});


VueAsyncComponent('ttrv', '/pages/hsba/edit/qly_benhnhan/tinhtrang_ravien.html', {
  mixins: [mixin],

  data: function() {
    return {
      emrDmKetQuaDieuTris: null
    }
  },

  created: async function() {
    this.emrDmKetQuaDieuTris = await this.get('/api/danhmuc/get_all_dm_list', {dm_type: 'DM_KET_QUA_DIEU_TRI'});
  },

  methods: {
  },
});