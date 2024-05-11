package cn.ningle.library.entity;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author ningle
 * @version : QueryCondition.java, v 0.1 2024/05/10 17:31 ningle
 * <p>
 * 查询条件对象
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryCondition {

    /**
     * 查询关键字
     */
    @NotBlank
    private String keyword;

    /**
     * 查询第几页
     */
    @Min(0)
    @Max(20)
    private int page;

}
