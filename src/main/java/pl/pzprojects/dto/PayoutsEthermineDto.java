package pl.pzprojects.dto;

import lombok.Data;

import java.util.List;

@Data
public class PayoutsEthermineDto {
    private String status;
    private List<PayoutsEthermineDataDto> data;
}
