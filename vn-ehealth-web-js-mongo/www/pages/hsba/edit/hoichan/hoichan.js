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
      hoichan_list : null,
      vaokhoa_list: null
    }    
  },

  props: ["hsba_id"],

  methods:  {

    getHoichanList: async function() {
      this.hoichan_list = await this.get('/api/hoichan/get_ds_hoichan', { hsba_id: this.hsba_id });
    },

    getVaoKhoaList: async function() {
      this.vaokhoa_list = await this.get('/api/vaokhoa/get_ds_vaokhoa', {hsba_id: this.hsba_id});
    },
    
    addHoichan: async function() {
      if(this.vaokhoa_list && this.vaokhoa_list.length > 0) {
  
        var maKhoaDieuTri = this.vaokhoa_list[0].emrDmKhoaDieuTri.ma;
        var hoichan = { 
          emrVaoKhoa: {emrHoSoBenhAnId: this.hsba_id, emrDmKhoaDieuTri:{ma : maKhoaDieuTri}},
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
  },

  created: async function() {
    this.getVaoKhoaList();
    this.getHoichanList();
  },
});

VueAsyncComponent('hoichan-edit', '/pages/hsba/edit/hoichan/hoichan_edit.html', {
  data: function() {
    return {
      maVaoKhoa: '',
      vaokhoa_list: null,
      emrVaiTroHoichans: []
    }
  },
  props: ["hoichan"],

  created: async function() {
    this.emrVaiTroHoichans = await this.get('/api/danhmuc/get_all_dm_list', {dm_type: 'DM_VAI_TRO_HOI_CHAN'});
    this.vaokhoa_list = await this.get('/api/vaokhoa/get_ds_vaokhoa', 
                          {hsba_id: this.hoichan.emrVaoKhoa.emrHoSoBenhAnId});
    this.maVaoKhoa = this.hoichan.emrVaoKhoa.emrDmKhoaDieuTri.ma;
  },

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

    addTvhc: function() {
      this.hoichan.emrThanhVienHoiChans.push({tenbacsi: '', emrDmVaiTro: {ma: ''}});
    },

    deleteTvhc: function(index) {
      this.hoichan.emrThanhVienHoiChans.splice(index, 1);
    },

    updateEmrDmTen(emrDm, list) {
      emrDm.ten = attr(list.find(x => x.ma == emrDm.ma), 'ten');
    },

    saveHoichan: async function() {
      var emrVaoKhoa = this.vaokhoa_list.find(x => x.emrDmKhoaDieuTri.ma == this.maVaoKhoa);
      this.hoichan.emrVaoKhoaId = attr(emrVaoKhoa, "id");

      this.hoichan.emrThanhVienHoiChans.forEach(tvhc => {
        this.updateEmrDmTen(tvhc.emrDmVaiTro, this.emrVaiTroHoichans);
      });

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

