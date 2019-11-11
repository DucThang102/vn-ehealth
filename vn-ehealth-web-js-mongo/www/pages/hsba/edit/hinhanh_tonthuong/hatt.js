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
    getHattList: async function(){
      this.hatt_list = await this.get('/api/hatt/get_ds_hatt', { hsba_id: this.hsba_id });
    },

    deleteHatt: async function(id) {
      if(confirm('Bạn có muốn xóa ảnh tổn thương này không?')){
        var result = await this.get("/api/hatt/delete_hatt", {hatt_id: id});
        if(result.success) {
          this.getHattList();
        }else {
          alert('Lỗi xảy ra quá trình xóa');
        }
      }
    },

    addHatt : function() {
      var hatt = {emrHoSoBenhAnId: this.hsba_id};
      this.$emit('editHatt', hatt);
    },

    editHatt : function(hatt) {
      this.$emit('editHatt', hatt);
    },

    editFiles : function(hatt) {
      this.$emit('editFiles', hatt);
    },
  },

  created: async function() {
    this.getHattList();
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
    },

    saveHatt : async function() {
      var result = await this.post("/api/hatt/create_or_update_hatt", this.hatt);
      if(result.success) {
        this.$emit('viewHattList');
      }else {
        alert('Lỗi xảy ra quá trình lưu thông tin');
      }
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
