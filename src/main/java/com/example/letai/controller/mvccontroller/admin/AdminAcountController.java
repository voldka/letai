package com.example.letai.controller.mvccontroller.admin;

import com.example.letai.config.authenticate.config.user.UserService;
import com.example.letai.exception.exceptionhandler.UserNotFoundException;
import com.example.letai.model.dto.UserDTO;
import com.example.letai.model.entity.enums.AppUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class AdminAcountController {

    @Autowired
    private UserService userService;


    @GetMapping("/admin/users")
    public String showUserList(Model model) {
        List<UserDTO> list = userService.listAll();
        model.addAttribute("listUsers", list);
        return "admin/users";
    }

    @GetMapping("/admin/users/new")
    public String showNewForm(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("pageTitle", "Add New User");
        return "addNewProduct";
    }

    @PostMapping("/admin/users/save")
    public String saveUser(UserDTO user, RedirectAttributes ra) {
        userService.save(user);
        ra.addFlashAttribute("message", "The user has been saved successfully.");
        return "redirect:/admin/users";
    }

    //chua co trang edit
    @GetMapping("/admin/users/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
        try {
            UserDTO user = userService.get(id);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");
            return "redirect:/admin/users";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/admin/users";
        }
    }

    @GetMapping("/admin/users/delete/{id}")
    public RedirectView deleteUser(@PathVariable("id") Long id, RedirectAttributes ra) {
        try {
            userService.delete(id);
            ra.addFlashAttribute("message", "The user ID " + id + " has been deleted.");
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return new RedirectView("/admin/users", true);
    }

    @GetMapping("/admin/register")
    public String adminRegister(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("userDTO", new UserDTO());
        return "admin/register";
    }

    @PostMapping("/admin/register")
    public String registryNewAdmin(UserDTO userDTO,
                                   Model model) {
        try {
            String email = userDTO.getEmail();
            UserDTO admin = userService.findByEmail(email);
            UserDTO checkEmail = userService.findByEmail(userDTO.getEmail());
            if (admin != null) {
                model.addAttribute("adminDto", userDTO);
                System.out.println("admin not null");
                model.addAttribute("usernameError", "Your userName has been registered!");
                return "admin/register";
            }
            if (checkEmail!=null){
                model.addAttribute("adminDto", userDTO);
                System.out.println("admin not null");
                model.addAttribute("emailError", "Your email has been registered!");
                return "admin/register";
            }
            userDTO.setRole(AppUserRole.ROLE_ADMIN.toString());
            userService.save(userDTO);
            System.out.println("success");
            model.addAttribute("success", "Register successfully!");
            model.addAttribute("adminDto", userDTO);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errors", "The server has been wrong!");
        }
        return "admin/register";
    }
    @PostMapping("/do-login")
    public String doLogin(@RequestParam("username") String username, @RequestParam("password") String password) {

        return "redirect:/admin/home";
    }

}
