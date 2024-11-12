package hello.order.v2;

import hello.order.OrderService;
import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfigV2 {

    @Bean
    public OrderService orderService() {
        return new OrderServiceV2();
    }

    /*
    CountedAspect
    들어가면 @Aspect 어노테이션 붙어있고,
    @Around("@annotation(counted)")로 설정된 interceptAndRecord 메서드 있음.
    @Counted가 적용된 메서드를 호출할 때마다 그 호출을 가로챔.
    CountedAspect는 @Counted 애노테이션이 붙은 메서드를 감지하고,
    메서드 실행 전후로 메트릭 수집 로직을 적용하여 성공/실패 여부에 따라 카운터를 기록.
    이 과정을 통해, 비즈니스 로직과 메트릭 수집 로직이 분리되면서도 AOP를 통해 자동으로 메트릭이 수집된다.
     */
    @Bean
    public CountedAspect countedAspect(MeterRegistry registry) {
        return new CountedAspect(registry);
    }
}
