var API_URL = "http://localhost:8001";

Vue.mixin({
  data: function() {
    return {
      API_URL: "http://localhost:8001"
    };
  },

  methods: {
    getParam: function(name) {
      if (
        (name = new RegExp("[?&]" + encodeURIComponent(name) + "=([^&]*)").exec(
          location.search
        ))
      )
        return decodeURIComponent(name[1]);
    },

    serialize: function(obj) {
      var str = [];
      for (var param in obj) {
        str.push(
          encodeURIComponent(param) + "=" + encodeURIComponent(obj[param])
        );
      }
      return str.join("&");
    },

    attr: function(obj, properties) {
      properties = properties.split(".");
      for (let i = 0; i < properties.length; i++) {
        if (obj) obj = obj[properties[i]];
      }
      return obj;
    },

    get: function(uri, params) {
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

    post: function(uri, params) {
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

    createURL: function(htmlURL, params) {
      var queryStr = serialize(params);
      return queryStr === "" ? htmlURL : htmlURL + "?" + queryStr;
    }
  }
});

function parseDate(st) {
  var dd = st.substring(0, 2);
  var mm = st.substring(3, 5);
  var yyyy = st.substring(6, 10);
  if(st.length == 10) {
    return new Date(yyyy, mm, dd);
  }else if(st.length >= 16) {
    HH = st.substring(11, 13);
    MM = st.substring(14, 16);
    return new Date(yyyy, mm, dd, HH, MM);
  }
}

function attr(obj, properties, defaultValue) {
  properties = properties.split(".");
  for (let i = 0; i < properties.length; i++) {
    if (obj) obj = obj[properties[i]];
  }
  return obj || defaultValue;
}

function getParam(name) {
  if (
    (name = new RegExp("[?&]" + encodeURIComponent(name) + "=([^&]*)").exec(
      location.search
    ))
  )
    return decodeURIComponent(name[1]);
}

function serialize(obj) {
  var str = [];
  for (var param in obj) {
    str.push(encodeURIComponent(param) + "=" + encodeURIComponent(obj[param]));
  }
  return str.join("&");
}

function createURL(htmlURL, params) {
  var queryStr = serialize(params);
  return queryStr === "" ? htmlURL : htmlURL + "?" + queryStr;
}

function VueAsyncComponent(componentName, templateFile, script) {
  Vue.component(componentName, function(resolve, reject) {
    fetch(templateFile)
      .then(resp => resp.text())
      .then(result => resolve(Object.assign({}, script, { template: result })));
  });
}
