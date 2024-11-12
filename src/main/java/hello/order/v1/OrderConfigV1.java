package hello.order.v1;

import hello.order.OrderService;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfigV1 {

    /*
    MeterRegistry는 Spring Boot Actuator 라이브러리에 포함됨.
    자동으로 빈으로 등록되어 있음.
    아래 메서드에서 스프링이 자동으로 MeterRegistry 객체 주입한다.
     */
    @Bean
    public OrderService orderService(MeterRegistry registry) {
        return new OrderServiceV1(registry);
    }
}
