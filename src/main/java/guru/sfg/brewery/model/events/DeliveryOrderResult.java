package guru.sfg.brewery.model.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Created by jt on 11/30/19.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryOrderResult {
    private UUID beerOrderId;
    private Boolean isDelivered;
}
