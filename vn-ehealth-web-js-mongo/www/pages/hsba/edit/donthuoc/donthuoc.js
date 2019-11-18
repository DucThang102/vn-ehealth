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
    this.donthuoc_list = await this.get('/api/donthuoc/get_ds_donthuoc', { hsba_id: this.hsba_id });
  }
});

VueAsyncComponent('donthuoc-edit', '/pages/hsba/edit/donthuoc/donthuoc_edit.html', {
  data: function() {
    return {
    }
  },
  props: ["donthuoc"],

  watch: {
    donthuoc: {
      handler: function (val, oldVal) {
        if (oldVal) {
          sessionStorage.setItem('dataChange', true);
        }
      },
      deep: true
    }
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
