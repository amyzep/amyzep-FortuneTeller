package com.codingdojo.FortuneTeller.controllers;

import java.util.ArrayList;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.codingdojo.FortuneTeller.models.Fortune;
import com.codingdojo.FortuneTeller.models.GenerateFortunes;

@Controller
public class FortuneTellerController {
	Random random = new Random();
	@RequestMapping("/")
	public String index() {
		return "index.jsp";
	}

	@RequestMapping(value = "/fortuneteller", method = RequestMethod.GET)
	 public String fortune(Model model) {

		ArrayList<Fortune> fortunes = new ArrayList<Fortune>();
	    int number = random.nextInt(10);
        GenerateFortunes newFortune = new GenerateFortunes(number);
	    fortunes.add(new Fortune(newFortune.getFortune(), newFortune.getImage()));
		model.addAttribute("fortunes", fortunes);
		return "fortuneteller.jsp";
	}
	
	@GetMapping("/personal_fortune")
	public String form() {
		return "form.jsp";
	}
	
	@GetMapping("/personal_fortune/show")
	public String show(HttpSession session, Model model) {
		
		String result = (String) session.getAttribute("resultFortune");
		model.addAttribute("result", result);
		
		return "show.jsp";
	}
	
	@PostMapping("/processFormData")
	public String process(
		@RequestParam("number") int number,
		@RequestParam("city") String city,
		@RequestParam("person") String person,
		@RequestParam("hobby") String hobby,
		@RequestParam("thing") String thing,
		@RequestParam("message") String message,
		HttpSession session
		) {
		String resultFortune = String.format(
				"In %s years, you will live in %s with %s as your roommate. You will be making a living from %s. The next time you see a %s, you will have good luck. Also, %s.", 
				number, city, person, hobby, thing, message);
		
		session.setAttribute("resultFortune", resultFortune);
		return "redirect:/personal_fortune/show";
	}
}