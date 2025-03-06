
import lombok.*;


@Getter
@ToString
@Builder
public class ProductDTO {
    private String productName;
    private String productDescription;
    private int productQuantity;
    private String productPrice;
    private String productCategory;
    private String productManager;
    private String registrationDate;
}
