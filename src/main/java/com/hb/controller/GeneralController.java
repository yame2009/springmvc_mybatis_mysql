package com.hb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

//@RequestMapping("/genController")
@Controller
public class GeneralController {

	/**
	 * http://localhost:8089/smm/index2.html
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index2.html")
	public String index_jsp(Model model) {
		model.addAttribute("liming", "黎明你好");
		System.out.println("index2.jsp");
		return "index2";
	}
}
