package com.dshard.freespace.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestFormComment {
    private String blogId;
    private String commentMessage;
}
