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
      vaokhoa_list : null
    }    
  },

  props: ["hsba_id"],

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
    this.vaokhoa_list = await this.get('/api/hsba/get_ds_vaokhoa', { hsba_id: this.hsba_id });
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

