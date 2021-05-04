package edu.miu.cs.neptune.controller;

import edu.miu.cs.neptune.Util.Util;
import edu.miu.cs.neptune.domain.*;
import edu.miu.cs.neptune.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ServletContext servletContext;
    private final BiddingService biddingService;
    private final UserService userService;

    public ProductController(ProductService productService, CategoryService categoryService, ServletContext servletContext,
                             BiddingService biddingService,
                             UserService userService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.servletContext = servletContext;
        this.biddingService = biddingService;
        this.userService = userService;
    }

    @GetMapping("/all")
    private String getAllProducts(Authentication authentication, Model model) {
        String username = authentication.getName();

        List<Product> products = productService.findProductsBySeller(username);
        Integer numberOfBid = 0;

        Map<Long,Integer> productMaps = new HashMap<>();
        if(products.size()>0) {
            for (Product p : products
            ) {
                numberOfBid = biddingService.getNumberOfBidByProductId(p.getProductId());

                productMaps.put(p.getProductId(), numberOfBid);
            }
        } else {
            model.addAttribute("error", "You don't have any product!");
        }
        model.addAttribute("products", products);
        model.addAttribute("productMaps", productMaps);

        return "seller/AllProducts";
    }

    @GetMapping("/products/{category}")
    public String getProductsByCategory(@PathVariable("category") Long categoryId, Model model) {
        List<Product> productList = productService.getProductsByCategoryId(categoryId);

        if (productList == null || productList.isEmpty()) {
            throw new RuntimeException("No product found under the category " + categoryId);
        }

        model.addAttribute("products", productList);

        return "product/AllProducts";
    }

    @GetMapping("/{id}")
    public String productDetail(@PathVariable("id") Long productId, Model model) {
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);

        return "seller/ProductDetail";
    }

    @GetMapping("/createProduct")
    public String addProduct(@ModelAttribute("product") Product product, Model model) {
        model.addAttribute("categories", categoryService.getAll());
        return "seller/ProductForm";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(Product product, Principal principal, @RequestParam(value="action", required=true) String action,
                              BindingResult result, Model model) {
        Auction auction = product.getAuction();
        List<String> errors = new ArrayList<>();
        if (product.getProductPrice() <= 0) {
            errors.add("The product price must be greater than 0.");
        }
        if(action.equals("Save And Release")) {
            LocalDateTime begin = auction.getBeginDate();
            LocalDateTime end = auction.getEndDate();
            if (begin == null || end == null) {
                errors.add("The start date and end date must not be null.");
            } else if (begin != null && end != null) {
                LocalDateTime now = LocalDateTime.now().minusMinutes(1);
                if (begin.isBefore(now) || end.isBefore(now)) {
                    errors.add("The start date and end date must be in the future.");
                } else if (begin.isAfter(end)) {
                    errors.add("The end date must be after the begin date.");
                }
            }
            Double beginPrice = auction.getBeginPrice();
            if (beginPrice == null) {
                errors.add("The begin price must not be null.");
            } else if (beginPrice <= 0) {
                errors.add("The begin price must be greater than 0.");
            }
            product.setProductState(ProductState.SAVE_AND_RELEASE);
            auction.setAuctionStatus(AuctionStatus.ACTIVE);
        } else {
            product.setProductState(ProductState.SAVE_WITHOUT_RELEASE);
            auction.setAuctionStatus(AuctionStatus.INACTIVE);
        }

        if (result.hasErrors() || !errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("categories", categoryService.getAll());
            return "seller/ProductForm";
        }



        if (auction.getDepositAmount() == null) {
            //the deposit is 10% of the starting price by default.
            auction.setDepositAmount(product.getProductPrice() * 0.1);
        }

        List<MultipartFile> images = product.getImages();
        Long now = System.currentTimeMillis();
        if (images != null && !images.isEmpty()) {
            try {

                int count = 0;
                int numberImages = images.size();

                //Remove images which has zero size.
                for(int i=0; i<numberImages; ++i) {
                    if(images.get(i).getSize() ==0) {
                        images.remove(i);
                    }
                }

                for (MultipartFile image : images) {
                    if(image.getSize() ==0) {
                        images.remove(image);
                    } else {
                        String uploadDir = "ProductImages/";
                        String fileName = now + "_" + ++count + ".png";
                        Util.saveFile(uploadDir, fileName, image);
                        product.addDbImage(new Image(fileName));
                    }
                }
            } catch (Exception ex) {
                throw new RuntimeException("Product image was saving failed", ex);
            }
        }
        User currentUser = userService.getByName(principal.getName()).orElse(null);
        product.setSeller(currentUser);
        productService.save(product);

        return "redirect:/product/all";
    }

    @RequestMapping(value = "/edit/{productId}")
    public String updateCategory(@PathVariable Long productId, Model model) {
        Product product = productService.getProductById(productId);

        Integer numberOfBid = biddingService.getNumberOfBidByProductId(product.getProductId());
        Boolean disabled = false;

        if(numberOfBid>0) {
            disabled = true;
        }

        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("disabled", disabled);
        model.addAttribute("numberOfBid", numberOfBid);

        return "seller/ProductEditForm";
    }

    @GetMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable Long productId, RedirectAttributes redirectAttributes) {
        try {
            productService.delete(productId);
        } catch (RuntimeException productDeleteException) {
            redirectAttributes.addFlashAttribute("error", productDeleteException.getMessage());
        }
        return "redirect:/product/all";
    }
}
