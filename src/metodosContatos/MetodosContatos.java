package metodosContatos;

import java.sql.*;
import java.util.Scanner;
import modelo.Contatos;
import dao.BaseConnection;

public class MetodosContatos {
    Scanner ler = new Scanner(System.in);

    public void cadastrarContato() {
        Contatos contato = new Contatos();

        System.out.println("Informe o nome do contato:");
        String nome = ler.nextLine();
        if(nome.length() > 3 & !nome.isEmpty()){
            contato.setNome(nome);
        }else{
            System.out.println("Nome inválido, preencha corretamente.");
            return;
        }

        System.out.println("Informe o Telefone do contato:");
        String telefone = ler.nextLine();
        if(!telefone.isEmpty()){
            System.out.println("Telefone inválido");
            return;
        }

        System.out.println("Informe o email do contato:");
        String email = ler.nextLine();
        if (!emailValido(email)) {
            System.out.println("Email inválido. Preencha novamente.");
            return;
        }
        contato.setEmail(email);

        try (Connection connection = BaseConnection.getConnection()) {
            String sql = "INSERT INTO contatos (nome, telefone, email) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, contato.getNome());
                stmt.setString(2, contato.getTelefone());
                stmt.setString(3, contato.getEmail());
                stmt.executeUpdate();
                System.out.println("Usuário Cadastrado com Sucesso!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean telefoneValido(String telefone){
        return telefone.length() > 0 & telefone.length() < 12
                && telefone.length() == 11 & telefone.isEmpty();
    }

    private boolean emailValido(String email) {
        return email.contains("@") && email.contains(".");
    }

    public void listarContatos() {
        try (Connection connection = BaseConnection.getConnection()) {
            String sql = "SELECT * FROM contatos";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Nome: " + rs.getString("nome"));
                    System.out.println("Telefone: " + rs.getString("telefone"));
                    System.out.println("Email: " + rs.getString("email"));
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluirContato() {
        System.out.println("Informe o nome do contato:");
        String nome = ler.nextLine();

        try (Connection connection = BaseConnection.getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM contatos WHERE nome = ?")) {
                stmt.setString(1, nome);
                int exitCode = stmt.executeUpdate();
                if (exitCode > 0) {
                    System.out.println("Contato excluído com sucesso!");
                } else {
                    System.out.println("Contato não encontrado.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void editarContato() {
        System.out.println("Informe o telefone do contato que deseja editar:");
        String telefone = ler.nextLine();

        if (telefone.length() < 14) {
            try (Connection connection = BaseConnection.getConnection()) {
                try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM contatos WHERE telefone = ?")) {
                    stmt.setString(1, telefone);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        Contatos contato = new Contatos();
                        contato.setId(rs.getInt("id"));
                        contato.setNome(rs.getString("nome"));
                        contato.setTelefone(rs.getString("telefone"));
                        contato.setEmail(rs.getString("email"));

                        System.out.println("Dados do contato:");
                        System.out.println("Nome: " + contato.getNome());
                        System.out.println("Telefone: " + contato.getTelefone());
                        System.out.println("Email: " + contato.getEmail());

                        System.out.println("Qual campo deseja editar:");
                        System.out.println("1- Nome");
                        System.out.println("2- Telefone");
                        System.out.println("3- Email");
                        System.out.println("4- Sair");
                        int opcao = ler.nextInt();
                        ler.nextLine();

                        switch (opcao) {
                            case 1:
                                System.out.println("Informe o novo nome:");
                                contato.setNome(ler.nextLine());
                                break;
                            case 2:
                                System.out.println("Informe o novo telefone:");
                                String novoTelefone = ler.nextLine();
                                if (novoTelefone.length() < 14) {
                                    contato.setTelefone(novoTelefone);
                                } else {
                                    System.out.println("Telefone inválido. Preencha novamente.");
                                    return;
                                }
                                break;
                            case 3:
                                System.out.println("Informe o novo email:");
                                String novoEmail = ler.nextLine();
                                if (novoEmail.contains("@") && novoEmail.contains(".")) {
                                    contato.setEmail(novoEmail);
                                } else {
                                    System.out.println("Email inválido. Preencha novamente.");
                                    return;
                                }
                                break;
                            default:
                                System.out.println("Redirecionando ao menu principal!");
                                return;
                        }

                        try (PreparedStatement updateStmt = connection.prepareStatement("UPDATE contatos SET nome = ?, telefone = ?, email = ? WHERE id = ?")) {
                            updateStmt.setString(1, contato.getNome());
                            updateStmt.setString(2, contato.getTelefone());
                            updateStmt.setString(3, contato.getEmail());
                            updateStmt.setInt(4, contato.getId());
                            updateStmt.executeUpdate();
                            System.out.println("Usuário editado com sucesso.");
                        }
                    } else {
                        System.out.println("Contato não encontrado para o telefone informado.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Telefone inválido. Preencha novamente.");
        }
    }
}
