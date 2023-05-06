package com.api.voterz.utilities;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Paginate<T> {
    private Long totalElements;
    private Long totalPages;
    private Long pageNumber;
    private Long pageSize;
    private List<T> content;
}
