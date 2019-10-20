package vn.ehealth.service;

public class Constants {

    public static interface MA_NHOM_DANH_MUC {
        final public static String LOAI_BENH_AN = "LOAI_BENH_AN";
        final public static String TRANGTHAI_HOSO = "TRANGTHAI_HOSO";
        final public static String NGUON_DU_LIEU = "NGUON_DU_LIEU";
    }
    
    public static interface MA_DANH_MUC {
        public static interface TRANGTHAI_HOSO {
            final public static String CHUA_XULY = "01";
            final public static String DA_LUU = "02";
        }
        
        public static interface NGUON_DU_LIEU {
            final public static String TU_HIS = "01";            
        }
    }    
    
	public static interface TRANGTHAI_HOSO {
		final public static int CHUA_XULY = 1;
		final public static int DA_LUU = 2;
	}
}
