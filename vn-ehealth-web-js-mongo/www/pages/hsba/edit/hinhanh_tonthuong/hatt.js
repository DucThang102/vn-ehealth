VueAsyncComponent('hatt', '/pages/hsba/edit/hinhanh_tonthuong/hatt.html', {
  data: function() {
    return {
      hatt: null,
      fileEditing: false
    }
  },

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
    var hs_id = this.getParam("hs_id");
    var hsba = await this.get('/api/hsba/get_hs', { hoso_id: hs_id });
    if(hsba) {
      this.hatt_list = hsba.emrHinhAnhTonThuongs;
    }
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
