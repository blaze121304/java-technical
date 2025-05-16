package com.rusty.menusystem.controller;

import com.rusty.menusystem.domain.entity.MenuItem;
import com.rusty.menusystem.domain.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @GetMapping
    public String showMenu(Model model) {
        model.addAttribute("menuItems", menuService.getAllMenuItems());
        model.addAttribute("categories", menuService.getAllCategories());
        return "menu/list";
    }

    @GetMapping("/cart")
    public String showCart(Model model) {
        return "menu/cart";
    }

    @GetMapping("/add")
    public String showAddForm() {
        return "menu/add-menu";
    }

    @PostMapping("/add")
    public String addMenu(@RequestParam("name") String name,
                          @RequestParam("price") int price,
                          @RequestParam("category") String category,
                          @RequestParam("description") String description,
                          @RequestParam(value = "image", required = false) MultipartFile image) {

        MenuItem menuItem = MenuItem.builder()
                .name(name)
                .price(price)
                .category(category)
                .description(description)
                .build();

        if (image != null && !image.isEmpty()) {
            // 이미지 처리 로직
            String imageUrl = saveImage(image);
            menuItem.setImageUrl(imageUrl);
        }

        MenuItem savedItem = menuService.addMenuItem(menuItem);
        log.info("저장된 메뉴 아이템: {}", savedItem);  // 로그 추가

        menuService.addMenuItem(menuItem);
        return "redirect:/menu";
    }

    private String saveImage(MultipartFile image) {
        // 이미지를 저장하고 URL을 반환하는 로직
        try {
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            String uploadDir = "uploads/menu/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/menu/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("이미지 저장 실패", e);
        }
    }
}