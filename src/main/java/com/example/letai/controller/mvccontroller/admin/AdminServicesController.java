package com.example.letai.controller.mvccontroller.admin;


import com.example.letai.exception.exceptionhandler.UserNotFoundException;
import com.example.letai.model.dto.ProductDTO;
import com.example.letai.services.ProductService;
import com.example.letai.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static com.example.letai.util.FileUploadUtil.deleteFileOrDirectory;

@Controller
public class AdminServicesController {
    @Autowired
    private ProductService productService;

    @GetMapping("/admin/tables")
    public String adminTables(Model model) {
        List<ProductDTO> list = productService.listAll();
        model.addAttribute("listProduct", list);
        return "admin/Product_Service/tables";
    }

    @GetMapping(value = "admin/editProduct/{id}")
    public String adminRedirectEditProduct(@PathVariable("id") Long id,Model model) throws UserNotFoundException {
        ProductDTO productDTO = productService.get(id);
        model.addAttribute("productDTO",productDTO);
        return "admin/Product_Service/editProduct";
    }
    @PostMapping(value = "/admin/editProduct")
    public String adminEditProduct(ProductDTO product,
                                   @RequestParam("image") MultipartFile multipartFile, RedirectAttributes ra) throws IOException, UserNotFoundException {
        Long id =product.getId();
        // if you have new image
        if(multipartFile.getOriginalFilename() != ""){
            //delete old image
            String pathToDelete = "src/main/resources/static/admin-services/" + id; // Đường dẫn thư mục cần xóa
            Path directoryPath = Paths.get(pathToDelete);
            try {
                deleteFileOrDirectory(directoryPath);
            } catch (IOException e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
            ra.addFlashAttribute("message", "The product ID " + id + " has been deleted.");
            // save new  path image
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            product.setImg(fileName);
            ProductDTO saveProduct = productService.save(product);
            //save new image
            String uploadDir = "src/main/resources/static/admin-services/" + saveProduct.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }
        else{
            //product is new, productDTO is old
            ProductDTO productDTO = productService.get(product.getId());
            product.setImg(productDTO.getImg()); // because new product not require image,so it can empty
            ProductDTO saveProduct = productService.save(product);
        }

        return "redirect:/admin/tables";
    }
    @GetMapping("/admin/product/new")
    public String adminAddNewProduct(Model model) {
        return "admin/Product_Service/addNewProduct";
    }

    @RequestMapping(value = "/admin/addProduct", method = RequestMethod.POST)
    public RedirectView adminPostsAdd(ProductDTO product,
                                      @RequestParam("image") MultipartFile multipartFile) throws IOException {
        //lấy tên của file và lưu tên vào database
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        product.setImg(fileName);
        ProductDTO saveProduct = productService.save(product);
        //lưu ảnh thực
        String uploadDir = "src/main/resources/static/admin-services/" + saveProduct.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return new RedirectView("/admin/tables", true);
    }

    @RequestMapping(value = "/admin/Product/delete/{id}")
    public RedirectView adminDeletePost(@PathVariable("id") Long id, RedirectAttributes ra) throws UserNotFoundException {
        productService.delete(id);
        String pathToDelete = "src/main/resources/static/admin-services/" + id; // Đường dẫn thư mục cần xóa
        Path directoryPath = Paths.get(pathToDelete);
        try {
            deleteFileOrDirectory(directoryPath);
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
        ra.addFlashAttribute("message", "The product ID " + id + " has been deleted.");
        return new RedirectView("/admin/tables", true);
    }
}
