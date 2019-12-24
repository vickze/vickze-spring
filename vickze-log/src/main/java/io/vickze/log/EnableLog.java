package io.vickze.log;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author vick.zeng
 * date-time: 2018/11/16 17:42
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({LogAutoConfiguration.class})
public @interface EnableLog {
}
