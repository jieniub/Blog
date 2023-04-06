package com.ljj.web.admin;

import com.ljj.pojo.Blog;
import com.ljj.pojo.User;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;


    @GetMapping("/manager")
    public String list(@PageableDefault(size=3,sort = {"updateDate"},direction = Sort.Direction.DESC)
                                   Pageable pageable,BlogQuery blog, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/manager";
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size=3,sort = {"updateDate"},direction = Sort.Direction.DESC)
                                     Pageable pageable,BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable, blog));
        return "admin/manager :: blogList";
    }

    @GetMapping("blogs/input")
    public String input(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("blog",new Blog());

        return "admin/input";
    }



    @GetMapping("blogs/{id}/input")
    public String update(@PathVariable Long id, Model model){
        model.addAttribute("types",typeService.listType());
        Blog blog = blogService.getBlog(id);
        model.addAttribute("blog",blog);

        return "admin/input";
    }

    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));

        if(blog.getViews() == null){
            blog.setViews(0);
        }
        Blog b = blogService.saveBlog(blog);
        if (b ==null){
            attributes.addFlashAttribute("message","新增失败");
        }else{
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/manager";
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/manager";
    }

}
