package vn.ehealth.emr.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListResultDTO<T> {
    private List<T> listData;
    private Long totalRow = 0L;
    private Integer totalPage = 0;
}