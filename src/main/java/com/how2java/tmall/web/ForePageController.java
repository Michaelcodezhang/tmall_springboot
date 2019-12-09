package com.how2java.tmall.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ForePageController {
    @GetMapping(value = "/")
    public String index(){
        System.out.println();
        return "redirect:home";
    }

    @GetMapping(value = "home")
    public String home(){
        return "fore/home";
    }

    @GetMapping(value = "register")
    public String register(){
        return "fore/register";
    }

    @GetMapping(value = "registerSuccess")
    public String registerSuccess(){
        return "fore/registerSuccess";
    }

    @GetMapping(value = "login")
    public String login(){
        return "fore/login";
    }

    @GetMapping(value = "/forelogout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:home";
    }

    @GetMapping(value = "/product")
    public String product(){
        return "fore/product";
    }
}
