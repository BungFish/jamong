package com.example.young_jin.jamong.network;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sngjoong on 16. 1. 23..
 */
public class APIRequester {

    private static final String LOG_TAG = APIRequester.class.getSimpleName();
    private static final String ROOT_URL_DEVELOP = "http://mobilehamdev.sknetworks.co.kr/web/json.jmd";
    //private static final String ROOT_URL_PRODUCTION = "http://mobileham.sknetworks.co.kr/web/json.jmd";

    // 파라미터 오브젝트 키정보
    private static final String PARAM_ROOT_KEY_TRASACTION = "transaction";
    private static final String PARAM_ROOT_KEY_ATTRIBUTES = "attributes";
    private static final String PARAM_ROOT_KEY_DATASETS = "dataSet";

    // 파라미터 서브오브젝트 키정보
    private static final String PARAM_SUB_KEY_OF_TRANSACTION_FID = "fid";
    private static final String PARAM_SUB_KEY_OF_TRANSACTION_ID = "id";
    private static final String PARAM_SUB_KEY_OF_ATTRIBUTE_FRST_TRNM_CHNL_CD = "FRST_TRNM_CHNL_CD";
    private static final String PARAM_SUB_KEY_OF_ATTRIBUTE_SSO_SESN_KEY_RENEW = "SSO_SESN_KEY_RENEW";
    private static final String PARAM_SUB_KEY_OF_ATTRIBUTE_SSO_SESN_KEY = "SSO_SESN_KEY";
    private static final String PARAM_SUB_KEY_OF_DATASETS_FIELDS = "fields";

    // Tmap api 리소스 루트
    private static final String OPEN_API_ROOT_AUTHORITY = "apis.skplanetx.com";
    private static final String TMAP_API_ROOT_PATH = "tmap";

    // Tmap api 리소스 지오코딩
    private static final String TMAP_API_GEO_PATH = "geo";
    private static final String TMAP_API_REV_GEOCODING_PATH = "reversegeocoding";

    //Tmap api 리소스 경로탐색
    private static final String TMAP_API_ROUTES_PATH = "routes";



    private static final String TMAP_PUB_API_KEY = APIKeyStore.TMAP_ZAM_API_KEY;

    //Tmap 좌표계 타입
    private static final String TMAP_COORD_TYPE = "WGS84GEO";


    private Context mContext;

    public interface APICallbackListener {

        public void onBefore();

        public void onSuccess(Object object);

        public void onFail(Error error);
    }

    public APIRequester(Context context) {
        mContext = context;
    }

    public void requestPOST(JSONObject jsonRequestParams, final APICallbackListener callbackListener) {

        callbackListener.onBefore();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ROOT_URL_DEVELOP, jsonRequestParams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                callbackListener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Error err = new Error(error.getLocalizedMessage());
                callbackListener.onFail(err);
            }
        });

        RequestQueueManager.getInstance(mContext).getRequestQueue().add(jsonObjectRequest);
    }

    public JsonObjectRequest getRouteTimeWithDistanceRequest(String startLat, String startLng, String endLat, String endLng, final APICallbackListener callbackListener) {

        callbackListener.onBefore();

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(OPEN_API_ROOT_AUTHORITY)
                .appendPath(TMAP_API_ROOT_PATH)
                .appendPath(TMAP_API_ROUTES_PATH)
                .appendQueryParameter(APIKeyStore.TMAP_QUERY_API_VERSION, "1");


        String routesUrl = builder.toString();

        JSONObject reqParams = new JSONObject();

        try {
            reqParams.put(APIKeyStore.TMAP_ROUTES_REQ_START_LAT, startLat);
            reqParams.put(APIKeyStore.TMAP_ROUTES_REQ_START_LON, startLng);
            reqParams.put(APIKeyStore.TMAP_ROUTES_REQ_END_LAT, endLat);
            reqParams.put(APIKeyStore.TMAP_ROUTES_REQ_END_LON, endLng);
            reqParams.put(APIKeyStore.TMAP_ROUTES_REQ_COORD_TYPE, TMAP_COORD_TYPE);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(LOG_TAG, reqParams.toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, routesUrl, reqParams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                callbackListener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Error err = new Error(error.getLocalizedMessage());
                callbackListener.onFail(err);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> headers = new HashMap<>();
                headers.put("appKey", TMAP_PUB_API_KEY);
                headers.put("Accept", "application/json");


                return headers;
            }
        };

//        RequestQueueManager.getInstance(mContext).getRequestQueue().add(jsonObjectRequest);
        return jsonObjectRequest;

    }

    public void requestReverseGeocoding(String lat, String lng, final APICallbackListener callbackListener) {

        callbackListener.onBefore();

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(OPEN_API_ROOT_AUTHORITY)
                .appendPath(TMAP_API_ROOT_PATH)
                .appendPath(TMAP_API_GEO_PATH)
                .appendPath(TMAP_API_REV_GEOCODING_PATH)
                .appendQueryParameter(APIKeyStore.TMAP_GEO_QUERY_LAT, lat)
                .appendQueryParameter(APIKeyStore.TMAP_GEO_QUERY_LONG, lng)
                .appendQueryParameter(APIKeyStore.TMAP_GEO_QUERY_COORD_TYPE, TMAP_COORD_TYPE)
                .appendQueryParameter(APIKeyStore.TMAP_QUERY_API_VERSION, "1");

        String reverseGeoCodingUrl = builder.toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, reverseGeoCodingUrl, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                callbackListener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Error err = new Error(error.getLocalizedMessage());
                callbackListener.onFail(err);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {


                HashMap<String, String> headers = new HashMap<>();
                headers.put("appKey", TMAP_PUB_API_KEY);

                return headers;
            }
        };

        RequestQueueManager.getInstance(mContext).getRequestQueue().add(jsonObjectRequest);
    }


    public JSONObject makeRequestParams(String fid, String id, JSONObject fields) {

        JSONObject params = new JSONObject();
        JSONObject transactions = new JSONObject();
        JSONObject attributes = new JSONObject();
        JSONObject dataSet = new JSONObject();

        try {

            if (fid != null && !fid.isEmpty()) {
                transactions.put(PARAM_SUB_KEY_OF_TRANSACTION_FID, fid);
            }
            if (id != null && !id.isEmpty()) {
                transactions.put(PARAM_SUB_KEY_OF_TRANSACTION_ID, id);
            }

            attributes.put(PARAM_SUB_KEY_OF_ATTRIBUTE_FRST_TRNM_CHNL_CD, "PUB");
            attributes.put(PARAM_SUB_KEY_OF_ATTRIBUTE_SSO_SESN_KEY_RENEW, "N");
            //Todo 엑세스토큰 갱신해야함
            attributes.put(PARAM_SUB_KEY_OF_ATTRIBUTE_SSO_SESN_KEY, "");

            if (fields != null) {

                dataSet.put(PARAM_SUB_KEY_OF_DATASETS_FIELDS, fields);
            } else {

                dataSet.put(PARAM_SUB_KEY_OF_DATASETS_FIELDS, new JSONObject());
            }


        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "makeRequestParams: Error making sub objects");
        }


        try {
            params.put(PARAM_ROOT_KEY_DATASETS, dataSet);
            params.put(PARAM_ROOT_KEY_TRASACTION, transactions);
            params.put(PARAM_ROOT_KEY_ATTRIBUTES, attributes);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "makeRequestParams: Error making root objects");
        }

        return params;
    }
}
