package com.example.demo.controllers;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.Part;
import com.example.demo.service.InhousePartService;
import com.example.demo.service.InhousePartServiceImpl;
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
    public String submitForm(@Valid @ModelAttribute("inhousepart") InhousePart part, BindingResult theBindingResult, Model theModel) {
        theModel.addAttribute("inhousepart", part);
        System.out.println("CHECK TO SEE IF IT WORKED: " + part.getMinInventory());
        System.out.println(part);
        // Check if the inventory is within the minimum and maximum limits
        if (part.getInv() < part.getMinInventory() || part.getInv() > part.getMaxInventory()) {
            theBindingResult.rejectValue("inv", "error.inhousepart", "Inventory must be between minimum and maximum values.");
            System.out.println("***INVENTORY value is less than MININVENTORY***");
            System.out.println(part.getName());
            System.out.println(part.getPartId());
            System.out.println(part.getId());
            System.out.println(part.getMinInventory());
            System.out.println(part.getMaxInventory());
            System.out.println(part.getPrice());
            return "InhousePartForm";
        }

        //if (theBindingResult.hasErrors()) {
        //    return "InhousePartForm";}
        else {
            InhousePartService repo = context.getBean(InhousePartServiceImpl.class);
            InhousePart ip = repo.findById((int) part.getId());
            if (ip != null) part.setProducts(ip.getProducts());
            repo.save(part);

            return "confirmationaddpart";
        }
    }
}