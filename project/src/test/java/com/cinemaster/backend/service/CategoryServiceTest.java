package com.cinemaster.backend.service;

import com.cinemaster.backend.data.dto.CategoryDto;
import com.cinemaster.backend.data.entity.Category;
import com.cinemaster.backend.data.service.CategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;

    @Test
    public void testSaveCategoryAndCheckFindAll() {
        Category category = new Category();
        category.setName("Azione");
        categoryService.save(category);

        List<CategoryDto> dto = categoryService.findAll();
        Assert.assertEquals(1, dto.size());
    }
}