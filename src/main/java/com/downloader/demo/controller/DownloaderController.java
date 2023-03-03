package com.downloader.demo.controller;

import com.downloader.demo.service.MyApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequiredArgsConstructor
public class DownloaderController {

    private final MyApiClient myApiClient;
    private static final AtomicInteger counter = new AtomicInteger();
    @GetMapping("/api/data")
    public int fetchData() {
        int number=counter.getAndIncrement();
        myApiClient.getData(number);
        return number;
    }
}
