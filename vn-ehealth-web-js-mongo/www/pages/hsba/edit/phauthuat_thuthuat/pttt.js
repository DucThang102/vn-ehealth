VueAsyncComponent('pttt', '/pages/hsba/edit/phauthuat_thuthuat/pttt.html', {
  data: function() {
    return {
      pttt: null,
      fileEditing: false
    }
  },

  props: ["hsba_id"],

  methods: {
    
    editPttt: function(pttt) {
      this.pttt = pttt;
      this.fileEditing = false;
    },

    editFiles: function(pttt) {
      this.pttt = pttt;
      this.fileEditing = true;
    },

    viewPtttList: function() {
      this.pttt = null;
    }
  },
});

VueAsyncComponent('pttt-list', '/pages/hsba/edit/phauthuat_thuthuat/pttt_list.html', {
  data: function(){
    return {
      pttt_list : null,
    }    
  },

  props: ["hsba_id"],

  methods:  {
    deletePttt: function(id) {
      if(confirm('Bạn có muốn xóa phẫu thuật/thủ thuật này không?')){
        alert(id);
      }
    },

    editPttt : function(pttt) {
      this.$emit('editPttt', pttt);
    },
    editFiles : function(pttt) {
      this.$emit('editFiles', pttt);
    },
  },

  created: async function() {
    this.pttt_list = await this.get('/api/pttt/get_ds_pttt', { hsba_id: this.hsba_id });
  }
});

VueAsyncComponent('pttt-edit', '/pages/hsba/edit/phauthuat_thuthuat/pttt_edit.html', {
  data: function() {
    return {
    }
  },
  props: ["pttt"],
  
  methods: {
    viewPtttList: function() {
      this.$emit('viewPtttList');
    }
  },
});

VueAsyncComponent('pttt-files', '/pages/hsba/edit/phauthuat_thuthuat/pttt_files.html', {
  data: function() {
    return {
    }
  },
  props: ["pttt"],
  
  methods: {
    viewPtttList: function() {
      this.$emit('viewPtttList');
    }
  },
});
