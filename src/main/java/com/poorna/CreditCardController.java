package com.poorna;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poorna.model.CreditCard;
import com.poorna.service.CreditCardService;

@Controller
public class CreditCardController {

	protected static Logger logger = Logger.getLogger("controller");
	
	@Autowired
	private CreditCardService creditCardService;
	
	@ModelAttribute("creditCardAttribute")
	public CreditCard getLoginForm() {
		return new CreditCard();

	}
    
    @RequestMapping(value = "/creditcard/add", method = RequestMethod.GET)
    public String getAdd(@RequestParam("id") Integer personId, Model model) {
    	logger.debug("Received request to show add page");
    
    	// Prepare model object
    	CreditCard creditCard = new CreditCard();
    	
    	// Add to model
    	model.addAttribute("personId", personId);
    	model.addAttribute("creditCardAttribute", creditCard);

    	// This will resolve to /WEB-INF/jsp/add-credit-card.jsp
    	return "add-credit-card";
	}
 
    /**
     * Adds a new credit card
     */
    @RequestMapping(value = "/creditcard/add", method = RequestMethod.POST)
    public String postAdd(@RequestParam("id") Integer personId, 
    						    @ModelAttribute("creditCardAttribute") CreditCard creditCard) {
		logger.debug("Received request to add new credit card");
		
		// Delegate to service
		creditCardService.add(personId, creditCard);

		// Redirect to url
		return "redirect:/list";
	}
    
    
    /**
     * Deletes a credit card
     */
    @RequestMapping(value = "/creditcard/delete", method = RequestMethod.GET)
    public String getDelete(@RequestParam("id") Integer creditCardId) {
    	logger.debug("Received request to delete credit card");
    
    	
    	// Delegate to service
		creditCardService.delete(creditCardId);

		// Redirect to url
		return "redirect:/list";
	}
   
    /**
     * Retrieves the "Edit Existing Credit Card" page
     */
    @RequestMapping(value = "/creditcard/edit", method = RequestMethod.GET)
    public String getEdit(@RequestParam("pid") Integer personId, 
    		@RequestParam("cid") Integer creditCardId, Model model) {
    	logger.debug("Received request to show edit page");
    	
    	// Retrieve credit card by id
    	CreditCard existingCreditCard = creditCardService.get(creditCardId);

    	// Add to model
    	model.addAttribute("personId", personId);
    	model.addAttribute("creditCardAttribute", existingCreditCard);

    	// This will resolve to /WEB-INF/jsp/edit-credit-card.jsp
    	return "edit-credit-card";
	}
 
    /**
     * Edits an existing credit card
     */
    @RequestMapping(value = "/creditcard/edit", method = RequestMethod.POST)
    public String postEdit(@RequestParam("id") Integer creditCardId, 
    						    @ModelAttribute("creditCardAttribute") CreditCard creditCard) {
		logger.debug("Received request to add new credit card");
		
		// Assign id
		creditCard.setId(creditCardId);
		
		// Delegate to service
		creditCardService.edit(creditCard);

		// Redirect to url
		return "redirect:/list";
	}
}
