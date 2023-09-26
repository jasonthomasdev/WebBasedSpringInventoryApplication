package com.example.demo.controllers;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Part;
import com.example.demo.service.OutsourcedPartService;
import com.example.demo.service.OutsourcedPartServiceImpl;
import com.example.demo.service.PartService;
import com.example.demo.service.PartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 *
 *
 *
 *
 */
@Controller
public class AddOutsourcedPartController {
    @Autowired
    private ApplicationContext context;

    @GetMapping("/showFormAddOutPart")
    public String showFormAddOutsourcedPart(Model theModel) {
        Part part = new OutsourcedPart();
        theModel.addAttribute("outsourcedpart", part);
        return "OutsourcedPartForm";
    }

    @PostMapping("/showFormAddOutPart")
    public String submitForm(@Valid @ModelAttribute("outsourcedpart") OutsourcedPart part, BindingResult bindingResult, Model theModel) {
        theModel.addAttribute("outsourcedpart", part);

        // Custom validation for inventory
        if (part.getInv() < 0) {
            // bindingResult.rejectValue("inv", "error.outsourcedpart", "Inventory must be 0 or greater.");
        } else if (part.getInv() < part.getMin()) {
            bindingResult.rejectValue("inv", "error.outsourcedpart", "Alert - Inventory is less than the Inventory Minimum. Value is saved. Click Link to Main Screen to return.");
        } else if (part.getInv() > part.getMax()) {
            bindingResult.rejectValue("inv", "error.outsourcedpart", "Alert - Inventory is greater than the Inventory Maximum. Value is saved. Click Link to Main Screen to return.");
        }

        if (part.getMin() < 0) {
            bindingResult.rejectValue("min", "error.outsourcedpart", "Minimum Inventory must be 0 or greater.");
        }

        if (part.getMax() < 0) {
            bindingResult.rejectValue("max", "error.outsourcedpart", "Maximum Inventory must be 0 or greater.");
        }

        // Proceed with saving the part only if there are no errors
        if (!bindingResult.hasErrors()) {
            OutsourcedPartService repo = context.getBean(OutsourcedPartServiceImpl.class);
            OutsourcedPart op = repo.findById((int) part.getId());
            if (op != null) part.setProducts(op.getProducts());
            repo.save(part);
        }

        // Check if there are any errors (including other validation errors)
        // If there are errors, return to the form view, otherwise, redirect to the confirmation view
        if (bindingResult.hasErrors()) {
            return "OutsourcedPartForm"; // Return to the form view with error messages
        } else {
            return "confirmationaddpart"; // Redirect to the confirmation view
        }
    }
}