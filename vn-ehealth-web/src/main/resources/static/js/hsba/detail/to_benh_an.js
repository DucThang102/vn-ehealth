var to_benh_an_script = {
	data: function() {
		return {
			hsId: 0,
			hoten : '',
			ngaysinh: ''
		}
	},	
	
	mounted: async function () {
		this.hsId = getParam('hs_id');
		var thongtin_hanhchinh = await this.get(`/api/hsba/get_thongtin_hanhchinh?hoso_id=${this.hsId}`);
		console.log(thongtin_hanhchinh);
		this.hoten = thongtin_hanhchinh.thongtin_benhnhan.hoten;
		this.ngaysinh = thongtin_hanhchinh.thongtin_benhnhan.ngaysinh;
	}
};