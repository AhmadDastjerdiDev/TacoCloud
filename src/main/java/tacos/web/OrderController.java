package tacos.web;

import jakarta.validation.Valid;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.support.SessionStatus;
import tacos.TacoOrder;
import tacos.User;
import tacos.data.OrderRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
@ConfigurationProperties(prefix = "taco.orders")
public class OrderController {

    private OrderProps props;

    private OrderRepository orderRepo;

    public OrderController(OrderRepository orderRepo, OrderProps props){
        this.orderRepo = orderRepo;
        this.props = props;
    }

    public OrderController(OrderRepository orderRepo){
        this.orderRepo = orderRepo;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @GetMapping
    public String orderForUser(
            @AuthenticationPrincipal User user, Model model){

        Pageable pageable = PageRequest.of(0, props.getPageSize());
        model.addAttribute("orders", orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));
        return "orderList";
    }


    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionstatus){
        if(errors.hasErrors()){
            return "orderForm";
        }
        orderRepo.save(order);
        log.info("Order submitted: {}", order);
        sessionstatus.setComplete();
        return "redirect:/";
    }
}
