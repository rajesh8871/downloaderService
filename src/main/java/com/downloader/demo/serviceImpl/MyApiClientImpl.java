package com.downloader.demo.serviceImpl;

import com.downloader.demo.service.MyApiClient;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class MyApiClientImpl implements MyApiClient {
    private final WebClient webClient;
    public MyApiClientImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().followRedirect(true)
                )).build();
    }

    @Override
    @Async("threadPoolTaskExecutor")
    public void getData(int requestNumber) {
        System.out.println("Available processor: "+Runtime.getRuntime().availableProcessors());
        System.out.println("Execute method with configured executor - "
                + Thread.currentThread().getName());
        Flux<DataBuffer> dataBufferFlux = webClient.method(HttpMethod.GET)
                .uri("http://api.apis.guru/v2/list.json")
                .retrieve()
                .bodyToFlux(DataBuffer.class);

        Path path = Paths.get("/Volumes/Rajesh/intelliJ/test"+requestNumber+".txt");
        DataBufferUtils.write(dataBufferFlux, path)
                .block();
    }
}
