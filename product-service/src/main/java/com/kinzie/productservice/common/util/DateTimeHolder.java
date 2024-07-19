package com.kinzie.productservice.common.util;

import com.kinzie.productservice.common.BaseEntity;

import java.time.LocalDateTime;

public interface DateTimeHolder {

    LocalDateTime getTimeNow();

    LocalDateTime getCreateTime(BaseEntity entity);

    LocalDateTime getUpdateTime(BaseEntity entity);

    LocalDateTime getDeleteTime(BaseEntity entity);
}
