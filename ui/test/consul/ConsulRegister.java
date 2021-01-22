package consul;

import com.dozenx.common.util.HttpRequestUtil;
import okhttp3.*;
import okio.BufferedSink;

import java.io.IOException;

public class ConsulRegister {

    public static void main(String args[]){
        String body = "{\n" +
                "\n" +
                "  \"Datacenter\": \"dc1\",\n" +
                "  \"ID\": \"40e4a748-2192-161a-0510-9bf59fe950b5\",\n" +
                "  \"Node\": \"foobar\",\n" +
                "  \"Address\": \"127.0.0.1\",\n" +
                "  \"TaggedAddresses\": {\n" +
                "    \"lan\": \"127.0.0.1\",\n" +
                "    \"wan\": \"127.0.0.1\"\n" +
                "  },\n" +
                "  \"NodeMeta\": {\n" +
                "    \"somekey\": \"somevalue\"\n" +
                "  },\n" +
                "  \"Service\": {\n" +
                "    \"ID\": \"redis1\",\n" +
                "    \"Service\": \"productRest\",\n" +
                "    \"Tags\": [\n" +
                "      \"primary\",\n" +
                "      \"v1\"\n" +
                "    ],\n" +
                "    \"Address\": \"192.168.0.201\",\n" +
                "    \"Port\": 8080\n" +
                "  },\n" +
                "  \"Check\": {\n" +
                "    \"Node\": \"foobar\",\n" +
                "    \"CheckID\": \"service:redis1\",\n" +
                "    \"Name\": \"Redis health check\",\n" +
                "    \"Notes\": \"Script based health check\",\n" +
                "    \"Status\": \"passing\",\n" +
                "    \"ServiceID\": \"redis1\"\n" +
                "  }\n" +
                "}\n"
           ;
//        String result = HttpRequestUtil.sendPut("http://192.168.213.91:8500/v1/catalog/register",body);
//        System.out.println("接口:"+result);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        String requestBody = body;

        OkHttpClient okHttpClient = new OkHttpClient();

        final Request request = new Request.Builder()
                .url("http://192.168.213.91:8500/v1/catalog/register")
                .put(RequestBody.create(mediaType, requestBody))//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println( "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               System.out.println( "onResponse: " + response.body().string());
            }
        });
    }
}
