package com.formacionbdi.springboot.app.item.Services;

import com.formacionbdi.springboot.app.item.Models.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IItemService {
    List<Item> finAll();
    Item finById(Long id, Integer cantidad);
}
