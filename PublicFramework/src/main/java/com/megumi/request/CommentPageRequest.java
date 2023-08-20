package com.megumi.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentPageRequest {
    private Long articleId;
    private Integer pageNum;
    private Integer pageSize;
}
