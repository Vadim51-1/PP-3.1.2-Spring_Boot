package com.example.demo.controller;

import com.example.demo.models.User;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("people")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }


    @GetMapping
    public String getFormPrintAllUsers(Model model) {
        model.addAttribute("people", service.getAllUsers());
        return "allUsers";
    }

    @GetMapping("/{id}")
    public String showUserData(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", service.showUser(id));
        return "showUser";
    }

    @GetMapping("/new")
    public String getFormToCreateUser(@ModelAttribute("person") User user) {

        return "newUser";
    }

    @PostMapping()
    public String saveUserDatabase(@ModelAttribute("person") @Valid User user,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "newUser";

        service.saveUser(user);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String getFormEditUser(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", service.showUser(id));
        return "editUser";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("person") @Valid User user, BindingResult bindingResult,
                             @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "editUser";

        service.updateUser(id, user);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        service.deleteUser(id);
        return "redirect:/people";
    }
}