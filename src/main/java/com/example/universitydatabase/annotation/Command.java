package com.example.universitydatabase.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Indicates command with certain path, requestMethod.
 *
 * Annotation expected to be used at Controller class to specify commands
 */
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
}
