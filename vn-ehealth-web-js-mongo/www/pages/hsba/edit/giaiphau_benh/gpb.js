VueAsyncComponent('gpb', '/pages/hsba/edit/giaiphau_benh/gpb.html', {
  data: function () {
    return {
      gpb: null,
      fileEditing: false
    }
  },

  props: ["hsba_id"],

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

  props: ["hsba_id"],

  methods: {
    getGpbList: async function(){
      this.gpb_list = await this.get('/api/gpb/get_ds_gpb', { hsba_id: this.hsba_id });
    },

    deleteGpb: async function (id) {
      if (confirm('Bạn có muốn xóa ảnh tổn thương này không?')) {
        var result = await this.get("/api/gpb/delete_gpb", {gpb_id: id});
        if(result.success) {
          this.getGpbList();
        }else {
          alert('Lỗi xảy ra quá trình xóa');
        }
      }
    },

    addGpb : function() {
      var gpb = {emrHoSoBenhAnId: this.hsba_id, emrDmLoaiGiaiPhauBenh: {}, emrDmGiaiPhauBenh:{}, emrDmViTriLayMau: {}};
      this.$emit('editGpb', gpb);
    },

    editGpb: function (gpb) {
      this.$emit('editGpb', gpb);
    },

    editFiles: function (gpb) {
      this.$emit('editFiles', gpb);
    },
  },

  created: async function () {
    this.getGpbList();
  }
});

VueAsyncComponent('gpb-edit', '/pages/hsba/edit/giaiphau_benh/gpb_edit.html', {
  data: function () {
    return {
      emrDmLoaiGiaiPhauBenhs: [],
      emrDmViTriLayMaus : []
    }
  },
  props: ["gpb"],

  created: async function() {
    this.emrDmLoaiGiaiPhauBenhs = await this.get('/api/danhmuc/get_all_dm_list', {dm_type: 'DM_LOAI_GIAI_PHAU_BENH'});
    this.emrDmViTriLayMaus = await this.get('/api/danhmuc/get_all_dm_list', {dm_type: 'DM_VI_TRI_LAY_MAU'});
  },

  computed: {
    emrDmGiaiPhauBenh: function() {
      return store.state.emrDmGiaiPhauBenh;
    }
  },

  watch: {
    gpb: {
      handler: function (val, oldVal) {
        if (oldVal) {
          sessionStorage.setItem('dataChange', true);
        }
      },
      deep: true
    },

    emrDmGiaiPhauBenh: function(val) {
      this.gpb.emrDmGiaiPhauBenh = val;
    },
  },

  methods: {
    viewGpbList: function () {
      if(sessionStorage.getItem('dataChange')) {
        if(!confirm('Bạn sẽ mất dữ liệu đang sửa, tiếp tục?')){
          return;
        }          
      }
      sessionStorage.removeItem('dataChange');
      this.$emit('viewGpbList');
    },

    updateEmrDmTen(emrDm, list) {
      emrDm.ten = attr(list.find(x => x.ma == emrDm.ma), 'ten');
    },

    saveGpb : async function() {
      this.updateEmrDmTen(this.gpb.emrDmLoaiGiaiPhauBenh, this.emrDmLoaiGiaiPhauBenhs);
      
      console.log(this.gpb);

      var result = await this.post("/api/gpb/create_or_update_gpb", this.gpb);
      if(result.success) {
        sessionStorage.removeItem('dataChange');
        this.$emit('viewGpbList');
      }else {
        alert('Lỗi xảy ra quá trình lưu thông tin');
      }
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
