package com.http.client;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ning.http.client.AsyncHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Request;
import com.ning.http.client.RequestBuilder;

/**
 * @author Ankur
 */

public class HttpRequestProcessor{

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestProcessor.class);

    public enum HttpMethod{
        HTTP_GET,HTTP_POST,HTTP_DELETE
    };

    @Inject
    private AsyncHttpClient client;

    private AtomicLong apiCallsCount = new AtomicLong(0);
    private AtomicInteger apiCallsCountInLastSecond = new AtomicInteger(0);


    public HttpRequestProcessor(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    LOGGER.info("API count in last seconds are:"+apiCallsCountInLastSecond.getAndSet(0) +"|Total count:"+apiCallsCount.get());
                }
            }
        }).start();
    }

    private String getRequestType(HttpMethod method){
        switch (method){
            case HTTP_DELETE:
                return "DELETE";
            case HTTP_GET:
                return "GET";
            case HTTP_POST:
                return "POST";
            default:
                return null;
        }
    }

    private RequestBuilder createRequestBuilder(HttpMethod method,String url,Map<String,String> headers,Map<String,String> queryParameters){
        RequestBuilder requestBuilder = new RequestBuilder(getRequestType(method));
        requestBuilder.setUrl(url);
        if(headers!=null){
            for (Map.Entry<String,String> header : headers.entrySet()){
                requestBuilder.setHeader(header.getKey(),header.getValue());
            }
        }
        if(queryParameters!=null){
            for (Map.Entry<String,String> requestParameter : queryParameters.entrySet()){
                requestBuilder.addQueryParameter(requestParameter.getKey(),requestParameter.getValue());
            }
        }
        return requestBuilder;
    }

    public Future submitRequest(HttpMethod method,String url,Map<String,String> headers,Map<String,String> queryParameters,Map<String,String> postParameters,String bodyData,AsyncHandler asyncHandler) throws IOException {
        RequestBuilder requestBuilder = createRequestBuilder(HttpMethod.HTTP_POST,url,headers,queryParameters);
        for (Map.Entry<String,String> posParameter : postParameters.entrySet()){
            requestBuilder.addParameter(posParameter.getKey(), posParameter.getValue());
        }
        if(bodyData!=null)
            requestBuilder.setBody(bodyData);
        return  submitRequest(requestBuilder.build(),asyncHandler);
    }

    public Future submitPostRequest(String url,Map<String,String> headers,Map<String,String> queryParameters, Map<String,String> postParameters,AsyncHandler asyncHandler) throws IOException {
        return submitRequest(HttpMethod.HTTP_POST,url,headers,queryParameters,postParameters,null,asyncHandler);
    }

    public void submitPostRequest(String url,Map<String,String> headers,Map<String,String> queryParameters,String bodyData,AsyncHandler asyncHandler) throws IOException {
        submitRequest(HttpMethod.HTTP_POST,url,headers,queryParameters,null,bodyData,asyncHandler);
    }

    public Future submitGetRequest(String url,Map<String,String> headers,Map<String,String> queryParameters,AsyncHandler asyncHandler) throws IOException {
        return submitRequest(createRequestBuilder(HttpMethod.HTTP_GET,url, headers, queryParameters).build(),asyncHandler);
    }

    public Future submitRequest(Request request,AsyncHandler asyncHandler) throws IOException {
    	System.out.println(request);
        apiCallsCountInLastSecond.incrementAndGet();
        apiCallsCount.incrementAndGet();
        return client.executeRequest(request,asyncHandler);
    }


    /*public static void main(String args[]) throws IOException, InterruptedException, ExecutionException {


        Socket socket = new Socket("apis.headlinesapp.mobi",80);
        InputStream inputStream = socket.getInputStream();

        byte b[] = new byte[100000];
        int i =0;
        int k=0;
        socket.getOutputStream().write("POST /headlines/api/v1/user/activity/login?userid=539884701&access_token=CAAC3MgnChTgBAHBZCWstPRqjX45aPqoePhkABIXnZAzj4iiZAMFUbXBQRxwBanN0lGWZBB9uZAFHVvvIxCjFHxYrMIFQypWBQgJpSikluZCf7nJBoVQcAKicwtw8VGdXIYc4Bgl3tqj9FVvGtZBIQxRealXns9a1VZBvTUYYgITfbgZDZD&userName=Saurabht&gmtTime=19800&language=en HTTP/1.0 \r\n".getBytes("UTF-8"));
        ///Thread.sleep(1000);
        socket.getOutputStream().write("Connection: Keep-Alive\r\n".getBytes());
        socket.getOutputStream().write("Host: localhost.com\r\n\r\n".getBytes());
        //socket.getOutputStream().write("Content-Type: application/json\r\n".getBytes("UTF-8"));
        //socket.getOutputStream().write("Content-Length: 100\r\n".getBytes());
        //socket.getOutputStream().write("123456789".getBytes("UTF-8"));

        //Thread.sleep(1000);

        //socket.getOutputStream().write("\r\n".getBytes("UTF-8"));
        //socket.getOutputStream().flush();

        //socket.getOutputStream().write("hello".getBytes());

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String s = null;
        while((s=bufferedReader.readLine())!=null){
          System.out.println(s);
            if(s.contains("Connection: keep-alive")){
                socket.getOutputStream().write("POST /headlines/api/v1/user/activity/login?userid=539884701&access_token=CAAC3MgnChTgBAHBZCWstPRqjX45aPqoePhkABIXnZAzj4iiZAMFUbXBQRxwBanN0lGWZBB9uZAFHVvvIxCjFHxYrMIFQypWBQgJpSikluZCf7nJBoVQcAKicwtw8VGdXIYc4Bgl3tqj9FVvGtZBIQxRealXns9a1VZBvTUYYgITfbgZDZD&userName=Saurabht&gmtTime=19800&language=en HTTP/1.0 \r\n".getBytes("UTF-8"));
                ///Thread.sleep(1000);
                socket.getOutputStream().write("Connection: Keep-Alive\r\n".getBytes());
                socket.getOutputStream().write("Host: localhost.com\r\n".getBytes());

            }
        }
            while((i=inputStream.read())!=-1){
           b[k++]= (byte) i;
        }
        //System.out.println(new String(b,0,k).trim());
        HttpRequestProcessor httpRequestProcessor = new HttpRequestProcessor();

        List<String> queryList = new LinkedList<>();
        FeedFetcher feedFetcher= new FeedFetcher();
        feedFetcher.setHttpRequestProcessor(httpRequestProcessor);
        //feedFetcher.updatePhotos("","CAAC3MgnChTgBAK5LJocVGWLHJaxM9WEofVE4oioVmWufBn8DNhRSfoFs88mgCk3ZCz3IMm28KSoyFZATbQMPgKE6S0SJgAYIvol2hMIEHdZBOyy7yJ7IhChTYqbgLkjCdvZC2tljvhb2MPQUlmEPiYwIWPZAEy52NsPIH9MrilAZDZD",1);
        Future<List<Reference>> f= feedFetcher.friends("CAAC3MgnChTgBAHXvVZAVyCj5ZBrGxYSbNUcsqz7a3LTDDTzp4d7Uq7zo8e3cyKpdXuOUjdhVHWq8hqJNlVbkqILhD4iKgCZAsyvefyS58ubDFHoLDkmvx80FR2tXxhZAzGUDcQiONw4YXWpcnuiKyDbw48DXxYuuT1x6hi8qcrWEVUyJNCWi5Jm1NiNB5ldmapv43SJnDNNqdCBRJBfaZBkIVPguPLZAw5tZAwS7mdwZAQZDZD");
        List<Reference> fList = f.get();;
        File file = new File("/home/ankit/test");
        final FileWriter fileWriter = new FileWriter(file);

        final StringBuffer pids=new StringBuffer("");


        for(int i = 0 ; i < 4000 ;i = i+50){
            //  Thread.sleep(200);
            final Map<String,String> qMap = new LinkedHashMap<>();
            String ids=null;
            for(int k=i;k<i+50&&k<fList.size();k++){
                ids = (ids==null)  ? fList.get(k).getId() : (ids+","+fList.get(k).getId());
            }
            if(ids==null){
                //System.out.println("tet");
                continue;
            }
            DateTime dateTime = new DateTime();

            dateTime = dateTime.minusHours(2);
            //String q ="select aid,pid,place_id,can_delete,can_tag , caption, caption_tags, comment_info, created,like_info,modified,object_id,owner,page_story_id,src_big,src_big_height,src_big_width,target_id from photo where aid in(select aid from album where owner in ("+ids+") and modified>"+dateTime.getMillis()/1000+") and modified>"+dateTime.getMillis()/1000;
            String q ="select aid,pid,place_id,can_delete,can_tag , caption, caption_tags, comment_info, created,like_info,modified,object_id,owner,page_story_id,src_big,src_big_height,src_big_width,target_id from photo where owner in ("+ids+") and modified>"+dateTime.getMillis()/1000;
            //String q = "select message,place_id,source,status_id,time,uid from status where uid in  ("+ids+") and time  > '"+ (dateTime.toDate().getTime()/1000)+"'";
            //String q = "select attachment,app_data, actor_id,comment_info,created_time,description,description_tags,is_published,likes,message,message_tags,place,post_id,source_id,tagged_ids,target_id,type,updated_time,with_tags from stream where source_id in ("+ids+") limit 200";
//            String q = "select status_id from status where uid in (select uid1 from friend where uid2=me()) limit 5000";
            //String q = "select aid,pid,place_id,can_delete,can_tag , caption, caption_tags, comment_info, created,like_info,modified,object_id,owner,page_story_id,src_big,src_big_height,src_big_width,target_id from photo where owner in ("+ids+") and modified > '"+ (dateTime.toDate().getTime()/1000)+"'";
            //String q = "SELECT aid,pid,place_id,can_delete,can_tag , caption, caption_tags, comment_info, created,like_info,modified,object_id,owner,page_story_id,src_big,src_big_height,src_big_width,target_id from photo where pid in (select pid from photo_tag where subject in ("+ids+") ) and modified > '"+ (dateTime.toDate().getTime()/1000)+"'";
            //String q = "select summary from link where owner in  (145271778878804,106077492770524,82913459129,33420113545,158276504188375,107629433911,102947479765209,103274306376166,26748225422,56268861803,123788044484500,524746287545042,174775305891523,7419689078,329272310504918,398613600192048,131125221374,327031699746,102841356695,152038011516462) and created_time >'"+ (dateTime.toDate().getTime()/1000)+"' limit 200";
            qMap.put("access_token","CAAC3MgnChTgBAHXvVZAVyCj5ZBrGxYSbNUcsqz7a3LTDDTzp4d7Uq7zo8e3cyKpdXuOUjdhVHWq8hqJNlVbkqILhD4iKgCZAsyvefyS58ubDFHoLDkmvx80FR2tXxhZAzGUDcQiONw4YXWpcnuiKyDbw48DXxYuuT1x6hi8qcrWEVUyJNCWi5Jm1NiNB5ldmapv43SJnDNNqdCBRJBfaZBkIVPguPLZAw5tZAwS7mdwZAQZDZD");
            qMap.put("q",q);
            queryList.add(q);
        }


        String data = null;
        for(String q: queryList){
            String json ="{\"method\":\"GET\",\"relative_url\":\"/fql?q="+q+"\"}";
            if(data==null)
                data = json;
            else
                data = data+","+ json;
        }

        String finalQ = "["+data+"]";
        Map<String,String> postParams = new HashMap<>();
        postParams.put("access_token","CAAC3MgnChTgBAHXvVZAVyCj5ZBrGxYSbNUcsqz7a3LTDDTzp4d7Uq7zo8e3cyKpdXuOUjdhVHWq8hqJNlVbkqILhD4iKgCZAsyvefyS58ubDFHoLDkmvx80FR2tXxhZAzGUDcQiONw4YXWpcnuiKyDbw48DXxYuuT1x6hi8qcrWEVUyJNCWi5Jm1NiNB5ldmapv43SJnDNNqdCBRJBfaZBkIVPguPLZAw5tZAwS7mdwZAQZDZD");
        postParams.put("batch",finalQ);

        httpRequestProcessor.submitPostRequest("https://graph.facebook.com",null,null,postParams,new AsyncCompletionHandler() {
            @Override
            public Object onCompleted(Response response) throws Exception {
                System.out.println("Response Status:" + response.getStatusCode());
              //  JsonNode jsonNode = FacebookJsonUtils.deserializeEntity(response.getResponseBody(), JsonNode.class);

              //  fileWriter.write(jsonNode.toString());
                fileWriter.write("\n");
                fileWriter.flush();

                return null;
            }
        });

        //Thread.sleep(30000);
        final Map<String,String> qMap = new LinkedHashMap<>();
        String url[] ={
                "http://apis.headlinesapp.mobi//headlines/api/v1/user/activity/login?userid=100000928239390&access_token=CAAC3MgnChTgBAAXehRvP2blIooIbxKVGBCq53tj70EFvtZCToPJgfxaZBcxuN5XakpBAbN6GKZBliCgbpZBZCAhQoZAn3vPLHojxGbBAzKaLyqQBOqOWnY4MsKCWu5zIAEVLdYkP5v1xSM77VJy4NQH1jtXIZBv6FTnfhTOeahu6wZDZD&userName=Dasari%20Venkatesh&gmtTime=19800&language=en",
                "http://apis.headlinesapp.mobi//headlines/api/v1/user/activity/login?userid=100001146641497&access_token=CAAC3MgnChTgBACy364lDBk5ZCbKBzTZA1UjQcR5cxBVHmmRZCxCGuKU24Bkp6wsa4PATDO2L7G1JfeR79ha4fr72DPWnjZA9IP8ijTb0SL4YZAUPZA7XBRs0ODep0mBymtn0X8L3nNUcycTrMY26I2nZA3eHXhSDx4ZCLEANCbmFcQZDZD&userName=Abhisheka%20Deshpande&gmtTime=19800&language=en-GB",
                "http://apis.headlinesapp.mobi//headlines/api/v1/user/activity/login?userid=100000190586167&access_token=CAAC3MgnChTgBAHGpcfSIMVZA9W2TdR5kNJsNjVtB2ShD5nS0AgWuMzvZBqWFZADle2L6cYAI2Fr5rez44ur2VnZBUJyEmQxgcpMzPr46TCyn8Agf8CGTY0yM6ScXMSJvYkLZBHFZBpVuh2BElZALQ7p7fEsArZCRA02e9bPE3eLLSAZDZD&userName=Dilbar%20Singh&gmtTime=19800&language=en-GB",
                "http://apis.headlinesapp.mobi//headlines/api/v1/user/activity/login?userid=1835085551&access_token=CAAC3MgnChTgBAORUgOxHAA48MV7apLZA4i5ZClqzjSkuuXw75OqBrbrasOimhiIfxNe7lseiLZCvoZCMJtog384qWC6PxzlgeHLf93TB975rdyzHD1GnIsZBEYjsPGdjHwIJvPwfluCU6iTGMZBDzbHOZC82qFzPfCyHqNzfIHQ7QZDZD&userName=Santosh%20Yerramsetti&gmtTime=19800&language=en-GB",
                "http://apis.headlinesapp.mobi//headlines/api/v1/user/activity/login?userid=100001343139228&access_token=CAAC3MgnChTgBAAWmaUJaypgZBYs7yD1q7VndRqM8zsAJRQZBPgLBxxwWNavuxfaDkQXsOA82rWgkIr0irfffxWOZBPRUpHC5mwmiZB4D3vRhJYBX5vGQ9YzxBpbSSVtZAtr1NR7N0FZC2ChwyC0sXjG5F7DjsiJqK2mZC3FA2Ff1gZDZD&userName=Anoop%20Chowdary&gmtTime=19800&language=en",
                "http://apis.headlinesapp.mobi//headlines/api/v1/user/activity/login?userid=100002899527407&access_token=CAAC3MgnChTgBAMyERIqGEWAHiOcSYggmoIEHNmZBYAaZChpL0Okmtc8XhIDvmDS8U40MR7Cd8lt85bDElj4MIBjeIYZA70pepA2ZAdSdjV1Cvt6wBULm3sf0vivTYwRXtCWXaZCBZCEH7GhViuOARWopxpFyZCEzZB2ZAm9FCwytKZCgZDZD&userName=Shrushti%20Kotadiya&gmtTime=19800&language=en-GB",
                "http://apis.headlinesapp.mobi//headlines/api/v1/user/activity/login?userid=1432076759&access_token=CAAC3MgnChTgBAC4hI0VA9TWm4Y6Ls4fqOm06LELMjKM1rFnSoq0phkQlXjZCZBXyUMXQjKzDr3pnbg2ZCP773NXjqggoHr2sHLfaZBgZCFPAZCAlZB67pYQEZBWlAcTYUvYIeRdCqbbeF1VTaNyunaBydSeB9TYY14EH9pUPSfkyogZDZD&userName=Rajanikant%20Ramchandra%20Bhagat&gmtTime=19800&language=en",
                "http://apis.headlinesapp.mobi//headlines/api/v1/user/activity/login?userid=1432076759&access_token=CAAC3MgnChTgBAC4hI0VA9TWm4Y6Ls4fqOm06LELMjKM1rFnSoq0phkQlXjZCZBXyUMXQjKzDr3pnbg2ZCP773NXjqggoHr2sHLfaZBgZCFPAZCAlZB67pYQEZBWlAcTYUvYIeRdCqbbeF1VTaNyunaBydSeB9TYY14EH9pUPSfkyogZDZD&userName=Rajanikant%20Ramchandra%20Bhagat&gmtTime=19800&language=en",
                "http://apis.headlinesapp.mobi//headlines/api/v1/user/activity/login?userid=100001144597405&access_token=CAAC3MgnChTgBAOYJl5aFuLv9KwpX8BME3JkLZAGmqw36sPMImLc7yTOOobxHzJLJzjkZAuSWZBHXyePqjrjw1EKv8ZAMdybksG8CyYCgYS4oXbCSFyUTGGqvzsGyo9xgdtf2azbILmmZBAMpQaUS5s6M427ZCHxhKN478ewSefeAZDZD&userName=Karandeep%20Singh&gmtTime=19800&language=en",
                "http://apis.headlinesapp.mobi//headlines/api/v1/user/activity/login?userid=100001174024086&access_token=CAAC3MgnChTgBAOuPg6fH9hZApNZCHY5eYC2iteXC3u2LZARDVlfkTszoPx96aMBSBnUc3JhQfygg70F8N1ifhzTESaDn00wFykFH2m6H7LmQiKGc0tunrtr6s6s0SZCHqD3Q4BcUu9g0IefXbrcpY2co0tUHCaxBhQwIJNPOaQZDZD&userName=Arun%20Bk&gmtTime=19800&language=en"};

        List<Future<Response>> futures = new LinkedList<>();
        for(String urlString:url){
           futures.add(httpRequestProcessor.submitRequest(httpRequestProcessor.createRequestBuilder(HttpMethod.HTTP_POST,urlString,qMap,qMap).build(),new AsyncCompletionHandlerBase()));
        }



        for(Future<Response> future: futures)  {
            try{
            System.out.println(future.get().getResponseBody());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }*/
}
