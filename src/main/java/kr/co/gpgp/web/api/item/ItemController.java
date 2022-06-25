package kr.co.gpgp.web.api.item;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/items")
public class ItemController {

    @GetMapping("/new")
    public String createForm(@ModelAttribute NewItemCreateForm form) {
        return "admin/create-new-item-form";
    }

}
