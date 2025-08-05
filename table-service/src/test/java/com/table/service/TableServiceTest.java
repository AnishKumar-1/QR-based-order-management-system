package com.table.service;

import com.table.service.dto.TableRequest;
import com.table.service.dto.TableResponse;
import com.table.service.model.Tables;
import com.table.service.repository.TableRepo;
import com.table.service.service.TableService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TableServiceTest {

    @Mock
    private TableRepo tableRepo;  //fake repo to perform unit testing
    @InjectMocks
    private TableService tableService;

    @Mock
    private ModelMapper modelMapper;
    private TableRequest tableRequest;

    @BeforeEach
    public void setup() {
        tableRequest = TableRequest.builder()
                .tableNumber(1)
                .build();
    }

    //for success test
    @Test
    public void testAddTable_Success(){
        //saved table
        Tables savedEntity=Tables.builder().id(1L).tableNumber(tableRequest.getTableNumber()).isActive(true).build();
        TableResponse tableResponse=TableResponse.builder().id(1L).tableNumber(1).isActive(true).build();
        when(tableRepo.findByTableNumber(1)).thenReturn(Optional.empty());
        when(tableRepo.save(any(Tables.class))).thenReturn(savedEntity);
        when(modelMapper.map(savedEntity,TableResponse.class)).thenReturn(tableResponse);
        ResponseEntity<TableResponse> response=tableService.addTable(tableRequest);
        Assertions.assertEquals(HttpStatus.CREATED,response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(1,response.getBody().getTableNumber());
        verify(tableRepo).save(any(Tables.class));
//        System.out.println(response.getBody());
    }


    //test table by id
    @Test
    public void testTableById_Success(){
        Tables savedEntity=Tables.builder().id(1L).tableNumber(tableRequest.getTableNumber()).isActive(true).build();
        TableResponse tableResponse=TableResponse.builder().id(1L).tableNumber(1).isActive(true).build();
        when(tableRepo.findById(1L)).thenReturn(Optional.of(savedEntity));
        when(modelMapper.map(savedEntity,TableResponse.class)).thenReturn(tableResponse);
        ResponseEntity<TableResponse> response=tableService.tableById(1L);
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(1L, Objects.requireNonNull(response.getBody()).getId());

    }


    //get all tables
    @Test
    public void testAllTables_success(){
        Tables table1=Tables.builder().id(1L).tableNumber(1).isActive(true).build();
        Tables table2=Tables.builder().id(2L).tableNumber(2).isActive(true).build();
        List<Tables> mockEntityList = Arrays.asList(table1,table2);
        when(tableRepo.findAll()).thenReturn(mockEntityList);
        ResponseEntity<List<TableResponse>>response=tableService.tables();
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(2,response.getBody().size());

        //extract first response list
        TableResponse res1=response.getBody().get(0);
        Assertions.assertEquals(1,res1.getTableNumber());
        TableResponse res2=response.getBody().get(1);
        Assertions.assertEquals(2,res2.getTableNumber());
        verify(tableRepo, times(1)).findAll();
        System.out.println(response);

    }

    //update table number by its id

    @Test
    public void testTableNumberSuccessfullyUpdate(){
        Long tableId=1L;
        Integer newNumber=5;

        Tables existing=Tables.builder().id(tableId).tableNumber(2).isActive(true).build();
        when(tableRepo.findById(tableId)).thenReturn(Optional.of(existing));

        ResponseEntity<String> response=tableService.updateTable(tableId,newNumber);
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals("Table number updated successfully",response.getBody());
        Assertions.assertEquals(newNumber,existing.getTableNumber());
        verify(tableRepo).save(existing);
    }

    @Test
    public void testTableIdWrong(){

        Long tableId=1L;
        Integer newNumber=5;
        when(tableRepo.findById(tableId)).thenReturn(Optional.empty());
      IllegalArgumentException error=Assertions.assertThrows(IllegalArgumentException.class,()->{
            tableService.updateTable(tableId,newNumber);
        });

      Assertions.assertEquals("Table not found with this id: "+tableId,error.getLocalizedMessage());
        verify(tableRepo, never()).save(any());
    }


//check remove table success first
    @Test
    public void testRemoveTable(){
        Long tableId=1L;
        Tables existing=Tables.builder().id(tableId).tableNumber(2).isActive(true).build();
        when(tableRepo.findById(tableId)).thenReturn(Optional.of(existing));
        doNothing().when(tableRepo).deleteById(tableId);
        ResponseEntity<String> response=tableService.removeTable(tableId);
        Assertions.assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
        Assertions.assertEquals("table deleted successfully",response.getBody());
        verify(tableRepo).deleteById(tableId);
    }

    //delete method test when id not found or wrong
    @Test
    public void testRemoveTableIdNotFound(){
        Long tableId=1L;
        when(tableRepo.findById(tableId)).thenReturn(Optional.empty());
       IllegalArgumentException error=Assertions.assertThrows(IllegalArgumentException.class,()->{
            tableService.removeTable(tableId);
        });

       Assertions.assertEquals("Table not found with this id: " + tableId,error.getLocalizedMessage());

    }

}
