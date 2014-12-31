package com.hb.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.hb.models.AccountModel;
import com.hb.models.TagsModel;

/**
 * 表单标签模型
 * 
 * SpringMVC学习系列（11） 之 表单标签 http://www.cnblogs.com/liukemng/p/3754211.html
 *
 */
@Controller
@RequestMapping(value = "/tags")
public class TagsController {

	/**
	 * localhost:8089/smm/tags/test.html
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/test", method = { RequestMethod.GET })
	public String test(Model model) {

		if (!model.containsAttribute("contentModel")) {

			TagsModel tagsModel = new TagsModel();

			tagsModel.setUsername("aaa");
			tagsModel.setPassword("bbb");
			tagsModel.setTestBoolean(true);
			tagsModel.setSelectArray(new String[] { "arrayItem 路人甲" });
			tagsModel.setTestArray(new String[] { "arrayItem 路人甲",
					"arrayItem 路人乙", "arrayItem 路人丙" });
			tagsModel.setRadiobuttonId(1);
			tagsModel.setSelectId(2);
			tagsModel.setSelectIds(Arrays.asList(1, 2));
			Map<Integer, String> map = new HashMap<Integer, String>();
			map.put(1, "mapItem 路人甲");
			map.put(2, "mapItem 路人乙");
			map.put(3, "mapItem 路人丙");
			tagsModel.setTestMap(map);
			tagsModel.setRemark("备注...");

			model.addAttribute("contentModel", tagsModel);
		}
		return "test/tagstest";
	}

	@RequestMapping(value = "/test", method = { RequestMethod.POST })
	public String modelAutoBind(HttpServletRequest request, Model model,
			AccountModel accountModel) {

		model.addAttribute("accountmodel", accountModel);
		model.addAttribute("author", "hb");
		
		return "test/tagstestResult";
	}

//	@RequestMapping(value = "/test", method = { RequestMethod.POST })
//	public String modelAutoBind(HttpServletRequest request,
//			@ModelAttribute("contentModel") AccountModel contentModel) {
//
//		System.out.println("contentModel " + new Gson().toJson(contentModel));
//
//		return "test/tagstestResult";
//	}

}