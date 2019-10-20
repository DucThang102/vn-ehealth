VueAsyncComponent('gpb', '/pages/hsba/view_detail/giaiphau_benh/gpb.html', {
  data: function() {
    return {
      gpb: null
    }
  },

  methods: {
    xemGpb: function(gpb) {
      this.gpb = gpb;
    },
    xemDsGpb: function() {
      this.gpb = null;
    }
  },
  
  props: ["hsba"]
});

VueAsyncComponent('gpb-list', '/pages/hsba/view_detail/giaiphau_benh/gpb_list.html', {
  data: function(){
    return {
      gpb_list : []
    }    
  },

  methods:  {
    xemGpb : function(gpb) {
      this.$emit('xemGpb', gpb);
    },
  },

  props: ["hsba"],

  mounted: function() {
    if(this.hsba) {
      this.gpb_list = this.hsba.emrGiaiPhauBenhs;
    }
  }  
});

VueAsyncComponent('gpb-view', '/pages/hsba/view_detail/giaiphau_benh/gpb_view.html', {
  data: function() {
    return {
    }
  },
  props: ["hsba", "gpb"],
  
  methods: {
    xemDsGpb: function() {
      this.$emit('xemDsGpb');
    }
  },
});
