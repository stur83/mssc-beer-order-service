package guru.sfg.brewery.model.events;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Created by jt on 11/30/19.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryOrderRequest {

    private UUID beerOrderId;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ", shape=JsonFormat.Shape.STRING)
    @JsonProperty("deliveryStartDate")
    private OffsetDateTime deliveryStartDate = null;
}
