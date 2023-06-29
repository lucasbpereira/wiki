package com.br.mestrainfo.wiki.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private String title;
    private String subtitle;
    private String body;
    private String category;
    private String created_at;
    private String updated_at;

}
