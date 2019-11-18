VueAsyncComponent('tdcn', '/pages/hsba/edit/thamdo_chucnang/tdcn.html', {
  data: function () {
    return {
      tdcn: null,
      fileEditing: false
    }
  },

  props: ["hsba_id"],

  methods: {

    editTdcn: function (tdcn) {
      this.tdcn = tdcn;
      this.fileEditing = false;
    },

    editFiles: function (tdcn) {
      this.tdcn = tdcn;
      this.fileEditing = true;
    },

    viewTdcnList: function () {
      this.tdcn = null;
    }
  },
});

VueAsyncComponent('tdcn-list', '/pages/hsba/edit/thamdo_chucnang/tdcn_list.html', {
  data: function () {
    return {
      tdcn_list: null,
    }
  },

  props: ["hsba_id"],

  methods: {
    getTdcnList: async function(){
      this.tdcn_list = await this.get('/api/tdcn/get_ds_tdcn', { hsba_id: this.hsba_id });
    },

    deleteTdcn: async function (id) {
      if (confirm('Bạn có muốn xóa ảnh tổn thương này không?')) {
        var result = await this.get("/api/tdcn/delete_tdcn", {tdcn_id: id});
        if(result.success) {
          this.getTdcnList();
        }else {
          alert('Lỗi xảy ra quá trình xóa');
        }
      }
    },

    addTdcn : function() {
      var tdcn = {emrHoSoBenhAnId: this.hsba_id, emrDmLoaiThamDoChucNang: {}, emrDmThamDoChucNang:{}};
      this.$emit('editTdcn', tdcn);
    },

    editTdcn: function (tdcn) {
      this.$emit('editTdcn', tdcn);
    },

    editFiles: function (tdcn) {
      this.$emit('editFiles', tdcn);
    },
  },

  created: async function () {
    this.getTdcnList();
  }
});

VueAsyncComponent('tdcn-edit', '/pages/hsba/edit/thamdo_chucnang/tdcn_edit.html', {
  data: function () {
    return {
    }
  },
  props: ["tdcn"],

  computed: {
    emrDmLoaiThamDoChucNang: function() {
      return store.state.emrDmLoaiThamDoChucNang;
    },
    emrDmThamDoChucNang: function() {
      return store.state.emrDmThamDoChucNang;
    }
  },

  watch: {
    tdcn: {
      handler: function (val, oldVal) {
        if (oldVal) {
          sessionStorage.setItem('dataChange', true);
        }
      },
      deep: true
    },

    emrDmLoaiThamDoChucNang: function(val) {
      this.tdcn.emrDmLoaiThamDoChucNang = val;
    },

    emrDmThamDoChucNang: function(val) {
      this.tdcn.emrDmThamDoChucNang = val;
    },
  },

  methods: {
    viewTdcnList: function () {
      if(sessionStorage.getItem('dataChange')) {
        if(!confirm('Bạn sẽ mất dữ liệu đang sửa, tiếp tục?')){
          return;
        }          
      }
      sessionStorage.removeItem('dataChange');
      this.$emit('viewTdcnList');
    },

    saveTdcn : async function() {
      var result = await this.post("/api/tdcn/create_or_update_tdcn", this.tdcn);
      if(result.success) {
        sessionStorage.removeItem('dataChange');
        this.$emit('viewTdcnList');
      }else {
        alert('Lỗi xảy ra quá trình lưu thông tin');
      }
    }
  },
});

VueAsyncComponent('tdcn-files', '/pages/hsba/edit/thamdo_chucnang/tdcn_files.html', {
  data: function () {
    return {
    }
  },
  props: ["tdcn"],

  methods: {
    viewTdcnList: function () {
      this.$emit('viewTdcnList');
    }
  },
});
