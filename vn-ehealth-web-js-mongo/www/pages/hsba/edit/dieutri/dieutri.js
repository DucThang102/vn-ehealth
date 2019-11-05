VueAsyncComponent('dieutri', '/pages/hsba/edit/dieutri/dieutri.html', {
  data: function() {
    return {
      dieutri: null,
      fileEditing: false
    }
  },

  props: ["hsba_id"],

  methods: {
    editDieutri: function(dieutri) {
      this.dieutri = dieutri;
      this.fileEditing = false;
    },

    editFiles: function(dieutri) {
      this.dieutri = dieutri;
      this.fileEditing = true;
    },

    viewDieutriList: function() {
      this.dieutri = null;
    }
  },

});

VueAsyncComponent('dieutri-list', '/pages/hsba/edit/dieutri/dieutri_list.html', {
  data: function(){
    return {
      vaokhoa_list : null
    }    
  },

  props: ["hsba_id"],

  methods:  {
    editDieutri : function(dieutri) {
      this.$emit('editDieutri', dieutri);
    },

    editFiles : function(dieutri) {
      this.$emit('editFiles', dieutri);
    },

    getTenKhoa: function(khoadieutri){
      return khoadieutri.tenkhoa || khoadieutri.emrDmKhoaDieuTri.ten;
    },
  },

  created: async function() {
    this.vaokhoa_list = await this.get('/api/hsba/get_ds_vaokhoa', { hsba_id: this.hsba_id });
  },
});

VueAsyncComponent('dieutri-edit', '/pages/hsba/edit/dieutri/dieutri_edit.html', {
  data: function() {
    return {
    }
  },
  props: ["dieutri"],
  
  methods: {
    viewDieutriList: function() {
      this.$emit('viewDieutriList');
    }    
  },
});

VueAsyncComponent('dieutri-files', '/pages/hsba/edit/dieutri/dieutri_files.html', {
  data: function() {
    return {
    }
  },
  props: ["dieutri"],
  
  methods: {
    viewDieutriList: function() {
      this.$emit('viewDieutriList');
    }
  },
});

