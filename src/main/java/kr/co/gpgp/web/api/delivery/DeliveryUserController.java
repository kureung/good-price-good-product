package kr.co.gpgp.web.api.delivery;

import java.util.List;
import javax.validation.Valid;
import kr.co.gpgp.auth.dto.UserDetails;
import kr.co.gpgp.domain.delivery.Delivery;
import kr.co.gpgp.domain.delivery.DeliveryUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/delivery")
public class DeliveryUserController {
    private final DeliveryUserService deliveryUserService;


    @GetMapping
    public String get(Model model, Authentication authentication) {
        UserDetails user = UserDetails.of(authentication.getPrincipal());

        List<Delivery> delivery = deliveryUserService.selectAll(user.getId());
        List<DeliveryResponse> deliveryResponse = DeliveryResponse.convertList(delivery);

        //주문정보
//        model.addAttribute("order",orderResponse);
        //배송정보
        model.addAttribute("delivery",deliveryResponse);
        return "/users/delivery";
    }

    @PutMapping
    public String update(
            @Valid Long deliveryId,
            Authentication authentication
    ){
        UserDetails user = UserDetails.of(authentication.getPrincipal());

        deliveryUserService.update(user.getId(),deliveryId);
        return "redirect:/users/delivery";
    }

}
