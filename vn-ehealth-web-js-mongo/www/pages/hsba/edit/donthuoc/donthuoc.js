VueAsyncComponent('donthuoc', '/pages/hsba/edit/donthuoc/donthuoc.html', {
  data: function() {
    return {
      donthuoc: null,
      fileEditing: false
    }
  },

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

  methods:  {
    deleteDonthuoc: function(id) {
      if(confirm('Bạn có muốn xóa ảnh tổn thương này không?')){
        alert(id);
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
    var hs_id = this.getParam("hs_id");
    var hsba = await this.get('/api/hsba/get_hs', { hoso_id: hs_id });
    if(hsba) {
      this.donthuoc_list = hsba.emrDonThuocs;
    }
  }
});

VueAsyncComponent('donthuoc-edit', '/pages/hsba/edit/donthuoc/donthuoc_edit.html', {
  data: function() {
    return {
    }
  },
  props: ["donthuoc"],
  
  methods: {
    viewDonthuocList: function() {
      this.$emit('viewDonthuocList');
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
    }
  },
});
