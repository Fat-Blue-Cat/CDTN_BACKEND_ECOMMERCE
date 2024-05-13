package org.example.ecommerceweb.controller.user;

import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.domains.User;
import org.example.ecommerceweb.service.AuthService;
import org.example.ecommerceweb.service.FavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;
    private final AuthService authService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllFavorite(@RequestHeader(value = "Authorization") String jwt) {
        try {
            User user = authService.findUserByJwt(jwt);
            return ResponseEntity.ok(favoriteService.getAllFavorite(user.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @GetMapping("/add")
    public ResponseEntity<?> addFavorite(@RequestHeader(value = "Authorization") String jwt, @RequestParam Long productId) {
        try {
            User user = authService.findUserByJwt(jwt);
            favoriteService.addFavorite(user.getId(), productId);
            return ResponseEntity.ok("Product added to favorite list");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/remove")
    public ResponseEntity<?> removeFavorite(@RequestHeader(value = "Authorization") String jwt, @RequestParam Long productId) {
        try {
            User user = authService.findUserByJwt(jwt);
            favoriteService.removeFavorite(user.getId(), productId);
            return ResponseEntity.ok("Product removed from favorite list");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/remove-all")
    public ResponseEntity<?> removeAllFavorites(@RequestHeader(value = "Authorization") String jwt) {
        try {
            User user = authService.findUserByJwt(jwt);
            favoriteService.removeAllFavorites(user.getId());
            return ResponseEntity.ok("All products removed from favorite list");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/is-favorite")
    public ResponseEntity<?> isFavorite(@RequestHeader(value="Authorization") String jwt, @RequestParam Long productId) {
        try {
            User user = authService.findUserByJwt(jwt);
            return ResponseEntity.ok(favoriteService.isFavorite(user.getId(), productId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }






}
