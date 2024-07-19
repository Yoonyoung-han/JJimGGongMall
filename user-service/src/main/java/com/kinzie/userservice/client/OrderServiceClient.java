package com.kinzie.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "order-service")
public interface OrderServiceClient {
}
