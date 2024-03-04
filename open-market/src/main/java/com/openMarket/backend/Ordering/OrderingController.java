package com.openMarket.backend.Ordering;


import com.openMarket.backend.JWT.JwtService;
import com.openMarket.backend.User.User;
import com.openMarket.backend.User.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordering")
@RequiredArgsConstructor
@Slf4j
public class OrderingController {
    private final OrderingService orderingService;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @PostMapping("/create") // 결제 전 정보 기입.
    public void create(HttpServletRequest request, @RequestParam OrderingInfoDTO infoDTO) {
        User user = userRepository.findByEmail(request.getUserPrincipal().getName()).orElse(null);
        log.info("user : {}", user.getName());
        orderingService.create(
                user,
                infoDTO.getReceiverName(),
                infoDTO.getReceiverPhone(),
                infoDTO.getReceiverAddress(),
                infoDTO.getMessage()
        );

    }

    @GetMapping("/list/{username}")
    public ResponseEntity<List<Ordering>> getListByUser(HttpServletRequest request, @PathVariable String username){
        User user = userRepository.findByName(username).orElse(null);

        return ResponseEntity.ok(orderingService.getOrderingByUser(user));

    }

}
