package com.example.young_jin.jamong.network;

/**
 * Created by sngjoong on 16. 1. 23..
 */
public class APIKeyStore {

    //공통 응답 객체 Keys
    public static final String RESP_DATA_SET = "dataSet";
    public static final String RESP_RECORD_SETS = "recordSets";
    public static final String RESP_FIELDS= "fields";

    //주유소 리스트 API 요청 파라미터 Keys
    public static final String STATION_LIST_REQ_ID = "MLNSearchOilStListPub";
    public static final String STATION_LIST_REQ_FID = "FIDMOB1702";
    public static final String STATION_LIST_REQ_UPD_DATE = "UPD_DATE";

    //주유소 리스트 API 응답 객체 Keys
    public static final String STATION_LIST_RESP_UPD_DATE = "UPD_DATE";
    public static final String STATION_LIST_RESP_ARR_SHOP = "ARR_SHOP";
    public static final String STATION_LIST_RESP_ARR_SHOP_EXCEPT = "ARR_SHOP_EXCEPT";
    public static final String STATION_LIST_RESP_NC_LIST = "nc_list";

    //주유소 리스트 API 응답 객체 안의 배열 요소 Keys
    public static final String STATION_LIST_RESP_OPT_CONVSTORE_YN = "OPT_CONVSTORE_YN";
    public static final String STATION_LIST_RESP_OPT_DIRECT_YN = "OPT_DIRECT_YN";
    public static final String STATION_LIST_RESP_TEL = "TEL";
    public static final String STATION_LIST_RESP_NM = "NM";
    public static final String STATION_LIST_RESP_SEQ = "SEQ";
    public static final String STATION_LIST_RESP_OPT_HGAS_YN = "OPT_HGAS_YN";
    public static final String STATION_LIST_RESP_OPT_SELF_YN = "OPT_SELF_YN";
    public static final String STATION_LIST_RESP_LOC_LONG = "LOC_LONG";
    public static final String STATION_LIST_RESP_LOC_LAT = "LOC_LAT";
    public static final String STATION_LIST_RESP_ADDR = "ADDR";
    public static final String STATION_LIST_RESP_OPT_WASH_YN = "OPT_WASH_YN";
    public static final String STATION_LIST_RESP_OPT_REPAIR_YN = "OPT_REPAIR_YN";

    //앱 초기버전 체크 요청 파라미터 Keys
    public static final String APP_INIT_REQ_ID = "COMAppInitDataPub";
    public static final String APP_INIT_REQ_UPD_DATE = "UPD_DATE";
    public static final String APP_INIT_REQ_OS_GBN = "OS_GBN";

    //앱 초기버전 체크 응답 파리미터 Keys
    public static final String APP_INIT_RESP_APP_VERSION = "APP_VER";
    public static final String APP_INIT_RESP_UPD_DATE = "UPD_DATE";

    //주소검색 요청 파라미터 Keys
    public static final String ADDR_SEARCH_REQ_FID = "FIDMOB2902";
    public static final String ADDR_SEARCH_REQ_ID = "COMSearchAddrList";
    public static final String ADDR_SEARCH_REQ_KUBUN = "KUBUN";
    public static final String ADDR_SEARCH_REQ_DONG = "DONG";
    public static final String ADDR_SEARCH_REQ_BUNJI = "BUNJI";
    public static final String ADDR_SEARCH_REQ_SI_DO = "SI_DO";
    public static final String ADDR_SEARCH_REQ_GU_GUN = "GU_GUN";
    public static final String ADDR_SEARCH_REQ_DORO = "DORO";
    public static final String ADDR_SEARCH_REQ_BULD_NO= "BULD_NO";

    //주소검색 응답 파라미터 Keys
    public static final String ADDR_SEARCH_RESP_POST = "POST";
    public static final String ADDR_SEARCH_RESP_ADDR = "ADDR";
    public static final String ADDR_SEARCH_RESP_SAN = "SAN";
    public static final String ADDR_SEARCH_RESP_HJD = "HJD";
    public static final String ADDR_SEARCH_RESP_BUNGI_S = "BUNGI_S";
    public static final String ADDR_SEARCH_RESP_BUNGI_E = "BUNGI_E";
    public static final String ADDR_SEARCH_RESP_DORO_NM = "DORO_NM";
    public static final String ADDR_SEARCH_RESP_JIHA = "JIHA";
    public static final String ADDR_SEARCH_RESP_BULD_NO_S = "BUIL_NO_S";
    public static final String ADDR_SEARCH_RESP_BULD_NO_E = "BULD_NO_E";
    public static final String ADDR_SEARCH_RESP_RANGE = "RANGE";

    //공지사항 요청 파라미터 Keys
    public static final String NOTICE_LIST_REQ_FID = "FIDMOB2401";
    public static final String NOTICE_LIST_REQ_ID = "MCYSearchBoardList";
    public static final String NOTICE_LIST_REQ_PAGE = "PAGE";
    public static final String NOTICE_LIST_REQ_PAGE_RECS = "PAGE_RECS";
    public static final String NOTICE_LIST_REQ_CHN = "CHN";

    //공지사항 응답 파라미터 Keys
    public static final String NOTICE_LIST_RESP_ARR_NOTICE = "ARR_NOTICE";
    public static final String NOTICE_LIST_RESP_NC_LIST = "nc_list";
    public static final String NOTICE_LIST_RESP_SEQ = "SEQ";
    public static final String NOTICE_LIST_RESP_TITLE = "TITLE";
    public static final String NOTICE_LIST_RESP_REG_DT= "REG_DT";

    //공지사항 상세 요청 파라미터 Keys
    public static final String NOTICE_DETAIL_REQ_FID = "FIDMOB2402";
    public static final String NOTICE_DETAIL_REQ_ID = "MCYDetailBoard";
    public static final String NOTICE_DETAIL_REQ_SEQ = "SEQ";

   //공지사항 상세 응답 파라미터 Keys
    public static final String NOTICE_DETAIL_RESP_TITLE = "TITLE";
    public static final String NOTICE_DETAIL_RESP_CONTENTS = "CONTENTS";
    public static final String NOTICE_DETAIL_RESP_REG_DT = "REG_DT";

    //시/도, 구/군, 동/면 리스트 요청 파라미터 Keys
    public static final String ADR_LIST_REQ_FID = "FIDMOB2901";
    public static final String ADR_LIST_REQ_ID = "COMSearchZipInfoList";
    public static final String ADR_LIST_REQ_SI_DO = "SI_DO";
    public static final String ADR_LIST_REQ_GU_GUN = "GU_GUN";

    //시/도, 구/군, 동/면 리스트 응답 파라미터 Keys
    public static final String ADR_LIST_RESP_NM = "NM";
    public static final String ADR_LIST_RESP_ARR_SI_DO = "ARR_SI_DO";
    public static final String ADR_LIST_RESP_ARR_GU_GUN = "ARR_GU_GUN";
    public static final String ADR_LIST_RESP_ARR_DONG_MYEON = "ARR_DONG_MYEON";
    public static final String ADR_LIST_RESP_NC_LIST = "nc_list";


    //Tmap api keys
    public static final String TMAP_PRODUCTION_API_KEY = "612b9f9b-2714-3436-8632-609e1513a5d5";
    public static final String TMAP_ZAM_API_KEY = "8fcabbc7-cf9d-34bc-a0f8-5674bbe62ee9";
    public static final String TMAP_DEV_API_KEY = "bdd542f6-63c6-3624-bf3d-e764d56c4249";

    //Tmap 지오코딩 쿼리 Keys
    public static final String TMAP_GEO_QUERY_LAT = "lat";
    public static final String TMAP_GEO_QUERY_LONG = "lon";
    public static final String TMAP_GEO_QUERY_COORD_TYPE = "coordType";
    public static final String TMAP_QUERY_API_VERSION = "version";

    //Tmap 지오코딩 응답 Keys
    public static final String TMAP_GEO_RESP_ADDR_INFO = "addressInfo";
    public static final String TMAP_GEO_RESP_FULL_ADDR = "fullAddress";

    //Tmap 경로탐색 요청 파라미터
    public static final String TMAP_ROUTES_REQ_START_LAT = "startY";
    public static final String TMAP_ROUTES_REQ_START_LON = "startX";

    public static final String TMAP_ROUTES_REQ_END_LAT = "endY";
    public static final String TMAP_ROUTES_REQ_END_LON = "endX";
    public static final String TMAP_ROUTES_REQ_COORD_TYPE = "reqCoordType";

    public static final String TMAP_ROUTES_RESP_FEATURES = "features";
    public static final String TMAP_ROUTES_RESP_PROPERTIES = "properties";

    public static final String TMAP_ROUTES_RESP_TOTAL_TIME = "totalTime";
    public static final String TMAP_ROUTES_RESP_TOTAL_DISTANCE = "totalDistance";

}
