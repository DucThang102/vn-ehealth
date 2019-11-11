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
    this.hoichan_list = await this.get('/api/hoichan/get_ds_hoichan', { hsba_id: this.hsba_id });
  },
});

VueAsyncComponent('hoichan-edit', '/pages/hsba/edit/hoichan/hoichan_edit.html', {
  data: function() {
    return {
    }
  },
  props: ["hoichan"],
  
  methods: {
    getTenKhoa: function(khoadieutri){
      return khoadieutri.tenkhoa || khoadieutri.emrDmKhoaDieuTri.ten;
    },

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

