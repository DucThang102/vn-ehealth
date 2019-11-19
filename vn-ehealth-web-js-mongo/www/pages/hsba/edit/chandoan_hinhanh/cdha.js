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
});

VueAsyncComponent('cdha-list', '/pages/hsba/edit/chandoan_hinhanh/cdha_list.html', {
  data: function () {
    return {
      cdha_list: null,
    }
  },

  props: ["hsba_id"],

  methods: {
    getCdhaList: async function(){
      this.cdha_list = await this.get('/api/cdha/get_ds_cdha', { hsba_id: this.hsba_id });
    },

    deleteCdha: async function (id) {
      if (confirm('Bạn có muốn xóa chẩn đoán hình ảnh này không?')) {
        var result = await this.get("/api/cdha/delete_cdha", {cdha_id: id});
        if(result.success) {
          this.getCdhaList();
        }else {
          alert('Lỗi xảy ra quá trình xóa');
        }
      }
    },

    addCdha : function() {
      var cdha = {emrHoSoBenhAnId: this.hsba_id, emrDmLoaiChanDoanHinhAnh: {}, emrDmChanDoanHinhAnh:{}};
      this.$emit('editCdha', cdha);
    },

    editCdha: function (cdha) {
      this.$emit('editCdha', cdha);
    },
    editFiles: function (cdha) {
      this.$emit('editFiles', cdha);
    },
  },

  created: async function () {
    this.getCdhaList();
  }
});

VueAsyncComponent('cdha-edit', '/pages/hsba/edit/chandoan_hinhanh/cdha_edit.html', {
  data: function () {
    return {
      emrDmLoaiChanDoanHinhAnhs: []
    }
  },
  props: ["cdha"],

  created: async function() {
    this.emrDmLoaiChanDoanHinhAnhs = await this.get('/api/danhmuc/get_all_dm_list', {dm_type: 'DM_LOAI_CHAN_DOAN_HINH_ANH'});    
  },

  computed: {
    emrDmChanDoanHinhAnh: function() {
      return store.state.emrDmChanDoanHinhAnh;
    }
  },

  watch: {    
    cdha: {
      handler: function (val, oldVal) {
        if (oldVal) {
          sessionStorage.setItem('dataChange', true);
        }
        val.emrDmLoaiChanDoanHinhAnh = this.emrDmLoaiChanDoanHinhAnhs.find(x => x.ma == attr(val, 'emrDmLoaiChanDoanHinhAnh.ma'));
      },
      deep: true
    },

    emrDmChanDoanHinhAnh: function(val) {
      this.cdha.emrDmChanDoanHinhAnh = val;
    },
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
    },

    saveCdha : async function() {
      var result = await this.post("/api/cdha/create_or_update_cdha", this.cdha);
      if(result.success) {
        sessionStorage.removeItem('dataChange');
        this.$emit('viewCdhaList');
      }else {
        alert('Lỗi xảy ra quá trình lưu thông tin');
      }
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
