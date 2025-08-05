package com.menu.service;

import com.menu.dto.CategoryRequestDto;
import com.menu.dto.CategoryResponseDto;
import com.menu.dto.ItemsResponseDto;
import com.menu.module.Category;
import com.menu.module.Items;
import com.menu.repository.CategoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepo categoryRepo;
    private final ModelMapper modelMapper;

    public CategoryService(ModelMapper modelMapper,CategoryRepo categoryRepo) {
        this.modelMapper = modelMapper;
        this.categoryRepo=categoryRepo;
    }

    //create category
    public ResponseEntity<CategoryResponseDto> createCategory(CategoryRequestDto category){
        if(categoryRepo.findByName(category.getName()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category already exists");
        }

        Category cat=modelMapper.map(category, Category.class);
        Category res=categoryRepo.save(cat);

//storing only available items
        List<ItemsResponseDto> items=res.getItems().stream().filter(Items::getAvailable)
                .map(item->ItemsResponseDto.builder().id(item.getId())
                        .name(item.getName())
                        .price(item.getPrice())
                        .description(item.getDescription())
                        .available(item.getAvailable())
                        .created_at(item.getCreated_at())
                        .last_updated(item.getLast_updated())
                        .build()
                ).toList();

        CategoryResponseDto categoryResponseDto=CategoryResponseDto.builder()
                .id(res.getId())
                .name(res.getName())
                .items(items)
                .created_at(res.getCreated_at())
                .last_updated(res.getLast_updated()).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryResponseDto);
    }
}
