package cn.ningle.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author ningle
 * @version : PageResult.java, v 0.1 2024/05/10 18:44 ningle
 * <p>
 * 分页查询结果
 **/
@Data
@AllArgsConstructor
public class PageResult<T> {

    private long pageNum;

    private long pageSize;

    private long total;

    private List<T> records;

}
