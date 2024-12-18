package com.elbakian.webapp.crud_webapp_sprongboot.controllers;


import com.elbakian.webapp.crud_webapp_sprongboot.dao.UserDaoImpl;
import com.elbakian.webapp.crud_webapp_sprongboot.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserDaoImpl userDaoImpl;

    @Autowired
    public AdminController(UserDaoImpl userDaoImpl) {
        this.userDaoImpl = userDaoImpl;
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userDaoImpl.showUsers());
        return "users";
    }

    @GetMapping("/users/{id}")
    public String showUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userDaoImpl.getUserById(id));
        return "show";
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "newUser";
    }

    @PostMapping("/users")
    public String create(@ModelAttribute("user") User user) {
        userDaoImpl.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/{id}/edit")
    public String editUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userDaoImpl.getUserById(id));
        return "editUser";
    }

    @PatchMapping("/users/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userDaoImpl.updateUser(user, id);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable("id") int id) {
        userDaoImpl.deleteUser(id);
        return "redirect:/admin/users";
    }
}
