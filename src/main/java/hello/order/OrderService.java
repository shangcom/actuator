package hello.order;

import java.util.concurrent.atomic.AtomicInteger;

public interface OrderService {

    void order();

    void cancel();

    /*
    AtomicInteger :
    일반적인 int와 달리, 스레드 안전한 연산을 위해 AtomicInteger 클래스가 제공하는 메서드를 사용.
    AtomicInteger를 사용할 때는 직접 접근하지 말고, AtomicInteger가 제공하는 메서드를 통해서만 값을 수정해야
    원자성이 유지된다.
    CAS(Compare-And-Swap) 적용.
    각 스레드는 독립적으로 값을 변경하려고 시도하며, 만약 변경 도중 다른 스레드가 값을 바꾸었다면,
    자신의 값을 재시도하는 방식으로 동작.
    따라서 특정 스레드의 완료 시점을 기다리지 않고 비동기적으로 병렬 실행이 가능.
     */
    AtomicInteger getStock();
}
