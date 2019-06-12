package org.spring.springboot.web;

import org.spring.springboot.domain.City;
import org.spring.springboot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestWebController {

  @Autowired
  private CityService cityService;

  @RequestMapping(method = RequestMethod.GET)
  public String getTestStr () {
    return "111";
  }
  

  @RequestMapping(value = "/api/city", method = RequestMethod.GET)
  public City findOneCity(@RequestParam(value = "cityName", required = true) String cityName) {
    return cityService.findCityByName(cityName);
  }
}