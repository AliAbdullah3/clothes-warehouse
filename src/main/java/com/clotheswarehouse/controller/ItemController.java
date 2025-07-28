package com.clotheswarehouse.controller;

import com.clotheswarehouse.dto.ItemRequestDto;
import com.clotheswarehouse.model.Brand;
import com.clotheswarehouse.model.DistributionCentre;
import com.clotheswarehouse.model.Item;
import com.clotheswarehouse.service.DistributionCentreService;
import com.clotheswarehouse.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {
    
    @Autowired
    private ItemService itemService;

    @Autowired
    private DistributionCentreService distributionCentreService;
    
    @GetMapping
    public String listItems(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size,
                           @RequestParam(defaultValue = "false") boolean sortByBrand,
                           Model model) {
        Page<Item> itemPage;
        
        if (sortByBrand) {
            itemPage = itemService.getAllItemsSortedByBrand(page, size);
        } else {
            itemPage = itemService.getAllItemsPaginated(page, size, "id");
        }
        
        model.addAttribute("items", itemPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", itemPage.getTotalPages());
        model.addAttribute("totalItems", itemPage.getTotalElements());
        model.addAttribute("sortByBrand", sortByBrand);
        model.addAttribute("brands", Brand.values());
        
        return "items/list";
    }
    
    @GetMapping("/add")
    @PreAuthorize("hasRole('ADMIN') or hasRole('WAREHOUSE_EMPLOYEE')")
    public String showAddForm(Model model) {
        model.addAttribute("item", new Item());
        model.addAttribute("brands", Brand.values());
        return "items/add";
    }
    
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN') or hasRole('WAREHOUSE_EMPLOYEE')")
    public String addItem(@Valid @ModelAttribute Item item, 
                         BindingResult result, 
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("brands", Brand.values());
            return "items/add";
        }
        
        itemService.saveItem(item);
        redirectAttributes.addFlashAttribute("successMessage", "Item added successfully!");
        return "redirect:/items";
    }
    
    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteItem(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        itemService.deleteItem(id);
        redirectAttributes.addFlashAttribute("successMessage", "Item deleted successfully!");
        return "redirect:/items";
    }
    
    @GetMapping("/filter")
    public String filterItems(@RequestParam Brand brand, Model model) {
        List<Item> filteredItems = itemService.getItemsByBrandAndYear2022(brand);
        model.addAttribute("items", filteredItems);
        model.addAttribute("brands", Brand.values());
        model.addAttribute("selectedBrand", brand);
        model.addAttribute("filtered", true);
        return "items/list";
    }
    
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminPage(Model model) {
        List<Item> items = itemService.getAllItems();
        model.addAttribute("items", items);
        
        List<DistributionCentre> distributionCentres = distributionCentreService.getAllDistributionCentres();
        model.addAttribute("distributionCentres", distributionCentres);
        
        model.addAttribute("itemRequest", new ItemRequestDto());
        model.addAttribute("brands", Brand.values());
        
        return "items/admin";
    }

    @PostMapping("/request-item")
    @PreAuthorize("hasRole('ADMIN')")
    public String requestItem(@Valid @ModelAttribute ItemRequestDto itemRequest,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please fill all required fields");
            return "redirect:/items/admin";
        }

        String resultMessage = distributionCentreService.requestItemFromDistributionCentre(itemRequest);
        
        if (resultMessage.startsWith("SUCCESS")) {
            redirectAttributes.addFlashAttribute("successMessage", resultMessage);
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", resultMessage);
            return "redirect:/items/request-error";
        }
        
        return "redirect:/items/admin";
    }

    @GetMapping("/request-error")
    @PreAuthorize("hasRole('ADMIN')")
    public String requestError() {
        return "items/request-error";
    }
}
