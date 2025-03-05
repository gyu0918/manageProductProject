import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;

public class productDAO {
    private String url;
    private String user;
    private String password;
    private Connection conn;

    public productDAO() throws  SQLException {
        url = "jdbc:mariadb://localhost:3306/product";
        user = "root";
        password = "1234";
        conn = DriverManager.getConnection(url, user, password);
    }

    public void insert(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String query = "insert into product values(0,?,?,?,?,?,?,now())";
        try {
            PreparedStatement cursor = conn.prepareStatement(query);

            System.out.println("제품명, 제품설명, 수량, 단가, 제품타입 (a,b,c,d 중하나), 담당자명 을 입력하세요.");
            //sql 파라미터 설정
            cursor.setString(1,br.readLine());
            cursor.setString(2,br.readLine());
            cursor.setInt(3,Integer.parseInt(br.readLine()));
            cursor.setString(4,br.readLine());
            cursor.setString(5,br.readLine());
            cursor.setString(6,br.readLine());

            //sql 실행
            cursor.executeUpdate();

        }catch (SQLException | IOException e){
            e.printStackTrace();
        }

    }
    public List<productDTO>  selectAll(){
        String query = "select product.productName, product.productDescription,product.productQuantity, product.productPrice " +
                ",pt.productTypeName, product.productManager, product.registrationDate " +
                "from product, productType pt " +
                "where product.productCategory=pt.productType";
        List<productDTO> productDTOList = new ArrayList<>();

        try {
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                productDTO productDTO = new productDTO();
                productDTO.setProductName(rs.getString("productName"));
                productDTO.setProductDescription(rs.getString("productDescription"));
                productDTO.setProductQuantity(rs.getInt("productQuantity"));
                productDTO.setProductPrice(rs.getString("productPrice"));
                productDTO.setProductCategory(rs.getString("productTypeName"));
                productDTO.setProductManager(rs.getString("productManager"));
                productDTO.setRegistrationDate(rs.getString("registrationDate"));
                //리스트에 추가
                productDTOList.add(productDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return productDTOList;
    }
}
