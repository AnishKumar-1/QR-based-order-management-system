package com.menu.service;

import com.menu.dto.CategoryItemsResponseDto;
import com.menu.dto.ItemRequestDto;
import com.menu.dto.ItemsResponseDto;
import com.menu.dto.UpdateSingleItemDto;
import com.menu.exceptions.ErrorResponse;
import com.menu.module.Category;
import com.menu.module.Items;
import com.menu.repository.CategoryRepo;
import com.menu.repository.ItemsRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemsService {

    @Autowired
    private ItemsRepo itemsRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    //create item
    public ResponseEntity<CategoryItemsResponseDto> createItem(ItemRequestDto item,Long categoryId){
        Items items=modelMapper.map(item,Items.class);
        Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"category not found with id: "+categoryId));
        items.setCategory(category);
        Items response=itemsRepo.save(items);
        ItemsResponseDto itemsResponseDto=modelMapper.map(response,ItemsResponseDto.class);
        CategoryItemsResponseDto result=CategoryItemsResponseDto.builder()
                .category(category.getName())
                .items(List.of(itemsResponseDto)).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }


    //GET /categories/{id}/items → list all items under a category (using category id, not name).
    public ResponseEntity<CategoryItemsResponseDto> allItemsByCategoryId(Long categoryId){
        Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"category not found with id: "+categoryId));
        List<ItemsResponseDto> items=itemsRepo.findAll().stream().filter(Items::getAvailable).map(item->
                modelMapper.map(item,ItemsResponseDto.class)
        ).toList();
        CategoryItemsResponseDto res=CategoryItemsResponseDto.builder().category(category.getName()).items(items).build();
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    //GET /items/{id} → get details of a single item (e.g., for showing details before adding to order).
      //get single item by its id
    public ResponseEntity<ItemsResponseDto> itemByItsId(Long itemId) {
        Items result = itemsRepo.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "item not found with id: " + itemId));

        if (!result.getAvailable()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "item not available with id: " + itemId);
        }

        ItemsResponseDto dto = modelMapper.map(result, ItemsResponseDto.class);
        return ResponseEntity.ok(dto);
    }

    //update status availablebility
    //patch items by its id
    public ResponseEntity<String> updateStatus(Long itemId, Boolean status) {

        Items result = itemsRepo.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "item not found with id: " + itemId));

        if (status == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "boolean value required"
            );
        }

        result.setAvailable(status);
        itemsRepo.save(result);

        return ResponseEntity.status(HttpStatus.OK).body("status updated successfully");
    }

    //update item details
    public ResponseEntity<String> updateSingleItem(Long itemId, UpdateSingleItemDto item){
        Items result = itemsRepo.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "item not found with id: " + itemId));

        if(!item.getName().isEmpty())
            result.setName(item.getName());

        if(!item.getDescription().isEmpty())
            result.setDescription(item.getDescription());
        if(item.getPrice() != null)
            result.setPrice(item.getPrice());

        itemsRepo.save(result);

        return ResponseEntity.status(HttpStatus.OK).body("item updated successfully");

    }

    //delete item by its id
    public ResponseEntity<String> deleteSingleItemByItsId(Long itemId){
        if(!itemsRepo.existsById(itemId)){
            throw new IllegalArgumentException("item not found with this id: "+itemId);
        }
        itemsRepo.deleteById(itemId);
        return ResponseEntity.status(HttpStatus.OK).body("item deleted successfully");
    }


}
