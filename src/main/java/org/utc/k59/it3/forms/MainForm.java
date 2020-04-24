package org.utc.k59.it3.forms;

import org.utc.k59.it3.dto.CandidateDTO;
import org.utc.k59.it3.models.Candidate;
import org.utc.k59.it3.models.Province;
import org.utc.k59.it3.utils.ServicesManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class MainForm {
    private JPanel mainPanel;
    private JButton btnInsert;
    private JButton btnDelete;
    private JButton btnCancel;
    private JButton btnEdit;
    private JButton btnOK;
    private JTextField txtIdOutput;
    private JTextField txtName;
    private JTextField txtDate;
    private JTextField txtMath;
    private JTextField txtPhysical;
    private JTextField txtTotal;
    private JTextField txtChemistry;
    private JTable table;
    private JTextField txt_ID_Input;
    private JButton btnFilter;
    private JRadioButton RB_male;
    private JRadioButton RB_Famale;
    private JComboBox cmbBirthPlace;
    private JComboBox cmbBirthPlaceOutput;

    public MainForm() {

        JOptionPane jOptionPane= new JOptionPane();
        ButtonGroup G = new ButtonGroup();
        G.add(RB_Famale);
        G.add(RB_male);

        DefaultTableModel defaultTableModel = new DefaultTableModel();
        table.setModel(defaultTableModel);
        defaultTableModel.addColumn("UID ");
        defaultTableModel.addColumn("NAME");
        defaultTableModel.addColumn("PROVINCE");
        defaultTableModel.addColumn("BIRTH DATE");
        defaultTableModel.addColumn("GENDER");
        defaultTableModel.addColumn("MATH");
        defaultTableModel.addColumn("PHYSICS");
        defaultTableModel.addColumn("CHEMISTRY");
        defaultTableModel.addColumn("TOTAL");

        List<CandidateDTO> candidateDTOList = ServicesManager.candidateRepository.getListCandidates();

        candidateDTOList.forEach(c -> {
            defaultTableModel
                    .addRow(new Object[] {
                            c.getId(), c.getName(), c.getProvinceName(), c.getBirthDate(), c.getGender(),
                            c.getMathMark(), c.getPhysicsMark(), c.getChemistryMark(),(double) Math.round(c.getTotalMark() * 10) / 10
                    });


            ListSelectionModel listSelectionModel= table.getSelectionModel();

            listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            listSelectionModel.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    int[]  rows = table.getSelectedRows();
                    int[]  cols = table.getSelectedColumns();
                    Integer code =Integer.valueOf((Integer) table.getValueAt(rows[0],0));
                    CandidateDTO candidate = ServicesManager.candidateRepository.getCandidate(code);
                    txtIdOutput.setText(String.valueOf(candidate.getId()));
                    txtChemistry.setText(String.valueOf(candidate.getChemistryMark()));
                    txtPhysical.setText(String.valueOf(candidate.getPhysicsMark()));
                    txtMath.setText(String.valueOf(candidate.getMathMark()));

                    Double  C= Double.valueOf(candidate.getChemistryMark());
                    Double  P = Double.valueOf(candidate.getPhysicsMark());
                    Double  M = Double.valueOf(candidate.getMathMark());
                    Double  total1= C+M+P;
                    txtTotal.setText(String.valueOf((double) Math.round(total1 * 10) / 10));

                    txtName.setText(String.valueOf(candidate.getName()));
                    txtDate.setText(String.valueOf(candidate.getBirthDate()));

                    if (candidate.getGender().equals("F")){
                        RB_Famale.setSelected(true);
                    }
                    else RB_male.setSelected(true);

                    cmbBirthPlaceOutput.setSelectedItem(candidate.getProvinceName());


                }
            });

        });

        List<Province> provinceList = ServicesManager.provinceRepository.findAll();

        DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        cmbBirthPlace.setModel(defaultComboBoxModel1);
        cmbBirthPlaceOutput.setModel(defaultComboBoxModel2);
        provinceList.stream().map(p -> p.getName()).forEach(ps -> {
            defaultComboBoxModel1.addElement(ps);
            defaultComboBoxModel2.addElement(ps);
        });


        btnInsert.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        boolean flag= true;
        btnFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Integer idinput= Integer.valueOf(txt_ID_Input.getText());
                    String birthplace = String.valueOf(cmbBirthPlace.getSelectedItem());
                    CandidateDTO candidate = ServicesManager.candidateRepository.getCandidate(idinput);

                    if (candidate == null) {
                       JOptionPane.showMessageDialog(null, "ID bạn vừa nhập không tồn tại "+idinput);
                        return;
                    }
                    txtIdOutput.setText(String.valueOf(candidate.getId()));
                    txtChemistry.setText(String.valueOf(candidate.getChemistryMark()));
                    txtPhysical.setText(String.valueOf(candidate.getPhysicsMark()));
                    txtMath.setText(String.valueOf(candidate.getMathMark()));

                    Double  C= Double.valueOf(candidate.getChemistryMark());
                    Double  P = Double.valueOf(candidate.getPhysicsMark());
                    Double  M = Double.valueOf(candidate.getMathMark());
                    Double  total1= C+M+P;
                    txtTotal.setText(String.valueOf((double) Math.round(total1 * 10) / 10));

                    txtName.setText(String.valueOf(candidate.getName()));
                    txtDate.setText(String.valueOf(candidate.getBirthDate()));
                    if (candidate.getGender().equals("F")){
                        RB_Famale.setSelected(true);
                    }
                    else RB_male.setSelected(true);

                    cmbBirthPlaceOutput.setSelectedItem(candidate.getProvinceName());

                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                    JOptionPane.showMessageDialog(null," Mời bạn nhập ID với giá trị [10000,10100]");
                }



            }
        });
    }

    public static void main(String[] args) {
        JFrame frame= new JFrame("Demo");
        frame.setContentPane(new MainForm().mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1200,768);
        frame.setVisible(true);
    }
}
