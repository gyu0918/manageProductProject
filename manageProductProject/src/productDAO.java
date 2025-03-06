import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;

public class ProductDAO {
    private String url;
    private String user;
    private String password;
    private Connection conn;

    public ProductDAO() throws SQLException {
        url = "jdbc:mariadb://localhost:3306/product";
        user = "root";
        password = "1234";
        conn = DriverManager.getConnection(url, user, password);
    }

    public void delete(String deleteProductName) {
        String query = "DELETE FROM product WHERE productName = ?";
        try {
            PreparedStatement cursor = conn.prepareStatement(query);

            //sql 파라미터 설정

            cursor.setString(1, deleteProductName);

            //sql 실행
            cursor.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void go(String query, String productName, boolean setInt) throws IOException, SQLException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            PreparedStatement cursor = conn.prepareStatement(query);

            //sql 파라미터 설정
            if (setInt) {
                System.out.println("수정할 숫자를 적으세요.");
                cursor.setInt(1, Integer.parseInt(br.readLine()));
                cursor.setString(2, productName);

            } else {
                System.out.println("수정할 내용을 적으세요.");
                String updateContent = br.readLine();
                cursor.setString(1, updateContent);
                cursor.setString(2, productName);
            }

            //sql 실행
            cursor.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void update(int choiceNum, String productName) throws SQLException, IOException {
        String query;
        switch (choiceNum) {
            case 1:
                query = "UPDATE product SET productDescription = ? WHERE productName = ?";
                go(query, productName, false);
                break;
            case 2:
                query = "UPDATE product SET productQuantity = ? WHERE productName = ?";
                go(query, productName, true);
                break;
            case 3:
                query = "UPDATE product SET productPrice = ? WHERE productName = ?";
                go(query, productName, false);
                break;
            case 4:
                query = "UPDATE product SET productCategory = ? WHERE productName = ?";
                go(query, productName, false);
                break;
            case 5:
                query = "UPDATE product SET productManager = ? WHERE productName = ?";
                go(query, productName, false);
                break;
            default:
                System.out.println("잘못 입력하셨습니다. 처음부터 다시 하세요.");
                return;
        }

        System.out.println("수정할 내용을 입력하세요.");


    }

    public void insert() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String query = "insert into product values(0,?,?,?,?,?,?,now())";
        try {
            PreparedStatement cursor = conn.prepareStatement(query);

            System.out.println("제품명, 제품설명, 수량, 단가, 제품타입 (a,b,c,d 중하나), 담당자명 을 입력하세요.");
            //sql 파라미터 설정
            cursor.setString(1, br.readLine());
            cursor.setString(2, br.readLine());
            cursor.setInt(3, Integer.parseInt(br.readLine()));
            cursor.setString(4, br.readLine());
            cursor.setString(5, br.readLine());
            cursor.setString(6, br.readLine());

            //sql 실행
            cursor.executeUpdate();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }

    public List<ProductDTO> selectAll() {
        String query = "select product.productName, product.productDescription,product.productQuantity, product.productPrice " +
                ",pt.productTypeName, product.productManager, product.registrationDate " +
                "from product, productType pt " +
                "where product.productCategory=pt.productType";
        List<ProductDTO> productDTOList = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                ProductDTO productDTO = ProductDTO.builder()
                        .productName(rs.getString("productName"))
                        .productDescription(rs.getString("productDescription"))
                        .productQuantity(rs.getInt("productQuantity"))
                        .productPrice(rs.getString("productPrice"))
                        .productCategory(rs.getString("productTypeName"))
                        .productManager(rs.getString("productManager"))
                        .registrationDate(rs.getString("registrationDate"))
                        .build();

                productDTOList.add(productDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return productDTOList;
    }
}
