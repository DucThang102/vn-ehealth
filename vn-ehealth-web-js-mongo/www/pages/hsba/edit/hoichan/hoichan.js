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
      vaokhoa_list : null
    }    
  },

  props: ["hsba_id"],

  methods:  {
    editHoichan : function(hoichan) {
      this.$emit('editHoichan', hoichan);
    },

    editFiles : function(hoichan) {
      this.$emit('editFiles', hoichan);
    },

    getTenKhoa: function(khoadieutri){
      return khoadieutri.tenkhoa || khoadieutri.emrDmKhoaDieuTri.ten;
    },

    getBacsichutoa: function(hoichan){
      var bacsi = hoichan.emrThanhVienHoiChans.find(x => x.emrDmVaiTro.ma == "1");
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

VueAsyncComponent('hoichan-edit', '/pages/hsba/edit/hoichan/hoichan_edit.html', {
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

