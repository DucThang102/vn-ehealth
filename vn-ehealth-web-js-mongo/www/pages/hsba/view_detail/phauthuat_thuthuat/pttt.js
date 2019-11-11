
VueAsyncComponent('pttt', '/pages/hsba/view_detail/phauthuat_thuthuat/pttt.html', {
  data: function() {
    return {
      pttt: null
    }
  },

  methods: {
    viewPttt: function(pttt) {
      this.pttt = pttt;
    },
    viewPtttList: function() {
      this.pttt = null;
    }
  },
  
  props: ["hsba_id"]
});

VueAsyncComponent('pttt-list', '/pages/hsba/view_detail/phauthuat_thuthuat/pttt_list.html', {
  data: function(){
    return {
      pttt_list : null
    }    
  },

  methods:  {
    viewPttt : function(pttt) {
      this.$emit('viewPttt', pttt);
    },
  },

  props: ["hsba_id"],

  created: async function() {
    if(this.hsba_id) {
      this.pttt_list = await this.get('/api/pttt/get_ds_pttt', { hsba_id: this.hsba_id });
    }
  }  
});

VueAsyncComponent('pttt-view', '/pages/hsba/view_detail/phauthuat_thuthuat/pttt_view.html', {
  data: function() {
    return {
      hsba: null
    }
  },
  props: ["hsba_id", "pttt"],
  
  methods: {
    viewPtttList: function() {
      this.$emit('viewPtttList');
    }
  },

  created: async function() {
    this.hsba = await this.get("/api/hsba/get_hsba_by_id", {"hsba_id": this.hsba_id});
  }
});
