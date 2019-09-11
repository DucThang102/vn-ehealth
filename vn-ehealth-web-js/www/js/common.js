Vue.mixin({
	data: function () {
		return {
			baseURL: 'http://localhost:8000'
		}
	},

	methods: {
		formatDate: function (dateStr) {
			var yyyy = dateStr.substring(0, 4);
			var mm = dateStr.substring(5, 7);
			var dd = dateStr.substring(8, 10);
			return `${dd}/${mm}/${yyyy}`;
		},

		getParam: function (name) {
			if (name = (new RegExp('[?&]' + encodeURIComponent(name) + '=([^&]*)')).exec(location.search))
				return decodeURIComponent(name[1]);
		},

		serialize: function (obj) {
			var str = [];
			for (var param in obj) {
				str.push(encodeURIComponent(param) + "=" + encodeURIComponent(obj[param]));
			}
			return str.join("&");
		},

		get: function (uri, params) {
			var url = this.baseURL + uri;

			if (params) {
				url += '?' + this.serialize(params);
			}

			console.log(`%cGET ${uri}`, 'background: blue; color: yellow', params);

			return fetch(url).then(response => response.json());
		},

		post: function (uri, params) {
			var url = this.baseURL + uri;
			console.log(`%POST ${uri} : `, 'background: blue; color: yellow', params);

			return fetch(url, {
				method: 'POST',
				headers: { 'Content-Type': 'application/json' },
				body: JSON.stringify(params)
			}).then(response => response.json());
		},

		createURL: function (htmlURL, params) {
			var queryStr = serialize(params);
            return queryStr === ''? htmlURL : htmlURL + '?' + queryStr;
		},

		callService: function (url, method, data) {
			var headers = {
				"Authorization": "Bearer " + localStorage.getItem("token")
			};

			if (method == 'GET' || method == 'get') {
				return fetch(url, { headers: headers }).then(result => result.json());
			} else {
				return fetch(url, {
					headers: headers,
					body: data,
					method: method
				})
                .then(result => result.json());
			}
		}
	}
})

function getParam(name) {
	if (name = (new RegExp('[?&]' + encodeURIComponent(name) + '=([^&]*)')).exec(location.search))
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
    return queryStr === ''? htmlURL : htmlURL + '?' + queryStr;
}

function VueAsyncComponent(componentName, templateFile, script) {
	Vue.component(componentName, function (resolve, reject) {
		fetch(templateFile).then(resp => resp.text()).then(result =>
			resolve(Object.assign({}, script, { template: result }))
		)
	});
}
