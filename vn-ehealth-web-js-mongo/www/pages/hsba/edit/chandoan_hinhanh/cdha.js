VueAsyncComponent('cdha', '/pages/hsba/edit/chandoan_hinhanh/cdha.html', {
  data: function () {
    return {
      cdha: null,
      fileEditing: false
    }
  },

  methods: {

    editCdha: function (cdha) {
      this.cdha = cdha;
      this.fileEditing = false;
    },

    editFiles: function (cdha) {
      this.cdha = cdha;
      this.fileEditing = true;
    },

    viewCdhaList: function () {
      this.cdha = null;
    }
  },
});

VueAsyncComponent('cdha-list', '/pages/hsba/edit/chandoan_hinhanh/cdha_list.html', {
  data: function () {
    return {
      cdha_list: null,
    }
  },

  methods: {
    deleteCdha: function (id) {
      if (confirm('Bạn có muốn xóa ảnh tổn thương này không?')) {
        alert(id);
      }
    },

    editCdha: function (cdha) {
      this.$emit('editCdha', cdha);
    },
    editFiles: function (cdha) {
      this.$emit('editFiles', cdha);
    },
  },

  created: async function () {
    var hs_id = this.getParam("hs_id");
    var hsba = await this.get('/api/hsba/get_hs', { hoso_id: hs_id });
    if (hsba) {
      this.cdha_list = hsba.emrChanDoanHinhAnhs;
    }
  }
});

VueAsyncComponent('cdha-edit', '/pages/hsba/edit/chandoan_hinhanh/cdha_edit.html', {
  data: function () {
    return {
    }
  },
  props: ["cdha"],

  methods: {
    viewCdhaList: function () {
      this.$emit('viewCdhaList');
    }
  },
});

VueAsyncComponent('cdha-files', '/pages/hsba/edit/chandoan_hinhanh/cdha_files.html', {
  data: function () {
    return {
    }
  },
  props: ["cdha"],

  methods: {
    viewCdhaList: function () {
      this.$emit('viewCdhaList');
    }
  },
});
