VueAsyncComponent('cdha', '/pages/hsba/edit/chandoan_hinhanh/cdha.html', {
  data: function () {
    return {
      cdha: null,
      fileEditing: false
    }
  },

  props: ["hsba_id"],

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

  created: function() {
    sessionStorage.removeItem('dataChange');
  }
});

VueAsyncComponent('cdha-list', '/pages/hsba/edit/chandoan_hinhanh/cdha_list.html', {
  data: function () {
    return {
      cdha_list: null,
    }
  },

  props: ["hsba_id"],

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
    this.cdha_list = await this.get('/api/cdha/get_ds_cdha', { hsba_id: this.hsba_id });
  }
});

VueAsyncComponent('cdha-edit', '/pages/hsba/edit/chandoan_hinhanh/cdha_edit.html', {
  data: function () {
    return {
    }
  },
  props: ["cdha"],

  watch: {
    cdha: {
      handler: function (val, oldVal) {
        if (oldVal) {
          sessionStorage.setItem('dataChange', true);
        }
      },
      deep: true
    }
  },

  methods: {
    viewCdhaList: function () {
      if(sessionStorage.getItem('dataChange')) {
        if(!confirm('Bạn sẽ mất dữ liệu đang sửa, tiếp tục?')){
          return;
        }          
      }
      sessionStorage.removeItem('dataChange');
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
