VueAsyncComponent('dieutri', '/pages/hsba/edit/dieutri/dieutri.html', {
  data: function() {
    return {
      dieutri: null,
      fileEditing: false
    }
  },

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
    var hs_id = this.getParam("hs_id");
    var hsba = await this.get('/api/hsba/get_hs', { hoso_id: hs_id });
    if(hsba) {
      this.vaokhoa_list = hsba.emrVaoKhoas;
    }
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

