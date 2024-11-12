package hello.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class TrafficController {

    /*
    CPU 사용량 초과
    여러 창에 localhost:8080/cpu로 요청
     */
    @GetMapping("cpu")
    public String cpu() {
        log.info("cpu");
        long value = 0;
        for (long i = 0; i < 10000000000L; i++) {
            value++;
        }
        return "ok value = " + value;
    }

    /*
    JVM 메모리 사용량 초과
     */
    private List<String> list = new ArrayList<>();

    @GetMapping("jvm")
    public String jvm() {
        log.info("jvm");
        for (int i = 0; i < 1000000; i++) {
            list.add("hello jvm!" + i);
        }
        return "ok jvm";
    }


    /*
     커넥션 풀 고갈
     */
    @Autowired
    DataSource dataSource;

    @GetMapping("/jdbc")
    public String jdbc() throws SQLException {
        log.info("jdbc");
        Connection conn = dataSource.getConnection();
        log.info("connection info = {}", conn);
        // 커넥션 반환 안함
        return "ok";
    }

    /*
     에러 로그 급증
     */
    @GetMapping("error-log")
    public String errorLog() {
        log.error("error log");
        return "error";
    }
}
