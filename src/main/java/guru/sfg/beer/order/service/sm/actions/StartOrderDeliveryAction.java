package guru.sfg.beer.order.service.sm.actions;

import guru.sfg.beer.order.service.config.JmsConfig;
import guru.sfg.beer.order.service.domain.BeerOrder;
import guru.sfg.beer.order.service.domain.BeerOrderEventEnum;
import guru.sfg.beer.order.service.domain.BeerOrderStatusEnum;
import guru.sfg.beer.order.service.repositories.BeerOrderRepository;
import guru.sfg.beer.order.service.services.BeerOrderManagerImpl;
import guru.sfg.beer.order.service.web.mappers.DateMapper;
import guru.sfg.brewery.model.events.DeliveryOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by jt on 12/2/19.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StartOrderDeliveryAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final JmsTemplate jmsTemplate;
    private final BeerOrderRepository beerOrderRepository;
    private final DateMapper dateMapper;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
        String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
        Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(UUID.fromString(beerOrderId));

        beerOrderOptional.ifPresentOrElse(beerOrder -> {
                    jmsTemplate.convertAndSend(JmsConfig.START_ORDER_DELIVERY_QUEUE,
                            DeliveryOrderRequest.builder()
                                    .beerOrderId(beerOrder.getId())
                                    .deliveryStartDate(dateMapper.asOffsetDateTime(beerOrder.getCreatedDate()))
                            .build());
                    log.debug("Sent Delivery Request for order id: " + beerOrderId);
                }, () -> log.error("Beer Order Not Found!"));
    }
}
