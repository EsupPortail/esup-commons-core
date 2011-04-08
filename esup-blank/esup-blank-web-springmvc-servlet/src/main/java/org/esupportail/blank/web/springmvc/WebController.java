package org.esupportail.blank.web.springmvc;

import org.esupportail.blank.domain.beans.User;
import org.esupportail.blank.services.auth.Authenticator;
import org.esupportail.commons.exceptions.EsupException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController extends AbastractExceptionController {

    @Autowired
	private Authenticator authenticator;
    
    @RequestMapping("/")
    public ModelAndView renderView() throws Exception {
    	User user = authenticator.getUser();
    	ModelMap model = new ModelMap("user", user);
        return new ModelAndView("view", model);
    }
    
    @RequestMapping("/about")
    public ModelAndView renderAbout() throws Exception {
        return new ModelAndView("about", null);
    }
    
    @RequestMapping("/exception")
    public ModelAndView renderException() throws Exception {
       throw new EsupException("exception.genere") {};
    }
    
}
