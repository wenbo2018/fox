package rpc.fox.web.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by shenwenbo on 2016/9/26.
 */
@Controller
@RequestMapping("/common")
public class CommonController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {

        return "index";
    }

    @RequestMapping(value = {" ", "/"})
    public String index() {
        return "index";
    }


}
