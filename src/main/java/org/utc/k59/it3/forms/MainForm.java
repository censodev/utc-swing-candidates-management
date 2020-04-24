package org.utc.k59.it3.forms;

import org.utc.k59.it3.dto.CandidateDTO;
import org.utc.k59.it3.models.Candidate;
import org.utc.k59.it3.models.Province;
import org.utc.k59.it3.utils.Gender;
import org.utc.k59.it3.utils.ServicesManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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
    private JTextField txtIdInput;
    private JButton btnFilter;
    private JRadioButton rbMale;
    private JRadioButton reFemale;
    private JComboBox cmbBirthPlace;
    private JComboBox cmbBirthPlaceOutput;

    private Integer currentRowInTable = null;

    public MainForm() {
        txtDate.setText("dd/mm/yyyy");

        JOptionPane jOptionPane= new JOptionPane();
        ButtonGroup G = new ButtonGroup();
        G.add(reFemale);
        G.add(rbMale);

        renderTable();

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
                try {
                    String dateString = txtDate.getText();
                    int[] dateArray = Arrays.stream(dateString.split("/")).mapToInt(str -> Integer.valueOf(str)).toArray();
                    LocalDate date = LocalDate.of(dateArray[2], dateArray[1], dateArray[0]);

                    String name = txtName.getText();
                    String provinceName = String.valueOf(cmbBirthPlaceOutput.getSelectedItem());
                    Integer provinceId = ServicesManager.provinceRepository.findByName(provinceName).getId();
                    Double  chemistry = Double.valueOf(txtChemistry.getText());
                    Double  physics = Double.valueOf(txtPhysical.getText());
                    Double  math = Double.valueOf(txtMath.getText());
                    String gender = reFemale.isSelected()? Gender.FEMALE:Gender.MALE;

                    Candidate candidate = new Candidate();
                    candidate.setName(name);
                    candidate.setMathMark(math);
                    candidate.setChemistryMark(chemistry);
                    candidate.setPhysicsMark(physics);
                    candidate.setProvinceId(provinceId);
                    candidate.setGender(gender);
                    candidate.setBirthDate(date);
                    Integer id = (Integer) ServicesManager.candidateRepository.save(candidate);

                    ((DefaultTableModel)table.getModel()).addRow(new Object[] {
                            id, candidate.getName(), provinceName, dateString, gender,
                            candidate.getMathMark(), candidate.getPhysicsMark(), candidate.getChemistryMark(),
                            (double) Math.round((candidate.getMathMark() + candidate.getPhysicsMark() + candidate.getChemistryMark()) * 10) / 10
                    });
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,"Vui lòng nhập laị giá trị ");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,"Vui lòng điền đầy đủ thông tin thí sinh");
                }





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
                    Integer idinput= Integer.valueOf(txtIdInput.getText());

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
                        reFemale.setSelected(true);
                    }
                    else rbMale.setSelected(true);

                    cmbBirthPlaceOutput.setSelectedItem(candidate.getProvinceName());

                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                    JOptionPane.showMessageDialog(null," Mời bạn nhập ID ");
                }



            }
        });


        btnDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Integer idoutput = Integer.valueOf(txtIdOutput.getText());
                Candidate candidate = ServicesManager.candidateRepository.find(idoutput);
                    ((DefaultTableModel)table.getModel()).removeRow(currentRowInTable);
                    JOptionPane.showMessageDialog(null,"Đã xóa dữ liệu thành công");
                    ServicesManager.candidateRepository.delete(candidate);


            }
        });
        txtDate.addFocusListener(new FocusAdapter() {
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

    private void renderTable() {
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
        });

        ListSelectionModel listSelectionModel= table.getSelectionModel();

        listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        listSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int[]  rows = table.getSelectedRows();
                int[]  cols = table.getSelectedColumns();
                currentRowInTable = rows[0];
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
                    reFemale.setSelected(true);
                }
                else rbMale.setSelected(true);

                cmbBirthPlaceOutput.setSelectedItem(candidate.getProvinceName());


            }
        });

    }
}
