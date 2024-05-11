package cn.ningle.library.controller;

import cn.ningle.library.common.R;
import cn.ningle.library.entity.Book;
import cn.ningle.library.entity.PageResult;
import cn.ningle.library.entity.QueryCondition;
import cn.ningle.library.service.AbstractNetworkLibrary;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.beans.BeanInfo;

/**
 * @author ningle
 * @version : BookController.java, v 0.1 2024/05/10 19:07 ningle
 **/
@RestController
@RequestMapping("/book")
public class BookController {

    @Resource
    private AbstractNetworkLibrary networkLibrary;


    @GetMapping("/search")
    public R<PageResult<Book>> searchBookList(@RequestParam(value = "keyword") String keyword,
                                              @RequestParam(value = "page") int page) {
        PageResult<Book> bookPageResult = networkLibrary.getBookList(new QueryCondition(keyword, page));
        return R.success(bookPageResult);
    }

    @GetMapping("/{majorId}/{minorId}")
    public R<Book> getBookInfo(@PathVariable("majorId") String majorId, @PathVariable("minorId") String minorId) {
        Book bookInfo = networkLibrary.getBookInfo(majorId, minorId);

        return R.success(bookInfo);
    }


}
