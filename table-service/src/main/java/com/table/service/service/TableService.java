package com.table.service.service;

import com.table.service.dto.TableRequest;
import com.table.service.dto.TableResponse;
import com.table.service.model.Tables;
import com.table.service.repository.TableRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TableService {
    @Autowired
    private TableRepo tableRepo;
    @Autowired
    private ModelMapper modelMapper;

    //add table
    public ResponseEntity<TableResponse> addTable(TableRequest tableRequest){
        Optional<Tables> table=tableRepo.findByTableNumber(tableRequest.getTableNumber());
        if(table.isPresent()){
            throw new IllegalArgumentException("Table already exists with id: "+tableRequest.getTableNumber());
        }
        Tables tables=Tables.builder().tableNumber(tableRequest.getTableNumber()).build();
        Tables result=tableRepo.save(tables);
      return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(result,TableResponse.class));
    }


    //get table by table id
    public ResponseEntity<TableResponse> tableById(Long tableId){
        Tables table = tableRepo.findById(tableId)
                .orElseThrow(() -> new IllegalArgumentException("table is not found with this id: " + tableId));
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(table,TableResponse.class));
    }

    //get list of tables
    public ResponseEntity<List<TableResponse>> tables(){
        List<TableResponse> table = tableRepo.findAll().stream().map(singleTable->TableResponse.builder().id(singleTable.getId())
                        .tableNumber(singleTable.getTableNumber()).isActive(singleTable.getIsActive()).build())
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(table);
    }

    //patch table by its id
    public ResponseEntity<String> updateTable(Long tableId, Integer tableNumber) {
        Tables table = tableRepo.findById(tableId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Table not found with this id: " + tableId));

        if (tableNumber != null && tableNumber > 0 && !tableNumber.equals(table.getTableNumber())) {
            table.setTableNumber(tableNumber);
            tableRepo.save(table);
            return ResponseEntity.ok("Table number updated successfully");
        }

        return ResponseEntity.ok("No change made to table number");
    }



    //patch table status by its id
    public ResponseEntity<String> updateTableStatus(Long tableId,Boolean status){
        if (status == null) {
            throw new IllegalArgumentException("Status value cannot be null");
        }

        Tables table = tableRepo.findById(tableId)
                .orElseThrow(() -> new IllegalArgumentException("Table not found with this id: " + tableId));

        table.setIsActive(status);
        tableRepo.save(table);
        return  ResponseEntity.status(HttpStatus.OK).body("table status updated successfully");
    }


    //delete table/remove table
    public ResponseEntity<String> removeTable(Long tableId){
        Tables table = tableRepo.findById(tableId)
                .orElseThrow(() -> new IllegalArgumentException("Table not found with this id: " + tableId));
        tableRepo.deleteById(tableId);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).body("table deleted successfully");
    }


}
