package com.bdtripp.hauntedhouse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Serves the web page of the Haunted House application.
 */
@Controller
public class PageController {

  /**
   * Creates a new PageController.
   */
  public PageController() {
  }

  /**
   * Handles requests to the home page.
   *
   * @return the name of the view to render
   */
  @GetMapping("/")
  public String home() {
    return "index";
  }
}