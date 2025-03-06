import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.*;

public class ProductAdmin {
    private ProductDAO productDAO;

    public ProductAdmin() throws IOException, SQLException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        productDAO = new ProductDAO();
        boolean endFlag = true;
        while (endFlag) {
            System.out.println("1. 제품등록 2. 전체 보는 기능 3. 제품 수정 4. 제품 삭제 5. 종료");
            int choice = Integer.parseInt(br.readLine());
            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    System.out.println("1. 전체 보는 기능 2. 제품 검색");
                    int choiceNum = Integer.parseInt(br.readLine());
                    switch (choiceNum) {
                        case 1:
                            prtAllProduct();
                            break;
                        case 2:
                            searchProduct();
                            break;
                    }
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    deleteProduct();
                    break;
                case 5:
                    endFlag = false;
                    break;
            }
        }
    }

    public void addProduct() {
        productDAO.insert();
    }

    public void prtAllProduct() {
        List<ProductDTO> ptr = productDAO.selectAll();
        ptr.stream().forEach(ProductDTO ->
                System.out.println("제품명 = " + ProductDTO.getProductName()
                        +" ||"+ " 제품설명 = " + ProductDTO.getProductDescription()
                        +" ||"+ " 수량 = " + ProductDTO.getProductQuantity()
                        +" ||"+ " 단가 = " + ProductDTO.getProductPrice()
                        +" ||"+ " 타입 = " + ProductDTO.getProductCategory()
                        +" ||"+ " 담당자명 = " + ProductDTO.getProductManager()
                        +" ||"+ " 등록일자 = " + ProductDTO.getRegistrationDate()));
    }

    public void searchProduct() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        List<ProductDTO> ptr = productDAO.selectAll();
        System.out.println(" 검색어를 입력하세요.");
        String word = br.readLine();

        for (ProductDTO productDTO : ptr) {
            if (productDTO.getProductName().contains(word) || productDTO.getProductDescription().contains(word)) {
                System.out.println("제품명 = " + productDTO.getProductName()
                        +" ||"+ " 제품설명 = " + productDTO.getProductDescription()
                        +" ||"+ " 수량 = " + productDTO.getProductQuantity()
                        +" ||"+ " 단가 = " + productDTO.getProductPrice()
                        +" ||"+ " 타입 = " + productDTO.getProductCategory()
                        +" ||"+ " 담당자명 = " + productDTO.getProductManager()
                        +" ||"+ " 등록일자 = " + productDTO.getRegistrationDate());
            }
        }
    }


    public void updateProduct() throws IOException, SQLException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        //수정하기전에 정보 보기 위해서 프린트 해준다.
        prtAllProduct();


        System.out.println("수정할 제품명을 선택하세요");
        String updateProductName = br.readLine();
        // updtate 할 제품찾기
        List<ProductDTO> ptr = productDAO.selectAll();

        for (ProductDTO productDTO : ptr) {
            if (productDTO.getProductName().contains(updateProductName)) {
                System.out.println("수정할 속성을 선택하세요.");
                System.out.println("1. 제품설명 2. 제품수량(숫자) 3. 단가 4. 종류(a,b,c,d 중하나) 5. 담당자");
                int choice = Integer.parseInt(br.readLine());
                productDAO.update(choice, productDTO.getProductName());
                return ;
            }
        }
        System.out.println("일치하는 제품명이 없습니다 처음부터 다시 하세요.");
    }

    public void deleteProduct() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //삭제하기전에 정보 보기 위해서 프린트 해준다.
        prtAllProduct();
        System.out.println("삭제할 제품명을 입력하세요.");
        String deleteProductName = br.readLine();
        productDAO.delete(deleteProductName);

    }

}
