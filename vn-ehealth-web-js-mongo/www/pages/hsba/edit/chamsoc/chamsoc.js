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
      vaokhoa_list : null
    }    
  },

  props: ["hsba_id"],

  methods:  {
    editChamsoc : function(chamsoc) {
      this.$emit('editChamsoc', chamsoc);
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
    this.vaokhoa_list = await this.get('/api/hsba/get_ds_vaokhoa', { hsba_id: this.hsba_id });
  },
});

VueAsyncComponent('chamsoc-edit', '/pages/hsba/edit/chamsoc/chamsoc_edit.html', {
  data: function() {
    return {
    }
  },
  props: ["chamsoc", "hsba_id"],
  
  methods: {
    viewChamsocList: function() {
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

