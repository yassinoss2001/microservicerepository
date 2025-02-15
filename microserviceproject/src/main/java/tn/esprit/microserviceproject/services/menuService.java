package tn.esprit.microserviceproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.microserviceproject.entities.Menu;
import tn.esprit.microserviceproject.repositories.menuRepository;

import java.util.List;

@Service
public class menuService implements IMenu {

    @Autowired
    private menuRepository menuRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public Menu createMenu(Menu menu) {
        Menu savedMenu = menuRepository.save(menu);

        // Send Email Notification for new menu
        String subject = "New Menu Added: " + menu.getNom();
        String body = "A new menu has been added:\n\n" +
                "Name: " + menu.getNom() + "\n" +
                "Price: $" + menu.getPrix() + "\n" +
                "Description: " + menu.getDescription();
        emailService.sendEmail(subject, body);

        return savedMenu;
    }

    @Override
    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    @Override
    public Menu getMenuById(Long id) {
        return menuRepository.findById(id).orElse(null);
    }

    @Override
    public Menu updateMenu(Long id, Menu menuDetails) {
        Menu existingMenu = menuRepository.findById(id).orElse(null);

        if (existingMenu == null) {
            return null; // or throw an exception
        }

        // Check if the price has changed
        boolean isPriceChanged = existingMenu.getPrix() != menuDetails.getPrix();

        // Update fields
        existingMenu.setNom(menuDetails.getNom());
        existingMenu.setImage(menuDetails.getImage());
        existingMenu.setCalories(menuDetails.getCalories());
        existingMenu.setPrix(menuDetails.getPrix());
        existingMenu.setDescription(menuDetails.getDescription());

        Menu updatedMenu = menuRepository.save(existingMenu);

        // Send Email Notification only if the price has changed
        if (isPriceChanged) {
            String subject = "Menu Price Updated: " + existingMenu.getNom();
            String body = "The price of the menu '" + existingMenu.getNom() + "' has been updated.\n" +
                    "New Price: $" + existingMenu.getPrix() + "\n" +
                    "Description: " + existingMenu.getDescription();
            emailService.sendEmail(subject, body);
        }

        return updatedMenu;
    }

    @Override
    public void deleteMenu(Long id) {
        Menu menu = menuRepository.findById(id).orElse(null);
        if (menu != null) {
            menuRepository.deleteById(id);
        }
    }
}