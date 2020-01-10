VueAsyncComponent('giayto', '/pages/hsba/edit/giayto/giayto.html', {
  data: function () {
    return {
      hsba: null,
      perPage: 10,
    	currentPage: 1,
    
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
      //alert(result.success);
      console.log(result);
      this.hsba = await this.get("/api/hsba/get_hsba_by_id", {"hsba_id": this.hsba_id});
      $('#upload').modal('hide');
    },

    deleteFileDinhKem: function(index) {
      this.hsba.emrFileDinhKems.splice(index, 1);
    },
    saveFileDinhKem: async function() {
      var result = await this.post("/api/FileDinhKem/create_or_update_FileDinhKem", this.FileDinhKem);
      if(result.success) {
        sessionStorage.removeItem('dataChange');
        this.$emit('hsba.emrFileDinhKems');
      }else {
        alert('Lỗi xảy ra quá trình lưu thông tin');
      }
    },
      },

  watch: {
    currentPage: async function() {
      await this.getData();
    }
  },
  created: async function() {
    this.hsba = await this.get("/api/hsba/get_hsba_by_id", {"hsba_id": this.hsba_id});
  }
});