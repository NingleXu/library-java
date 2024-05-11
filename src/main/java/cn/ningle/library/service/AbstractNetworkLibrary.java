package cn.ningle.library.service;

import cn.ningle.library.config.NetProxyConfig;
import cn.ningle.library.entity.Book;
import cn.ningle.library.entity.PageResult;
import cn.ningle.library.entity.QueryCondition;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author ningle
 * @version : NetworkLibrary.java, v 0.1 2024/05/10 17:30 ningle
 * <p>
 * 网络数据库抽象类
 **/
@Slf4j
public abstract class AbstractNetworkLibrary {

    /**
     * 查询图书列表的URL
     */
    private final String searchListURL;

    /**
     * 查询图书详情的URL
     */
    private final String searchInfoURL;

    /**
     * 网络代理配置类
     */
    protected NetProxyConfig netProxyConfig;


    protected AbstractNetworkLibrary(NetProxyConfig netProxyConfig, String searchListURL, String searchInfoURL) {
        this.netProxyConfig = netProxyConfig;
        this.searchListURL = searchListURL;
        this.searchInfoURL = searchInfoURL;
    }


    /**
     * 通过 基本条件分页查询书籍列表
     *
     * @param condition 查询条件
     * @return 书籍简介列表
     */
    public PageResult<Book> getBookList(QueryCondition condition) {

        // 获取请求url
        String requestURL = getSearchListURL(condition);
        PageResult<Book> bookPageResult = parseBookListFromHtml(doRequest(requestURL));
        bookPageResult.setPageNum(condition.getPage());
        return bookPageResult;
    }

    /**
     * 依据主要id 和 次要id 查询数据详情
     *
     * @param majorId 主要id
     * @param minorId 次要id
     * @return 书籍详情
     */
    public Book getBookInfo(String majorId, String minorId) {

        String requestURL = getSearchInfoURL(majorId, minorId);

        return parseBookInfoFromHtml(doRequest(requestURL), majorId, minorId);
    }


    String doRequest(String requestURL) {
        Long startTime = System.currentTimeMillis();
        // 获取网络代理config
        RequestConfig config = netProxyConfig.getNetProxyConfig();

        HttpGet httpGet = new HttpGet(requestURL);
        httpGet.setConfig(config);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                try (InputStream inputStream = response.getEntity().getContent()) {
                    byte[] bytes = inputStream.readAllBytes();
                    // 执行解析
                    Long endTime = System.currentTimeMillis();
                    log.info("请求路径【{}】耗时:{}ms", requestURL, endTime - startTime);
                    return new String(bytes);
                }
            }
        } catch (IOException e) {
            log.error("IO异常:{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String getSearchListURL(QueryCondition condition) {
        return searchListURL + "/" + URLEncoder.encode(condition.getKeyword(), StandardCharsets.UTF_8) + "?page=" + Math.min(20, condition.getPage());
    }

    public String getSearchInfoURL(String majorId, String minorId) {
        return searchInfoURL + "/" + majorId + "/" + minorId;
    }

    protected abstract PageResult<Book> parseBookListFromHtml(String pageHtmlStr);

    protected abstract Book parseBookInfoFromHtml(String pageHtmlStr, String majorId, String minorId);


}
