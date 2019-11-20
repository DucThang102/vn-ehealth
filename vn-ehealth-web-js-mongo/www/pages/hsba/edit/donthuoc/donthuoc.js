VueAsyncComponent('donthuoc', '/pages/hsba/edit/donthuoc/donthuoc.html', {
  data: function() {
    return {
      donthuoc: null,
      fileEditing: false
    }
  },

  props: ["hsba_id"],

  methods: {
    
    editDonthuoc: function(donthuoc) {
      this.donthuoc = donthuoc;
      this.fileEditing = false;
    },

    editFiles: function(donthuoc) {
      this.donthuoc = donthuoc;
      this.fileEditing = true;
    },

    viewDonthuocList: function() {
      this.donthuoc = null;
    }
  },
});

VueAsyncComponent('donthuoc-list', '/pages/hsba/edit/donthuoc/donthuoc_list.html', {
  data: function(){
    return {
      donthuoc_list : null,
    }    
  },

  props: ["hsba_id"],

  methods:  {
    getDonthuocList: async function() {
      this.donthuoc_list = await this.get('/api/donthuoc/get_ds_donthuoc', { hsba_id: this.hsba_id });
    },

    addDonthuoc: function() {
      var donthuoc = {emrHoSoBenhAnId: this.hsba_id, emrDonThuocChiTiets: []};
      this.$emit('editDonthuoc', donthuoc);
    },

    deleteDonthuoc: async function(id) {
      if (confirm('Bạn có muốn xóa đơn thuốc này không?')) {
        var result = await this.get("/api/donthuoc/delete_donthuoc", {donthuoc_id: id});
        if(result.success) {
          this.getDonthuocList();
        }else {
          alert('Lỗi xảy ra quá trình xóa');
        }
      }
    },

    editDonthuoc : function(donthuoc) {
      this.$emit('editDonthuoc', donthuoc);
    },
    
    editFiles : function(donthuoc) {
      this.$emit('editFiles', donthuoc);
    },
  },

  created: async function() {
    this.getDonthuocList();
  }
});

VueAsyncComponent('donthuoc-edit', '/pages/hsba/edit/donthuoc/donthuoc_edit.html', {
  data: function() {
    return {
      dtct: {}
    }
  },

  props: ["donthuoc"],

  computed: {
    emrDmThuoc: function() {
      return store.state.emrDmThuoc;
    },

    emrDmtanXuatDungThuoc: function() {
      return store.state.emrDmtanXuatDungThuoc;
    },

    emrDmDuongDungThuoc: function() {
      return store.state.emrDmDuongDungThuoc;
    },
  },

  watch: {
    donthuoc: {
      handler: function (val, oldVal) {
        if (oldVal) {
          sessionStorage.setItem('dataChange', true);
        }
      },
      deep: true
    },

    emrDmThuoc: function(val) {
      this.dtct.emrDmThuoc = val;
    },

    emrDmtanXuatDungThuoc: function(val) {
      this.dtct.emrDmtanXuatDungThuoc = val;
    },

    emrDmDuongDungThuoc: function(val) {
      this.dtct.emrDmDuongDungThuoc = val;
    },    
  },
  
  methods: {
    viewDonthuocList: function() {
      if(sessionStorage.getItem('dataChange')) {
        if(!confirm('Bạn sẽ mất dữ liệu đang sửa, tiếp tục?')){
          return;
        }          
      }
      sessionStorage.removeItem('dataChange');
      this.$emit('viewDonthuocList');
    },

    openDmThuocModal: function(dtct) {
      this.dtct = dtct;
      $('#dmThuocSelect').modal();
    },

    openDmtanXuatDungThuocModal: function(dtct) {
      this.dtct = dtct;
      $('#dmtanXuatDungThuocSelect').modal();
    },

    openDmDuongDungThuocModal: function(dtct) {
      this.dtct = dtct;
      $('#dmDuongDungThuocSelect').modal();
    },

    addDtct: function() {
      this.donthuoc.emrDonThuocChiTiets.push({
        emrDmThuoc: {},
        emrDmtanXuatDungThuoc: {},
        emrDmDuongDungThuoc: {}
      })
    },

    deleteDtct: function(index) {
      this.donthuoc.emrDonThuocChiTiets.splice(index, 1);
    },

    saveDonthuoc: async function() {
      var result = await this.post("/api/donthuoc/create_or_update_donthuoc", this.donthuoc);
      if(result.success) {
        sessionStorage.removeItem('dataChange');
        this.$emit('viewDonthuocList');
      }else {
        alert('Lỗi xảy ra quá trình lưu thông tin');
      }
    }
  },
});

VueAsyncComponent('donthuoc-files', '/pages/hsba/edit/donthuoc/donthuoc_files.html', {
  data: function() {
    return {
    }
  },
  props: ["donthuoc"],
  
  methods: {
    viewDonthuocList: function() {
      this.$emit('viewDonthuocList');
    },
    
  },
});
