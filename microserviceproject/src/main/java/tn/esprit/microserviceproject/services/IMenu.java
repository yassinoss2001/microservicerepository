package tn.esprit.microserviceproject.services;

import tn.esprit.microserviceproject.entities.Menu;

import java.util.List;

public interface IMenu {
    Menu createMenu(Menu menu);

    // Get all menus
    List<Menu> getAllMenus();

    // Get a menu by ID
    Menu getMenuById(Long id);

    // Update a menu
    Menu updateMenu(Long id, Menu menuDetails);

    // Delete a menu by ID
    void deleteMenu(Long id);
}
