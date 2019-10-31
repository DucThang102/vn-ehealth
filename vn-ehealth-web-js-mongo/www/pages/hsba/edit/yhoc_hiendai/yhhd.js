VueAsyncComponent('yhhd', '/pages/hsba/edit/yhoc_hiendai/yhhd.html', {
  data: function() {
    return {
      tab: 0,
    }
  },  

  methods: {
    changeTab(tab){
      this.tab = tab;
    }
  },
  
});


VueAsyncComponent('hoibenh', '/pages/hsba/edit/yhoc_hiendai/hoibenh.html', {
  data: function() {
    return {
      hsba: null
    }
  },

  methods: {
    
  },

  created: async function() {
    var hs_id = this.getParam("hs_id");
    this.hsba = await this.get('/api/hsba/get_hs', { hoso_id: hs_id });
    sessionStorage.setItem('dataChange', false);
  }
  
});

VueAsyncComponent('khambenh', '/pages/hsba/edit/yhoc_hiendai/khambenh.html', {
  data: function() {
    return {
      hsba: null
    }
  },

  methods: {
  },

  created: async function() {
    var hs_id = this.getParam("hs_id");
    this.hsba = await this.get('/api/hsba/get_hs', { hoso_id: hs_id });
    sessionStorage.setItem('dataChange', false);
  }
});

VueAsyncComponent('huongdieutri', '/pages/hsba/edit/yhoc_hiendai/huongdieutri.html', {
  data: function() {
    return {
      hsba: null
    }
  },

  methods: {
  },

  created: async function() {
    var hs_id = this.getParam("hs_id");
    this.hsba = await this.get('/api/hsba/get_hs', { hoso_id: hs_id });
    sessionStorage.setItem('dataChange', false);
  }
  
});

  