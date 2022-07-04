package kr.co.gpgp.web.api.item;

import javax.validation.Valid;
import kr.co.gpgp.auth.dto.UserDetails;
import kr.co.gpgp.domain.item.Item;
import kr.co.gpgp.domain.item.ItemCommandService;
import kr.co.gpgp.domain.item.ItemFindService;
import kr.co.gpgp.domain.item.ItemSearchCondition;
import kr.co.gpgp.domain.item.ItemSearchDto;
import kr.co.gpgp.domain.user.Seller;
import kr.co.gpgp.domain.user.SellerService;
import kr.co.gpgp.domain.common.Pages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemCommandService itemCommandService;
    private final ItemFindService itemFindService;
    private final SellerService sellerService;

    @GetMapping("/registration")
    public String createForm(@ModelAttribute NewItemCreateForm newItemCreateForm) {
        return "sellers/create-new-item-form";
    }

    @PostMapping("/registration")
    public String create(@Valid @ModelAttribute NewItemCreateForm newItemCreateForm,
                         BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "seller/create-new-item-form";
        }
        UserDetails userDetails = UserDetails.of(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Long userId = userDetails.getId();

        Seller seller = sellerService.findOneByUser(userId);

        Item item = ItemAdapter.toEntity(newItemCreateForm, seller);

        itemCommandService.save(item);
        return "redirect:/items";
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

    @GetMapping
    public String searchItem(
            @RequestParam(required = false) String condition,
            Pageable pageable,
            Model model
    ) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), 10);
        ItemSearchCondition searchCondition = ItemSearchCondition.from(condition);
        Page<ItemSearchDto> searchItems = itemFindService.search(searchCondition, pageRequest);
        model.addAttribute("searchItems", searchItems);

        Pages<ItemSearchDto> pages = Pages.of(searchItems, 4);
        model.addAttribute("pages", pages.getPages());

        log.info("condition='{}'", condition);
        log.info("pages.getPages()= '{}'", pages.getPages());
        log.info("firstPage='{}'", pages.getFirstPage());
        log.info("lastPage='{}'", pages.getLastPage());

        return "items/list";
    }

}
