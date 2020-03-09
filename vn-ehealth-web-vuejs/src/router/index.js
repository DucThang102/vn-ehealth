import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '@/views/Login.vue'
import Registration from '@/views/Registration.vue'

import TimKiemBenhNhan from '@/views/benhnhan/TimKiemBenhNhan.vue'
import XemTTBenhNhan from '@/views/benhnhan/XemTTBenhNhan.vue'
import TTBenhNhanTongQuan from '@/views/benhnhan/detail/TTBenhNhanTongQuan.vue'
import TTBenhNhanLichSuDungThuoc from '@/views/benhnhan/detail/TTBenhNhanLichSuDungThuoc.vue'
import TTBenhNhanChanDoanHinhAnh from '@/views/benhnhan/detail/TTBenhNhanChanDoanHinhAnh.vue'
import TTBenhNhanPhauThuatThuThuat from '@/views/benhnhan/detail/TTBenhNhanPhauThuatThuThuat.vue'
import TTBenhNhanTaiLieuLamSang from '@/views/benhnhan/detail/TTBenhNhanTaiLieuLamSang.vue'
import TTBenhNhanChiSoSinhTon from '@/views/benhnhan/detail/TTBenhNhanChiSoSinhTon.vue'

import DsHsbaChuaXuly from '@/views/hsba/DsChuaXuly.vue'
import DsHsbaDaLuu from '@/views/hsba/DsDaLuu.vue'

// Xem thong tin Hsba
import XemHsba from '@/views/hsba/XemHsba.vue'
import ToBenhAn from '@/views/hsba/view_detail/ToBenhAn.vue'
import XemTTHinhAnhTonThuong from '@/views/hsba/view_detail/HinhAnhTonThuong.vue'
import XemTTPhauThuatThuThuat from '@/views/hsba/view_detail/PhauThuatThuThuat.vue'
import XemTTXetNghiem from '@/views/hsba/view_detail/XetNghiem.vue'
import XemTTChanDoanHinhAnh from '@/views/hsba/view_detail/ChanDoanHinhAnh.vue'
import XemTTThamDoChucNang from '@/views/hsba/view_detail/ThamDoChucNang.vue'
import XemTTGiaiPhauBenh from '@/views/hsba/view_detail/GiaiPhauBenh.vue'
import XemTTHoiChan from '@/views/hsba/view_detail/HoiChan.vue'
import XemTTDieuTri from '@/views/hsba/view_detail/DieuTri.vue'
import XemTTChamSoc from '@/views/hsba/view_detail/ChamSoc.vue'
import XemTTChucNangSong from '@/views/hsba/view_detail/ChucNangSong.vue'
import XemTTDonThuoc from '@/views/hsba/view_detail/DonThuoc.vue'
import XemTTGiayToKhac from '@/views/hsba/view_detail/GiayToKhac.vue'

//Chinh sua Hsba
import ChinhSuaHsba from '@/views/hsba/ChinhSuaHsba.vue'

//Qlybn
import ChinhSuaTTQlyBenhNhan from '@/views/hsba/edit/QuanLyBenhNhan.vue'
import ChinhSuaTTHanhChinh from '@/views/hsba/edit/qlbn/ThongTinHanhChinh.vue'
import ChinhSuaTTNguoiBenh from '@/views/hsba/edit/qlbn/ThongTinNguoiBenh.vue'
import ChinhSuaTTChanDoan from '@/views/hsba/edit/qlbn/ChanDoan.vue'
import ChinhSuaTTTinhTrangRaVien from '@/views/hsba/edit/qlbn/TinhTrangRaVien.vue'

import ChinhSuaTTYhocHienDai from '@/views/hsba/edit/YhocHienDai'
import ChinhSuaTTHoiBenh from '@/views/hsba/edit/yhhd/HoiBenh.vue'
import ChinhSuaTTKhamBenh from '@/views/hsba/edit/yhhd/KhamBenh.vue'
import ChinhSuaTTHuongDieuTri from '@/views/hsba/edit/yhhd/HuongDieuTri.vue'

import ChinhSuaTTTongKetBenhAn from '@/views/hsba/edit/TongKetBenhAn.vue'

import ChinhSuaTTHinhAnTonThuong from '@/views/hsba/edit/HinhAnhTonThuong.vue'
import ChinhSuaTTHinhAnTonThuongList from '@/views/hsba/edit/hatt/List.vue'
import ChinhSuaTTHinhAnTonThuongItem from '@/views/hsba/edit/hatt/Item.vue'

import ChinhSuaTTPhauThuatThuThuat from '@/views/hsba/edit/PhauThuatThuThuat.vue'
import ChinhSuaTTXetNghiem from '@/views/hsba/edit/XetNghiem.vue'
import ChinhSuaTTChanDoanHinhAnh from '@/views/hsba/edit/ChanDoanHinhAnh.vue'
import ChinhSuaTTThamDoChucNang from '@/views/hsba/edit/ThamDoChucNang.vue'
import ChinhSuaTTGiaiPhauBenh from '@/views/hsba/edit/GiaiPhauBenh.vue'
import ChinhSuaTTHoiChan from '@/views/hsba/edit/HoiChan.vue'
import ChinhSuaTTDieuTri from '@/views/hsba/edit/DieuTri.vue'
import ChinhSuaTTChamSoc from '@/views/hsba/edit/ChamSoc.vue'
import ChinhSuaTTChucNangSong from '@/views/hsba/edit/ChucNangSong.vue'
import ChinhSuaTTDonThuoc from '@/views/hsba/edit/DonThuoc.vue'
import ChinhSuaTTGiayToKhac from '@/views/hsba/edit/GiayToKhac.vue'

import DashBoard from '@/views/DashBoard.vue';

import DmDungChung from '@/views/danhmuc/DmDungChung.vue'
import DmChuanHoa from '@/views/danhmuc/DmChuanHoa.vue'
import DsNguoiDung from '@/views/quantri/DanhSachNguoiDung.vue'
import ThemNguoiDung from '@/views/quantri/ThemNguoiDung.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: DsHsbaChuaXuly
  },
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/registration',
    name: 'registration',
    component: Registration
  },

  {
    path: '/benhnhan/timkiem',
    name: 'timKiemBenhNhan',
    component: TimKiemBenhNhan
  },

  {
    path: '/benhnhan/xem_thongtin/:patientId',
    name: 'xemTTBenhNhan',
    component: XemTTBenhNhan,
    props: true,
    children: [
      {
        path: 'tongquan',
        name: 'ttBenhNhanTongQuan',
        component: TTBenhNhanTongQuan,
        props: true
      },
      {
        path: 'lsdt',
        name: 'ttBenhNhanLichSuDungThuoc',
        component: TTBenhNhanLichSuDungThuoc,
        props: true,
        meta: {
          tabId: 2
        }
      },
      {
        path: 'cdha',
        name: 'ttBenhNhanChanDoanHinhAnh',
        component: TTBenhNhanChanDoanHinhAnh,
        props: true,
        meta: {
          tabId: 3
        }
      },
      {
        path: 'pttt',
        name: 'ttBenhNhanPhauThuatThuThuat',
        component: TTBenhNhanPhauThuatThuThuat,
        props: true,
        meta: {
          tabId: 4
        }
      },
      {
        path: 'tlls',
        name: 'ttBenhNhanTaiLieuLamSang',
        component: TTBenhNhanTaiLieuLamSang,
        props: true,
        meta: {
          tabId: 5
        }
      },
      {
        path: 'csst',
        name: 'ttBenhNhanChiSoSinhTon',
        component: TTBenhNhanChiSoSinhTon,
        props: true,
        meta: {
          tabId: 6
        }
      }
    ]
  },

  {
    path: '/hsba/daluu',
    name: 'dsHsbaDaLuu',
    component: DsHsbaDaLuu
  },
  {
    path: '/hsba/xem_hsba/:hsbaId',
    component: XemHsba,
    props: true,
    children: [
      {
        path: 'benh_an',
        name: 'xemToBenhAn',
        component: ToBenhAn,
        props: true
      },
      {
        path: 'hatt',
        name: 'xemTTHinhAnhTonThuong',
        component: XemTTHinhAnhTonThuong,
        props: true,
        meta: {
          tabId: 2
        }
      },
      {
        path: 'pttt',
        name: 'xemTTPhauThuatThuThuat',
        component: XemTTPhauThuatThuThuat,
        props: true,
        meta: {
          tabId: 3
        }
      },
      {
        path: 'xetnghiem',
        name: 'xemTTXetNghiem',
        component: XemTTXetNghiem,
        props: true,
        meta: {
          tabId: 4
        }
      },
      {
        path: 'cdha',
        name: 'xemTTChanDoanHinhAnh',
        component: XemTTChanDoanHinhAnh,
        props: true,
        meta: {
          tabId: 5
        }
      },
      {
        path: 'tdcn',
        name: 'xemTTThamDoChucNang',
        component: XemTTThamDoChucNang,
        props: true,
        meta: {
          tabId: 6
        }
      },
      {
        path: 'gpb',
        name: 'xemTTGiaiPhauBenh',
        component: XemTTGiaiPhauBenh,
        props: true,
        meta: {
          tabId: 7
        }
      },
      {
        path: 'hoichan',
        name: 'xemTTHoiChan',
        component: XemTTHoiChan,
        props: true,
        meta: {
          tabId: 8
        }
      },
      {
        path: 'dieutri',
        name: 'xemTTDieuTri',
        component: XemTTDieuTri,
        props: true,
        meta: {
          tabId: 9
        }
      },
      {
        path: 'chamsoc',
        name: 'xemTTChamSoc',
        component: XemTTChamSoc,
        props: true,
        meta: {
          tabId: 10
        }
      },
      {
        path: 'chucnangsong',
        name: 'xemTTChucNangSong',
        component: XemTTChucNangSong,
        props: true,
        meta: {
          tabId: 11
        }
      },
      {
        path: 'donthuoc',
        name: 'xemTTDonThuoc',
        component: XemTTDonThuoc,
        props: true,
        meta: {
          tabId: 12
        }
      },
      {
        path: 'giayto_khac',
        name: 'xemTTGiayToKhac',
        component: XemTTGiayToKhac,
        props: true,
        meta: {
          tabId: 13
        }
      }
    ]
  },
  {
    path: '/hsba/chinh_sua/:hsbaId/',
    component: ChinhSuaHsba,
    props: true,
    children: [
      {
        path: 'qlbn/',
        component: ChinhSuaTTQlyBenhNhan,
        props: true,
        children: [
          {
            path: 'tthc/',
            name: 'chinhSuaTTHanhChinh',
            component: ChinhSuaTTHanhChinh,
            props: true,
          },
          {
            path: 'ttbn/',
            name: 'chinhSuaTTNguoiBenh',
            component: ChinhSuaTTNguoiBenh,
            props: true,
            meta: {
              subTabId: 2
            }
          },
          {
            path: 'chandoan/',
            name: 'chinhSuaTTChanDoan',
            component: ChinhSuaTTChanDoan,
            props: true,
            meta: {
              subTabId: 3
            }
          },
          {
            path: 'tinhtrang_ravien/',
            name: 'chinhSuaTTTinhTrangRaVien',
            component: ChinhSuaTTTinhTrangRaVien,
            props: true,
            meta: {
              subTabId: 4
            }
          },
        ]
      },
      {
        path: 'yhhd',
        name: 'chinhSuaTTYhocHienDai',
        component: ChinhSuaTTYhocHienDai,
        props: true,
        children: [
          {
            path: 'hoibenh/',
            name: 'chinhSuaTTHoiBenh',
            component: ChinhSuaTTHoiBenh,
            props: true,
            meta: {
              tabId: 2
            }
          },
          {
            path: 'khambenh/',
            name: 'chinhSuaTTKhamBenh',
            component: ChinhSuaTTKhamBenh,
            props: true,
            meta: {
              tabId: 2,
              subTabId: 2
            }
          },
          {
            path: 'huongdieutri/',
            name: 'chinhSuaTTHuongDieuTri',
            component: ChinhSuaTTHuongDieuTri,
            props: true,
            meta: {
              tabId: 2,
              subTabId: 3
            }
          }
        ],
        meta: {
          tabId: 2
        }
      },
      {
        path: 'tkba',
        name: 'chinhSuaTTTongKetBenhAn',
        component: ChinhSuaTTTongKetBenhAn,
        props: true,
        meta: {
          tabId: 3
        }
      },
      {
        path: 'hatt/',
        component: ChinhSuaTTHinhAnTonThuong,
        props: true,
        children: [
          {
            path: 'list',
            name: 'chinhSuaTTHinhAnTonThuongList',
            component: ChinhSuaTTHinhAnTonThuongList,
            props: true,
            meta: {
              tabId: 4
            },
          },
          {
            path: 'item/:hattId',
            name: 'chinhSuaTTHinhAnTonThuongItem',
            component: ChinhSuaTTHinhAnTonThuongItem,
            props: true,
            meta: {
              tabId: 4
            },
          }
        ]
      },
      {
        path: 'pttt',
        name: 'chinhSuaTTPhauThuatThuThuat',
        component: ChinhSuaTTPhauThuatThuThuat,
        props: true,
        meta: {
          tabId: 5
        }
      },
      {
        path: 'xetnghiem',
        name: 'chinhSuaTTXetNghiem',
        component: ChinhSuaTTXetNghiem,
        props: true,
        meta: {
          tabId: 6
        }
      },
      {
        path: 'cdha',
        name: 'chinhSuaTTChanDoanHinhAnh',
        component: ChinhSuaTTChanDoanHinhAnh,
        props: true,
        meta: {
          tabId: 7
        }
      },
      {
        path: 'tdcn',
        name: 'chinhSuaTTThamDoChucNang',
        component: ChinhSuaTTThamDoChucNang,
        props: true,
        meta: {
          tabId: 8
        }
      },
      {
        path: 'gpb',
        name: 'chinhSuaTTGiaiPhauBenh',
        component: ChinhSuaTTGiaiPhauBenh,
        props: true,
        meta: {
          tabId: 9
        }
      },
      {
        path: 'hoichan',
        name: 'chinhSuaTTHoiChan',
        component: ChinhSuaTTHoiChan,
        props: true,
        meta: {
          tabId: 10
        }
      },
      {
        path: 'dieutri',
        name: 'chinhSuaTTDieuTri',
        component: ChinhSuaTTDieuTri,
        props: true,
        meta: {
          tabId: 11
        }
      },
      {
        path: 'chamsoc',
        name: 'chinhSuaTTChamSoc',
        component: ChinhSuaTTChamSoc,
        props: true,
        meta: {
          tabId: 12
        }
      },
      {
        path: 'chucnangsong',
        name: 'chinhSuaTTChucNangSong',
        component: ChinhSuaTTChucNangSong,
        props: true,
        meta: {
          tabId: 13
        }
      },
      {
        path: 'donthuoc',
        name: 'chinhSuaTTDonThuoc',
        component: ChinhSuaTTDonThuoc,
        props: true,
        meta: {
          tabId: 14
        }
      },
      {
        path: 'giayto_khac',
        name: 'chinhSuaTTGiayToKhac',
        component: ChinhSuaTTGiayToKhac,
        props: true,
        meta: {
          tabId: 15
        }
      }
    ]
  },
  {
    path: '/dashboard',
    name: 'dashboard',
    component: DashBoard
  },

  {
    path: '/danhmuc/dungchung',
    name: 'dmDungChung',
    component: DmDungChung
  },
  {
    path: '/danhmuc/chuanhoa',
    name: 'dmChuanHoa',
    component: DmChuanHoa
  },
    {
        path: '/quantri/dsnguoidung',
        name: 'dsnguoidung',
        component: DsNguoiDung
    },
    {
        path: '/quantri/themnguoidung',
        name: 'themnguoidung',
        component: ThemNguoiDung
    }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router;
