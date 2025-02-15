package tn.esprit.microserviceproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.microserviceproject.entities.Menu;
import tn.esprit.microserviceproject.services.IMenu;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
public class menuController {

    @Autowired
    private IMenu iMenu;

    // Create a new menu
    @PostMapping
    public ResponseEntity<Menu> createMenu(@RequestBody Menu menu) {
        Menu createdMenu = iMenu.createMenu(menu);
        return ResponseEntity.ok(createdMenu);
    }

    // Get all menus
    @GetMapping
    public ResponseEntity<List<Menu>> getAllMenus() {
        List<Menu> menus = iMenu.getAllMenus();
        return ResponseEntity.ok(menus);
    }

    // Get a menu by ID
    @GetMapping("/{id}")
    public ResponseEntity<Menu> getMenuById(@PathVariable Long id) {
        Menu menu = iMenu.getMenuById(id);
        if (menu != null) {
            return ResponseEntity.ok(menu);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update a menu
    @PutMapping("/{id}")
    public ResponseEntity<Menu> updateMenu(@PathVariable Long id, @RequestBody Menu menuDetails) {
        Menu updatedMenu = iMenu.updateMenu(id, menuDetails);
        if (updatedMenu != null) {
            return ResponseEntity.ok(updatedMenu);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a menu by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        iMenu.deleteMenu(id);
        return ResponseEntity.noContent().build();
    }
}