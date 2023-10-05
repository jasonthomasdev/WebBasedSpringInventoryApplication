package com.example.demo.controllers;

import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.service.PartService;
import com.example.demo.service.PartServiceImpl;
import com.example.demo.service.ProductService;
import com.example.demo.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 *
 *
 */
@Controller
public class AddProductController {
    @Autowired
    private ApplicationContext context;
    private PartService partService;
    private List<Part> theParts;
    private static Product product1;
    private Product product;

    @GetMapping("/showFormAddProduct")
    public String showFormAddPart(Model theModel) {
        theModel.addAttribute("parts", partService.findAll());
        product = new Product();
        product1=product;
        theModel.addAttribute("product", product);

        List<Part>availParts=new ArrayList<>();
        for(Part p: partService.findAll()){
            if(!product.getParts().contains(p))availParts.add(p);
        }
        theModel.addAttribute("availparts",availParts);
        theModel.addAttribute("assparts",product.getParts());
        return "productForm";
    }

    @PostMapping("/showFormAddProduct")
    public String submitForm(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, Model theModel) {
        theModel.addAttribute("product", product);
        for (Part p : product.getParts()) {
            if (p.getInv() < p.getMinInventory()) {
                bindingResult.rejectValue("inv", "error.product", "One of the associated parts has inventory below its minimum.");
                break;
            }
            if (p.getInv() > p.getMaxInventory()) {
                bindingResult.rejectValue("inv", "error.product", "One of the associated parts has inventory above its maximum.");
                break;
            }
        }
        if(bindingResult.hasErrors()){
            ProductService productService = context.getBean(ProductServiceImpl.class);
            Product product2=productService.findById((int)product.getId());
            theModel.addAttribute("parts", partService.findAll());
            List<Part>availParts=new ArrayList<>();
            for(Part p: partService.findAll()){
                if(!product2.getParts().contains(p))availParts.add(p);
            }
            theModel.addAttribute("availparts",availParts);
            theModel.addAttribute("assparts",product2.getParts());
            return "productForm";
        }
 //       theModel.addAttribute("assparts", assparts);
 //       this.product=product;
//        product.getParts().addAll(assparts);
        else {
            ProductService repo = context.getBean(ProductServiceImpl.class);
            if(product.getId() != 0) {
                Product product2 = repo.findById((int) product.getId());
                PartService partService1 = context.getBean(PartServiceImpl.class);
                int inventoryDifference = product.getInv() - product2.getInv();
                if(inventoryDifference > 0) {
                    for (Part p : product2.getParts()) {
                        int newInventory = p.getInv() - inventoryDifference;
                        if (newInventory < p.getMinInventory() || newInventory > p.getMaxInventory()) {
                            bindingResult.rejectValue("inv", "error.product", "Adjusting the product's inventory would cause a part's inventory to go out of bounds.");
                            setupModelForProductForm(theModel, product);
                            return "productForm";
                        }
                        p.setInv(newInventory);
                        partService1.save(p);
                    }
                }
            } else {
                product.setInv(0);
            }
            try {
                repo.save(product);
                return "confirmationaddproduct";
            } catch (ConstraintViolationException e) {
                bindingResult.rejectValue("inv", "error.product", "One of the associated parts has inventory outside its allowed range.");
                setupModelForProductForm(theModel, product);
                return "productForm";
            }
        }
    }

    @GetMapping("/showProductFormForUpdate")
    public String showProductFormForUpdate(@RequestParam("productID") int theId, Model theModel) {
        theModel.addAttribute("parts", partService.findAll());
        ProductService repo = context.getBean(ProductServiceImpl.class);
        Product theProduct = repo.findById(theId);
        product1=theProduct;
    //    this.product=product;
        //set the employ as a model attibute to prepopulate the form
        theModel.addAttribute("product", theProduct);
        theModel.addAttribute("assparts",theProduct.getParts());
        List<Part>availParts=new ArrayList<>();
        for(Part p: partService.findAll()){
            if(!theProduct.getParts().contains(p))availParts.add(p);
        }
        theModel.addAttribute("availparts",availParts);
        //send over to our form
        return "productForm";
    }

    @GetMapping("/deleteproduct")
    public String deleteProduct(@RequestParam("productID") int theId, Model theModel) {
        ProductService productService = context.getBean(ProductServiceImpl.class);
        Product product2=productService.findById(theId);
        for(Part part:product2.getParts()){
            part.getProducts().remove(product2);
            partService.save(part);
        }
        product2.getParts().removeAll(product2.getParts());
        productService.save(product2);
        productService.deleteById(theId);

        return "confirmationdeleteproduct";
    }

    public AddProductController(PartService partService) {
        this.partService = partService;
    }
// make the add and remove buttons work

    @GetMapping("/associatepart")
    public String associatePart(@Valid @RequestParam("partID") int theID, Model theModel){
    //    theModel.addAttribute("product", product);
    //    Product product1=new Product();
        if (product1.getName()==null) {
            return "saveproductscreen";
        }
        else{
        product1.getParts().add(partService.findById(theID));
        partService.findById(theID).getProducts().add(product1);
        ProductService productService = context.getBean(ProductServiceImpl.class);
        productService.save(product1);
        partService.save(partService.findById(theID));
        theModel.addAttribute("product", product1);
        theModel.addAttribute("assparts",product1.getParts());
        List<Part>availParts=new ArrayList<>();
        for(Part p: partService.findAll()){
            if(!product1.getParts().contains(p))availParts.add(p);
        }
        theModel.addAttribute("availparts",availParts);
        return "productForm";}
 //        return "confirmationassocpart";
    }
    @GetMapping("/removepart")
    public String removePart(@RequestParam("partID") int theID, Model theModel){
        theModel.addAttribute("product", product);
      //  Product product1=new Product();
        product1.getParts().remove(partService.findById(theID));
        partService.findById(theID).getProducts().remove(product1);
        ProductService productService = context.getBean(ProductServiceImpl.class);
        productService.save(product1);
        partService.save(partService.findById(theID));
        theModel.addAttribute("product", product1);
        theModel.addAttribute("assparts",product1.getParts());
        List<Part>availParts=new ArrayList<>();
        for(Part p: partService.findAll()){
            if(!product1.getParts().contains(p))availParts.add(p);
        }
        theModel.addAttribute("availparts",availParts);
        return "productForm";
    }

    @GetMapping("/buyProduct")
    public String buyProduct(@RequestParam("productID") int productId, RedirectAttributes redirectAttributes) {
        ProductService productService = context.getBean(ProductServiceImpl.class);
        Product theProduct = productService.findById(productId);

        if (theProduct != null && theProduct.getInv() > 0) {
            theProduct.setInv(theProduct.getInv() - 1);  // decrement inventory by one
            productService.save(theProduct);
            redirectAttributes.addFlashAttribute("successMessage", "Purchase successful!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Purchase failed! Not enough inventory or product not found.");
        }

        return "redirect:/mainscreen";
    }

    private void setupModelForProductForm(Model theModel, Product product) {
        ProductService productService = context.getBean(ProductServiceImpl.class);
        Product product2 = productService.findById((int) product.getId());

        theModel.addAttribute("parts", partService.findAll());

        List<Part> availParts = new ArrayList<>();
        for (Part p : partService.findAll()) {
            if (!product2.getParts().contains(p)) {
                availParts.add(p);
            }
        }

        theModel.addAttribute("availparts", availParts);
        theModel.addAttribute("assparts", product2.getParts());
    }

}
