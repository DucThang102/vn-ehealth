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
      chucnangsong_list : null
    }    
  },

  props: ["hsba_id"],

  methods:  {
    getChucnangsongList: async function() {
      this.chucnangsong_list = await this.get('/api/chucnangsong/get_ds_chucnangsong', { hsba_id: this.hsba_id });
    },

    editChucnangsong : function(chucnangsong) {
      this.$emit('editChucnangsong', chucnangsong);
    },

    addChucnangsong: async function() {
      var vaokhoa_list = await this.get('/api/vaokhoa/get_ds_vaokhoa', {hsba_id: this.hsba_id, detail: false});
      if(vaokhoa_list && vaokhoa_list.length > 0) {
        
        var chucnangsong = { 
          emrVaoKhoaId: vaokhoa_list[0].id, 
          emrVaoKhoa: vaokhoa_list[0],
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

    getTenKhoa: function(khoadieutri){
      return khoadieutri.tenkhoa || khoadieutri.emrDmKhoaDieuTri.ten;
    },

    getBacsichutoa: function(chucnangsong){
      var bacsi = chucnangsong.emrThanhVienHoiChans.find(x => x.emrDmVaiTro.ma == "1");
      if(bacsi){
        return bacsi.tenbacsi;
      }
      return "";
    }
  },

  created: async function() {
    this.getChucnangsongList();
  },
});

VueAsyncComponent('chucnangsong-edit', '/pages/hsba/edit/chucnangsong/chucnangsong_edit.html', {
  data: function() {
    return {
    }
  },
  props: ["chucnangsong"],

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
    getTenKhoa: function(khoadieutri){
      return khoadieutri.tenkhoa || khoadieutri.emrDmKhoaDieuTri.ten;
    },

    addCnsct: function() {
      this.chucnangsong.emrChucNangSongChiTiets.push({});
    },

    deleteCnsct: function(index) {
      this.chucnangsong.emrChucNangSongChiTiets.splice(index, 1);
    },
    
    saveChucnangsong: async function() {
      var result = await this.post("/api/chucnangsong/create_or_update_chucnangsong", this.chucnangsong);
      if(result.success) {
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

