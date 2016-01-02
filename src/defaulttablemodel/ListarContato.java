package defaulttablemodel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * @author Rosicléia Frasson
 */
public class ListarContato extends JFrame {

    private JPanel painelFundo;
    private JPanel painelBotoes;
    private JTable tabela;
    private JScrollPane barraRolagem;
    private JButton btInserir;
    private JButton btExcluir;
    private JButton btEditar;
    private DefaultTableModel modelo = new DefaultTableModel();

    public ListarContato() {
        super("Contatos");
        criaJTable();
        criaJanela();
    }

    public void criaJanela() {
        btInserir = new JButton("Inserir");
        btExcluir = new JButton("Excluir");
        btEditar = new JButton("Editar");
        painelBotoes = new JPanel();
        barraRolagem = new JScrollPane(tabela);
        painelFundo = new JPanel();
        painelFundo.setLayout(new BorderLayout());
        painelFundo.add(BorderLayout.CENTER, barraRolagem);
        painelBotoes.add(btInserir);
        painelBotoes.add(btEditar);
        painelBotoes.add(btExcluir);
        painelFundo.add(BorderLayout.SOUTH, painelBotoes);

        getContentPane().add(painelFundo);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 320);
        setVisible(true);
        btInserir.addActionListener(new BtInserirListener());
        btEditar.addActionListener(new BtEditarListener());
        btExcluir.addActionListener(new BtExcluirListener());
    }

    private void criaJTable() {
        tabela = new JTable(modelo);
        modelo.addColumn("Id");
        modelo.addColumn("Nome");
        modelo.addColumn("Telefone");
        modelo.addColumn("Email");
        tabela.getColumnModel().getColumn(0).setPreferredWidth(10);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(120);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(80);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(120);
        pesquisar(modelo);
    }

    public static void pesquisar(DefaultTableModel modelo) {
        modelo.setNumRows(0);
        ContatoDao dao = new ContatoDao();

        for (Contato c : dao.getContatos()) {
            modelo.addRow(new Object[]{c.getId(), c.getNome(), c.getTelefone(), c.getEmail()});
        }
    }

    private class BtInserirListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            InserirContato ic = new InserirContato(modelo);
            ic.setVisible(true);
        }
    }

    private class BtEditarListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            int linhaSelecionada = -1;
            linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada >= 0) {
                int idContato = (int) tabela.getValueAt(linhaSelecionada, 0);
                AtualizarContato ic = new AtualizarContato(modelo, idContato, linhaSelecionada);
                ic.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "É necesário selecionar uma linha.");
            }
        }
    }

    private class BtExcluirListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            int linhaSelecionada = -1;
            linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada >= 0) {
                int idContato = (int) tabela.getValueAt(linhaSelecionada, 0);
                ContatoDao dao = new ContatoDao();
                dao.remover(idContato);
                modelo.removeRow(linhaSelecionada);
            } else {
                JOptionPane.showMessageDialog(null, "É necesário selecionar uma linha.");
            }
        }
    }
    
    public static void main(String[] args) {
        ListarContato lc = new ListarContato();
        lc.setVisible(true);
    }
}

