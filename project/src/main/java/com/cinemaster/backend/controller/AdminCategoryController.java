package com.cinemaster.backend.controller;

import com.cinemaster.backend.core.exception.ForbiddenException;
import com.cinemaster.backend.core.exception.InvalidDataException;
import com.cinemaster.backend.data.dto.AccountPasswordLessDto;
import com.cinemaster.backend.data.dto.AdminPasswordLessDto;
import com.cinemaster.backend.data.dto.CategoryDto;
import com.cinemaster.backend.data.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admin/categories")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity categoryList(@CookieValue(value = "sessionid", defaultValue = "") String sessionId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof AdminPasswordLessDto) {
            return ResponseEntity.ok(categoryService.findAll());
        } else {
            throw new ForbiddenException();
        }
    }

    @PostMapping("")
    public ResponseEntity categoryAdd(@RequestBody CategoryDto categoryDto, @CookieValue(value = "sessionid", defaultValue = "") String sessionId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof AdminPasswordLessDto) {
            try {
                categoryService.save(categoryDto);
                return ResponseEntity.ok(categoryDto);
            } catch (RuntimeException e) {
                throw new InvalidDataException();
            }
        } else {
            throw new ForbiddenException();
        }
    }

    @PutMapping("")
    public ResponseEntity categoryEdit(@RequestBody CategoryDto categoryDto, @CookieValue(value = "sessionid", defaultValue = "") String sessionId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof AdminPasswordLessDto) {
            try {
                categoryService.update(categoryDto);
                return ResponseEntity.ok(categoryDto);
            } catch (RuntimeException e) {
                throw new InvalidDataException();
            }
        } else {
            throw new ForbiddenException();
        }
    }

    @DeleteMapping("")
    public ResponseEntity categoryDelete(@RequestBody CategoryDto categoryDto, @CookieValue(value = "sessionid", defaultValue = "") String sessionId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof AdminPasswordLessDto) {
            categoryService.delete(categoryDto);
            return ResponseEntity.ok("Successfully deleted");
        } else {
            throw new ForbiddenException();
        }
    }
}
