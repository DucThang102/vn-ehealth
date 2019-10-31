VueAsyncComponent('gpb', '/pages/hsba/edit/giaiphau_benh/gpb.html', {
  data: function () {
    return {
      gpb: null,
      fileEditing: false
    }
  },

  methods: {

    editGpb: function (gpb) {
      this.gpb = gpb;
      this.fileEditing = false;
    },

    editFiles: function (gpb) {
      this.gpb = gpb;
      this.fileEditing = true;
    },

    viewGpbList: function () {
      this.gpb = null;
    }
  },
});

VueAsyncComponent('gpb-list', '/pages/hsba/edit/giaiphau_benh/gpb_list.html', {
  data: function () {
    return {
      gpb_list: null,
    }
  },

  methods: {
    deleteGpb: function (id) {
      if (confirm('Bạn có muốn xóa ảnh tổn thương này không?')) {
        alert(id);
      }
    },

    editGpb: function (gpb) {
      this.$emit('editGpb', gpb);
    },
    editFiles: function (gpb) {
      this.$emit('editFiles', gpb);
    },
  },

  created: async function () {
    var hs_id = this.getParam("hs_id");
    var hsba = await this.get('/api/hsba/get_hs', { hoso_id: hs_id });
    if (hsba) {
      this.gpb_list = hsba.emrGiaiPhauBenhs;
    }
  }
});

VueAsyncComponent('gpb-edit', '/pages/hsba/edit/giaiphau_benh/gpb_edit.html', {
  data: function () {
    return {
    }
  },
  props: ["gpb"],

  methods: {
    viewGpbList: function () {
      this.$emit('viewGpbList');
    }
  },
});

VueAsyncComponent('gpb-files', '/pages/hsba/edit/giaiphau_benh/gpb_files.html', {
  data: function () {
    return {
    }
  },
  props: ["gpb"],

  methods: {
    viewGpbList: function () {
      this.$emit('viewGpbList');
    }
  },
});
