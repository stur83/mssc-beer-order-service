package guru.sfg.beer.order.service.services.listeners;

import guru.sfg.beer.order.service.config.JmsConfig;
import guru.sfg.beer.order.service.services.BeerOrderManager;
import guru.sfg.brewery.model.events.DeliveryOrderResult;
import guru.sfg.brewery.model.events.ValidateOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by jt on 12/2/19.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class DeliveryResultListener {

    private final BeerOrderManager beerOrderManager;

    @JmsListener(destination = JmsConfig.ORDER_DELIVERY_RESULT)
    public void listen(DeliveryOrderResult result){
        final UUID beerOrderId = result.getBeerOrderId();

        log.debug("Delivery Result for Order Id: " + beerOrderId);

        beerOrderManager.processDeliveryResult(beerOrderId, result.getIsDelivered());
    }
}
