VueAsyncComponent('giayto', '/pages/hsba/edit/giayto/giayto.html', {
  data: function () {
    return {
      giayto_list: [],
    }
  },
  methods: {
    upload: async function() {
      var formData = new FormData(document.getElementById("fmt"));
      var response = await fetch('/api/hsba/add_giayto',
        { body: formData, method: 'POST' });
      var result = await response.json();
      alert(result.success);
      console.log(result);
    }
  }

});