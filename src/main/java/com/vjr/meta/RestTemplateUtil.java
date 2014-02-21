package com.vjr.meta;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


public class RestTemplateUtil {
    
    private static final RestTemplate REST_TEMPLATE;
    private static final int DEFAULT_TIMEOUT = 10; //in seconds
    static {
        HttpComponentsClientHttpRequestFactory rf = new HttpComponentsClientHttpRequestFactory();
        rf.setReadTimeout(DEFAULT_TIMEOUT * 1000);
        rf.setConnectTimeout(DEFAULT_TIMEOUT * 1000);
        rf.setHttpClient(wrapClient(rf.getHttpClient()));
        REST_TEMPLATE = new RestTemplate(rf);
    }
    
    public static RestTemplate getInstance() {
        return REST_TEMPLATE;
    }
    
    @SuppressWarnings("deprecation")
    public static HttpClient wrapClient(HttpClient base) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
 
                public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }
 
                public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }
 
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            X509HostnameVerifier verifier = new X509HostnameVerifier() {
 
                @Override
                public void verify(String string, SSLSocket ssls) throws IOException {
                }
 
                @Override
                public void verify(String string, X509Certificate xc) throws SSLException {
                }
 
                @Override
                public void verify(String string, String[] strings, String[] strings1) throws SSLException {
                }
 
                @Override
                public boolean verify(String string, SSLSession ssls) {
                    return true;
                }

            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(verifier);
            ClientConnectionManager ccm = base.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", ssf, 443));
            return new DefaultHttpClient(ccm, base.getParams());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static HttpEntity<String> getHttpEntityWithUAHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", Constants.CHROME_USER_AGENT);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        return entity;
    }
}
