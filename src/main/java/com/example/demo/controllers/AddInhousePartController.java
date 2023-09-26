package com.example.demo.controllers;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.Part;
import com.example.demo.service.InhousePartService;
import com.example.demo.service.InhousePartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AddInhousePartController {
    @Autowired
    private ApplicationContext context;

    @GetMapping("/showFormAddInPart")
    public String showFormAddInhousePart(Model theModel) {
        InhousePart inhousepart = new InhousePart();
        theModel.addAttribute("inhousepart", inhousepart);
        return "InhousePartForm";
    }

    @PostMapping("/showFormAddInPart")
    public String submitForm(@Valid @ModelAttribute("inhousepart") InhousePart part, BindingResult bindingResult, Model theModel) {
        theModel.addAttribute("inhousepart", part);

        // Custom validation for inventory
        if (part.getInv() < 0) {
            bindingResult.rejectValue("inv", "error.inhousepart", "Inventory must be 0 or greater.");
        } else if (part.getInv() < part.getMin()) {
            bindingResult.rejectValue("inv", "error.inhousepart", "Alert - Inventory is less than the Inventory Minimum. \r\nValue is saved. Click Link to Main Screen to return.");
        } else if (part.getInv() > part.getMax()) {
            bindingResult.rejectValue("inv", "error.inhousepart", "Alert - Inventory is greater than the Inventory Maximum. \n\nValue is saved. Click Link to Main Screen to return.");
        }

        if (part.getMin() < 0) {
            bindingResult.rejectValue("min", "error.inhousepart", "Minimum Inventory must be 0 or greater.");
        }

        if (part.getMax() < 0) {
            bindingResult.rejectValue("max", "error.inhousepart", "Maximum Inventory must be 0 or greater.");
        }

        // Proceed with saving the part regardless of the inventory validation errors
        InhousePartService repo = context.getBean(InhousePartServiceImpl.class);
        InhousePart ip = repo.findById((int) part.getId());
        if (ip != null) part.setProducts(ip.getProducts());
        repo.save(part);

        // Check if there are any errors (including other validation errors)
        // If there are errors, return to the form view, otherwise, redirect to the confirmation view
        if (bindingResult.hasErrors()) {
            return "InhousePartForm"; // Return to the form view with error messages
        } else {
            return "confirmationaddpart"; // Redirect to the confirmation view
        }
    }
}