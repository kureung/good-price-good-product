package kr.co.gpgp.web.api.requirement;

import java.util.List;
import javax.validation.Valid;
import kr.co.gpgp.auth.dto.UserDetails;
import kr.co.gpgp.domain.requirement.Requirement;
import kr.co.gpgp.domain.requirement.RequirementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/requirement")
public class RequirementApiController {

    private final RequirementService requirementService;

    @PostMapping
    public ResponseEntity<Requirement> create(
            @Valid String message
    ) {
        UserDetails user = UserDetails.of(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        Requirement requirement = requirementService.create(user.getId(), message);

        return ResponseEntity.ok().body(requirement);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(
            @Valid Long requirementId
    ) {
        UserDetails user = UserDetails.of(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        requirementService.delete(user.getId(), requirementId);

        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(
            @Valid String message,
            @Valid Long id
    ) {
        UserDetails user = UserDetails.of(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        requirementService.update(user.getId(), id, message);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public String home(Model model,
            Authentication authentication) {

        UserDetails user = UserDetails.of(authentication.getPrincipal());

        List<Requirement> requirement = requirementService.select(user.getId());
        List<RequirementResponse> response = RequirementResponse.of(requirement);

        model.addAttribute("requirementList", response);
        return "/requirement";
    }


}
