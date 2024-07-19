package com.kinzie.productservice.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "order-service")
public interface OrderServiceClient {
}
