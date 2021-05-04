package edu.miu.cs.neptune.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.miu.cs.neptune.domain.Category;
import edu.miu.cs.neptune.domain.Product;
import edu.miu.cs.neptune.domain.ProductState;
import edu.miu.cs.neptune.service.CategoryService;
import edu.miu.cs.neptune.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Controller

@RequestMapping("customer/")
public class CustomerController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;
    int count = 1;
    public void general(Model model, Page<Product> page, int pageNum, String sortField, String sortDir){

        List<Product> list = page.getContent();

        System.out.println(list);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("products", list);
    }

    @GetMapping("products")
    public String viewHomePage(Model model) {
        count = 1;
        return listProduct(model, 1, "uploadDate", "desc");
    }


    @GetMapping("products/{pageNum}")
    public String listProduct(Model model, @PathVariable(name = "pageNum") int pageNum,
                              @Param("sortField") String sortField,
                              @Param("sortDir") String sortDir){
        count = 1;
        Page<Product> page = productService.findProductsByProductStateEquals(ProductState.SAVE_AND_RELEASE,pageNum, sortField, sortDir);
        general(model, page, pageNum, sortField, sortDir);
        return "customer/productList";
    }
    @GetMapping("category/products")
    public String listProductByCategory(@RequestParam("id") Long id, Model model){
        count = 1;
        Page<Product> page = productService.getProductsByCategoryIdAndProductStateEquals(id,ProductState.SAVE_AND_RELEASE, 1, "uploadDate", "desc");
        general(model, page, 1, "uploadDate", "desc");

        List<Product> list = page.getContent();

        System.out.println("----------category list"+list.toString());
        model.addAttribute("products", list);
        model.addAttribute("categoryId", id);
        return "customer/productsByCategory";
    }
    @GetMapping("category/productsSort/{pageNum}")
    public String listProductByCategorySort(Model model, @PathVariable(name = "pageNum") int pageNum,
                                            @Param("sortField") String sortField,
                                            @Param("sortDir") String sortDir,
                                            @Param("categoryId") Long categoryId){
        count = 1;
        Page<Product> page = productService.getProductsByCategoryIdAndProductStateEquals(categoryId,ProductState.SAVE_AND_RELEASE, 1, sortField, sortDir);
        general(model, page, 1, sortField, sortDir);

        List<Product> list = page.getContent();

        System.out.println("----------category list"+list.toString());
        model.addAttribute("products", list);
        model.addAttribute("categoryId", categoryId);
        return "customer/productsByCategory";
    }

    @PostMapping(value = "category/productsNext")
    @ResponseBody
    public String listProductByCategoryOrder(@RequestParam("categoryId") Long categoryId, Model model) throws JsonProcessingException {
        count++;
        System.out.println(categoryId);
        Page<Product> page = productService.getProductsByCategoryIdAndProductStateEquals(categoryId,ProductState.SAVE_AND_RELEASE, count, "uploadDate", "decs");
        general(model, page, count, "uploadDate", "decs");
        List<Product> list = page.getContent();

        if(page.getTotalPages()>=count){
            return list.toString() + page.getTotalPages();
        }
        else
            return "done";
    }


    @GetMapping("categories")
    public String listCategory(Model model){
        count = 1;
        model.addAttribute("categories", categoryService.getAll());
        return "customer/categoryList";
    }

    @GetMapping("product")
    public String getProductById(@RequestParam("id") Long productId, Model model){
        count = 1;
        System.out.println("product ID: " + productId);
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);
        model.addAttribute("images", product.getDbImages());
        System.out.println(product.getDbImages());
        return "customer/product";
    }

    @RequestMapping("product_search")
    public String  searchProducts( @RequestParam("keyword") String keyword, Model model){
        count = 1;
        //System.out.println(pageNum);
        String key=keyword.toLowerCase();
        System.out.println(key);
        Page<Product> page = productService.findProductsByProductStateEqualsAndProductNameContaining( ProductState.SAVE_AND_RELEASE, keyword,1, "uploadDate", "desc");
        List<Product> list = page.getContent();

        System.out.println(list);
        general(model, page, 1, "uploadDate", "desc");
        model.addAttribute("products", list);
        model.addAttribute("keyword", keyword);
        return "customer/productList";
    }

}
