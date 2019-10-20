VueAsyncComponent('xetnghiem', '/pages/hsba/view_detail/xetnghiem/xetnghiem.html', {
  data: function() {
    return {
      xetnghiem: null
    }
  },

  methods: {
    xemXetnghiem: function(xetnghiem) {
      this.xetnghiem = xetnghiem;
    },
    xemDsXetnghiem: function() {
      this.xetnghiem = null;
    }
  },
  
  props: ["hsba"]
});

VueAsyncComponent('xetnghiem-list', '/pages/hsba/view_detail/xetnghiem/xetnghiem_list.html', {
  data: function(){
    return {
      xetnghiem_list : []
    }    
  },

  methods:  {
    xemXetnghiem : function(xetnghiem) {
      this.$emit('xemXetnghiem', xetnghiem);
    },
  },

  props: ["hsba"],

  mounted: function() {
    if(this.hsba) {
      this.xetnghiem_list = this.hsba.emrXetNghiems;
    }
  }  
});

VueAsyncComponent('xetnghiem-view', '/pages/hsba/view_detail/xetnghiem/xetnghiem_view.html', {
  data: function() {
    return {
    }
  },
  props: ["hsba", "xetnghiem"],
  
  methods: {
    xemDsXetnghiem: function() {
      this.$emit('xemDsXetnghiem');
    }
  },
});