package com.formacionbdi.springboot.app.item.Controllers;

import com.formacionbdi.springboot.app.item.Models.Item;
import com.formacionbdi.springboot.app.item.Services.IItemService;
import com.formacionbdi.springboot.app.item.Services.Impl.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Items")
public class itemController {
    @Autowired
    private IItemService iItemService;

    @GetMapping("/AllItems")
    public ResponseEntity<List<Item>> AllItems(){
        return ResponseEntity.ok(iItemService.finAll());
    }
    @GetMapping("/ItemById/{id}/Cantidad/{cantidad}")
    public ResponseEntity<Item> itemById(@PathVariable Long id,@PathVariable Integer cantidad){
        return ResponseEntity.ok(iItemService.finById(id,cantidad));
    }

}
