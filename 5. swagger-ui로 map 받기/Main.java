import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.net.ssl.HttpsURLConnection;

public class Main {
    
    private final static String requestUrlString = "https://infuser.odcloud.kr/api/stages/26783/api-docs?1629699964954";

    public static void main(String[] args) throws Exception {

        JSONObject jsonObject = sendGet(requestUrlString);
        Map<String, Object> resultMap = null;

        if (jsonObject != null) {
            resultMap = makeMapFromJson(jsonObject);
            resultMap.put("status", true);
        } else {
            resultMap.put("status", false);
        }
    }

    /* HTTP GET 통신 후 json return */
    private static JSONObject sendGet(String requestUrl) throws Exception {
        URL url = new URL(requestUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET"); // optional default is GET
        con.setRequestProperty("User-Agent", "Mozilla/5.0"); // add request header

        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        if (responseCode == 200) {
            return (JSONObject) (new JSONParser()).parse(response.toString());
        } else {
            return null;
        }

    }

    /* swagger ui로 받은 json을 map 형태로 변경 */
    private static Map<String, Object> makeMapFromJson(JSONObject jsonObject) {

        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> operationMapList = new HashMap<>();

        // 기본 정보
        JSONObject infoJsonObject = (JSONObject) jsonObject.get("info");
        resultMap.put("publicDataSj", infoJsonObject.get("title").toString());
        resultMap.put("publicDataDc", infoJsonObject.get("description").toString());

        JSONObject pathsJsonObject = (JSONObject) jsonObject.get("paths");

        JSONObject definitionsObject = (JSONObject) jsonObject.get("definitions");

        Iterator iterator = pathsJsonObject.keySet().iterator();

        List<String> pathsKeyList = new ArrayList<>();

        while (iterator.hasNext()) {
            pathsKeyList.add(String.valueOf(iterator.next()));
        }

        for (String s : pathsKeyList) {

            Map<String, Object> oprationMap = new HashMap<>();


            JSONObject infoObject = (JSONObject) ((JSONObject) pathsJsonObject.get(s)).get("get");


            String operaDc = infoObject.get("description").toString();

            JSONArray requestParametersArray = (JSONArray) infoObject.get("parameters");
            Map<String, String> requestParameterHashMap = new HashMap<>();

            for (Object o : requestParametersArray) {
                String name = ((JSONObject) o).get("name").toString();
                String desc = ((JSONObject) o).get("description").toString();
                requestParameterHashMap.put(name, desc);
            }

            String operationId = infoObject.get("operationId").toString().split("GET")[1];

            Iterator it;
            Map<String, String> responseNecessaryParameterHashMap = new HashMap<>();

            // 필수 파라미터
            List<String> necessaryResponseParameterList = new ArrayList<>();
            JSONObject apiObject = (JSONObject) ((JSONObject) definitionsObject.get(operationId + "_api")).get("properties");
            it = apiObject.keySet().iterator();
            while (it.hasNext()) {
                necessaryResponseParameterList.add(String.valueOf(it.next()));
            }

            // 필수 공통 response parameter
            responseNecessaryParameterHashMap.put("page", "페이지");
            responseNecessaryParameterHashMap.put("perPage", "펄페이");
            responseNecessaryParameterHashMap.put("totalCount", "토탈카운트");
            responseNecessaryParameterHashMap.put("currentCount", "커런트카운트");

            // response data[] 안에 있는 명세
            List<String> responseParameterDataList = new ArrayList<>();
            // response operation
            JSONObject modelObject = (JSONObject) ((JSONObject) definitionsObject.get(operationId + "_model")).get("properties");

            it = modelObject.keySet().iterator();
            while (it.hasNext()) {
                responseParameterDataList.add(String.valueOf(it.next()));
            }

            Map<String, String> responseParameterHashMap = new HashMap<>();

            for (String value : responseParameterDataList) {
                responseParameterHashMap.put(value, ((JSONObject) modelObject.get(value)).get("description").toString());
            }

            oprationMap.put("필수응답파라미터", responseNecessaryParameterHashMap);
            oprationMap.put("응답파라미터", responseParameterHashMap);
            oprationMap.put("요청파라미터", requestParameterHashMap);

            operationMapList.put(s, oprationMap);
        }
        resultMap.put("오퍼레이션", operationMapList);

        return resultMap;
    }

    private static void sendPost(String targetUrl, String parameters) throws Exception {
        URL url = new URL(targetUrl);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("POST"); // HTTP POST 메소드 설정
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(parameters);
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println("HTTP 응답 코드 : " + responseCode);
        System.out.println("HTTP body : " + response.toString());
    }

}

