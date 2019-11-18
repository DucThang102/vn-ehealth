VueAsyncComponent('hoichan', '/pages/hsba/edit/hoichan/hoichan.html', {
  data: function() {
    return {
      hoichan: null,
      fileEditing: false
    }
  },

  props: ["hsba_id"],

  methods: {
    editHoichan: function(hoichan) {
      this.hoichan = hoichan;
      this.fileEditing = false;
    },

    editFiles: function(hoichan) {
      this.hoichan = hoichan;
      this.fileEditing = true;
    },

    viewHoichanList: function() {
      this.hoichan = null;
    }
  },
});

VueAsyncComponent('hoichan-list', '/pages/hsba/edit/hoichan/hoichan_list.html', {
  data: function(){
    return {
      hoichan_list : null
    }    
  },

  props: ["hsba_id"],

  methods:  {

    getHoichanList: async function() {
      this.hoichan_list = await this.get('/api/hoichan/get_ds_hoichan', { hsba_id: this.hsba_id });
    },
    
    addHoichan: async function() {
      var vaokhoa_list = await this.get('/api/vaokhoa/get_ds_vaokhoa', {hsba_id: this.hsba_id, detail: false});
      if(vaokhoa_list && vaokhoa_list.length > 0) {
        
        var hoichan = { 
          emrVaoKhoaId: vaokhoa_list[0].id, 
          emrVaoKhoa: vaokhoa_list[0],
          emrThanhVienHoiChans: []
        };
        
        this.$emit('editHoichan', hoichan);
      }else{
        alert('Hồ sơ bệnh án chưa có khoa điều trị');
      }
    },

    deleteHoichan: async function(id) {
      if (confirm('Bạn có muốn xóa hội chẩn này không?')) {
        var result = await this.get("/api/hoichan/delete_hoichan", {hoichan_id: id});
        if(result.success) {
          this.getHoichanList();
        }else {
          alert('Lỗi xảy ra quá trình xóa');
        }
      }
    },

    editHoichan : function(hoichan) {
      this.$emit('editHoichan', hoichan);
    },

    editFiles : function(hoichan) {
      this.$emit('editFiles', hoichan);
    },

    getTenKhoa: function(khoadieutri){
      return khoadieutri.tenkhoa || khoadieutri.emrDmKhoaDieuTri.ten;
    }
  },

  created: async function() {
    this.getHoichanList();
  },
});

VueAsyncComponent('hoichan-edit', '/pages/hsba/edit/hoichan/hoichan_edit.html', {
  data: function() {
    return {
    }
  },
  props: ["hoichan"],

  watch: {
    hoichan: {
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

    addTvhc: function() {
      this.hoichan.emrThanhVienHoiChans.push({tenbacsi: '', emrDmVaiTro: {ma: ''}});
    },

    deleteTvhc: function(index) {
      this.hoichan.emrThanhVienHoiChans.splice(index, 1);
    },

    saveHoichan: async function() {
      var result = await this.post("/api/hoichan/create_or_update_hoichan", this.hoichan);
      if(result.success) {
        sessionStorage.removeItem('dataChange');
        this.$emit('viewHoichanList');
      }else {
        alert('Lỗi xảy ra quá trình lưu thông tin');
      }
    },

    viewHoichanList: function() {
      if(sessionStorage.getItem('dataChange')) {
        if(!confirm('Bạn sẽ mất dữ liệu đang sửa, tiếp tục?')){
          return;
        }          
      }
      sessionStorage.removeItem('dataChange');
      this.$emit('viewHoichanList');
    }    
  },
});

VueAsyncComponent('hoichan-files', '/pages/hsba/edit/hoichan/hoichan_files.html', {
  data: function() {
    return {
    }
  },
  props: ["hoichan"],
  
  methods: {
    viewHoichanList: function() {
      this.$emit('viewHoichanList');
    }
  },
});

