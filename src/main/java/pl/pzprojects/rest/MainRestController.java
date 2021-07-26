package pl.pzprojects.rest;

import org.springframework.boot.devtools.remote.client.HttpHeaderInterceptor;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;

import java.util.ArrayList;
import java.util.List;

public abstract class MainRestController {
    protected static List<ClientHttpRequestInterceptor> setEthermineHeaders(){
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HttpHeaderInterceptor("Accept", MediaType.APPLICATION_JSON_VALUE));
        interceptors.add(new HttpHeaderInterceptor("ContentType", MediaType.APPLICATION_JSON_VALUE));
        interceptors.add(new HttpHeaderInterceptor("User-Agent", "Application Ethermine Profit Counter"));
        return interceptors;
    }
}
