package hello.order.v1;

import hello.order.OrderService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class OrderServiceV1 implements OrderService {

    private final MeterRegistry registry;
    private AtomicInteger stock = new AtomicInteger(100);

    public OrderServiceV1(MeterRegistry registry) {
        this.registry = registry;
    }

    /*
    Count 객체 매 요청마다 새로 생성하고 있음.
    그러나,  Counter.builder()로 생성된 카운터는 MeterRegistry에
    동일한 이름과 태그 조합으로 이미 존재할 경우 기존의 카운터를 반환.
    즉, 같은 이름과 태그를 가진 Counter를 여러 번 생성해도 중복으로 생성되지 않고(중복 방지)
    기존의 Counter를 참조하게 되어, 값을 계속 누적.

    http://localhost:8080/actuator/metrics/my.order
    여기에 order, cancel 메서드 모두 등록되어 있다.
    메서드별 횟수 확인하려면
    http://localhost:8080/actuator/metrics/my.order?tag=method:order
    http://localhost:8080/actuator/metrics/my.order?tag=method:cancel
     */
    @Override
    public void order() {
        log.info("주문");
        stock.decrementAndGet();

        Counter.builder("my.order")
                .tag("class", this.getClass().getName())
                .tag("method", "order")
                .description("order")
                .register(registry).increment();
    }

    @Override
    public void cancel() {
        log.info("취소");
        stock.incrementAndGet();

        Counter.builder("my.order")


                .tag("class", this.getClass().getName())
                .tag("method", "cancel")
                .description("order")
                .register(registry).increment();
    }

    @Override
    public AtomicInteger getStock() {
        return stock;
    }
}
