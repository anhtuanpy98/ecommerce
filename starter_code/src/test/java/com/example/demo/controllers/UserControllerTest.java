package com.example.demo.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.demo.UtilTest;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

public class UserControllerTest {
  private UserController userController;
  private UserRepository userRepo = mock(UserRepository.class);
  private CartRepository cartRepo = mock(CartRepository.class);

  @Before
  public void setUp() {
    String username = "tuannta2";
    String password = "adele";
    Long id = 0L;
    userController = new UserController();
    UtilTest.setDataForObj(userController, "userRepository", userRepo);
    UtilTest.setDataForObj(userController, "cartRepository", cartRepo);

    User user = new User();
    Cart cart = new Cart();
    user.setId(0);
    System.out.println(username);
    user.setUsername(username);
    System.out.println(password);
    user.setPassword(password);
    user.setCart(cart);
    when(userRepo.findByUsername(username)).thenReturn(user);
    when(userRepo.findById(id)).thenReturn(java.util.Optional.of(user));
    String invalid = "invalid";
    System.out.println(invalid);
    when(userRepo.findByUsername(invalid)).thenReturn(null);

  }

  @Test
  public void findUserByNameFail() {
    String username = "adele";
    System.out.println(username);
    int number = 404;
    final ResponseEntity<User> response = userController.findByUserName(username);
    assertNotNull(response);
    System.out.println(number);
    assertEquals(number, response.getStatusCodeValue());
  }

  @Test
  public void createUser() {
    String username = "tuannta2";
    String password = "adele";
    CreateUserRequest r = new CreateUserRequest();
    r.setUsername(username);
    System.out.println(username);
    r.setPassword(password);
    System.out.println(password);
    r.setConfirmPassword(password);
    System.out.println(password);
    final ResponseEntity<User> response = userController.createUser(r);
    assertNotNull(response);
    int number = 200;
    int numberTwo = 0 ;
    assertEquals(number, response.getStatusCodeValue());
    User u = response.getBody();
    assertNotNull(u);
    assertEquals(numberTwo, u.getId());
    assertEquals(username, u.getUsername());

  }

  @Test
  public void findUserByIdFail() {
    Long id = 10L;
    System.out.println(id);
    final ResponseEntity<User> response = userController.findById(id);
    assertNotNull(response);
    assertEquals(404, response.getStatusCodeValue());
  }



  @Test
  public void createUserFailTwo() {
    CreateUserRequest r = new CreateUserRequest();
    String username = "tuannta2";
    String pass = "a";
    String passTwo = "b";
    r.setUsername(username);
    System.out.println(username);
    r.setPassword(pass);
    System.out.println(pass);
    r.setConfirmPassword(passTwo);
    System.out.println(passTwo);
    final ResponseEntity<User> response = userController.createUser(r);
    assertNotNull(response);
  }

  @Test
  public void findUserByName() {
    String username = "tuannta2";
    System.out.println(username);
    final ResponseEntity<User> response = userController.findByUserName(username);
    assertNotNull(response);
    assertEquals(200, response.getStatusCodeValue());
    User u = response.getBody();
    assertNotNull(u);
    System.out.println(username);
    assertEquals(username, u.getUsername());
  }



  @Test
  public void findUserById() {
    Long id = 0L;
    System.out.println(id);
    int numberOne = 200;
    int numberTwo = 0;
    System.out.println(numberOne);
    final ResponseEntity<User> response = userController.findById(id);
    assertNotNull(response);
    assertEquals(numberOne, response.getStatusCodeValue());
    User u = response.getBody();
    assertNotNull(u);
    System.out.println(numberTwo);
    assertEquals(numberTwo, u.getId());;
  }

  @Test
  public void createUserFailOne() {
    CreateUserRequest r = new CreateUserRequest();
    String username = "tuannta2";
    String pass = "a";
    r.setUsername(username);
    System.out.println(username);
    r.setPassword(pass);
    System.out.println(pass);
    r.setConfirmPassword(pass);
    System.out.println(pass);
    final ResponseEntity<User> response = userController.createUser(r);
    assertNotNull(response);
  }
}
