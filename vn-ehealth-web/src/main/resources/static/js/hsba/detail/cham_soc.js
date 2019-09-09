var cham_soc_script = {
	data: function() {
		return {
			hsId: 0
		}
	},	
	
	mounted: function () {
		this.hsId = getParam('hs_id');
	}
};