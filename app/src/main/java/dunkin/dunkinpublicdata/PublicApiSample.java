package dunkin.dunkinpublicdata;
/*
 * Copyright (c) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class PublicApiSample extends AsyncTask {

    static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    static final JsonFactory JSON_FACTORY = new JacksonFactory();
    String strUrl, result;
    URL Url;
        protected void onPreExecute() {
            super.onPreExecute();
            String ServiceUrl = "http://openapi.tago.go.kr/openapi/service"; //탐색하고 싶은 URL이다.
            String strKeys = "???";
            String services = "ArvlInfoInqireService";
            String code = "getCtyCodeList";
            strUrl = ServiceUrl + "/" + services + "/" + code + "?" + "serviceKey=" + strKeys;
            Log.d("dk test", strUrl);
        }
    @Override
    protected Object doInBackground(Object[] params) {
        try{
            Url = new URL(strUrl); // URL화 한다.
            HttpURLConnection conn = (HttpURLConnection) Url.openConnection(); // URL을 연결한 객체 생성.


//            conn.setRequestMethod("POST"); // get방식 통신
//            conn.setDoOutput(true); // 쓰기모드 지정
//            conn.setDoInput(true); // 읽기모드 지정
            conn.setUseCaches(false); // 캐싱데이터를 받을지 안받을지
            conn.setDefaultUseCaches(false); // 캐싱데이터 디폴트 값 설정
//            strCookie = conn.getHeaderField("Set-Cookie"); //쿠키데이터 보관

            int statusCode = conn.getResponseCode();
            InputStream is = null;

            if (statusCode >= 200 && statusCode < 400) {
                // Create an InputStream in order to extract the response object
                Log.d("dk test", "normal case");
                is = conn.getInputStream();
            }
            else {
                Log.d("dk test", "error case "  + statusCode);
                is = conn.getErrorStream();
            }
//            InputStream is = conn.getInputStream(); //input스트림 개방

            StringBuilder builder = new StringBuilder(); //문자열을 담기 위한 객체
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8")); //문자열 셋 세팅
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line+ "\n");
            }

            result = builder.toString();
            Log.d("test dk", result);

        }catch(MalformedURLException | ProtocolException exception) {
            exception.printStackTrace();
        }catch(IOException io){
            io.printStackTrace();
        }
        return null;
    }
}
