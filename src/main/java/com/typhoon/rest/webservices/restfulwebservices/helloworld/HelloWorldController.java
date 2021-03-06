package com.typhoon.rest.webservices.restfulwebservices.helloworld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    // Auto refer to ResourceBundleMessageSource
    @Autowired
    private MessageSource messageSource;

    // @RequestMapping(method = RequestMethod.GET, path = "/hello-world")
    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    // This function will generate a json file (not text as the previous function)
    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World");
    }
    
    @GetMapping(path = "/hello-world/path-variable/{name}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello world, %s", name));
    }

    // @GetMapping("/hello-world-internationalized")
    // public String helloWorldInternationalized(
    //         @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
    //     // point to the line ¨good.morning.message" in all reference files
    //     return messageSource.getMessage("good.morning.message", null, locale);
    // }

    @GetMapping("/hello-world-internationalized")
    public String helloWorldInternationalized() {
        // point to the line ¨good.morning.message" in all reference files
        return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
    }

}