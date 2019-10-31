VueAsyncComponent('xetnghiem', '/pages/hsba/edit/xetnghiem/xetnghiem.html', {
  data: function () {
    return {
      xetnghiem: null,
      editMode: 'xetnghiem'
    }
  },

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

  methods: {
    deleteXetnghiem: function (id) {
      if (confirm('Bạn có muốn xóa phẫu thuật/thủ thuật này không?')) {
        alert(id);
      }
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
    var hs_id = this.getParam("hs_id");
    var hsba = await this.get('/api/hsba/get_hs', { hoso_id: hs_id });
    if (hsba) {
      this.xetnghiem_list = hsba.emrXetNghiems;
    }
  }
});

VueAsyncComponent('xetnghiem-edit', '/pages/hsba/edit/xetnghiem/xetnghiem_edit.html', {
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

VueAsyncComponent('xetnghiem-ketqua', '/pages/hsba/edit/xetnghiem/xetnghiem_ketqua.html', {
  data: function () {
    return {
      maDvxn: '',
      dvxn: null
    }
  },

  props: ["xetnghiem"],

  watch: {
    maDvxn: function(val) {
      this.dvxn = this.xetnghiem.emrXetNghiemDichVus.find(x => x.emrDmXetNghiem.ma == val);
    }
  },

  methods: {
    viewXetnghiemList: function () {
      this.$emit('viewXetnghiemList');
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
