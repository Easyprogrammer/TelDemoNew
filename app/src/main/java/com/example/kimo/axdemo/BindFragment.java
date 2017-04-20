package com.example.kimo.axdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
 * Date:     2017/4/16 12:34 <br/>
 *
 * @author 76dgs02
 * @see
 * @since JDK 1.6
 */


public class BindFragment extends Fragment implements View.OnClickListener {

    private static final String BASE_URL = "https://api.ucpaas.com/";
    private static final String VERSION = "2014-06-30";
    private static final String TAG = "BindFragment";
    private EditText mEtAccountSid;
    private EditText mEtToken;
    private EditText mEtAppId;
    private EditText mEtMiddleNumber;
    private EditText mEtCallee;
    private EditText mEtMaxAge;
    private RadioGroup mRgShowOption;
    private LinearLayout mLlResult;
    private TextView mTvResult;
    private Button mBtnBind;
    private Button mBtnClear;
    private Retrofit retrofit;
    private String accountSid;
    private String token;
    private String appId;
    private BindInfo bindInfo;

    public static BindFragment newInstance() {
        BindFragment fragment = new BindFragment();
        return fragment;
    }

    public BindFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bind, container, false);
        view.setFocusableInTouchMode(true);
        view.setFocusable(true);
        view.requestFocus();
        initView(view);
        return view;
    }

    private void initView(View view) {
        mEtAccountSid = (EditText) view.findViewById(R.id.accountSid);
        mEtToken = (EditText) view.findViewById(R.id.token);
        mEtAppId = (EditText) view.findViewById(R.id.appId);
        mEtMiddleNumber = (EditText) view.findViewById(R.id.middleNumber);
        mEtCallee = (EditText) view.findViewById(R.id.callee);
        mEtMaxAge = (EditText) view.findViewById(R.id.maxAge);
        mRgShowOption = (RadioGroup) view.findViewById(R.id.showOption);
        mLlResult = (LinearLayout) view.findViewById(R.id.ll_result);
        mTvResult = (TextView) view.findViewById(R.id.tv_result);
        mBtnBind = (Button) view.findViewById(R.id.btn_bind);
        mBtnBind.setOnClickListener(this);
        mBtnClear = (Button) view.findViewById(R.id.btn_clear);
        mBtnClear.setOnClickListener(this);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        bind();
    }

    private void bind() {
        initJsonData();
        String timeStr = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME_NO_SLASH);
        Log.d(TAG, "bind: timeStr: " + timeStr);
        EncryptUtil encryptUtil = new EncryptUtil();
        String sigParameter = null;
        try {
            sigParameter = encryptUtil.md5Digest(accountSid + token + timeStr).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initRetrofit(timeStr);
        Api api = retrofit.create(Api.class);
        Gson gson = new Gson();
        byte[] jsonStr = gson.toJson(bindInfo).getBytes();
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);
        Observable<String> result = api.bindAX(VERSION, accountSid, sigParameter, body);
        result
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(@NonNull String restStr) throws Exception {
                                   mLlResult.setVisibility(View.VISIBLE);
                                   mTvResult.setText(restStr);
                                   JSONObject jsonObject = new JSONObject(restStr);
                                   String bindId = null;
                                   try {
                                       bindId = (String) jsonObject.get("bindId");
                                   } catch (JSONException e) {
                                       e.printStackTrace();
                                   }
                                   if (null != bindId) {
                                       Toast.makeText(getActivity(), "小号绑定成功。绑定周期内，任意号码拨打中间号都将接通您的电话！", Toast.LENGTH_LONG).show();
                                       ListDataSave listDataSave = new ListDataSave(BindFragment.this.getContext(), "AxDemo");
                                       List<Map<String, Object>> bindIds = listDataSave.getDataList("bindIds");
                                       Map<String, Object> param = new HashMap<>();
                                       param.put("bindId", bindId);
                                       param.put("bindTime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
                                       param.put("accountSid", mEtAccountSid.getText().toString());
                                       param.put("token", mEtToken.getText().toString());
                                       param.put("appId", mEtAppId.getText().toString());
                                       param.put("middleNumber", mEtMiddleNumber.getText().toString());
                                       bindIds.add(param);
                                       listDataSave.setDataList("bindIds", bindIds);
                                   }

                               }
                           }
                );
    }

    private void initJsonData() {
        accountSid = mEtAccountSid.getText().toString();
        token = mEtToken.getText().toString();
        appId = mEtAppId.getText().toString();
        bindInfo = new BindInfo();
        bindInfo.setAppId(appId);
        bindInfo.setCallee(mEtCallee.getText().toString());
        bindInfo.setName("hou");
        bindInfo.setCardno("0");
        bindInfo.setCardtype("0");
        bindInfo.setCityId("008610");
        bindInfo.setDstVirtualNum(mEtMiddleNumber.getText().toString());
        bindInfo.setRecord("0");
        bindInfo.setMaxAge(String.valueOf(Integer.parseInt(mEtMaxAge.getText().toString()) * 60));
        bindInfo.setRequestId(UUID.randomUUID().toString().replaceAll("-", ""));
        bindInfo.setVirtualType("0");
        bindInfo.setCallDisplay(mRgShowOption.getCheckedRadioButtonId() == R.id.rb_showx ? "1" : "0");
        bindInfo.setBindId(UUID.randomUUID().toString().replaceAll("-", ""));
    }

    private void initRetrofit(String time) {
        String Authorization = null;
        try {
            Authorization = Base64.encodeToString((accountSid + ":" + time).getBytes("UTF-8"), Base64.NO_WRAP);
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
                                .addHeader("Authorization", BindFragment.this.getAu(finalAuthorization))
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_bind:
                if (TextUtils.isEmpty(mEtAccountSid.getText()) || TextUtils.isEmpty(mEtToken.getText()) || TextUtils.isEmpty(mEtAppId.getText()) || TextUtils.isEmpty(mEtMiddleNumber.getText()) || TextUtils.isEmpty(mEtCallee.getText()) || TextUtils.isEmpty(mEtMaxAge.getText())) {
                    Toast.makeText(getActivity(), "字段不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                bind();
                break;
            case R.id.btn_clear:
                mTvResult.setText("");
                mLlResult.setVisibility(View.GONE);
                break;
        }
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
}
