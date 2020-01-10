VueAsyncComponent('giayto', '/pages/hsba/edit/giayto/giayto.html', {
  data: function () {
    return {
      giayto_list: [],
    }
  },
  
  props: ["hsba_id"],

  methods: {
    openUploadModal: function() {
      $('#upload').modal();
    },
    upload: async function() {
      var formData = new FormData(document.getElementById("fmt"));
      var response = await fetch( this.API_URL + '/api/hsba/add_giayto',
        { body: formData, method: 'POST' });
      var result = await response.json();
      alert(result.success);
      console.log(result);
    }
  }

});