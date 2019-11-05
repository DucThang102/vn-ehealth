VueAsyncComponent('yhhd', '/pages/hsba/edit/yhoc_hiendai/yhhd.html', {
  data: function() {
    return {
      tab: 0,
    }
  },  

  props: ["hsba_id"],

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

  props: ["hsba_id"],

  methods: {
    
  },

  created: async function() {
    this.hsba = await this.get("/api/hsba/get_hsba_by_id", {"hsba_id": this.hsba_id});
    sessionStorage.setItem('dataChange', false);
  }
  
});

VueAsyncComponent('khambenh', '/pages/hsba/edit/yhoc_hiendai/khambenh.html', {
  data: function() {
    return {
      hsba: null
    }
  },

  props: ["hsba_id"],

  methods: {
  },

  created: async function() {
    this.hsba = await this.get("/api/hsba/get_hsba_by_id", {"hsba_id": this.hsba_id});
    sessionStorage.setItem('dataChange', false);
  }
});

VueAsyncComponent('huongdieutri', '/pages/hsba/edit/yhoc_hiendai/huongdieutri.html', {
  data: function() {
    return {
      hsba: null
    }
  },

  props: ["hsba_id"],

  methods: {
  },

  created: async function() {
    this.hsba = await this.get("/api/hsba/get_hsba_by_id", {"hsba_id": this.hsba_id});
    sessionStorage.setItem('dataChange', false);
  }
  
});

  