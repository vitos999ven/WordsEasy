package controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MainController {

	@RequestMapping("/")
	public ModelAndView home() {

		ModelAndView model = new ModelAndView("index");
		//model.addObject("msg", "hello world");

		return model;
	}
        
}