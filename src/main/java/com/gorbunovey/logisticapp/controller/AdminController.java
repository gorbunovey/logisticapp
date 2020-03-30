package com.gorbunovey.logisticapp.controller;

import com.gorbunovey.logisticapp.dto.UserDTO;
import com.gorbunovey.logisticapp.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/accounts")
    public String getAccounts(Model model) {
        model.addAttribute("accounts", userService.getUsers());
        return "admin/accounts";
    }

    @GetMapping(value = "/account/edit/{pathId}")
    public String getAccount(@PathVariable(value = "pathId") Long pathId, Model model) {
        model.addAttribute("account", userService.getUser(pathId));
        model.addAttribute("roles", userService.getRoles());
        return "admin/edit";
    }

    @PostMapping(value = "/account/edit/{pathId}")
    public String setAccount(@PathVariable(value = "pathId") Long pathId,
                             @ModelAttribute("account") @Valid UserDTO userDTO, BindingResult bindingResult,
                             final RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("statusMsg", "Failure. " +
                    "Account #" + pathId + " wasn't edit. Cause - invalid data");
            model.addAttribute("roles", userService.getRoles());
            return "admin/edit";
        }else{
            userService.updateUser(userDTO);
            redirectAttributes.addFlashAttribute("statusMsg", "Success. " +
                    "Account #" + userDTO.getId() + " was edit");
            return "redirect:/admin/accounts";
        }
    }

    @GetMapping(value = "/account/delete/{pathId}")
    public String deleteAccountConfirmation(@PathVariable(value = "pathId") Long pathId, Model model) {
        model.addAttribute("account", userService.getUser(pathId));
        return "admin/delete";
    }

    @PostMapping(value = "/account/delete/{pathId}")
    public String deleteAccount(
            @PathVariable(value = "pathId") Long pathId, final RedirectAttributes redirectAttributes) {
        boolean isDeleted = userService.deleteUser(pathId);
        if (isDeleted) {
            redirectAttributes.addFlashAttribute("statusMsg", "Success. Account #" + pathId + " was deleted");
        } else {
            redirectAttributes.addFlashAttribute("statusMsg", "Failure. Driver #" + pathId + " wasn't deleted");
        }
        return "redirect:/admin/accounts";
    }

}
