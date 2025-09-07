package com.menu.controllers;

import com.menu.dto.CategoryItemsResponseDto;
import com.menu.dto.ItemRequestDto;
import com.menu.dto.ItemsResponseDto;
import com.menu.dto.UpdateSingleItemDto;
import com.menu.service.ItemsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/item")
@Validated
public class ItemController {

    @Autowired
    private ItemsService itemsService;

    //create item under a category
    @PostMapping("/category/{categoryId}")
    public ResponseEntity<CategoryItemsResponseDto> createItem(@Valid @RequestBody ItemRequestDto item,@NotNull(message = "required category id") @PathVariable Long categoryId){
     return itemsService.createItem(item,categoryId);
    }

    //all items under a category by category id
    @GetMapping("/category/{catId}")
    public ResponseEntity<CategoryItemsResponseDto> itemsUnderCategory(@NotNull(message = "required category id") @PathVariable Long catId){
        return itemsService.allItemsByCategoryId(catId);
    }

    //single item by its id
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemsResponseDto> singleItemByItsId(@NotNull(message = "required itemId id") @PathVariable Long itemId){
        return itemsService.itemByItsId(itemId);
    }

    //patch item status to showcase its availability
    @PatchMapping("/{itemId}/status")
    public ResponseEntity<String> patchItemStatus(@NotNull(message = "required itemId id") @PathVariable Long itemId,
                                                  @NotNull(message = "status required")
                                                  @RequestParam Boolean status){
        return itemsService.updateStatus(itemId,status);
    }

    //update item details by item id and item dto objects
    @PatchMapping("/{id}")
    public ResponseEntity<String> patchSingleItemByItsId(@NotNull(message = "item id required")
                                                             @PathVariable  Long id,
                                                         @RequestBody UpdateSingleItemDto item){
        return itemsService.updateSingleItem(id,item);
    }

    //delete item  by item id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSingleItem(@NotNull(message = "item id required")
                                                         @PathVariable  Long id){
        return itemsService.deleteSingleItemByItsId(id);
    }

}
