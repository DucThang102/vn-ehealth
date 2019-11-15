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
        console.log(result.emrHoSoBenhAn);
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
    sessionStorage.removeItem('dataChange');
  }
};

VueAsyncComponent('tthc', '/pages/hsba/edit/qly_benhnhan/thongtin_hanhchinh.html', {
  mixins: [mixin],

  data: function() {
    return {
    }
  },

  computed: {
    emrDmNgheNghiep: function() {
      return store.state.emrDmNgheNghiep;
    },
    emrDmQuocGia: function() {
      return store.state.emrDmQuocGia;
    },
    emrDmDanToc: function() {
      return store.state.emrDmDanToc;
    },
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
    emrDmNgheNghiep: function(val) {
      this.hsba.emrBenhNhan.emrDmNgheNghiep = val;
    },
    emrDmQuocGia: function(val) {
      this.hsba.emrBenhNhan.emrDmQuocGia = val;
    },
    emrDmDanToc: function(val) {
      this.hsba.emrBenhNhan.emrDmDanToc = val;
    },
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
      emrVaoKhoas: [],
      khoadieutri: null
    }
  },

  created: async function() {
    this.emrVaoKhoas = await this.get('/api/vaokhoa/get_ds_vaokhoa', { hsba_id: this.hsba_id, detail: false });
  },

  methods: {
  },
});

VueAsyncComponent('chandoan', '/pages/hsba/edit/qly_benhnhan/chandoan.html', {
  mixins: [mixin],

  data: function() {
    return {
    }
  },
  methods: {
  },
});


VueAsyncComponent('ttrv', '/pages/hsba/edit/qly_benhnhan/tinhtrang_ravien.html', {
  mixins: [mixin],

  data: function() {
    return {
    }
  },

  methods: {
  },
});