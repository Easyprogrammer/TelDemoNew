package com.example.kimo.axdemo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;

/**
 * ClassName:${TYPE_NAME} <br/>
 * Function: ${TODO} ADD FUNCTION. <br/>
 * Reason:   ${TODO} ADD REASON. <br/>
 * Date:     2017/4/17 15:55 <br/>
 *
 * @author 76dgs02
 * @see
 * @since JDK 1.6
 */


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.NormalViewHolder> {
    private static final String BASE_URL = "https://api.ucpaas.com/";
    private static final String VERSION = "2014-06-30";
    //    private static final String TAG = "RecyclerViewAdapter";
    private LayoutInflater mLayoutInflater;
    private Activity mContext;
    private List<Map<String, Object>> mBindIds;
    private Retrofit retrofit;
    private CallBInfo callBInfo;
    private UnbindInfo unbindInfo;


    public RecyclerViewAdapter(Context context, List<Map<String, Object>> bindIds) {
        mContext = (Activity) context;
        mBindIds = bindIds;
        mLayoutInflater = LayoutInflater.from(context);

    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        TextView mTvTime;
        TextView mTvBindId;
        EditText mEtCaller;
        EditText mEtCityId;
        Button mBtnCall;
        Button mBtnUnbind;

        public NormalViewHolder(View itemView) {
            super(itemView);
            mTvTime = (TextView) itemView.findViewById(R.id.tv_time);
            mTvBindId = (TextView) itemView.findViewById(R.id.tv_bindid);
            mEtCaller = (EditText) itemView.findViewById(R.id.et_caller);
            mEtCityId = (EditText) itemView.findViewById(R.id.et_cityid);
            mBtnCall = (Button) itemView.findViewById(R.id.btn_call);
            mBtnUnbind = (Button) itemView.findViewById(R.id.btn_unbind);

        }


    }

    //在该方法中我们创建一个ViewHolder并返回，ViewHolder必须有一个带有View的构造函数，这个View就是我们Item的根布局，在这里我们使用自定义Item的布局；
    @Override
    public NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalViewHolder(mLayoutInflater.inflate(R.layout.item_setb, parent, false));
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(NormalViewHolder holder, final int position) {
        String bindTime = ((LinkedTreeMap) mBindIds.get(position)).get("bindTime").toString();
        String bindId = ((LinkedTreeMap) mBindIds.get(position)).get("bindId").toString();
        String accountSid = ((LinkedTreeMap) mBindIds.get(position)).get("accountSid").toString();
        String token = ((LinkedTreeMap) mBindIds.get(position)).get("token").toString();
        String appId = ((LinkedTreeMap) mBindIds.get(position)).get("appId").toString();
        String middleNumber = ((LinkedTreeMap) mBindIds.get(position)).get("middleNumber").toString();
        holder.mEtCaller.setTag(position);
        holder.mEtCityId.setTag(position);
        holder.mEtCaller.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if ((int) (holder.mEtCaller.getTag()) == position) {
                    if (position < mBindIds.size()) {
                        ListDataSave listDataSave = new ListDataSave(mContext, "AxDemo");
                        List<Map<String, Object>> bindIds = listDataSave.getDataList("bindIds");
                        Map<String, Object> item = bindIds.get(position);
                        item.remove("callee");
                        item.put("callee", s.toString());
                        bindIds.remove(position);
                        bindIds.add(item);
                        listDataSave.setDataList("bindIds", bindIds);
                    }
                }
            }
        });
        holder.mEtCityId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if ((int) (holder.mEtCityId.getTag()) == position) {
                    if (position < mBindIds.size()) {
                        ListDataSave listDataSave = new ListDataSave(mContext, "AxDemo");
                        List<Map<String, Object>> bindIds = listDataSave.getDataList("bindIds");
                        Map<String, Object> item = bindIds.get(position);
                        item.remove("cityid");
                        item.put("cityid", s.toString());
                        bindIds.remove(position);
                        bindIds.add(item);
                        listDataSave.setDataList("bindIds", bindIds);
                    }
                }
            }
        });
        holder.mTvTime.setText("BindTime: " + bindTime);
        holder.mTvBindId.setText("BindId: " + bindId);
        holder.mBtnCall.setOnClickListener(v -> {
            if (holder.mEtCaller.getText().toString().equals("") || holder.mEtCityId.getText().toString().equals("")) {
                Toast.makeText(mContext, "callee和cityId不能为空", Toast.LENGTH_SHORT).show();
            } else {
                ListDataSave listDataSave = new ListDataSave(mContext, "AxDemo");
                List<Map<String, Object>> bindIds = listDataSave.getDataList("bindIds");
                Map<String, Object> itemMap = bindIds.get(position);
                String callee = (String) itemMap.get("callee");
                String cityId = (String) itemMap.get("cityid");
                callB(accountSid, token, appId, bindId, callee, middleNumber, cityId);
            }

        });
        holder.mBtnUnbind.setOnClickListener(v -> {
            if (holder.mEtCityId.getText().toString().equals("")) {
                Toast.makeText(mContext, "cityId不能为空", Toast.LENGTH_SHORT).show();
            } else {
                ListDataSave listDataSave = new ListDataSave(mContext, "AxDemo");
                List<Map<String, Object>> bindIds = listDataSave.getDataList("bindIds");
                Map<String, Object> itemMap = bindIds.get(position);
                String cityId = (String) itemMap.get("cityid");
                unbind(accountSid, token, appId, bindId, cityId);
                bindIds.remove(position);
                listDataSave.setDataList("bindIds", bindIds);
                mBindIds = bindIds;
                notifyDataSetChanged();
            }
        });

    }

    private void unbind(String accountSid, String token, String appId, String bindId, String cityId) {
        initUnbindJsonData(appId, bindId, cityId);
        String timeStr = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME_NO_SLASH);
//        Log.d(TAG, "bind: timeStr: " + timeStr);
        EncryptUtil encryptUtil = new EncryptUtil();
        String sigParameter = null;
        try {
            sigParameter = encryptUtil.md5Digest(accountSid + token + timeStr).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initCallBRetrofit(accountSid, timeStr);
        Api api = retrofit.create(Api.class);
        Gson gson = new Gson();
        byte[] jsonStr = gson.toJson(unbindInfo).getBytes();
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);
        Observable<String> result = api.unBindAX(VERSION, accountSid, sigParameter, body);
        result
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(@NonNull String restStr) throws Exception {

                                   JSONObject jsonObject = new JSONObject(restStr);
                                   String errorCode = null;
                                   try {
                                       errorCode = (String) jsonObject.get("errorCode");
                                   } catch (JSONException e) {
                                       e.printStackTrace();
                                   }
                                   if (null != bindId) {
                                       if ("000000".equals(errorCode)) {
                                           Toast.makeText(mContext, "解绑成功", Toast.LENGTH_SHORT).show();

                                       } else {
                                           Toast.makeText(mContext, restStr, Toast.LENGTH_SHORT).show();
                                       }

                                   }

                               }
                           }
                );
    }

    private void initUnbindJsonData(String appId, String bindId, String cityId) {
        unbindInfo = new UnbindInfo();
        unbindInfo.setAppId(appId);
        unbindInfo.setCityId("0086" + cityId);
        unbindInfo.setBindId(bindId);

    }

    private void callB(String accountSid, String token, String appId, String bindId, String caller, String middleNumber, String cityId) {
        initCallBJsonData(appId, bindId, caller, cityId);
        String timeStr = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME_NO_SLASH);
//        Log.d(TAG, "bind: timeStr: " + timeStr);
        EncryptUtil encryptUtil = new EncryptUtil();
        String sigParameter = null;
        try {
            sigParameter = encryptUtil.md5Digest(accountSid + token + timeStr).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initCallBRetrofit(accountSid, timeStr);
        Api api = retrofit.create(Api.class);
        Gson gson = new Gson();
        byte[] jsonStr = gson.toJson(callBInfo).getBytes();
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);
        Observable<String> result = api.updataB(VERSION, accountSid, sigParameter, body);
        result
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(@NonNull String restStr) throws Exception {

                                   JSONObject jsonObject = new JSONObject(restStr);
                                   String errorCode = null;
                                   try {
                                       errorCode = (String) jsonObject.get("errorCode");
                                   } catch (JSONException e) {
                                       e.printStackTrace();
                                   }
                                   if (null != bindId) {
                                       if ("000000".equals(errorCode)) {
                                           Toast.makeText(mContext, "正在通过小号拨打：" + caller, Toast.LENGTH_SHORT).show();
                                           dial(middleNumber);
                                       } else {
                                           Toast.makeText(mContext, restStr, Toast.LENGTH_SHORT).show();
                                       }
                                   }

                               }
                           }
                );

    }

    private void initCallBJsonData(String appId, String bindId, String caller, String cityId) {
        callBInfo = new CallBInfo();
        callBInfo.setAppId(appId);
        callBInfo.setBindId(bindId);
        callBInfo.setCaller(caller);
        callBInfo.setCityId("0086" + cityId);
        callBInfo.setRequestId(UUID.randomUUID().toString().replaceAll("-", ""));
    }

    private void initCallBRetrofit(String accountSid, String timeStr) {
        String Authorization = null;
        try {
            Authorization = Base64.encodeToString((accountSid + ":" + timeStr).getBytes("UTF-8"), Base64.NO_WRAP);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final String finalAuthorization = Authorization;
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/json; charset=UTF-8")
                                .addHeader("Accept", "application/json")
                                .addHeader("Authorization", RecyclerViewAdapter.this.getAu(finalAuthorization))
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();

        retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }


    //获取数据的数量
    @Override
    public int getItemCount() {
        return mBindIds == null ? 0 : mBindIds.size();
    }

    private String getAu(String au) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0, length = au.length(); i < length; i++) {
            char c = au.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                stringBuffer.append(String.format("\\u%04x", (int) c));
            } else {
                stringBuffer.append(c);
            }
        }
        au = stringBuffer.toString();
//        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
//        Matcher m = p.matcher(au);
//        au = m.replaceAll("");
        return au;
    }

    public void dial(String caller) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + caller));
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission
                    .CALL_PHONE}, 1);
        } else {
            mContext.startActivity(intent);
        }
    }
}
