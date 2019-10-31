VueAsyncComponent('tdcn', '/pages/hsba/edit/thamdo_chucnang/tdcn.html', {
  data: function () {
    return {
      tdcn: null,
      fileEditing: false
    }
  },

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
    var hs_id = this.getParam("hs_id");
    var hsba = await this.get('/api/hsba/get_hs', { hoso_id: hs_id });
    if (hsba) {
      this.tdcn_list = hsba.emrThamDoChucNangs;
    }
  }
});

VueAsyncComponent('tdcn-edit', '/pages/hsba/edit/thamdo_chucnang/tdcn_edit.html', {
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
