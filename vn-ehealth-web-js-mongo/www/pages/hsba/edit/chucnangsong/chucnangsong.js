VueAsyncComponent('chucnangsong', '/pages/hsba/edit/chucnangsong/chucnangsong.html', {
  data: function() {
    return {
      chucnangsong: null,
      fileEditing: false
    }
  },

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
      vaokhoa_list : null
    }    
  },

  methods:  {
    editChucnangsong : function(chucnangsong) {
      this.$emit('editChucnangsong', chucnangsong);
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
    var hs_id = this.getParam("hs_id");
    var hsba = await this.get('/api/hsba/get_hs', { hoso_id: hs_id });
    if(hsba) {
      this.vaokhoa_list = hsba.emrVaoKhoas;
    }
  },
});

VueAsyncComponent('chucnangsong-edit', '/pages/hsba/edit/chucnangsong/chucnangsong_edit.html', {
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

