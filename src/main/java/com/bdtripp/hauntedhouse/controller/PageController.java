package com.bdtripp.hauntedhouse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Serves the main HTML page.
 *
 * The PageController handles requests to the root URL and returns the static index view, which
 * loads the client-side terminal interface.
 *
 * @author Brian Tripp
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