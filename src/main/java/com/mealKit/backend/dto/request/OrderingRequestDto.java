package com.mealKit.backend.dto.request;



import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderingRequestDto {
    @NotBlank(message = "수령인은 필수 입력입니다.")
    private String receiverName;

    private String receiverPhone;

    @NotBlank(message = "배송지는 필수 입력입니다.")
    private String receiverAddress;

    private String message;

}
