package com.ljj.web;

import com.ljj.service.BlogService;
import com.ljj.service.TypeService;
import com.ljj.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class indexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;


    @GetMapping("/index")
    public String index(@PageableDefault(size=6,sort = {"updateDate"},direction = Sort.Direction.DESC)
                                    Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.ListTypeTop(6));

        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(10));
        return "index";
    }
    @GetMapping("/")
    public String preindex(){
        return "preindex";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model) {
        model.addAttribute("blog",blogService.getBlog(id));
        return "blog";
    }

    @PostMapping("/search")
    public String Search(@PageableDefault(size=6,sort = {"updateDate"},direction = Sort.Direction.DESC)
                            @RequestParam String query,Pageable pageable, BlogQuery blog, Model model){
    model.addAttribute("page",blogService.listBlog(query,pageable));
    model.addAttribute("query",query);
    return "search";
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }

    @GetMapping("/footer/newblog")
    public String newBlogs(Model model){
        model.addAttribute("newBlogs",blogService.listRecommendBlogTop(3));
        return "_fragments :: newBlogList";
    }

}
