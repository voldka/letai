package com.example.letai.controller.mvccontroller.admin;


import com.example.letai.config.authenticate.config.user.UserService;
import com.example.letai.model.dto.PostDTO;
import com.example.letai.services.PostService;
import com.example.letai.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @GetMapping("/admin/posts")
    public String adminPosts() {
        return "admin/posts";
    }

    @GetMapping("/admin/401")
    public String admin401() {
        return "admin/401";
    }

    @GetMapping("/admin/404")
    public String admin404() {
        return "admin/404";
    }

    @GetMapping("/admin/500")
    public String admin500() {
        return "admin/500";
    }

    @GetMapping("/admin/charts")
    public String adminCharts() {
        return "admin/charts";
    }

    @GetMapping("/admin/layout-light")
    public String adminLayoutLight() {
        return "admin/layout-sidenav-light";
    }

    @GetMapping("/admin/layout-static")
    public String adminLayoutStatic() {
        return "admin/layout-static";
    }

    @GetMapping("/admin/login")
    public String adminLogin() {
        return "admin/login";
    }
    @GetMapping("/admin/password")
    public String adminPassword() {
        return "admin/password";
    }




    @RequestMapping(value = "/admin/addPost", method = RequestMethod.POST)
    public RedirectView adminPostsAdd(PostDTO postDTO,
                                      @RequestParam("image") MultipartFile multipartFile) throws IOException {
        //lấy tên của file và lưu tên vào database
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        postDTO.setImg(fileName);

        PostDTO savedUser = postService.save(postDTO);
        //lưu ảnh thực
        String uploadDir = "src/main/resources/static/admin-posts/" + savedUser.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return new RedirectView("/admin/posts", true);
    }

}
