package cn.ningle.library.service.impl;

import cn.ningle.library.config.NetProxyConfig;
import cn.ningle.library.entity.Book;
import cn.ningle.library.entity.PageResult;
import cn.ningle.library.service.AbstractNetworkLibrary;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ningle
 * @version : ZLibrary.java, v 0.1 2024/05/10 17:45 ningle
 **/
@Component
public class ZLibrary extends AbstractNetworkLibrary {

    private static final String SEARCH_LIST_URL = "https://singlelogin.re/s";

    private static final String SEARCH_INFO_URL = "https://singlelogin.re/book";

    public ZLibrary(NetProxyConfig netProxyConfig) {
        super(netProxyConfig, SEARCH_LIST_URL, SEARCH_INFO_URL);
    }

    public PageResult<Book> parseBookListFromHtml(String pageHtmlStr) {
        // jsoup 解析
        Document doc = Jsoup.parse(pageHtmlStr);
        // (328) 或 (1000+)
        String totalCountStr = doc.select(".totalCounter").first().text();

        Element listContainer = doc.getElementById("searchResultBox");

        Elements bookDivs = listContainer.select(".resItemBox.resItemBoxBooks.exactMatch");

        List<Book> bookList = bookDivs.stream()
                .map((element) -> {
                    Element zCover = element.select("z-cover").first();
                    String infoRef = zCover.select("a").first().attr("href");
                    String[] infoArr = infoRef.split("/");

                    String bookLanguage = element.select(".bookProperty.property_language").isEmpty() ? "未知" :
                            element.select(".bookProperty.property_language").first().
                                    select(".property_value").text();
                    String bookYear = element.select(".bookProperty.property_year").isEmpty() ? "未知" :
                            element.select(".bookProperty.property_language").first().
                                    select(".property_value").text();
                    return new Book(
                            infoArr[2],
                            infoArr[3],
                            zCover.attr("title"),
                            zCover.attr("author"),
                            element.select("img").first().attr("data-src"),
                            zCover.attr("isbn"),
                            bookLanguage,
                            bookYear,
                            element.select(".bookProperty.property__file").first()
                                    .select(".property_value").first().text()
                    );
                }).toList();

        // 封装分页结果
        int totalCount = handleTotalCount(totalCountStr);
        int pageCount = totalCount == 0 ? 0 : (totalCount / 50) + (totalCount % 50 == 0 ? 0 : 1);
        return new PageResult<>(-1, pageCount, totalCount, bookList);
    }

    /**
     * (328) => 328   (1000+) => 1000
     *
     * @param totalCount 查询到的总数
     * @return 转成int后的结果
     */
    private int handleTotalCount(String totalCount) {
        // 移除括号
        totalCount = totalCount.substring(1, totalCount.length() - 1);
        // 超过1000 需要移除 +
        if (totalCount.endsWith("+")) {
            return 1000;
        }
        return Integer.parseInt(totalCount);
    }

    public Book parseBookInfoFromHtml(String pageHtmlStr, String majorId, String minorId) {
        Document doc = Jsoup.parse(pageHtmlStr);

        return new Book(
                majorId, minorId,
                doc.select("z-cover").first().attr("title"),
                doc.select(".bookProperty.property_publisher").select(".property_value").text(),
                doc.select(".bookProperty.property_edition").select(".property_value").text(),
                doc.select("#bookDescriptionBox").text(),
                doc.select(".bookProperty.property_categories").select(".property_value").text(),
                doc.select("z-cover").attr("author"),
                doc.select("z-cover").first().select("img").attr("data-src"),
                doc.select(".bookProperty.property_isbn.13").select(".property_value").text(),
                doc.select(".bookProperty.property_language").select(".property_value").text(),
                doc.select(".bookProperty.property_year").select(".property_value").text(),
                doc.select(".bookProperty.property__file").select(".property_value").text()
        );

    }

}
