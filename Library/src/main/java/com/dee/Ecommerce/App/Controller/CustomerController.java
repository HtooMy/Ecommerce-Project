package com.dee.Ecommerce.App.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {

	@GetMapping("/customer-index")
	public String index(Model model) {
		return "./customer/templates/index";
	}
}
