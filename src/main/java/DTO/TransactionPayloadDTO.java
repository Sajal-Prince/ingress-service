package DTO;

public class TransactionPayloadDTO{
    private Long userId;
    private Long merchantId;
    private Double amount;
    private String currency;

    public TransactionPayloadDTO(Long userId, Long merchantId, Double amount, String currency){
        this.userId = userId;
        this.merchantId = merchantId;
        this.amount = amount;
        this.currency = currency;
    }

}