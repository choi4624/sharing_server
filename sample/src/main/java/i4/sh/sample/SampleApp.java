package i4.sh.sample;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;

@Controller
public class SampleApp{

	@GetMapping("/test")
    String index(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
    	Map<String, String> fruitmap = new HashMap<String, String>();
    	fruitmap.put("fruit1", "apple");
    	fruitmap.put("fruit2", "banana");
    	fruitmap.put("fruit3", "orange");
        model.addAttribute("fruit", fruitmap);
        return "testapp";
	}

	@GetMapping("/dbshow")
	String dbshow(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {

		sql = select * from item;
        return json;
	}

}

@RestController

