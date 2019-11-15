VueAsyncComponent('chamsoc', '/pages/hsba/edit/chamsoc/chamsoc.html', {
  data: function() {
    return {
      chamsoc: null,
      fileEditing: false
    }
  },

  props: ["hsba_id"],

  methods: {
    editChamsoc: function(chamsoc) {
      this.chamsoc = chamsoc;
      this.fileEditing = false;
    },

    editFiles: function(chamsoc) {
      this.chamsoc = chamsoc;
      this.fileEditing = true;
    },

    viewChamsocList: function() {
      this.chamsoc = null;
    }
  },

  created: function() {
    sessionStorage.removeItem('dataChange');
  }
});

VueAsyncComponent('chamsoc-list', '/pages/hsba/edit/chamsoc/chamsoc_list.html', {
  data: function(){
    return {
      chamsoc_list : null
    }    
  },

  props: ["hsba_id"],

  methods:  {
    editChamsoc : function(chamsoc) {
      this.$emit('editChamsoc', chamsoc);
    },

    addChamsoc: async function() {
      var vaokhoa_list = await this.get('/api/vaokhoa/get_ds_vaokhoa', {hsba_id: this.hsba_id, detail: false});
      if(vaokhoa_list && vaokhoa_list.length > 0) {
        
        var chamsoc = { 
          emrVaoKhoaId: vaokhoa_list[0].id, 
          emrVaoKhoa: vaokhoa_list[0],
          emrQuaTrinhChamSocs: []
        };
        
        this.$emit('editChamsoc', chamsoc);
      }else{
        alert('Hồ sơ bệnh án chưa có khoa điều trị');
      }
    },

    editFiles : function(chamsoc) {
      this.$emit('editFiles', chamsoc);
    },

    getTenKhoa: function(khoadieutri){
      return khoadieutri.tenkhoa || khoadieutri.emrDmKhoaDieuTri.ten;
    },

    getBacsichutoa: function(chamsoc){
      var bacsi = chamsoc.emrThanhVienHoiChans.find(x => x.emrDmVaiTro.ma == "1");
      if(bacsi){
        return bacsi.tenbacsi;
      }
      return "";
    }
  },

  created: async function() {
    this.chamsoc_list = await this.get('/api/chamsoc/get_ds_chamsoc', { hsba_id: this.hsba_id });
  },
});

VueAsyncComponent('chamsoc-edit', '/pages/hsba/edit/chamsoc/chamsoc_edit.html', {
  data: function() {
    return {
    }
  },
  props: ["chamsoc", "hsba_id"],

  watch: {
    chamsoc: {
      handler: function (val, oldVal) {
        if (oldVal) {
          sessionStorage.setItem('dataChange', true);
        }
      },
      deep: true
    }
  },
  
  methods: {
    getTenKhoa: function(khoadieutri){
      return khoadieutri.tenkhoa || khoadieutri.emrDmKhoaDieuTri.ten;
    },

    addQtcs: function() {
      this.chamsoc.emrQuaTrinhChamSocs.push({});
    },

    deleteQtcs: function(index) {
      this.chamsoc.emrQuaTrinhChamSocs.splice(index, 1);
    },
    
    saveChamsoc: async function() {
      var result = await this.post("/api/chamsoc/create_or_update_chamsoc", this.chamsoc);
      if(result.success) {
        this.$emit('viewChamsocList');
      }else {
        alert('Lỗi xảy ra quá trình lưu thông tin');
      }
    },

    viewChamsocList: function() {
      if(sessionStorage.getItem('dataChange')) {
        if(!confirm('Bạn sẽ mất dữ liệu đang sửa, tiếp tục?')){
          return;
        }          
      }
      sessionStorage.removeItem('dataChange');
      this.$emit('viewChamsocList');
    }    
  },
});

VueAsyncComponent('chamsoc-files', '/pages/hsba/edit/chamsoc/chamsoc_files.html', {
  data: function() {
    return {
    }
  },
  props: ["chamsoc"],
  
  methods: {
    viewChamsocList: function() {
      this.$emit('viewChamsocList');
    }
  },
});

