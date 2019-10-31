VueAsyncComponent('pttt', '/pages/hsba/edit/phauthuat_thuthuat/pttt.html', {
  data: function() {
    return {
      pttt: null,
      fileEditing: false
    }
  },

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
    var hs_id = this.getParam("hs_id");
    var hsba = await this.get('/api/hsba/get_hs', { hoso_id: hs_id });
    if(hsba) {
      this.pttt_list = hsba.emrPhauThuatThuThuats;
    }
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
