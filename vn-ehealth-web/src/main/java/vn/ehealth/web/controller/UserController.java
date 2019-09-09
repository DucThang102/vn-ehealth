package vn.ehealth.web.controller;

import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import vn.ehealth.web.util.PageUtil;

@Controller
public class UserController {   
    
    @GetMapping("/")
    public String index(@RequestParam(required=false) String htmlURL, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        var roles = auth.getAuthorities().stream()
                        .map(x -> x.getAuthority())
                        .filter(x -> x.startsWith("ROLE_"))
                        .map(x -> x.substring(5))
                        .collect(Collectors.toSet());
                        
        var pages = PageUtil.getPages(roles);        
        model.addAttribute("pages", pages);
        model.addAttribute("current_page", PageUtil.defaultPage);
        model.addAttribute("mode", "normal");

        if(htmlURL == null || htmlURL.isEmpty()) {
            htmlURL = PageUtil.getHtmlURL(pages, PageUtil.defaultPage);
        }
        
        model.addAttribute("html_url", htmlURL);
        
        return "index";
    }
    
    @GetMapping("/-/{page}/{mode}")
    public String pageServe(@PathVariable String page, @PathVariable String mode,                                                     
                            @RequestParam(required=false) String htmlURL,
                            Model model) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        var roles = auth.getAuthorities().stream()
                .map(x -> x.getAuthority())
                .filter(x -> x.startsWith("ROLE_"))
                .map(x -> x.substring(5))
                .collect(Collectors.toSet());
        
        var pages = PageUtil.getPages(roles);
        model.addAttribute("pages", pages);
        model.addAttribute("current_page", page);
        model.addAttribute("mode", mode);
        
        if(htmlURL == null || htmlURL.isEmpty()) {
            htmlURL = PageUtil.getHtmlURL(pages, page);
        }
        
        model.addAttribute("html_url", htmlURL);
        return "index";    
    }
    
    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Wrong.username.password");

        if (logout != null)
            model.addAttribute("message", "Logout.success");

        return "login";
    }
}
