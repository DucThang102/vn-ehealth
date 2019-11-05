VueAsyncComponent('tkba', '/pages/hsba/edit/tongket_benh_an/tkba.html', {
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