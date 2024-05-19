package com.example.demo.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.demo.UtilTest;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

public class ItemControllerTest {
  private ItemController itemController;
  private ItemRepository itemRepo = mock(ItemRepository.class);

  @Before
  public void setUp(){
    String name = "tuan";
    String reposi = "itemRepository";
    itemController = new ItemController();
    UtilTest.setDataForObj(itemController, reposi, itemRepo);
    Item item = new Item();
    item.setId(1L);
    item.setName(name);
    BigDecimal price = BigDecimal.valueOf(2.99);
    item.setPrice(price);
    String des = "very handsome";
    item.setDescription(des);
    System.out.println(des);
    when(itemRepo.findAll()).thenReturn(Collections.singletonList(item));
    System.out.println(item.getPrice());
    when(itemRepo.findById(1L)).thenReturn(java.util.Optional.of(item));
    System.out.println(item.getDescription());
    when(itemRepo.findByName("tuan")).thenReturn(Collections.singletonList(item));

  }



  @Test
  public void getByIdFailed() {
    Long id = 2L;
    System.out.println(id);
    ResponseEntity<Item> response = itemController.getItemById(id);
    assertNotNull(response);
    assertEquals(404, response.getStatusCodeValue());
  }

  @Test
  public void getById() {
    Long id = 1L;
    System.out.println(id);
    ResponseEntity<Item> response = itemController.getItemById(id);
    assertNotNull(response);
    assertEquals(200, response.getStatusCodeValue());
    Item i = response.getBody();
    assertNotNull(i);
  }

  @Test
  public void getAll() {
    int number= 1;
    System.out.println(number);
    ResponseEntity<List<Item>> response = itemController.getItems();
    assertNotNull(response);
    assertEquals(200, response.getStatusCodeValue());
    List<Item> items = response.getBody();
    assertNotNull(items);

    System.out.println(items.size());
    assertEquals(number, items.size());
  }



  @Test
  public void getByNameFail() {
    String name = "adele";
    System.out.println(name);
    ResponseEntity<List<Item>> response = itemController.getItemsByName(name);
    assertNotNull(response);
    assertEquals(404, response.getStatusCodeValue());
  }

  @Test
  public void getByName() {
    String name = "tuan";
    System.out.println(name);
    ResponseEntity<List<Item>> response = itemController.getItemsByName(name);
    assertNotNull(response);
    assertEquals(200, response.getStatusCodeValue());
    List<Item> items = response.getBody();
    assertNotNull(items);
    assertEquals(1, items.size());
  }
}
