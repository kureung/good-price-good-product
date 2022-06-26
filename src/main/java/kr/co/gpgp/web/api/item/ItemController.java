package kr.co.gpgp.web.api.item;

import javax.validation.Valid;
import kr.co.gpgp.domain.item.Item;
import kr.co.gpgp.domain.item.ItemCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemCommandService itemCommandService;

    @GetMapping("/new")
    public String createForm(@ModelAttribute NewItemCreateForm newItemCreateForm) {
        return "sellers/create-new-item-form";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute NewItemCreateForm form,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "admin/create-new-item-form";
        }

        Item item = ItemAdapter.toEntity(form);
        itemCommandService.save(item);
        return "redirect:/";
    }

}
