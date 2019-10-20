VueAsyncComponent('tdcn', '/pages/hsba/view_detail/thamdo_chucnang/tdcn.html', {
  data: function() {
    return {
      tdcn: null
    }
  },

  methods: {
    xemTdcn: function(tdcn) {
      this.tdcn = tdcn;
    },
    xemDsTdcn: function() {
      this.tdcn = null;
    }
  },
  
  props: ["hsba"]
});

VueAsyncComponent('tdcn-list', '/pages/hsba/view_detail/thamdo_chucnang/tdcn_list.html', {
  data: function(){
    return {
      tdcn_list : []
    }    
  },

  methods:  {
    xemTdcn : function(tdcn) {
      this.$emit('xemTdcn', tdcn);
    },
  },

  props: ["hsba"],

  mounted: function() {
    if(this.hsba) {
      this.tdcn_list = this.hsba.emrThamDoChucNangs;
    }
  }  
});

VueAsyncComponent('tdcn-view', '/pages/hsba/view_detail/thamdo_chucnang/tdcn_view.html', {
  data: function() {
    return {
    }
  },
  props: ["hsba", "tdcn"],
  
  methods: {
    xemDsTdcn: function() {
      this.$emit('xemDsTdcn');
    }
  },
});
