import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Delegacao {
    public String[] leValores (String [] dadosIn){
        String [] dadosOut = new String [dadosIn.length];

        for (int i = 0; i < dadosIn.length; i++)
            dadosOut[i] = JOptionPane.showInputDialog  ("Entre com " + dadosIn[i]+ ": ");

        return dadosOut;
    }

    public Corredor leCorredor (){

        String [] valores = new String [2];
        String [] nomeVal = {"Nome", "Numero", "Velocidade"};
        valores = leValores (nomeVal);

        Corredor atlCorr = new Corredor (valores[0],valores[1],valores[2]);
        return atlCorr;
    }

    public Nadador leNadador (){

        String [] valores = new String [2];
        String [] nomeVal = {"Nome", "Numero", "Estilo"};
        valores = leValores (nomeVal);

        Nadador atlNad = new Nadador (valores[0],valores[1],valores[2]);
        return atlNad;
    }

    public Saltador leSaltador (){

        String [] valores = new String [2];
        String [] nomeVal = {"Nome", "Numero", "Altura"};
        valores = leValores (nomeVal);

        Saltador atlSalt = new Saltador (valores[0],valores[1],valores[2]);
        return atlSalt;
    }

    public void mostraAtleta (String dados){
        JOptionPane.showMessageDialog(null,"ATLETA\n-------\n +" +dados);
    }

    public void salvaAtletas (ArrayList<Atleta> atletas){
        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream
                    (new FileOutputStream("AQUI VAI O CAMNHO ATÉ CHEGAR NO ARQUIVO\\pooProjetoFinal\\src\\Delegacao.txt"));
            for (int i=0; i < atletas.size(); i++)
                outputStream.writeObject(atletas.get(i));
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null,"Impossível criar arquivo!");
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {  //Close the ObjectOutputStream
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @SuppressWarnings("finally")
    public ArrayList<Atleta> recuperaAtletas (){
        ArrayList<Atleta> atletas = new ArrayList<Atleta>();

        ObjectInputStream inputStream = null;

        try {
            inputStream = new ObjectInputStream
                    (new FileInputStream("AQUI VAI O CAMNHO ATÉ CHEGAR NO ARQUIVO\\pooProjetoFinal\\src\\Delegacao.txt"));
            Object obj = null;
            while ((obj = inputStream.readObject()) != null) {
                if (obj instanceof Atleta) {
                    atletas.add((Atleta) obj);
                }
            }
        } catch (EOFException ex) { // when EOF is reached
            System.out.println("End of file reached.");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null,"Arquivo com produtos NÃO existe!");
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {  //Close the ObjectInputStream
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (final IOException ex) {
                ex.printStackTrace();
            }
            return atletas;
        }
    }

    public void menuAtletas (){

        ArrayList<Atleta> atletas = new ArrayList<Atleta>();


        String menu = "";
        String entrada;
        int opc1, opc2;

        do {
            menu = "Controle Delegação\n" +
                    "Opções:\n" +
                    "1. Entrar Atletas\n" +
                    "2. Exibir Atletas\n" +
                    "3. Limpar Atletas\n" +
                    "4. Gravar Atletas\n" +
                    "5. Recuperar Atletas\n" +
                    "9. Sair";
            entrada = JOptionPane.showInputDialog (menu + "\n\n");

            while (!numeroInteiroValido(entrada)) {
                entrada = JOptionPane.showInputDialog(null, menu +
                        "\n\nEntrada inválida! Digite um número inteiro.");
            }
            opc1 = new Integer(entrada);

            switch (opc1) {
                case 1:// Entrar dados
                    menu = "Entrada de Atletas\n" +
                            "Opções:\n" +
                            "1. Corredor\n" +
                            "2. Nadador\n" +
                            "3. Saltador\n";

                    entrada = JOptionPane.showInputDialog (menu + "\n\n");
                    while (!numeroInteiroValido(entrada)) {
                        entrada = JOptionPane.showInputDialog(null, menu +
                                "\n\nEntrada inválida! Digite um número inteiro.");
                    }
                    opc2 = new Integer(entrada);

                    switch (opc2){
                        case 1: atletas.add((Atleta) leCorredor());
                            break;
                        case 2: atletas.add((Atleta) leNadador());
                            break;
                        case 3: atletas.add((Atleta) leSaltador());
                            break;
                        default:
                            JOptionPane.showMessageDialog(null,"Atleta para entrada NÃO escolhido!");
                    }

                    break;
                case 2: // Exibir dados
                    if (atletas.size() == 0) {
                        JOptionPane.showMessageDialog(null,"Entre com atletas primeiramente");
                        break;
                    }
                    String dados = "";
                    for (int i=0; i < atletas.size(); i++)	{
                        dados += atletas.get(i).toString() + "\n";
                    }
                    JOptionPane.showMessageDialog(null,dados);
                    break;
                case 3: // Limpar Dados
                    if (atletas.size() == 0) {
                        JOptionPane.showMessageDialog(null,"Entre com atletas primeiramente");
                        break;
                    }
                    atletas.clear();
                    JOptionPane.showMessageDialog(null,"Dados LIMPOS com sucesso!");
                    break;
                case 4: // Grava Dados
                    if (atletas.size() == 0) {
                        JOptionPane.showMessageDialog(null,"Entre com atletas primeiramente");
                        break;
                    }
                    salvaAtletas(atletas);
                    JOptionPane.showMessageDialog(null,"Dados SALVOS com sucesso!");
                    break;
                case 5: // Recupera Dados
                    atletas = recuperaAtletas();
                    if (atletas.size() == 0) {
                        JOptionPane.showMessageDialog(null,"Sem dados para apresentar.");
                        break;
                    }
                    JOptionPane.showMessageDialog(null,"Dados RECUPERADOS com sucesso!");
                    break;
                case 9:
                    JOptionPane.showMessageDialog(null,"Fim do aplicativo DELEGAÇÃO");
                    break;
            }
        } while (opc1 != 9);
    }

    private boolean numeroInteiroValido(String s) {
        boolean resultado;
        try {
            Integer.parseInt(s);
            resultado = true;
        } catch (NumberFormatException e) {
            resultado = false;
        }
        return resultado;
    }

    public static void main (String [] args){
        Delegacao del = new Delegacao ();
        del.menuAtletas();
    }
}