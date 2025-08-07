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

    //get category by id
    public ResponseEntity<CategoryResponseDto> category(Long categoryId){
       Category cat=categoryRepo.findById(categoryId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found with id:"+categoryId));
       List<ItemsResponseDto> items=cat.getItems().stream().filter(Items::getAvailable)
               .map(data->ItemsResponseDto.builder()
                       .id(data.getId())
                       .name(data.getName())
                       .price(data.getPrice())
                       .available(data.getAvailable())
                       .description(data.getDescription())
                       .created_at(data.getCreated_at())
                       .last_updated(data.getLast_updated())
                       .build()
               ).toList();

       CategoryResponseDto res=CategoryResponseDto.builder()
               .id(cat.getId())
               .name(cat.getName())
               .created_at(cat.getCreated_at())
               .last_updated(cat.getLast_updated())
               .items(items).build();
       return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    //delete category
    public ResponseEntity<String> removeCategory(Long categoryId){
        if(!categoryRepo.existsById(categoryId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"category id not found with this id: "+categoryId);
        }
        categoryRepo.deleteById(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body("category deleted successfully");

    }

    //find category by its name
    public ResponseEntity<CategoryResponseDto> categorybyName(String categoryName){
        Category cat=categoryRepo.findByName(categoryName).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found with the name:"+categoryName));
        List<ItemsResponseDto> items=cat.getItems().stream().filter(Items::getAvailable)
                .map(data->ItemsResponseDto.builder()
                        .id(data.getId())
                        .name(data.getName())
                        .price(data.getPrice())
                        .available(data.getAvailable())
                        .description(data.getDescription())
                        .created_at(data.getCreated_at())
                        .last_updated(data.getLast_updated())
                        .build()
                ).toList();

        CategoryResponseDto res=CategoryResponseDto.builder()
                .id(cat.getId())
                .name(cat.getName())
                .created_at(cat.getCreated_at())
                .last_updated(cat.getLast_updated())
                .items(items).build();
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    //update category name
    public ResponseEntity<String> updateCategoryName(Long categoryId,CategoryRequestDto categoryRequestDto){
       Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"category not found with id: "+categoryId));
        category.setName(categoryRequestDto.getName());
        categoryRepo.save(category);
        return ResponseEntity.status(HttpStatus.OK).body("category updated successfully");
    }

}
