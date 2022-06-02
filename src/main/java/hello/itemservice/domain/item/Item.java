package hello.itemservice.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// DTO에서는 @Data를 써도 좋은데 확인하면서 쓰면 됨
@Data // @Data는 예측하지 못하게 위험하게 동작할 수 있으므로 핵심 도메인에서는 @Getter, @Setter 이용하는 것이 좋음
// but 지금은 예제이므로 편하게 @Data 사용..
public class Item {
    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
