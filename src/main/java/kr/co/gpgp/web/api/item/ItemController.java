package kr.co.gpgp.web.api.item;

import javax.validation.Valid;
import kr.co.gpgp.auth.dto.UserDetails;
import kr.co.gpgp.domain.item.Item;
import kr.co.gpgp.domain.item.ItemCommandService;
import kr.co.gpgp.domain.user.Seller;
import kr.co.gpgp.domain.user.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemCommandService itemCommandService;
    private final SellerService sellerService;

    @GetMapping
    public String createForm(@ModelAttribute NewItemCreateForm newItemCreateForm) {
        return "sellers/create-new-item-form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute NewItemCreateForm form,
                         BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            return "seller/create-new-item-form";
        }
        UserDetails userDetails = UserDetails.of(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Long userId = userDetails.getId();

        Seller seller = sellerService.findOneByUser(userId);

        Item item = ItemAdapter.toEntity(form, seller);
        itemCommandService.save(item);
        return "redirect:/";
    }

    @PatchMapping("/{itemId}")
    public String edit(@PathVariable Long itemId,
                       @Valid @ModelAttribute NewItemCreateForm form,
                       BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            return "seller/create-new-item-form";
        }

        Item item = ItemAdapter.toEntity(form);
        itemCommandService.update(itemId, item);

        return "redirect:/";
    }

}
