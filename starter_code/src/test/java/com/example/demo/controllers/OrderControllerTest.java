package com.example.demo.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.demo.UtilTest;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

public class OrderControllerTest {
  private OrderController orderController;
  private OrderRepository orderRepo = mock(OrderRepository.class);
  private UserRepository userRepo = mock(UserRepository.class);

  @Before
  public void setUp(){
    String name = "tuan";
    String des = "hello";
    Double numberDb = 5.0;
    orderController = new OrderController();
    UtilTest.setDataForObj(orderController, "orderRepository", orderRepo);
    UtilTest.setDataForObj(orderController, "userRepository", userRepo);


    String username = "tuannta2";
    String password = "adele";
    Item item = new Item();
    item.setId(1L);
    System.out.println(name);
    item.setName(name);
    System.out.println(numberDb);
    BigDecimal price = BigDecimal.valueOf(numberDb);
    item.setPrice(price);
    System.out.println(des);
    item.setDescription(des);
    List<Item> items = new ArrayList<Item>();
    items.add(item);

    User user = new User();
    Cart cart = new Cart();
    user.setId(0);
    System.out.println(username);
    user.setUsername(username);
    System.out.println(password);
    user.setPassword(password);
    cart.setId(0L);
    cart.setUser(user);
    cart.setItems(items);
    System.out.println(numberDb);
    BigDecimal total = BigDecimal.valueOf(numberDb);
    cart.setTotal(total);
    user.setCart(cart);
    when(userRepo.findByUsername(username)).thenReturn(user);
    when(userRepo.findByUsername(username + "invalid")).thenReturn(null);

  }

  @Test
  public void getOrder() {
    String user = "tuannta2";
    System.out.println(user);
    ResponseEntity<List<UserOrder>> ordersForUser = orderController.getOrdersForUser(user);
    assertNotNull(ordersForUser);
    assertEquals(200, ordersForUser.getStatusCodeValue());
    List<UserOrder> orders = ordersForUser.getBody();
    assertNotNull(orders);

  }


  @Test
  public void submitOrderFail() {
    String user = "invalid";
    System.out.println(user);
    ResponseEntity<UserOrder> response = orderController.submit(user);
    assertNotNull(response);
    assertEquals(404, response.getStatusCodeValue());
  }



  @Test
  public void getOrderFail() {
    String user = "invalid";
    System.out.println(user);
    ResponseEntity<List<UserOrder>> ordersForUser = orderController.getOrdersForUser(user);
    assertNotNull(ordersForUser);
    assertEquals(404, ordersForUser.getStatusCodeValue());

  }

  @Test
  public void submitOrder() {
    String user = "tuannta2";
    System.out.println(user);
    ResponseEntity<UserOrder> response = orderController.submit(user);
    assertNotNull(response);
    assertEquals(200, response.getStatusCodeValue());
    UserOrder order = response.getBody();
    assertNotNull(order);
    assertEquals(1, order.getItems().size());
  }
}
