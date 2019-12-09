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
});

VueAsyncComponent('dieutri-list', '/pages/hsba/edit/dieutri/dieutri_list.html', {
  data: function(){
    return {
      dieutri_list : null,
      vaokhoa_list: null
    }    
  },

  props: ["hsba_id"],

  methods:  {
    getDieutriList: async function() {
      this.dieutri_list = await this.get('/api/dieutri/get_ds_dieutri', { hsba_id: this.hsba_id });
    },

    getVaoKhoaList: async function() {
      this.vaokhoa_list = await this.get('/api/vaokhoa/get_ds_vaokhoa', {hsba_id: this.hsba_id, detail: false});
    },

    editDieutri : function(dieutri) {
      this.$emit('editDieutri', dieutri);
    },

    addDieutri: async function() {
      if(this.vaokhoa_list && this.vaokhoa_list.length > 0) {        
        var maKhoaDieuTri = this.vaokhoa_list[0].emrDmKhoaDieuTri.ma;
        var dieutri = { 
          emrVaoKhoa: {emrHoSoBenhAnId: this.hsba_id, emrDmKhoaDieuTri:{ma : maKhoaDieuTri}},
          emrQuaTrinhDieuTris: []
        };
        
        this.$emit('editDieutri', dieutri);
      }else{
        alert('Hồ sơ bệnh án chưa có khoa điều trị');
      }
    },

    deleteDieutri: async function(id) {
      if (confirm('Bạn có muốn xóa điều trị này không?')) {
        var result = await this.get("/api/dieutri/delete_dieutri", {dieutri_id: id});
        if(result.success) {
          this.getDieutriList();
        }else {
          alert('Lỗi xảy ra quá trình xóa');
        }
      }
    },

    editFiles : function(dieutri) {
      this.$emit('editFiles', dieutri);
    },
  },

  created: async function() {
    this.getVaoKhoaList();
    this.getDieutriList();    
  },
});

VueAsyncComponent('dieutri-edit', '/pages/hsba/edit/dieutri/dieutri_edit.html', {
  data: function() {
    return {
      maVaoKhoa: '',
      vaokhoa_list: null,
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

  created: async function() {
    this.vaokhoa_list = await this.get('/api/vaokhoa/get_ds_vaokhoa', 
                          {hsba_id: this.dieutri.emrVaoKhoa.emrHoSoBenhAnId, detail: false});
    this.maVaoKhoa = this.dieutri.emrVaoKhoa.emrDmKhoaDieuTri.ma;
  },
  
  methods: {
    addQtdt: function() {
      this.dieutri.emrQuaTrinhDieuTris.push({});
    },

    deleteQtdt: function(index) {
      this.dieutri.emrQuaTrinhDieuTris.splice(index, 1);
    },
    
    saveDieutri: async function() {
      var emrVaoKhoa = this.vaokhoa_list.find(x => x.emrDmKhoaDieuTri.ma == this.maVaoKhoa);
      this.dieutri.emrVaoKhoaId = attr(emrVaoKhoa, "id");

      var result = await this.post("/api/dieutri/create_or_update_dieutri", this.dieutri);

      if(result.success) {
        sessionStorage.removeItem('dataChange');
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

