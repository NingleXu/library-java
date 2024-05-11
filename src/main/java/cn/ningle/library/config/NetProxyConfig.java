package cn.ningle.library.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author ningle
 * @version : NetProxyConfig.java, v 0.1 2024/05/10 18:00 ningle
 **/

@Data
@Configuration
@ConfigurationProperties(prefix = "network.proxy")
@PropertySource(value = "classpath:config/network-proxy.properties")
public class NetProxyConfig {


    // 网络代理ip
    private String ip;

    // 网络代理端口
    private int port;


    public RequestConfig getNetProxyConfig() {
        return RequestConfig.custom()
                .setProxy(new HttpHost(ip, port))
                .build();
    }

}


