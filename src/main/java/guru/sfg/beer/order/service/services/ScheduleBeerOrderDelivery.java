package guru.sfg.beer.order.service.services;

import guru.sfg.beer.order.service.bootstrap.BeerOrderBootStrap;
import guru.sfg.beer.order.service.domain.BeerOrder;
import guru.sfg.beer.order.service.domain.BeerOrderStatusEnum;
import guru.sfg.beer.order.service.domain.Customer;
import guru.sfg.beer.order.service.repositories.BeerOrderRepository;
import guru.sfg.beer.order.service.repositories.CustomerRepository;
import guru.sfg.brewery.model.BeerOrderDto;
import guru.sfg.brewery.model.BeerOrderLineDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class ScheduleBeerOrderDelivery {

    private final BeerOrderManager beerOrderManager;
    private BeerOrderRepository beerOrderRepository;

    @Scheduled(fixedRate = 2000) //run every 2 seconds
    public void doPlaceOrder() {
        List<BeerOrder> allocatedBeerOrders = beerOrderRepository.findAllByOrderStatus(BeerOrderStatusEnum.ALLOCATED);

        allocatedBeerOrders.stream().forEach(beerOrder -> {
            beerOrderManager.scheduleOrderDelivery(beerOrder.getId());
        });

    }
}
