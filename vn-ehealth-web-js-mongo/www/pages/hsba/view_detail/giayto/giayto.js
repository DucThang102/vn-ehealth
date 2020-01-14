VueAsyncComponent('giayto', '/pages/hsba/view_detail/giayto/giayto.html', {

  data: function () {
    return {
      hsba: null,
    }
  },
  props: ["hsba_id"],

  created: async function() {
    this.hsba = await this.get("/api/hsba/get_hsba_by_id", {"hsba_id": this.hsba_id});
  }
});