VueAsyncComponent('chucnangsong', '/pages/hsba/edit/chucnangsong/chucnangsong.html', {
  data: function() {
    return {
      chucnangsong: null,
      fileEditing: false
    }
  },

  props: ["hsba_id"],

  methods: {
    editChucnangsong: function(chucnangsong) {
      this.chucnangsong = chucnangsong;
      this.fileEditing = false;
    },

    editFiles: function(chucnangsong) {
      this.chucnangsong = chucnangsong;
      this.fileEditing = true;
    },

    viewChucnangsongList: function() {
      this.chucnangsong = null;
    }
  },
});

VueAsyncComponent('chucnangsong-list', '/pages/hsba/edit/chucnangsong/chucnangsong_list.html', {
  data: function(){
    return {
      chucnangsong_list : null,
      vaokhoa_list: null
    }    
  },

  props: ["hsba_id"],

  methods:  {
    getChucnangsongList: async function() {
      this.chucnangsong_list = await this.get('/api/chucnangsong/get_ds_chucnangsong', { hsba_id: this.hsba_id });
    },

    getVaoKhoaList: async function() {
      this.vaokhoa_list = await this.get('/api/vaokhoa/get_ds_vaokhoa', {hsba_id: this.hsba_id, detail: false});
    },

    editChucnangsong : function(chucnangsong) {
      this.$emit('editChucnangsong', chucnangsong);
    },

    addChucnangsong: async function() {
      if(this.vaokhoa_list && this.vaokhoa_list.length > 0) {

        var maKhoaDieuTri = this.vaokhoa_list[0].emrDmKhoaDieuTri.ma;
        var chucnangsong = { 
          emrVaoKhoa: {emrHoSoBenhAnId: this.hsba_id, emrDmKhoaDieuTri:{ma : maKhoaDieuTri}},
          emrChucNangSongChiTiets: []
        };        
        
        this.$emit('editChucnangsong', chucnangsong);
      }else{
        alert('Hồ sơ bệnh án chưa có khoa điều trị');
      }
    },

    deleteChucnangsong: async function(id) {
      if (confirm('Bạn có muốn xóa chức năng sống này không?')) {
        var result = await this.get("/api/chucnangsong/delete_chucnangsong", {chucnangsong_id: id});
        if(result.success) {
          this.getChucnangsongList();
        }else {
          alert('Lỗi xảy ra quá trình xóa');
        }
      }
    },

    editFiles : function(chucnangsong) {
      this.$emit('editFiles', chucnangsong);
    },

    getBacsichutoa: function(chucnangsong){
      var bacsi = chucnangsong.emrThanhVienHoiChans.find(x => attr(x, 'emrDmVaiTro.ma') == "1");
      return bacsi?bacsi.tenbacsi : "";
    }
  },

  created: async function() {
    this.getVaoKhoaList();
    this.getChucnangsongList();
  },
});

VueAsyncComponent('chucnangsong-edit', '/pages/hsba/edit/chucnangsong/chucnangsong_edit.html', {
  data: function() {
    return {
      maVaoKhoa: '',
      vaokhoa_list: null,
    }
  },

  props: ["chucnangsong"],

  created: async function() {
    this.vaokhoa_list = await this.get('/api/vaokhoa/get_ds_vaokhoa', 
                          {hsba_id: this.chucnangsong.emrVaoKhoa.emrHoSoBenhAnId, detail: false});
    this.maVaoKhoa = this.chucnangsong.emrVaoKhoa.emrDmKhoaDieuTri.ma;
  },

  watch: {
    chucnangsong: {
      handler: function (val, oldVal) {
        if (oldVal) {
          sessionStorage.setItem('dataChange', true);
        }
      },
      deep: true
    }
  },
  
  methods: {
    addCnsct: function() {
      this.chucnangsong.emrChucNangSongChiTiets.push({});
    },

    deleteCnsct: function(index) {
      this.chucnangsong.emrChucNangSongChiTiets.splice(index, 1);
    },
    
    saveChucnangsong: async function() {      
      var emrVaoKhoa = this.vaokhoa_list.find(x => x.emrDmKhoaDieuTri.ma == this.maVaoKhoa);
      this.chucnangsong.emrVaoKhoaId = attr(emrVaoKhoa, "id");

      var result = await this.post("/api/chucnangsong/create_or_update_chucnangsong", this.chucnangsong);
      if(result.success) {
        sessionStorage.removeItem('dataChange');
        this.$emit('viewChucnangsongList');
      }else {
        alert('Lỗi xảy ra quá trình lưu thông tin');
      }
    },

    viewChucnangsongList: function() {
      if(sessionStorage.getItem('dataChange')) {
        if(!confirm('Bạn sẽ mất dữ liệu đang sửa, tiếp tục?')){
          return;
        }          
      }
      sessionStorage.removeItem('dataChange');
      this.$emit('viewChucnangsongList');
    }    
  },
});

VueAsyncComponent('chucnangsong-files', '/pages/hsba/edit/chucnangsong/chucnangsong_files.html', {
  data: function() {
    return {
    }
  },
  props: ["chucnangsong"],
  
  methods: {
    viewChucnangsongList: function() {
      this.$emit('viewChucnangsongList');
    }
  },
});

