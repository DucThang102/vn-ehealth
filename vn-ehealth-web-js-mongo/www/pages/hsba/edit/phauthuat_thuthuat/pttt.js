VueAsyncComponent('pttt', '/pages/hsba/edit/phauthuat_thuthuat/pttt.html', {
  data: function() {
    return {
      pttt: null,
      fileEditing: false
    }
  },

  props: ["hsba_id"],

  methods: {
    
    editPttt: function(pttt) {
      this.pttt = pttt;
      this.fileEditing = false;
    },

    editFiles: function(pttt) {
      this.pttt = pttt;
      this.fileEditing = true;
    },

    viewPtttList: function() {
      this.pttt = null;
    }
  },
});

VueAsyncComponent('pttt-list', '/pages/hsba/edit/phauthuat_thuthuat/pttt_list.html', {
  data: function(){
    return {
      pttt_list : null,
    }    
  },

  props: ["hsba_id"],

  methods:  {
    getPtttList: async function(){
      this.pttt_list = await this.get('/api/pttt/get_ds_pttt', { hsba_id: this.hsba_id });
    },

    getBacsithuchien: function(pttt) {
      var bacsi = pttt.emrThanhVienPttts.find(
        x => attr(x, "emrDmVaiTro.ma") == "01" || attr(x, "emrDmVaiTro.ma") == "03"
      );
      if (bacsi) {
        return bacsi.tenbacsi;
      }
      return "";
    },

    deletePttt: async function(id) {
      if(confirm('Bạn có muốn xóa phẫu thuật/thủ thuật này không?')){
        var result = await this.get("/api/pttt/delete_pttt", {pttt_id: id});
        if(result.success) {
          this.getPtttList();
        }else {
          alert('Lỗi xảy ra quá trình xóa');
        }
      }
    },

    addPttt : function() {
      var pttt = {
                  emrHoSoBenhAnId: this.hsba_id, 
                  emrThanhVienPttts:[], 
                  emrDmPhauThuThuat: {},
                  emrDmMaBenhChandoantruocs:[],
                  emrDmMaBenhChandoansaus: []
              };
      this.$emit('editPttt', pttt);
    },

    editPttt : function(pttt) {
      this.$emit('editPttt', pttt);
    },
    editFiles : function(pttt) {
      this.$emit('editFiles', pttt);
    },
  },

  created: async function() {
    this.getPtttList();
  }
});

VueAsyncComponent('pttt-edit', '/pages/hsba/edit/phauthuat_thuthuat/pttt_edit.html', {
  data: function() {
    return {
      emrVaiTroPttts : []
    }
  },

  props: ["pttt"],

  computed: {
    emrDmMaBenhChandoantruocs: function() {
      return store.state.emrDmMaBenhChandoantruocs;
    },
    emrDmMaBenhChandoansaus: function() {
      return store.state.emrDmMaBenhChandoansaus;
    },
    emrDmPhauThuThuat: function() {
      return store.state.emrDmPhauThuThuat;
    }    
  },

  created: async function() {
    this.emrVaiTroPttts = await this.get('/api/danhmuc/get_all_dm_list', {dm_type: 'DM_VAI_TRO_PHAU_THU_THUAT'});
  },

  watch: {
    pttt: {
      handler: function (val, oldVal) {
        if (oldVal) {
          sessionStorage.setItem('dataChange', true);
        }
      },
      deep: true
    },

    emrDmMaBenhChandoantruocs: function(val) {
      this.pttt.emrDmMaBenhChandoantruocs = val;
    },

    emrDmMaBenhChandoansaus: function(val) {
      this.pttt.emrDmMaBenhChandoansaus = val;
    },

    emrDmPhauThuThuat: function(val) {
      this.pttt.emrDmPhauThuThuat = val;
    },
  },

  methods: {
    viewPtttList: function() {
      if(sessionStorage.getItem('dataChange')) {
        if(!confirm('Bạn sẽ mất dữ liệu đang sửa, tiếp tục?')){
          return;
        }          
      }
      sessionStorage.removeItem('dataChange');
      this.$emit('viewPtttList');
    },

    getTextChanDoans: function(chandoans){
      if(chandoans && chandoans.length > 0){
        return chandoans.map(x => x.ma + " - " + x.ten).join(' ; ');
      }
      return '';
    },

    addTvpttt: function() {
      this.pttt.emrThanhVienPttts.push({tenbacsi: '', emrDmVaiTro: {ma: ''}});
    },

    deleteTvpttt: function(index) {
      this.pttt.emrThanhVienPttts.splice(index, 1);
    },

    updateEmrDmTen(emrDm, list) {
      emrDm.ten = attr(list.find(x => x.ma == emrDm.ma), 'ten');
    },

    savePttt : async function() {
      this.pttt.emrThanhVienPttts.forEach(tvpttt => {
        this.updateEmrDmTen(tvpttt.emrDmVaiTro, this.emrVaiTroPttts);
      });

      var result = await this.post("/api/pttt/create_or_update_pttt", this.pttt);
      if(result.success) {
        sessionStorage.removeItem('dataChange');
        this.$emit('viewPtttList');
      }else {
        alert('Lỗi xảy ra quá trình lưu thông tin');
      }
    }
  },
});

VueAsyncComponent('pttt-files', '/pages/hsba/edit/phauthuat_thuthuat/pttt_files.html', {
  data: function() {
    return {
    }
  },
  props: ["pttt"],
  
  methods: {
    viewPtttList: function() {
      if(sessionStorage.getItem('dataChange')) {
        if(!confirm('Bạn sẽ mất dữ liệu đang sửa, tiếp tục?')){
          return;
        }          
      }
      sessionStorage.removeItem('dataChange');
      this.$emit('viewPtttList');
    }
  },
});
