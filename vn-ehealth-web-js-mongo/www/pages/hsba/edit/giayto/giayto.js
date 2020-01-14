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
      document.getElementById("attached_files").value = "";
      $('#upload').modal();
    },
    upload: async function() {
      if(document.getElementById("attached_files").value == "") {
        alert("Bạn cần chọn file tải lên");
        return;
      }
      
      var formData = new FormData(document.getElementById("fmt"));
      var response = await fetch( this.API_URL + '/api/hsba/add_giayto',
        { body: formData, method: 'POST' });
      var result = await response.json();
      if(result.success) {
        this.hsba = await this.get("/api/hsba/get_hsba_by_id", {"hsba_id": this.hsba_id});
        $('#upload').modal('hide');
      }else {
        alert("Tải file không thành công");
      }
    },

    deleteFileDinhKem: function(index) {
      this.hsba.emrFileDinhKems.splice(index, 1);
    },
    saveHsba: async function() {   
      var result = await this.post("/api/hsba/update_hsba", this.hsba);
        if(result.success) {
          alert('Cập nhật thông tin thành công');
          sessionStorage.removeItem('dataChange');
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