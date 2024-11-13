package hello.order.gauge;

import hello.order.OrderService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.MeterBinder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class StockConfigV2 {

    /*
     MeterBinder를 빈으로 등록해두면, Spring Actuator가 MeterRegistry에
     자동으로 bindTo를 호출하여 메트릭을 등록.
     MeterBinder : bindTo() 추상 메서드 하나만 있는 함수형 인터페이스.
     익명함수로 MeterBinder 인터페이스 구현체 만들어 반환하고 빈으로 등록.
     void bindTo(MeterRegistry registry);
     MeterRegistry를 인자로 받아, 여러 개의 메트릭을 MeterRegistry에 일관되게 등록.
     */
    @Bean
    public MeterBinder stockSize(OrderService orderService) {
        return registry -> Gauge.builder("my.stock",
                orderService,
                service -> {
                    log.info("stock gauge call");
                    return service.getStock().get();
                }).register(registry);
    }

}
