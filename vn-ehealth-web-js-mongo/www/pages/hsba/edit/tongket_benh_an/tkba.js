VueAsyncComponent('tkba', '/pages/hsba/edit/tongket_benh_an/tkba.html', {
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