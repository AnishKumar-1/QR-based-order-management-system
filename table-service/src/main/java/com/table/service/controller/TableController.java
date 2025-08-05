package com.table.service.controller;

import com.table.service.dto.TableRequest;
import com.table.service.dto.TableResponse;
import com.table.service.service.TableService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/table")
@Validated
public class TableController {

    @Autowired
    private TableService tableService;
    private final Map<String,Object> res=new HashMap<>();

    //add table
    @PostMapping
    public ResponseEntity<TableResponse> addTable(@Valid @RequestBody TableRequest tableNumber){
        return tableService.addTable(tableNumber);
    }
    //get table by its id
    @GetMapping("/{tableId}")
    public ResponseEntity<Map<String,Object>> tableByTableId(@PathVariable @NotNull(message = "required table id") Long tableId){
        res.put("table",tableService.tableById(tableId));
        return ResponseEntity.ok(res);
    }
    //patch table number by its id
    @PatchMapping("/{tableId}")
    public ResponseEntity<String> updateTableNumber(@PathVariable @NotNull(message = "required table id") Long tableId,@NotNull(message = "table number required") @RequestParam Integer tableNumber){
        return tableService.updateTable(tableId,tableNumber);
    }

    //patch table status by its id
    @PatchMapping("/{tableId}/status")
    public ResponseEntity<String> updateTableStatus(@PathVariable @NotNull(message = "required table id") Long tableId,@RequestParam(name = "status",required = true) Boolean status){
        return tableService.updateTableStatus(tableId,status);
    }

    //remove table
    @DeleteMapping("/{tableId}")
    public ResponseEntity<String> removeTable(@PathVariable @NotNull(message = "required table id") Long tableId){
        return tableService.removeTable(tableId);
    }

    //get all table list
    @GetMapping
    public ResponseEntity<Map<String,Object>> tableList(){
        res.put("tables",tableService.tables());
        return ResponseEntity.ok(res);
    }

}
