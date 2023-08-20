package com.megumi.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailsDTO {
    private String categoryName;
    private Date createTime;
    private Long id;
    private Long categoryId;
    private String content;
    private String summary;
    private String thumbnail;
    private String title;
    private Long viewCount;
}
