package web.app.caravanamedieval.controller;

import org.springframework.web.bind.annotation.*;
import web.app.caravanamedieval.dto.InviteCodeDTO;
import web.app.caravanamedieval.service.InviteCodeService;

@RestController
@RequestMapping("/invite-code")
public class InviteCodeController {

    private final InviteCodeService inviteCodeService;

    public InviteCodeController(InviteCodeService inviteCodeService) {
        this.inviteCodeService = inviteCodeService;
    }

    @PostMapping("/generate/{gameId}")
    public InviteCodeDTO generateOrReuse(@PathVariable Long gameId) {
        return inviteCodeService.generateOrReuseCode(gameId);
    }

    @GetMapping("/validate/{code}")
    public Integer validateCode(@PathVariable String code) {
        return inviteCodeService.validateCode(code)
                .orElseThrow(() -> new RuntimeException("Invalid or expired invite code"));
    }
}
