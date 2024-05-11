package cn.ningle.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ningle
 * @version : Book.java, v 0.1 2024/05/10 10:50 ningle
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    /**
     * 图书主要id
     */
    private String majorId;

    /**
     * 图书次要id 依据 面向z-library设计 暂时补招有什么含义
     */
    private String minorId;

    /**
     * 书籍标题
     */
    private String title;

    /**
     * 书籍发布者
     */
    private String publisher;

    /**
     * 编辑次数
     */
    private String edition;

    /**
     * 书籍介绍
     */
    private String intro;

    /**
     * 书籍分类
     */
    private String categories;

    /**
     * 书籍作者
     */
    private String author;

    /**
     * 书籍封面URL
     */
    private String coverUrl;

    /**
     * 书籍唯一的isbn号 可用于搜索
     */
    private String isbn;

    /**
     * 该书籍的主要语言
     */
    private String language;

    /**
     * 书籍出版年份
     */
    private String year;

    /**
     * 资源文件默认文件类型 与 大小  e.g pdf,3.56MB
     */
    private String defaultFileInfo;


    public Book(String majorId, String minorId, String title, String author, String coverUrl, String isbn, String language, String year, String defaultFileInfo) {
        this.majorId = majorId;
        this.minorId = minorId;
        this.title = title;
        this.author = author;
        this.coverUrl = coverUrl;
        this.isbn = isbn;
        this.language = language;
        this.year = year;
        this.defaultFileInfo = defaultFileInfo;
    }
}
