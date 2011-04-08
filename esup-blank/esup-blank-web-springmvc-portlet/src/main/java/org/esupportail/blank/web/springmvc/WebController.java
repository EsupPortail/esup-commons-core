package org.esupportail.blank.web.springmvc;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.esupportail.blank.domain.beans.User;
import org.esupportail.blank.services.auth.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

@Controller
public class WebController extends AbastractExceptionController {

	private static final String PREF_USERNAME = "usernamepref";
	
    private static final String DEFAULT_USERNAME = "JavaWorld";
	
    @Autowired
	private Authenticator authenticator;
    
    @RequestMapping("VIEW")
    protected ModelAndView renderView(RenderRequest request, RenderResponse response) throws Exception {
        
    	ModelMap model = new ModelMap();
    	
    	final PortletPreferences prefs = request.getPreferences();
    	String usernamePref = prefs.getValue(PREF_USERNAME, DEFAULT_USERNAME);
    	model.put("usernamePref", usernamePref);
    	
    	String remoteUser = request.getRemoteUser();
    	model.put("remoteUser", remoteUser);
    	
    	User userFromEC2 = authenticator.getUser();
    	model.put("userFromEC2", userFromEC2);

        return new ModelAndView("view", model);
    }

    @RequestMapping("ABOUT")
	public ModelAndView renderAboutView(RenderRequest request, RenderResponse response) throws Exception {
		ModelMap model = new ModelMap();
		return new ModelAndView("about", model);
	}
    

}
