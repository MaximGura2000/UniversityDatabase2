package com.example.universitydatabase.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping
@ResponseBody
public @interface Command {

  @AliasFor(annotation = RequestMapping.class)
  String[] path() default {};

  @AliasFor(annotation = RequestMapping.class)
  RequestMethod[] method() default {RequestMethod.POST};

  boolean primaryMapping() default false;

  @AliasFor(annotation = RequestMapping.class)
  String[] headers() default {};

  @AliasFor(annotation = RequestMapping.class)
  String[] consumes() default {MediaType.ALL_VALUE };

  @AliasFor(annotation = RequestMapping.class)
  String[] produces() default {};

  @AliasFor(annotation = RequestMapping.class)
  String[] params() default {};

  String spp() default "";
}
