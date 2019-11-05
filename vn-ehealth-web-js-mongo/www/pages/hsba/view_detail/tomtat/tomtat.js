VueAsyncComponent('tomtat', '/pages/hsba/view_detail/tomtat/tomtat.html', {
  data: function() {
    return {
      hsba: null
    }
  },

  props: ["hsba", "title"]
});