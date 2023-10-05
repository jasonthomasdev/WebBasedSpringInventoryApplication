package com.example.demo.bootstrap;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.repositories.OutsourcedPartRepository;
import com.example.demo.repositories.PartRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.service.OutsourcedPartService;
import com.example.demo.service.OutsourcedPartServiceImpl;
import com.example.demo.service.ProductService;
import com.example.demo.service.ProductServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 *
 *
 *
 *
 */
@Component
public class BootStrapData implements CommandLineRunner {

    private final PartRepository partRepository;
    private final ProductRepository productRepository;

    private final OutsourcedPartRepository outsourcedPartRepository;

    public BootStrapData(PartRepository partRepository, ProductRepository productRepository, OutsourcedPartRepository outsourcedPartRepository) {
        this.partRepository = partRepository;
        this.productRepository = productRepository;
        this.outsourcedPartRepository = outsourcedPartRepository;
    }

    @Override
    public void run(String... args) throws Exception {

       /*
        OutsourcedPart o= new OutsourcedPart();
        o.setCompanyName("Western Governors University");
        o.setName("out test");
        o.setInv(5);
        o.setPrice(20.0);
        o.setId(100L);
        outsourcedPartRepository.save(o);
        OutsourcedPart thePart=null;
        List<OutsourcedPart> outsourcedParts=(List<OutsourcedPart>) outsourcedPartRepository.findAll();
        for(OutsourcedPart part:outsourcedParts){
            if(part.getName().equals("out test"))thePart=part;
        }

        System.out.println(thePart.getCompanyName());
        */

        if (partRepository.count() == 0 && productRepository.count() == 0) {

            // Create and Save Inhouse Parts using the new constructor
            InhousePart violin = new InhousePart("Violin", 500.0, 50, 10, 100, 1101);
            InhousePart bow = new InhousePart("Bow", 80.0, 50, 11, 99, 1102);
            InhousePart rosin = new InhousePart("Rosin", 10.0, 50, 12, 98, 1103);

            // Create and Save Outsourced Parts using the new constructor
            OutsourcedPart violinCase = new OutsourcedPart("Violin Case", 100.0, 50, 13, 97, "Cases Unlimited");
            OutsourcedPart shoulderRest = new OutsourcedPart("Shoulder Rest", 20.0, 50, 14, 96, "Cushions R Us");

            // Save Parts

            List<OutsourcedPart> outsourcedParts = (List<OutsourcedPart>) outsourcedPartRepository.findAll();
            for (OutsourcedPart part : outsourcedParts) {
                System.out.println(part.getName() + " " + part.getCompanyName());
            }

            // Create and Save Products
            Product beginnersSet = new Product("Beginner's Violin Set", 680.0, 5);
            beginnersSet.addPart(violin);
            beginnersSet.addPart(bow);
            beginnersSet.addPart(violinCase);

            Product performersSet = new Product("Performer's Set", 690.0, 5);
            performersSet.addPart(violin);
            performersSet.addPart(bow);
            performersSet.addPart(rosin);
            performersSet.addPart(violinCase);

            Product practiceSet = new Product("Practice Set", 610.0, 5);
            practiceSet.addPart(violin);
            practiceSet.addPart(bow);
            practiceSet.addPart(rosin);
            practiceSet.addPart(shoulderRest);

            Product professionalBundle = new Product("Professional Violinist Bundle", 710.0, 5);
            professionalBundle.addPart(violin);
            professionalBundle.addPart(bow);
            professionalBundle.addPart(rosin);
            professionalBundle.addPart(violinCase);
            professionalBundle.addPart(shoulderRest);

            Product accessoriesKit = new Product("Violin Accessories Kit", 210.0, 5);
            accessoriesKit.addPart(bow);
            accessoriesKit.addPart(rosin);
            accessoriesKit.addPart(violinCase);
            accessoriesKit.addPart(shoulderRest);
            // Save Products
/*
            productRepository.save(beginnersSet);
            productRepository.save(performersSet);
            productRepository.save(practiceSet);
            productRepository.save(professionalBundle);
            productRepository.save(accessoriesKit);
            */


            // Save Products
            productRepository.saveAll(List.of(beginnersSet, performersSet, practiceSet, professionalBundle, accessoriesKit));
            partRepository.saveAll(List.of(violin, bow, rosin, violinCase, shoulderRest));


        }
        /*
        Product bicycle= new Product("bicycle",100.0,15);
        Product unicycle= new Product("unicycle",100.0,15);
        productRepository.save(bicycle);
        productRepository.save(unicycle);
        */

            System.out.println("Started in Bootstrap");
            System.out.println("Number of Products" + productRepository.count());
            System.out.println(productRepository.findAll());
            System.out.println("Number of Parts" + partRepository.count());
            System.out.println(partRepository.findAll());



    }
}
