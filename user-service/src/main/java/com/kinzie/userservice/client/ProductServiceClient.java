package com.kinzie.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "product-service")
public interface ProductServiceClient {
}
