package pl.pzprojects.dto;

import lombok.Data;

@Data
public class PayoutsEthermineDataDto {
    private Double start;
    private Double end;
    private Double amount;
    private String txHash;
    private Long paidOn;

    public static PayoutsEthermineDataDto of(CurrentStatsModuleDto currentStatsModuleDto)
    {
        PayoutsEthermineDataDto dto = new PayoutsEthermineDataDto();
        dto.setStart(0.0);
        dto.setEnd(0.0);
        dto.setAmount(currentStatsModuleDto.getUnpaid());
        dto.setTxHash("none");
        dto.setPaidOn(0L);
        return dto;
    }

}
