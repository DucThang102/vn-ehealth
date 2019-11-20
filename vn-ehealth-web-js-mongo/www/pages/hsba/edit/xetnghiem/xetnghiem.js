VueAsyncComponent('xetnghiem', '/pages/hsba/edit/xetnghiem/xetnghiem.html', {
  data: function () {
    return {
      xetnghiem: null,
      editMode: 'xetnghiem'
    }
  },

  props: ["hsba_id"],

  methods: {

    editXetnghiem: function (xetnghiem) {
      this.xetnghiem = xetnghiem;
      this.editMode = 'xetnghiem';
    },

    editKetqua: function (xetnghiem) {
      this.xetnghiem = xetnghiem;
      this.editMode = 'ketqua';
    },

    editFiles: function (xetnghiem) {
      this.xetnghiem = xetnghiem;
      this.editMode = 'files';
    },

    viewXetnghiemList: function () {
      this.xetnghiem = null;
    }
  },
});

VueAsyncComponent('xetnghiem-list', '/pages/hsba/edit/xetnghiem/xetnghiem_list.html', {
  data: function () {
    return {
      xetnghiem_list: null,
    }
  },

  props: ["hsba_id"],

  methods: {
    getXetnghiemList: async function() {
      this.xetnghiem_list = await this.get('/api/xetnghiem/get_ds_xetnghiem', { hsba_id: this.hsba_id });
    },

    deleteXetnghiem: async function (id) {
      if (confirm('Bạn có muốn xóa xét nghiệm này không?')) {
        var result = await this.get("/api/xetnghiem/delete_xetnghiem", {xetnghiem_id: id});
        if(result.success) {
          this.getXetnghiemList();
        }else {
          alert('Lỗi xảy ra quá trình xóa');
        }
      }
    },

    addXetnghiem : function() {
      var xetnghiem = {emrHoSoBenhAnId: this.hsba_id, emrDmLoaiXetNghiem: {}, emrXetNghiemDichVus:[]};
      this.$emit('editXetnghiem', xetnghiem);
    },

    editXetnghiem: function (xetnghiem) {
      this.$emit('editXetnghiem', xetnghiem);
    },

    editKetqua: function (xetnghiem) {
      this.$emit('editKetqua', xetnghiem);
    },

    editFiles: function (xetnghiem) {
      this.$emit('editFiles', xetnghiem);
    },
  },

  created: async function () {
    this.getXetnghiemList();
  }
});

VueAsyncComponent('xetnghiem-edit', '/pages/hsba/edit/xetnghiem/xetnghiem_edit.html', {
  data: function () {
    return {
      dvxn: {},
      emrDmLoaiXetNghiems: []
    }
  },
  props: ["xetnghiem"],

  created: async function() {
    this.emrDmLoaiXetNghiems = await this.get('/api/danhmuc/get_all_dm_list', {dm_type: 'DM_LOAI_XET_NGHIEM'});
  },

  computed: {
    emrDmXetNghiem: function() {
      return store.state.emrDmXetNghiem;
    },
  },

  watch: {
    xetnghiem: {
      handler: function (val, oldVal) {
        if (oldVal) {
          sessionStorage.setItem('dataChange', true);
        }
      },
      deep: true
    },

    emrDmXetNghiem: function(val) {
      this.dvxn.emrDmXetNghiem = val;
      this.dvxn.emrXetNghiemKetQuas = [];
    },
  },

  methods: {
    viewXetnghiemList: function () {
      if(sessionStorage.getItem('dataChange')) {
        if(!confirm('Bạn sẽ mất dữ liệu đang sửa, tiếp tục?')){
          return;
        }          
      }
      sessionStorage.removeItem('dataChange');
      this.$emit('viewXetnghiemList');
    },

    openXetnghiemModal: function(dvxn) {
      this.dvxn = dvxn;
      $('#dmXetnghiemSelect').modal();
    },

    addDvxn: function() {
      this.xetnghiem.emrXetNghiemDichVus.push({emrDmXetNghiem: {ma: ''}, emrXetNghiemKetQuas:[]});
    },

    deleteDvxn: function(index) {
      this.xetnghiem.emrXetNghiemDichVus.splice(index, 1);
    },

    updateEmrDmTen(emrDm, list) {
      emrDm.ten = attr(list.find(x => x.ma == emrDm.ma), 'ten');
    },

    saveXetnghiem: async function() {
      this.updateEmrDmTen(this.xetnghiem.emrDmLoaiXetNghiem, this.emrDmLoaiXetNghiems);
      
      var result = await this.post("/api/xetnghiem/create_or_update_xetnghiem", this.xetnghiem);
      if(result.success) {
        sessionStorage.removeItem('dataChange');
        this.$emit('viewXetnghiemList');
      }else {
        alert('Lỗi xảy ra quá trình lưu thông tin');
      }
    }
  },
});

VueAsyncComponent('xetnghiem-ketqua', '/pages/hsba/edit/xetnghiem/xetnghiem_ketqua.html', {
  data: function () {
    return {
      maDvxn: '',
      dvxn: null,
      xnkq: {}
    }
  },

  props: ["xetnghiem"],

  computed: {
    emrDmChiSoXetNghiem: function() {
      return store.state.emrDmChiSoXetNghiem;
    },
  },

  watch: {
    maDvxn: function(val) {
      this.dvxn = this.xetnghiem.emrXetNghiemDichVus.find(x => x.emrDmXetNghiem.ma == val);
    },

    emrDmChiSoXetNghiem: function(val) {
      this.xnkq.emrDmChiSoXetNghiem = val;
    },
  },

  methods: {
    viewXetnghiemList: function () {      
      this.$emit('viewXetnghiemList');
    },

    openChiSoXetNghiemModal: function(xnkq) {
      this.xnkq = xnkq;
      $('#dmChiSoXetNghiemSelect').modal();
    },

    addXnkq: function() {
      this.dvxn.emrXetNghiemKetQuas.push({emrDmChiSoXetNghiem: {}});
    },

    deleteXnkq: function(index) {
      this.dvxn.emrXetNghiemKetQuas.splice(index, 1);
    },

    saveXetnghiem: async function() {
      var result = await this.post("/api/xetnghiem/create_or_update_xetnghiem", this.xetnghiem);
      if(result.success) {
        sessionStorage.removeItem('dataChange');
        this.$emit('viewXetnghiemList');
      }else {
        alert('Lỗi xảy ra quá trình lưu thông tin');
      }
    }
  },
});

VueAsyncComponent('xetnghiem-files', '/pages/hsba/edit/xetnghiem/xetnghiem_files.html', {
  data: function () {
    return {
      
    }
  },
  props: ["xetnghiem"],

  methods: {
    viewXetnghiemList: function () {
      this.$emit('viewXetnghiemList');
    }
  },
});
