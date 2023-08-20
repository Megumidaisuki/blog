package com.megumi.request;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPageRequest {
    private Integer pageSize = 10;
    private Integer pageNum = 1;
    //到时候判定，如果categoryId为null则查询所有种类
    private Long categoryId = null;
}
