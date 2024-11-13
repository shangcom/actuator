package hello.order.gauge;

import hello.order.OrderService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * .application에서 Import에 추가로 등록해야함.
 */
@Configuration
public class StockConfigV1 {

    @Bean
    public MyStockMetric myStockMetric(OrderService orderService, MeterRegistry registry) {
        return new MyStockMetric(orderService, registry);
    }

    @Slf4j
    static class MyStockMetric {
        private OrderService orderService;
        private MeterRegistry registry;

        public MyStockMetric(OrderService orderService, MeterRegistry registry) {
            this.orderService = orderService;
            this.registry = registry;

        }

        /**
         * "my.stock"라는 이름의 게이지 메트릭을 생성하고 등록하는 메서드.
         * Gauge.builder(메트릭 이름, 게이지가 참조할 대상 객체, 대상 객체의 값을 반환하는 람다식)
         * 이 Gauge는 람다식에서 반환된 값을 실시간으로 저장하고 참조함.
         * 현재는 OrderService 구현체에 있는 AtomicInteger를 통해 메모리에서 재고를 관리하므로
         * orderService를 대상 객체로 지정함.
         * 만약 Repository가 따로 있었다면, 그 Repository 객체를 대상 객체로 지정해야 한다.
         */
        @PostConstruct
        public void init() {
            Gauge.builder(
                    "my.stock",
                    orderService,
                    service -> { // service는 대상 객체인 orderService를 뜻함. 헷갈리니까 살짝 바꾼것.
                        log.info("stock gauge call");
                        int stock = service.getStock().get();
                        return stock;
                    }).register(registry);
        }
    }
}
