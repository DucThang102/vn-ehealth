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

  created: function() {
    sessionStorage.removeItem('dataChange');
  }
});

VueAsyncComponent('tdcn-list', '/pages/hsba/edit/thamdo_chucnang/tdcn_list.html', {
  data: function () {
    return {
      tdcn_list: null,
    }
  },

  props: ["hsba_id"],

  methods: {
    deleteTdcn: function (id) {
      if (confirm('Bạn có muốn xóa ảnh tổn thương này không?')) {
        alert(id);
      }
    },

    editTdcn: function (tdcn) {
      this.$emit('editTdcn', tdcn);
    },
    editFiles: function (tdcn) {
      this.$emit('editFiles', tdcn);
    },
  },

  created: async function () {
    this.tdcn_list = await this.get('/api/tdcn/get_ds_tdcn', { hsba_id: this.hsba_id });
  }
});

VueAsyncComponent('tdcn-edit', '/pages/hsba/edit/thamdo_chucnang/tdcn_edit.html', {
  data: function () {
    return {
    }
  },
  props: ["tdcn"],

  watch: {
    tdcn: {
      handler: function (val, oldVal) {
        if (oldVal) {
          sessionStorage.setItem('dataChange', true);
        }
      },
      deep: true
    }
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
