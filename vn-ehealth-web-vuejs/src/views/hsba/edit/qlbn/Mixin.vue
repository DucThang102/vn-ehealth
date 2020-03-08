<script>
export default {
  data: function() {
    return {
      hsba: null
    };
  },

  props: ["hsbaId"],

  methods: {
    saveHsba: async function() {
      var result = await this.post("/api/hsba/update_hsba", this.hsba);
      if (result.success) {
        alert("Cập nhật thông tin thành công");
        sessionStorage.removeItem("dataChange");
      } else {
        alert("Lỗi xảy ra quá trình lưu thông tin");
      }
    }
  },

  watch: {
    hsba: {
      handler: function(val, oldVal) {
        if (oldVal) {
          sessionStorage.setItem("dataChange", true);
        }
      },
      deep: true
    }
  },

  created: async function() {
    this.hsba = await this.get("/api/hsba/get_hsba_by_id", {
      hsba_id: this.hsbaId
    });
  }
}
</script>