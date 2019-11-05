VueAsyncComponent('chamsoc', '/pages/hsba/edit/chamsoc/chamsoc.html', {
  data: function() {
    return {
      chamsoc: null,
      fileEditing: false
    }
  },

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
    var hs_id = this.getParam("hs_id");
    var hsba = await this.get('/api/hsba/get_hs', { hoso_id: hs_id });
    if(hsba) {
      this.vaokhoa_list = hsba.emrVaoKhoas;
    }
  },
});

VueAsyncComponent('chamsoc-edit', '/pages/hsba/edit/chamsoc/chamsoc_edit.html', {
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

