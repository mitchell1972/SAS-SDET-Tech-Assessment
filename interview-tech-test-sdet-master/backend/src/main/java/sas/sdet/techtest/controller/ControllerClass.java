package sas.sdet.techtest.controller;


import sas.sdet.techtest.domain.Order;
import sas.sdet.techtest.domain.User;
import sas.sdet.techtest.repository.RepositoryClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ControllerClass {
	
	@Autowired
	RepositoryClass repositoryClass;
    
	@RequestMapping(path="/user/{name}")
	@ResponseBody
	public User getUserDexterity(@PathVariable String name){
		return repositoryClass.loadUser(name);
	}	
	
	@RequestMapping(path="/order", method=RequestMethod.POST)
	@ResponseBody
	public String addUser(@RequestParam String user, String item) throws Exception {
		try {
			Order order = repositoryClass.order(user, item);
			if(order == null) {
				throw new Exception();
			}
			return "OK";
		} catch (Exception Ko) {
			return "KO";
		}
	}

}