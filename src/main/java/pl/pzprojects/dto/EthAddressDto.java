package pl.pzprojects.dto;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class EthAddressDto {
    @NonNull
    private final String address;

    private List<String> miners;
    private List<Double> payouts;
    private Double coinsPerMin;
    private Double usdPerMin;
}
