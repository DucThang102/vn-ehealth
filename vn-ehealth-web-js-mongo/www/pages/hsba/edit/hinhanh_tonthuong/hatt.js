VueAsyncComponent('hatt', '/pages/hsba/edit/hinhanh_tonthuong/hatt.html', {
  data: function() {
    return {
      hatt: null,
      fileEditing: false
    }
  },

  props: ["hsba_id"],

  methods: {
    
    editHatt: function(hatt) {
      this.hatt = hatt;
      this.fileEditing = false;
    },

    editFiles: function(hatt) {
      this.hatt = hatt;
      this.fileEditing = true;
    },

    viewHattList: function() {
      this.hatt = null;
    }
  },
});

VueAsyncComponent('hatt-list', '/pages/hsba/edit/hinhanh_tonthuong/hatt_list.html', {
  data: function(){
    return {
      hatt_list : null,
    }    
  },

  props: ["hsba_id"],

  methods:  {
    deleteHatt: function(id) {
      if(confirm('Bạn có muốn xóa ảnh tổn thương này không?')){
        alert(id);
      }
    },

    editHatt : function(hatt) {
      this.$emit('editHatt', hatt);
    },
    
    editFiles : function(hatt) {
      this.$emit('editFiles', hatt);
    },
  },

  created: async function() {
    this.hatt_list = await this.get('/api/hsba/get_ds_hatt', { hsba_id: this.hsba_id });
  }
});

VueAsyncComponent('hatt-edit', '/pages/hsba/edit/hinhanh_tonthuong/hatt_edit.html', {
  data: function() {
    return {
    }
  },
  props: ["hatt"],
  
  methods: {
    viewHattList: function() {
      this.$emit('viewHattList');
    }
  },
});

VueAsyncComponent('hatt-files', '/pages/hsba/edit/hinhanh_tonthuong/hatt_files.html', {
  data: function() {
    return {
    }
  },
  props: ["hatt"],
  
  methods: {
    viewHattList: function() {
      this.$emit('viewHattList');
    }
  },
});
