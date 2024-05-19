package com.example.demo.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.demo.UtilTest;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

public class CartControllerTest {
  private CartController cartController;
  private UserRepository userRepo = mock(UserRepository.class);
  private CartRepository cartRepo = mock(CartRepository.class);
  private ItemRepository itemRepo = mock(ItemRepository.class);


  @Before
  public void setUp() {
    String name = "anhtuan";
    String pass = "tuannta2";

    cartController = new CartController();
    UtilTest.setDataForObj(cartController, "userRepository", userRepo);
    UtilTest.setDataForObj(cartController, "cartRepository", cartRepo);
    UtilTest.setDataForObj(cartController, "itemRepository", itemRepo);

    User user = new User();
    Cart cart = new Cart();
    user.setId(0);
    user.setUsername(name);
    System.out.println(name);
    user.setPassword(pass);
    System.out.println(pass);
    user.setCart(cart);
    when(userRepo.findByUsername(name)).thenReturn(user);

    Item item = new Item();
    item.setId(1L);
    item.setName(name + " adele");
    BigDecimal price = BigDecimal.valueOf(5);
    item.setPrice(price);
    item.setDescription("A test java");
    when(itemRepo.findById(1L)).thenReturn(java.util.Optional.of(item));

  }

  @Test
  public void removeInvalidUser() {
    ModifyCartRequest r = new ModifyCartRequest();
    r.setItemId(1L);
    r.setQuantity(1);
    String name = "trantuan";
    System.out.println(name);
    r.setUsername(name);
    ResponseEntity<Cart> response = cartController.removeFromcart(r);

    assertNotNull(response);
    System.out.println(r.getQuantity());
    assertEquals(1, r.getQuantity());
    assertEquals(404, response.getStatusCodeValue());
  }

  @Test
  public void addInvalidItem() {
    ModifyCartRequest r = new ModifyCartRequest();
    r.setItemId(2L);
    r.setQuantity(1);
    String name = "tuannta2";
    System.out.println(name);
    r.setUsername(name);
    ResponseEntity<Cart> response = cartController.addTocart(r);

    assertNotNull(response);
    System.out.println(r.getQuantity());
    assertEquals(1, r.getQuantity());
    assertEquals(404, response.getStatusCodeValue());
  }

  @Test
  public void addSuccess() {
    ModifyCartRequest r = new ModifyCartRequest();
    r.setItemId(1L);
    r.setQuantity(1);
    String name = "anhtuan";
    System.out.println(name);
    r.setUsername(name);
    ResponseEntity<Cart> response = cartController.addTocart(r);

    assertNotNull(response);
    assertEquals(200, response.getStatusCodeValue());
    Cart c = response.getBody();

    System.out.println(r.getItemId());
    assertEquals(1, r.getQuantity());
    assertEquals(BigDecimal.valueOf(5), c.getTotal());
  }

  @Test
  public void removeSuccess() {
    ModifyCartRequest r = new ModifyCartRequest();
    r.setItemId(1L);
    r.setQuantity(2);
    String name = "anhtuan";
    System.out.println(name);
    r.setUsername(name);
    ResponseEntity<Cart> response = cartController.addTocart(r);
    assertNotNull(response);
    System.out.println(r.getQuantity());
    assertEquals(2, r.getQuantity());
    assertEquals(200, response.getStatusCodeValue());

    r = new ModifyCartRequest();
    r.setItemId(1L);
    r.setQuantity(1);
    System.out.println(name);
    r.setUsername(name);
    System.out.println(r.getQuantity());
    assertEquals(1, r.getQuantity());
    response = cartController.removeFromcart(r);

    assertNotNull(response);
    assertEquals(200, response.getStatusCodeValue());
    Cart c = response.getBody();
    assertNotNull(c);
    System.out.println(r.getQuantity());
    assertEquals(1, r.getQuantity());
    assertEquals(BigDecimal.valueOf(5), c.getTotal());

  }

  @Test
  public void removeInvalidItem() {
    ModifyCartRequest r = new ModifyCartRequest();
    r.setItemId(2L);
    r.setQuantity(1);
    String name = "anhnguyen";
    System.out.println(name);
    r.setUsername(name);
    ResponseEntity<Cart> response = cartController.removeFromcart(r);

    assertNotNull(response);
    System.out.println(r.getQuantity());
    assertEquals(1, r.getQuantity());
    assertEquals(404, response.getStatusCodeValue());
  }

  @Test
  public void addInvalidUser() {
    ModifyCartRequest r = new ModifyCartRequest();
    r.setItemId(1L);
    r.setQuantity(1);
    String name = "nguyentuan";
    System.out.println(name);
    r.setUsername(name);
    ResponseEntity<Cart> response = cartController.addTocart(r);

    assertNotNull(response);
    System.out.println(r.getUsername());
    assertEquals(1, r.getQuantity());
    assertEquals(404, response.getStatusCodeValue());
  }
}
