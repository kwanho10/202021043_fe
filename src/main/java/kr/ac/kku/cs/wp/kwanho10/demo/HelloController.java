package kr.ac.kku.cs.wp.kwanho10.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	
	@RequestMapping("/hello")
	public ModelAndView hello(@RequestParam(name="name", required = true, defaultValue = "홍길동") String name) {	
		System.out.println("hello ~~~~~");
		
		logger.info(" 안녕~~~~~~!!!!!!!!!!!!!!!!!!!!!!!!!!!!");		
		final String formattedGreeting = "hello %s";
		String greeting =formattedGreeting.formatted(name);
		
		ModelAndView mav=new ModelAndView();
		mav.addObject("hello", greeting);
		mav.setViewName("index");		
		return mav;
	}
	
	
	
}
