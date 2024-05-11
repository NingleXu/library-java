package cn.ningle.library.config;


import cn.ningle.library.common.NotLoggedInException;
import cn.ningle.library.common.R;
import cn.ningle.library.common.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;


/**
 * 全局异常处理器
 *
 * @author ruoyi
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public R handleServiceException(ServiceException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        Integer code = e.getCode();
        return code != null ? R.error(e.getMessage(), code) : R.error(e.getMessage());
    }


    /**
     * BindException异常处理
     * <p>BindException: 作用于@Validated @Valid 注解，仅对于表单提交有效，对于以json格式提交将会失效</p>
     *
     * @param e BindException异常信息
     * @return 响应数据
     */
    @ExceptionHandler(BindException.class)
    public R bindExceptionHandler(BindException e) {
        String msg = e.getBindingResult().getFieldErrors()
                .stream()
                .map(n -> String.format("%s: %s", n.getField(), n.getDefaultMessage()))
                .reduce((x, y) -> String.format("%s; %s", x, y))
                .orElse("参数输入有误");

        log.error("BindException异常，参数校验异常：{}", msg);
        return R.error(msg);
    }

    /**
     * MethodArgumentNotValidException-Spring封装的参数验证异常处理
     * <p>MethodArgumentNotValidException：作用于 @Validated @Valid 注解，接收参数加上@RequestBody注解（json格式）才会有这种异常。</p>
     *
     * @param e MethodArgumentNotValidException异常信息
     * @return 响应数据
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldErrors()
                .stream()
                .map(n -> String.format("%s: %s", n.getField(), n.getDefaultMessage()))
                .reduce((x, y) -> String.format("%s; %s", x, y))
                .orElse("参数输入有误");
        log.error("MethodArgumentNotValidException异常，参数校验异常：{}", msg);
        return R.error(msg);
    }

    /**
     * ConstraintViolationException-jsr规范中的验证异常，嵌套检验问题
     * <p>ConstraintViolationException：作用于 @NotBlank @NotNull @NotEmpty 注解，校验单个String、Integer、Collection等参数异常处理。</p>
     * <p>注：Controller类上必须添加@Validated注解，否则接口单个参数校验无效</p>
     *
     * @param e ConstraintViolationException异常信息
     * @return 响应数据
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public R constraintViolationExceptionHandler(ConstraintViolationException e) {
        String msg = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; "));

        log.error("ConstraintViolationException，参数校验异常：{}", msg);
        return R.error(msg);
    }

    /**
     * 非法的参数校验
     *
     * @param e 异常信息
     * @return 业务响应数据
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public R IllegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.error("IllegalArgumentException，非法的参数：{}", e.getMessage(), e);
        return R.error(e.getMessage());
    }

    @ExceptionHandler(value = NotLoggedInException.class)
    public R NotLoggedInExceptionHandler(NotLoggedInException e) {
        log.error("NotLoggedInException，用户未登录：{}", e.getMessage(), e);
        return R.error(e.getMessage(), SC_UNAUTHORIZED);
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public R handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        return R.error("请求失败！", 500);
    }
}
