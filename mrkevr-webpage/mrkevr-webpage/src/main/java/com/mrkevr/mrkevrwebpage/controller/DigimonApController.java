package com.mrkevr.mrkevrwebpage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DigimonApController {

	@GetMapping("/digimon")
	public String digimon() {
		
		return "digimon";
	}

}
