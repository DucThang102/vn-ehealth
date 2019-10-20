VueAsyncComponent('cdha', '/pages/hsba/view_detail/chandoan_hinhanh/cdha.html', {
  data: function() {
    return {
      cdha: null
    }
  },

  methods: {
    xemCdha: function(cdha) {
      this.cdha = cdha;
    },
    xemDsCdha: function() {
      this.cdha = null;
    }
  },
  
  props: ["hsba"]
});

VueAsyncComponent('cdha-list', '/pages/hsba/view_detail/chandoan_hinhanh/cdha_list.html', {
  data: function(){
    return {
      cdha_list : []
    }    
  },

  methods:  {
    xemCdha : function(cdha) {
      this.$emit('xemCdha', cdha);
    },
  },

  props: ["hsba"],

  mounted: function() {
    if(this.hsba) {
      this.cdha_list = this.hsba.emrChanDoanHinhAnhs;
    }
  }  
});

VueAsyncComponent('cdha-view', '/pages/hsba/view_detail/chandoan_hinhanh/cdha_view.html', {
  data: function() {
    return {
    }
  },
  props: ["hsba", "cdha"],
  
  methods: {
    xemDsCdha: function() {
      this.$emit('xemDsCdha');
    }
  },
});