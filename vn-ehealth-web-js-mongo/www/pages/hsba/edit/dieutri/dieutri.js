VueAsyncComponent('dieutri', '/pages/hsba/edit/dieutri/dieutri.html', {
  data: function() {
    return {
      dieutri: null,
      fileEditing: false
    }
  },

  props: ["hsba_id"],

  methods: {
    editDieutri: function(dieutri) {
      this.dieutri = dieutri;
      this.fileEditing = false;
    },

    editFiles: function(dieutri) {
      this.dieutri = dieutri;
      this.fileEditing = true;
    },

    viewDieutriList: function() {
      this.dieutri = null;
    }
  },

  created: function() {
    sessionStorage.removeItem('dataChange');
  }
});

VueAsyncComponent('dieutri-list', '/pages/hsba/edit/dieutri/dieutri_list.html', {
  data: function(){
    return {
      dieutri_list : null
    }    
  },

  props: ["hsba_id"],

  methods:  {
    editDieutri : function(dieutri) {
      this.$emit('editDieutri', dieutri);
    },

    addDieutri: async function() {
      var vaokhoa_list = await this.get('/api/vaokhoa/get_ds_vaokhoa', {hsba_id: this.hsba_id, detail: false});
      if(vaokhoa_list && vaokhoa_list.length > 0) {
        
        var dieutri = { 
          emrVaoKhoaId: vaokhoa_list[0].id, 
          emrVaoKhoa: vaokhoa_list[0],
          emrQuaTrinhDieuTris: []
        };
        
        this.$emit('editDieutri', dieutri);
      }else{
        alert('Hồ sơ bệnh án chưa có khoa điều trị');
      }
    },

    editFiles : function(dieutri) {
      this.$emit('editFiles', dieutri);
    },

    getTenKhoa: function(khoadieutri){
      return khoadieutri.tenkhoa || khoadieutri.emrDmKhoaDieuTri.ten;
    },
  },

  created: async function() {
    this.dieutri_list = await this.get('/api/dieutri/get_ds_dieutri', { hsba_id: this.hsba_id });
  },
});

VueAsyncComponent('dieutri-edit', '/pages/hsba/edit/dieutri/dieutri_edit.html', {
  data: function() {
    return {
    }
  },
  props: ["dieutri"],

  watch: {
    dieutri: {
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

    addQtdt: function() {
      this.dieutri.emrQuaTrinhDieuTris.push({});
    },

    deleteQtdt: function(index) {
      this.dieutri.emrQuaTrinhDieuTris.splice(index, 1);
    },
    
    saveDieutri: async function() {
      var result = await this.post("/api/dieutri/create_or_update_dieutri", this.dieutri);
      if(result.success) {
        this.$emit('viewDieutriList');
      }else {
        alert('Lỗi xảy ra quá trình lưu thông tin');
      }
    },

    viewDieutriList: function() {
      if(sessionStorage.getItem('dataChange')) {
        if(!confirm('Bạn sẽ mất dữ liệu đang sửa, tiếp tục?')){
          return;
        }          
      }
      sessionStorage.removeItem('dataChange');
      this.$emit('viewDieutriList');
    }    
  },
});

VueAsyncComponent('dieutri-files', '/pages/hsba/edit/dieutri/dieutri_files.html', {
  data: function() {
    return {
    }
  },
  props: ["dieutri"],
  
  methods: {
    viewDieutriList: function() {
      this.$emit('viewDieutriList');
    }
  },
});

