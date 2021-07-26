package pl.pzprojects.dto;

import lombok.Data;

@Data
public class CurrentStatsModuleDto {
    private Long time;
    private Long lastSeen;
    private Double reportedHashrate;
    private Double currentHashrate;
    private Integer validShares;
    private Integer invalidShares;
    private Integer staleShares;
    private Double averageHashrate;
    private Integer activeWorkers;
    private Double unpaid;
    private Double unconfirmed;
    private Double coinsPerMin;
    private Double usdPerMin;
    private Double btcPerMin;
}
