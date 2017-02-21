package com.abooc.joker.dialog;

import org.fourthline.cling.support.model.Res;
import org.junit.Test;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {




    @Test
    public void test_upnp_xml() throws IOException {

        String url = "http://images.apple.com/";
        Res res = UPnP.buildRes("image/jpeg", "filePath", url, 0);
//        Photo videoItem = new Photo("1", String.valueOf(1), "The New iPhone7", "unknown", "unknown", res);
//        String metadata = UPnP.buildMetadataXml(videoItem);


//        TVVideoItem item = new TVVideoItem("1", "1", "标题", null, res);
//        item.setChannelID("222");
//        item.setChannelUid("1");
//        item.setChannelName("张三的频道");
//        item.setChannelNickname("张三");
//        item.setChannelAvatar("头像");
//
//        String metadata = UPnP.buildMetadataXml(item);
//
//        out(metadata);
    }


    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    OkHttpClient client = new OkHttpClient();


    @Test
    public void test_post() throws IOException {
        String url = "http://192.168.1.218:21367";
        String json = "{" +
                "\"code\":230," +
                "\"info\":\"\"" +
                "}";

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        String string = response.body().string();

        out(string);
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    static void out(String message) {
        System.out.println(message);
    }

}