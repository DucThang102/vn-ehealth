
VueAsyncComponent('pttt', '/pages/hsba/view_detail/phauthuat_thuthuat/pttt.html', {
  data: function() {
    return {
      pttt: null
    }
  },

  methods: {
    xemPttt: function(pttt) {
      this.pttt = pttt;
    },
    xemDsPttt: function() {
      this.pttt = null;
    }
  },
  
  props: ["hsba"]
});

VueAsyncComponent('pttt-list', '/pages/hsba/view_detail/phauthuat_thuthuat/pttt_list.html', {
  data: function(){
    return {
      pttt_list : []
    }    
  },

  methods:  {
    xemPttt : function(pttt) {
      this.$emit('xemPttt', pttt);
    },
  },

  props: ["hsba"],

  mounted: function() {
    if(this.hsba) {
      this.pttt_list = this.hsba.emrPhauThuatThuThuats;
    }
  }  
});

VueAsyncComponent('pttt-view', '/pages/hsba/view_detail/phauthuat_thuthuat/pttt_view.html', {
  data: function() {
    return {
    }
  },
  props: ["hsba", "pttt"],
  
  methods: {
    xemDsPttt: function() {
      this.$emit('xemDsPttt');
    }
  },
});
