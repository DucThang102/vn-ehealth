import urllib.parse
import pymongo 
import json

username = 'test'
password = urllib.parse.quote_plus('abc@123')

myclient = pymongo.MongoClient('mongodb://%s:%s@34.87.51.9' % (username, password))
mydb = myclient["openmrs"]
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


importJson('emr_dm_ket_qua_dieu_tri.json', 'DM_KET_QUA_DIEU_TRI')
importJson2('emr_dm_dvhc.json', 'DM_DVHC')
