package org.example.ecommerceweb.controller.user;


import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.domains.User;
import org.example.ecommerceweb.dto.req.AddItemRequestDto;
//import org.example.ecommerceweb.service.CartItemService;
import org.example.ecommerceweb.service.AuthService;
import org.example.ecommerceweb.service.CartItemService;
import org.example.ecommerceweb.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/cart")
public class CartController {
    private final CartService cartService;
    private final AuthService authService;
    private final CartItemService cartItemService;

    @GetMapping("")
    public ResponseEntity<?> getCart(@RequestHeader(value = "Authorization") String jwt){
       try{
           User user = authService.findUserByJwt(jwt);
           return new ResponseEntity<>(cartService.findUserCart(user.getId()), HttpStatus.OK);
       }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
       }

    }

    @PostMapping("")
    public ResponseEntity<?> addCartItem(@RequestHeader(value = "Authorization") String jwt, @RequestBody AddItemRequestDto req){
        try{
            User user = authService.findUserByJwt(jwt);
            return new ResponseEntity<>(cartService.addCartItem(user.getId(), req), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
//
    @PutMapping("/{cartItemId}")
    public ResponseEntity<?> updateCartItem(@RequestHeader(value = "Authorization") String jwt,@PathVariable Long cartItemId, @RequestParam Integer quantity, @RequestParam Double price){
        try{
            User user = authService.findUserByJwt(jwt);
            return new ResponseEntity<>(cartItemService.updateCartItem(cartItemId,quantity, price), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<?> removeCartItem(@RequestHeader(value = "Authorization") String jwt,@PathVariable Long cartItemId){
        try{
            User user = authService.findUserByJwt(jwt);
            cartItemService.removeCartItem(cartItemId);
            return new ResponseEntity<>("Item removed from cart!", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
