import metodosContatos.MetodosContatos;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        MetodosContatos metodosContatos = new MetodosContatos();
        Scanner ler = new Scanner(System.in);
        int opcao = 0;
        do{
            System.out.println("Agenda Eletrônica");
            System.out.println("1-Cadastrar Contato");
            System.out.println("2-Editar Contato");
            System.out.println("3-Listar Contato");
            System.out.println("4-Excluir Contato novo");
            System.out.println("5-Sair da agenda");
            opcao = ler.nextInt();

            switch (opcao){
                case 1:
                    metodosContatos.cadastrarContato();
                    break;
                case 2:
                    metodosContatos.editarContato();
                    break;
                case 3:  metodosContatos.listarContatos();
                    break;
                case 4:  metodosContatos.excluirContato();
                    break;
                default:
                    System.out.println("Encerramos");
                    break;
            }
        }while(opcao < 5);
    }
}