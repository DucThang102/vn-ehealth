import Vue from 'vue'
import App from './App.vue'
import DanhMucPopup from './components/DanhMucPopup.vue'
import router from './router'
import store from './store'
import 'bootstrap'
import { BootstrapVue } from 'bootstrap-vue'
import datePicker from 'vue-bootstrap-datetimepicker';
import 'bootstrap-vue/dist/bootstrap-vue.css';
import 'pc-bootstrap4-datetimepicker/build/css/bootstrap-datetimepicker.css';


window.$ = require('jquery')
window.JQuery = require('jquery')
window.JQuery.extend(true, window.JQuery.fn.datetimepicker.defaults, {
  icons: {
    time: 'far fa-clock',
    date: 'far fa-calendar',
    up: 'fas fa-arrow-up',
    down: 'fas fa-arrow-down',
    previous: 'fas fa-chevron-left',
    next: 'fas fa-chevron-right',
    today: 'fas fa-calendar-check',
    clear: 'far fa-trash-alt',
    close: 'far fa-times-circle'
  }
});

Vue.use(BootstrapVue);
Vue.config.productionTip = false;

window.attr = function (obj, properties, defaultValue) {
  properties = properties.split(".");
  for (let i = 0; i < properties.length; i++) {
    if (obj) obj = obj[properties[i]];
  }
  return obj || defaultValue;
}

window.parseDate = function (st) {
  if (st.length == 10) {
    var yyyy = date.substring(0, 4);
    var mm = date.substring(5, 7);
    var dd = date.substring(8, 10);    
    return new Date(yyyy, mm, dd);
  } else if (st.length >= 16) {
    var yyyy = date.substring(0, 4);
    var mm = date.substring(5, 7);
    var dd = date.substring(8, 10);
    var HH = date.substring(11, 13);
    var MM = date.substring(14, 16);
    return new Date(yyyy, mm, dd, HH, MM);
  }
}

Vue.mixin({
  components: {
    datePicker,
    DanhMucPopup
  },
  data: function () {
    return {
      API_URL: "http://emr.com.vn:8001",
    };
  },

  methods: {
    serialize: function (obj) {
      var str = [];
      for (var param in obj) {
        str.push(
          encodeURIComponent(param) + "=" + encodeURIComponent(obj[param])
        );
      }
      return str.join("&");
    },

    attr: function (obj, properties) {
      properties = properties.split(".");
      for (let i = 0; i < properties.length; i++) {
        if (obj) obj = obj[properties[i]];
      }
      return obj;
    },

    get: function (uri, params) {
      var url = this.API_URL + uri;

      if (params) {
        url += "?" + this.serialize(params);
      }

      console.log(`%cGET ${uri}`, "background: blue; color: yellow", params);

      console.log(url);

      var headers = {
        Authorization: "Bearer " + localStorage.getItem("token")
      };

      return fetch(url, { headers }).then(response => response.json());
    },

    post: function (uri, params) {
      var url = this.API_URL + uri;
      console.log(`%POST ${uri} : `, "background: blue; color: yellow", params);

      return fetch(url, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + localStorage.getItem("token")
        },
        body: JSON.stringify(params)
      }).then(response => response.json());
    },

    formatDate: function (date) {
      if (date && date.length >= 10) {
        var yyyy = date.substring(0, 4);
        var mm = date.substring(5, 7);
        var dd = date.substring(8, 10);
        return `${dd}/${mm}/${yyyy}`;
      }
      return "";
    },
    
    formatDateTime: function (date) {
      if (date && date.length >= 16) {
        var yyyy = date.substring(0, 4);
        var mm = date.substring(5, 7);
        var dd = date.substring(8, 10);
        var HH = date.substring(11, 13);
        var MM = date.substring(14, 16);
        return `${dd}/${mm}/${yyyy} ${HH}:${MM}`;
      }
      return "";
    },
  }
});

new Vue({
  router,
  store,
  render: function (h) { return h(App) }
}).$mount('#app')
