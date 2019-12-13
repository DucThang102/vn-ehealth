import urllib.parse
import pymongo 
import json

username = 'test2'
password = urllib.parse.quote_plus('abc@123')

myclient = pymongo.MongoClient('mongodb://%s:%s@34.87.51.9/openmrs2' % (username, password))
mydb = myclient["openmrs2"]
mycol = mydb["emr_nhom_dm"]

x = mycol.find_one({'ma': 'DM_DVHC'})

def importJson(file, maNhom):
    nhomDm = mydb["emr_nhom_dm"].find_one({'ma': maNhom})
    if nhomDm:
        with open(file, 'r', encoding='utf-8') as f:
            dmList = json.load(f)
        #            
        for dm in dmList:
            dm['emrNhomDmId'] = nhomDm['_id']
        #   
        x = mydb['emr_dm'].insert_many(dmList)
        print(x.inserted_ids)
        
def importJson2(file, maNhom):
    nhomDm = mydb["emr_nhom_dm"].find_one({'ma': maNhom})
    if nhomDm:
        with open(file, 'r', encoding='utf-8') as f:
            dmList = json.load(f)
        #
        level = 1
        while True:
            print('level=', level)
            lst = [dm for dm in dmList if dm['capdo'] == level]
            if len(lst) == 0:
                break
            #            
            for dm in lst:
                if level > 1:
                    dmCha = mydb['emr_dm'].find_one({'ma': dm['ma_cha'], 'capdo': level-1})
                    if dmCha == None:
                        print(level, dm['ma'], dm['ma_cha'])
                    dm['emrDmChaId'] = dmCha['_id']
                del dm['ma_cha']
                dm['emrNhomDmId'] = nhomDm['_id']
            #
            mydb['emr_dm'].insert_many(lst)
            level += 1            


importJson("emr_dm_nghe_nghiep.json", "DM_NGHE_NGHIEP")
importJson("emr_dm_quoc_gia.json", "DM_QUOC_GIA")
importJson("emr_dm_dan_toc.json", "DM_DAN_TOC")
importJson("emr_dm_ma_benh.json", "DM_MA_BENH")
importJson("emr_dm_loai_chan_doan_hinh_anh.json", "DM_LOAI_CHAN_DOAN_HINH_ANH")
importJson("emr_dm_chan_doan_hinh_anh.json", "DM_CHAN_DOAN_HINH_ANH")
importJson("emr_dm_loai_tham_do_chuc_nang.json", "DM_LOAI_THAM_DO_CHUC_NANG")
importJson("emr_dm_tham_do_chuc_nang.json", "DM_THAM_DO_CHUC_NANG")
importJson("emr_dm_loai_giai_phau_benh.json", "DM_LOAI_GIAI_PHAU_BENH")
importJson("emr_dm_giai_phau_benh.json", "DM_GIAI_PHAU_BENH")
importJson("emr_dm_loai_phau_thu_thuat.json", "DM_LOAI_PHAU_THU_THUAT")
importJson("emr_dm_phau_thu_thuat.json", "DM_PHAU_THU_THUAT")
importJson("emr_dm_khoa_dieu_tri.json", "DM_KHOA_DIEU_TRI")
importJson("emr_dm_loai_xet_nghiem.json", "DM_LOAI_XET_NGHIEM")
importJson("emr_dm_xet_nghiem.json", "DM_XET_NGHIEM")
importJson("emr_dm_chi_so_xet_nghiem.json", "DM_CHI_SO_XET_NGHIEM")
importJson("emr_dm_thuoc.json", "DM_THUOC")
importJson("emr_dm_duong_dung_thuoc.json", "DM_DUONG_DUNG_THUOC")
importJson("emr_dm_tan_xuat_dung_thuoc.json", "DM_TAN_SUAT_DUNG_THUOC")
importJson("emr_dm_loai_ra_vien.json", "DM_LOAI_RA_VIEN")
importJson("emr_dm_loai_chuyen_vien.json", "DM_LOAI_CHUYEN_VIEN")
importJson("emr_dm_vai_tro_phau_thu_thuat.json", "DM_VAI_TRO_PHAU_THU_THUAT")
importJson("emr_dm_vai_tro_hoi_chan.json", "DM_VAI_TRO_HOI_CHAN")
importJson("emr_dm_vi_tri_lay_mau.json", "DM_VI_TRI_LAY_MAU")
importJson("emr_dm_loai_doi_tuong_tai_chinh.json", "DM_LOAI_DOI_TUONG_TAI_CHINH")
importJson("emr_dm_noi_gioi_thieu.json", "DM_NOI_GIOI_THIEU")
importJson("emr_dm_noi_truc_tiep_vao.json", "DM_NOI_TRUC_TIEP_VAO")
importJson("emr_dm_loai_vao_vien.json", "DM_LOAI_VAO_VIEN")
importJson("emr_dm_ket_qua_dieu_tri.json", "DM_KET_QUA_DIEU_TRI")


importJson2('emr_dm_dvhc.json', 'DM_DVHC')
