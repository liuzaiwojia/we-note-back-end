package org.spring.springboot.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestWebController {

  @RequestMapping(method = RequestMethod.GET)
  public String getTestStr () {
    return "111";
  }
}