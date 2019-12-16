[1mdiff --git a/vn-ehealth-web-js-mongo/www/pages/hsba/view_detail/chandoan_hinhanh/cdha.js b/vn-ehealth-web-js-mongo/www/pages/hsba/view_detail/chandoan_hinhanh/cdha.js[m
[1mindex 055a8f9..d403ba8 100644[m
[1m--- a/vn-ehealth-web-js-mongo/www/pages/hsba/view_detail/chandoan_hinhanh/cdha.js[m
[1m+++ b/vn-ehealth-web-js-mongo/www/pages/hsba/view_detail/chandoan_hinhanh/cdha.js[m
[36m@@ -1,59 +1,75 @@[m
[31m-VueAsyncComponent('cdha', '/pages/hsba/view_detail/chandoan_hinhanh/cdha.html', {[m
[31m-  data: function() {[m
[31m-    return {[m
[31m-      cdha: null[m
[31m-    }[m
[31m-  },[m
[32m+[m[32mVueAsyncComponent([m
[32m+[m[32m  "cdha",[m
[32m+[m[32m  "/pages/hsba/view_detail/chandoan_hinhanh/cdha.html",[m
[32m+[m[32m  {[m
[32m+[m[32m    data: function() {[m
[32m+[m[32m      return {[m
[32m+[m[32m        cdha: null[m
[32m+[m[32m      };[m
[32m+[m[32m    },[m
 [m
[31m-  methods: {[m
[31m-    viewCdha: function(cdha) {[m
[31m-      this.cdha = cdha;[m
[32m+[m[32m    methods: {[m
[32m+[m[32m      viewCdha: function(cdha) {[m
[32m+[m[32m        this.cdha = cdha;[m
[32m+[m[32m      },[m
[32m+[m[32m      viewCdhaList: function() {[m
[32m+[m[32m        this.cdha = null;[m
[32m+[m[32m      }[m
     },[m
[31m-    viewCdhaList: function() {[m
[31m-      this.cdha = null;[m
[31m-    }[m
[31m-  },[m
[31m-  [m
[31m-  props: ["hsba_id"][m
[31m-});[m
[31m-[m
[31m-VueAsyncComponent('cdha-list', '/pages/hsba/view_detail/chandoan_hinhanh/cdha_list.html', {[m
[31m-  data: function(){[m
[31m-    return {[m
[31m-      cdha_list : null[m
[31m-    }    [m
[31m-  },[m
[31m-[m
[31m-  methods:  {[m
[31m-    viewCdha : function(cdha) {[m
[31m-      this.$emit('viewCdha', cdha);[m
[32m+[m
[32m+[m[32m    props: ["hsba_id"][m
[32m+[m[32m  }[m
[32m+[m[32m);[m
[32m+[m
[32m+[m[32mVueAsyncComponent([m
[32m+[m[32m  "cdha-list",[m
[32m+[m[32m  "/pages/hsba/view_detail/chandoan_hinhanh/cdha_list.html",[m
[32m+[m[32m  {[m
[32m+[m[32m    data: function() {[m
[32m+[m[32m      return {[m
[32m+[m[32m        cdha_list: null[m
[32m+[m[32m      };[m
     },[m
[31m-  },[m
 [m
[31m-  props: ["hsba_id"],[m
[32m+[m[32m    methods: {[m
[32m+[m[32m      viewCdha: function(cdha) {[m
[32m+[m[32m        this.$emit("viewCdha", cdha);[m
[32m+[m[32m      }[m
[32m+[m[32m    },[m
 [m
[31m-  created: async function() {[m
[31m-    if(this.hsba_id) {[m
[31m-      this.cdha_list = await this.get('/api/cdha/get_ds_cdha', { hsba_id: this.hsba_id });[m
[31m-    }[m
[31m-  }  [m
[31m-});[m
[32m+[m[32m    props: ["hsba_id"],[m
 [m
[31m-VueAsyncComponent('cdha-view', '/pages/hsba/view_detail/chandoan_hinhanh/cdha_view.html', {[m
[31m-  data: function() {[m
[31m-    return {[m
[31m-      hsba:null[m
[32m+[m[32m    created: async function() {[m
[32m+[m[32m      if (this.hsba_id) {[m
[32m+[m[32m        this.cdha_list = await this.get("/api/cdha/get_ds_cdha", {[m
[32m+[m[32m          hsba_id: this.hsba_id[m
[32m+[m[32m        });[m
[32m+[m[32m      }[m
     }[m
[31m-  },[m
[31m-  props: ["hsba_id", "cdha"],[m
[31m-  [m
[31m-  methods: {[m
[31m-    viewCdhaList: function() {[m
[31m-      this.$emit('viewCdhaList');[m
[31m-    }[m
[31m-  },[m
[32m+[m[32m  }[m
[32m+[m[32m);[m
[32m+[m
[32m+[m[32mVueAsyncComponent([m
[32m+[m[32m  "cdha-view",[m
[32m+[m[32m  "/pages/hsba/view_detail/chandoan_hinhanh/cdha_view.html",[m
[32m+[m[32m  {[m
[32m+[m[32m    data: function() {[m
[32m+[m[32m      return {[m
[32m+[m[32m        hsba: null[m
[32m+[m[32m      };[m
[32m+[m[32m    },[m
[32m+[m[32m    props: ["hsba_id", "cdha"],[m
 [m
[31m-  created: async function() {[m
[31m-    this.hsba = await this.get("/api/hsba/get_hsba_by_id", {"hsba_id": this.hsba_id});[m
[32m+[m[32m    methods: {[m
[32m+[m[32m      viewCdhaList: function() {[m
[32m+[m[32m        this.$emit("viewCdhaList");[m
[32m+[m[32m      }[m
[32m+[m[32m    },[m
[32m+[m
[32m+[m[32m    created: async function() {[m
[32m+[m[32m      this.hsba = await this.get("/api/hsba/get_hsba_by_id", {[m
[32m+[m[32m        hsba_id: this.hsba_id[m
[32m+[m[32m      });[m
[32m+[m[32m    }[m
   }[m
[31m-});[m
\ No newline at end of file[m
[32m+[m[32m);[m
