package embedded.tomcat.spring.http;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewController {
    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE) 
    public String test() {
        return "{'name':'Mehrab'}";
    }
}
