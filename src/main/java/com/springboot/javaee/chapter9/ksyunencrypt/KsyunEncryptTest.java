package com.springboot.javaee.chapter9.ksyunencrypt;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author: leiyulin
 * @description:
 * @date:2018/9/5上午11:07
 */
public class KsyunEncryptTest {
    public static void main(String[] args) throws Exception {
        String method = "POST";
        String service = "kir";
        String host = "kir.api.ksyun.com";
        String contenttype = "application/json";
        String region = "cn-beijing-6";
        String endpoint = "http://kir.api.ksyun.com";
        String request_parameters = "Action=ClassifyImage&Version=2017-11-07";


        String access_key = "ak from www.ksyun.com";
        String secret_key = "ak from www.ksyun.com";


        String postData = "{\n\t\"business\":[\"politic\",\"terrorism\"],\n   \"image_urls\":[\n        \"https://imgdb.ks3-cn-beijing.ksyun.com/11d78de0aaee5ecc070bb0a3c8011b67.jpg\",\n       \"https://ks3-cn-beijing.ksyun.com/imgdb/chenshuibian.jpeg\"\n    ]\n\n}";

        Date t = new Date();
        SimpleDateFormat timeFormater = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        timeFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        String amzdate = timeFormater.format(t);
//        String amzdate = "20171211T123249Z";



        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
        timeFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        String datestamp = dateFormater.format(t);
//        String datestamp = "20171211";

        String canonical_uri = "/";
        String canonical_querystring = request_parameters;
        String canonical_headers = "content-type:" + contenttype + "\n" + "host:" + host + "\n" + "x-amz-date:" + amzdate + "\n";
        String signed_headers = "content-type;host;x-amz-date";

        String payload_hash = toHex(hash(postData));
        System.out.println("payload_hash:" + payload_hash);

        String canonical_request = method + '\n' + canonical_uri + '\n' + canonical_querystring + '\n' + canonical_headers + '\n' + signed_headers + '\n' + payload_hash;

        System.out.println("canonical_request:" + canonical_request);

        String algorithm = "AWS4-HMAC-SHA256";
        String credential_scope = datestamp + '/' + region + '/' + service + '/' + "aws4_request";
        String string_to_sign = algorithm + '\n' +  amzdate + '\n' +  credential_scope + '\n' +  toHex(hash(canonical_request));
        System.out.println("string_to_sign:\t" + string_to_sign);
        byte[] signing_key = getSignatureKey(secret_key, datestamp, region, service);

        String signature = toHex(hmacSHA256(string_to_sign, signing_key));

        System.out.println("signature:\t" + signature);

        String authorization_header = algorithm + ' ' + "Credential=" + access_key + '/' + credential_scope + ", " +  "SignedHeaders=" + signed_headers + ", " + "Signature=" + signature;

        System.out.println("authorization_header:" + authorization_header);

        String request_url = endpoint + '?' + canonical_querystring;


        HttpURLConnection connection = (HttpURLConnection)new URL(request_url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("x-amz-date", amzdate);
        connection.setRequestProperty("Authorization", authorization_header);
        connection.setRequestProperty("Content-Type", contenttype);
        connection.setDoOutput(true);


        OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
        wr.write(postData);
        wr.flush();
        System.out.println(postData);

        try {
            int code = connection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

                String content = "";
                String line = null;
                while ((line = reader.readLine()) != null) {
                    content += line;
                }
                reader.close();
                System.out.println(content);
            } else {
                System.out.println(connection.getResponseMessage() + connection.getResponseCode());
            }
        } catch (Exception e) {

        }

    }
    public static String toHex(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    static byte[] hash(String text) throws Exception {
        if (text == null)
            text = "";
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes("UTF8"));
        return md.digest();
    }

    static byte[] hmacSHA256(String data, byte[] key) throws Exception {
        String algorithm="HmacSHA256";
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(data.getBytes("UTF8"));
    }

    static byte[] getSignatureKey(String key, String dateStamp, String regionName, String serviceName) throws Exception {
        byte[] kSecret = ("AWS4" + key).getBytes("UTF8");
        byte[] kDate = hmacSHA256(dateStamp, kSecret);
        byte[] kRegion = hmacSHA256(regionName, kDate);
        byte[] kService = hmacSHA256(serviceName, kRegion);
        byte[] kSigning = hmacSHA256("aws4_request", kService);
        return kSigning;
    }
}
