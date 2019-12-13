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
});

VueAsyncComponent('chamsoc-list', '/pages/hsba/edit/chamsoc/chamsoc_list.html', {
  data: function(){
    return {
      chamsoc_list : null,
      vaokhoa_list: null
    }    
  },

  props: ["hsba_id"],

  methods:  {
    getChamsocList: async function() {
      this.chamsoc_list = await this.get('/api/chamsoc/get_ds_chamsoc', { hsba_id: this.hsba_id });
    },

    getVaoKhoaList: async function() {
      this.vaokhoa_list = await this.get('/api/vaokhoa/get_ds_vaokhoa', {hsba_id: this.hsba_id});
    },

    editChamsoc : function(chamsoc) {
      this.$emit('editChamsoc', chamsoc);
    },

    addChamsoc: async function() {
      if(this.vaokhoa_list && this.vaokhoa_list.length > 0) {

        var maKhoaDieuTri = this.vaokhoa_list[0].emrDmKhoaDieuTri.ma;
        var chamsoc = { 
          emrVaoKhoa: {emrHoSoBenhAnId: this.hsba_id, emrDmKhoaDieuTri:{ma : maKhoaDieuTri}},
          emrQuaTrinhChamSocs: []
        };        
        
        this.$emit('editChamsoc', chamsoc);
      }else{
        alert('Hồ sơ bệnh án chưa có khoa điều trị');
      }
    },

    deleteChamsoc: async function(id) {
      if (confirm('Bạn có muốn xóa chăm sóc này không?')) {
        var result = await this.get("/api/chamsoc/delete_chamsoc", {chamsoc_id: id});
        if(result.success) {
          this.getChamsocList();
        }else {
          alert('Lỗi xảy ra quá trình xóa');
        }
      }
    },

    editFiles : function(chamsoc) {
      this.$emit('editFiles', chamsoc);
    },

    getBacsichutoa: function(chamsoc){
      var bacsi = chamsoc.emrThanhVienHoiChans.find(x => attr(x, 'emrDmVaiTro.ma') == "1");
      return bacsi? bacsi.tenbacsi: "";
    }
  },

  created: async function() {
    this.getVaoKhoaList();
    this.getChamsocList();
  },
});

VueAsyncComponent('chamsoc-edit', '/pages/hsba/edit/chamsoc/chamsoc_edit.html', {
  data: function() {
    return {
      maVaoKhoa: '',
      vaokhoa_list: null,
    }
  },
  props: ["chamsoc"],

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

  created: async function() {
    this.vaokhoa_list = await this.get('/api/vaokhoa/get_ds_vaokhoa', 
                          {hsba_id: this.chamsoc.emrVaoKhoa.emrHoSoBenhAnId});
    this.maVaoKhoa = this.chamsoc.emrVaoKhoa.emrDmKhoaDieuTri.ma;
  },
  
  methods: {
   
    addQtcs: function() {
      this.chamsoc.emrQuaTrinhChamSocs.push({});
    },

    deleteQtcs: function(index) {
      this.chamsoc.emrQuaTrinhChamSocs.splice(index, 1);
    },
    
    saveChamsoc: async function() {
      var emrVaoKhoa = this.vaokhoa_list.find(x => x.emrDmKhoaDieuTri.ma == this.maVaoKhoa);
      this.chamsoc.emrVaoKhoaId = attr(emrVaoKhoa, "id");

      var result = await this.post("/api/chamsoc/create_or_update_chamsoc", this.chamsoc);
      if(result.success) {
        sessionStorage.removeItem('dataChange');
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

