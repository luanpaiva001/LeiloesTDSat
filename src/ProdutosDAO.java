import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;

    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    // CADASTRAR PRODUTO
    public void cadastrarProduto(ProdutosDTO produto) {

        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

        conn = new conectaDAO().connectDB();

        try {

            prep = conn.prepareStatement(sql);

            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());

            prep.executeUpdate();

            JOptionPane.showMessageDialog(
                    null,
                    "Produto cadastrado com sucesso!"
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Erro ao cadastrar produto:\n" + e.getMessage()
            );

        } finally {

            try {
                if (prep != null) {
                    prep.close();
                }

                if (conn != null) {
                    conn.close();
                }

            } catch (Exception e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    // LISTAR PRODUTOS
    public ArrayList<ProdutosDTO> listarProdutos() {

        listagem.clear();

        String sql = "SELECT * FROM produtos";

        conn = new conectaDAO().connectDB();

        try {

            prep = conn.prepareStatement(sql);

            resultset = prep.executeQuery();

            while (resultset.next()) {

                ProdutosDTO produto = new ProdutosDTO();

                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                listagem.add(produto);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Erro ao listar produtos:\n" + e.getMessage()
            );

        } finally {

            try {

                if (resultset != null) {
                    resultset.close();
                }

                if (prep != null) {
                    prep.close();
                }

                if (conn != null) {
                    conn.close();
                }

            } catch (Exception e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }

        return listagem;
    }
}