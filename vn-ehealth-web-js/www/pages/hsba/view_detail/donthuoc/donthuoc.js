VueAsyncComponent('donthuoc', '/pages/hsba/view_detail/donthuoc/donthuoc.html', {
  data: function() {
    return {
      donthuoc: null
    }
  },

  methods: {
    xemDonThuoc: function(donthuoc) {
      this.donthuoc = donthuoc;
    },
    xemDsDonThuoc: function() {
      this.donthuoc = null;
    }
  },
  
  props: ["hsba"]
});

VueAsyncComponent('donthuoc-list', '/pages/hsba/view_detail/donthuoc/donthuoc_list.html', {
  data: function(){
    return {
      donthuoc_list : []
    }    
  },

  methods:  {
    xemDonThuoc : function(donthuoc) {
      this.$emit('xemDonThuoc', donthuoc);
    },
  },

  props: ["hsba"],

  mounted: function() {
    if(this.hsba) {
      this.donthuoc_list = this.hsba.emrDonThuocs;
    }
  }  
});

VueAsyncComponent('donthuoc-view', '/pages/hsba/view_detail/donthuoc/donthuoc_view.html', {
  data: function() {
    return {
    }
  },
  props: ["hsba", "donthuoc"],
  
  methods: {
    xemDsDonThuoc: function() {
      this.$emit('xemDsDonThuoc');
    }
  },
});