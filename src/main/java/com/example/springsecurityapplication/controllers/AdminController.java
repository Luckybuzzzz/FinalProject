package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.enumm.Role;
import com.example.springsecurityapplication.enumm.Status;
import com.example.springsecurityapplication.models.*;
import com.example.springsecurityapplication.repositories.CategoryRepository;
import com.example.springsecurityapplication.repositories.OrderRepostitory;
import com.example.springsecurityapplication.services.ImageService;
import com.example.springsecurityapplication.services.OrderService;
import com.example.springsecurityapplication.services.PersonService;
import com.example.springsecurityapplication.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Controller
public class AdminController {

    private final ProductService productService;
    private final ImageService imageService;
    private final OrderService orderService;
    private final OrderRepostitory orderRepostitory;
    private final PersonService personService;

    @Value("${upload.path}")
    private String uploadPath;

    private final CategoryRepository categoryRepository;

    public AdminController(ProductService productService, ImageService imageService, OrderService orderService, OrderRepostitory orderRepostitory, PersonService personService, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.imageService = imageService;
        this.orderService = orderService;
        this.orderRepostitory = orderRepostitory;
        this.personService = personService;
        this.categoryRepository = categoryRepository;
    }


    @GetMapping("/admin")
    public String admin() {
        return "admin/admin";
    }


    @GetMapping("/admin/product")
    public String admin(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        return "admin/infoProduct";
    }

   @GetMapping("admin/product/add")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("category", categoryRepository.findAll());
        return "product/addProduct";
    }

    @PostMapping("/admin/product/add")
    public String addProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult,
                             @RequestParam("file_one") MultipartFile file_one,
                             @RequestParam("file_two") MultipartFile file_two,
                             @RequestParam("file_three") MultipartFile file_three,
                             @RequestParam("file_four") MultipartFile file_four,
                             @RequestParam("file_five") MultipartFile file_five,
                             @RequestParam("category") int category, Model model) throws IOException {
        Category category_db = categoryRepository.findById(category).orElseThrow();
            if (bindingResult.hasErrors()) {
        model.addAttribute("category", categoryRepository.findAll());
            return "product/addProduct";
        }

        if (file_one != null && file_one.getSize() >0) {

            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFile = uuidFile + "." + file_one.getOriginalFilename();
            file_one.transferTo(new File(uploadPath + "/" + resultFile));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFile);
            product.addImageToProduct(image);
        }
        if (file_two != null && file_two.getSize() >0) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFile = uuidFile + "." + file_two.getOriginalFilename();
            file_two.transferTo(new File(uploadPath + "/" + resultFile));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFile);
            product.addImageToProduct(image);
        }
        if (file_three != null && file_three.getSize() >0) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFile = uuidFile + "." + file_three.getOriginalFilename();
            file_three.transferTo(new File(uploadPath + "/" + resultFile));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFile);
            product.addImageToProduct(image);
        }
        if (file_four != null && file_four.getSize() >0) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFile = uuidFile + "." + file_four.getOriginalFilename();
            file_four.transferTo(new File(uploadPath + "/" + resultFile));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFile);
            product.addImageToProduct(image);
        }
        if (file_five != null && file_five.getSize() >0) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFile = uuidFile + "." + file_five.getOriginalFilename();
            file_five.transferTo(new File(uploadPath + "/" + resultFile));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFile);
            product.addImageToProduct(image);
        }
        productService.saveProduct(product, category_db);
        return "redirect:/admin";
    }


    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
        return "redirect:/admin";
    }


    @GetMapping("/admin/product/edit/{id}")
    public String editProduct(Model model, @PathVariable("id") int id) {

        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("category", categoryRepository.findAll());
        return "product/editProduct";
    }

    @PostMapping("/admin/product/edit/{id}")
    public String editProduct(@ModelAttribute("product")
                              @Valid Product product, BindingResult bindingResult,
                              @PathVariable("id") int id,
                              @RequestParam("file_one") MultipartFile file_one,
                              @RequestParam("file_two") MultipartFile file_two,
                              @RequestParam("file_three") MultipartFile file_three,
                              @RequestParam("file_four") MultipartFile file_four,
                              @RequestParam("file_five") MultipartFile file_five,
                              Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("category", categoryRepository.findAll());
            return "product/editProduct";
        }


        imageService.deleteImageByProductId(product.getId());
        if (!file_one.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFile = uuidFile + "." + file_one.getOriginalFilename();

            file_one.transferTo(new File(uploadPath + "/" + resultFile));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFile);
            product.addImageToProduct(image);
        }
        if (!file_two.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFile = uuidFile + "." + file_two.getOriginalFilename();
            file_two.transferTo(new File(uploadPath + "/" + resultFile));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFile);
            product.addImageToProduct(image);
        }
        if (!file_three.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFile = uuidFile + "." + file_three.getOriginalFilename();
            file_three.transferTo(new File(uploadPath + "/" + resultFile));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFile);
            product.addImageToProduct(image);
        }
        if (!file_four.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFile = uuidFile + "." + file_four.getOriginalFilename();
            file_four.transferTo(new File(uploadPath + "/" + resultFile));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFile);
            product.addImageToProduct(image);
        }
        if (!file_five.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFile = uuidFile + "." + file_five.getOriginalFilename();
            file_five.transferTo(new File(uploadPath + "/" + resultFile));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFile);
            product.addImageToProduct(image);
        }
        productService.updateProduct(id, product);
        return "redirect:/product/info/{id}";
    }


    @GetMapping("/admin/orders")
    public String showAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("status", Status.values());
        return "/admin/orders";
    }


    @GetMapping("/admin/order/{id}")
    public String orderInfo(@PathVariable("id") int id, Model model) {
        model.addAttribute("order", orderService.getOrderById(id));
        model.addAttribute("status", Status.values());
        return "/admin/orderInfo";
    }


    @PostMapping("/admin/order/{id}")
    public String ChangeOrderStatus(@ModelAttribute("status") Status status,
                                    @PathVariable("id") int id)
    {
        Order order = orderService.getOrderById(id);
        order.setStatus(status);
        orderService.updateOrder(id, order);
        return "redirect:/admin/order/{id}";
    }


    @PostMapping("/admin/order/search")
    public String orderSearch(@RequestParam("search") String search, Model model){

        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("search_order", orderRepostitory.findOrderByPartOfNumber(search));
        model.addAttribute("value_search", search);
        return "/admin/orders";
    }


    @GetMapping("/admin/users")
    public String showAllUsers(Model model) {
        model.addAttribute("users", personService.findAllUsers());
        model.addAttribute("role", Role.values());
        return "/admin/users";
    }


    @GetMapping("/admin/user/{id}")
    public String userInfo(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", personService.findById(id));
        model.addAttribute("role", Role.values());
        return "/admin/userInfo";
    }

    @PostMapping("/admin/user/{id}")
    public String setUserRole (@ModelAttribute("role") String role, @PathVariable("id") int id){
        Person person = personService.findById(id);
        person.setRole(role);
        personService.updatePerson(id, person);
        return "redirect:/admin/user/{id}";
    }
}