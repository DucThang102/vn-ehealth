var hinhanh_tonthuong_script = {
	data: function() {
		return {
			hsId: 0
		}
	},	
	
	mounted: function () {
		this.hsId = getParam('hs_id');
		//alert(this.hsId);
	}
};