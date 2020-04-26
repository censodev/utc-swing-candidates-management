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
import java.util.ArrayList;
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

    private DefaultTableModel defaultTableModel;
    private List<CandidateDTO> dataSource;

    public MainForm() {
        txtDate.setText("dd/mm/yyyy");

        ButtonGroup G = new ButtonGroup();
        G.add(reFemale);
        G.add(rbMale);

        renderTable();

        List<Province> provinceList = ServicesManager.provinceRepository.findAll();

        DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        cmbBirthPlace.setModel(defaultComboBoxModel1);
        defaultComboBoxModel1.addElement("");
        cmbBirthPlaceOutput.setModel(defaultComboBoxModel2);
        provinceList.stream().map(p -> p.getName()).forEach(ps -> {
            defaultComboBoxModel1.addElement(ps);
            defaultComboBoxModel2.addElement(ps);
        });


        btnInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // get data from text field
                    String dateString = txtDate.getText();
                    int[] dateArray = Arrays.stream(dateString.split("/")).mapToInt(Integer::valueOf).toArray();
                    LocalDate date = LocalDate.of(dateArray[2], dateArray[1], dateArray[0]);

                    String name = txtName.getText();
                    String provinceName = String.valueOf(cmbBirthPlaceOutput.getSelectedItem());
                    Integer provinceId = ServicesManager.provinceRepository.findByName(provinceName).getId();
                    Double  chemistry = Double.valueOf(txtChemistry.getText());
                    Double  physics = Double.valueOf(txtPhysical.getText());
                    Double  math = Double.valueOf(txtMath.getText());
                    String gender = reFemale.isSelected() ? Gender.FEMALE : Gender.MALE;

                    // add to DB
                    Candidate candidate = new Candidate();
                    candidate.setName(name);
                    candidate.setMathMark(math);
                    candidate.setChemistryMark(chemistry);
                    candidate.setPhysicsMark(physics);
                    candidate.setProvinceId(provinceId);
                    candidate.setGender(gender);
                    candidate.setBirthDate(date);
                    Integer id = (Integer) ServicesManager.candidateRepository.save(candidate);

                    // refresh frame
                    dataSource = ServicesManager.candidateRepository.getListCandidates();
                    refreshTable(dataSource);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get data from text field
                String dateString = txtDate.getText();
                int[] dateArray = Arrays.stream(dateString.split("/")).mapToInt(Integer::valueOf).toArray();
                LocalDate date = LocalDate.of(dateArray[2], dateArray[1], dateArray[0]);

                Integer id = (Integer) table.getValueAt(table.getSelectedRow(), 0);
                String name = txtName.getText();
                String provinceName = String.valueOf(cmbBirthPlaceOutput.getSelectedItem());
                Integer provinceId = ServicesManager.provinceRepository.findByName(provinceName).getId();
                Double  chemistry = Double.valueOf(txtChemistry.getText());
                Double  physics = Double.valueOf(txtPhysical.getText());
                Double  math = Double.valueOf(txtMath.getText());
                String gender = reFemale.isSelected() ? Gender.FEMALE : Gender.MALE;

                // update DB
                Candidate candidate = new Candidate();
                candidate.setId(id);
                candidate.setName(name);
                candidate.setMathMark(math);
                candidate.setChemistryMark(chemistry);
                candidate.setPhysicsMark(physics);
                candidate.setProvinceId(provinceId);
                candidate.setGender(gender);
                candidate.setBirthDate(date);
                ServicesManager.candidateRepository.update(candidate);

                // refresh frame
                dataSource = ServicesManager.candidateRepository.getListCandidates();
                refreshTable(dataSource);
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

        btnFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String selectedId = txtIdInput.getText();
                    String birthplace = String.valueOf(cmbBirthPlace.getSelectedItem());

                    List<CandidateDTO> candidateDTOList = new ArrayList<>();

                    if (!selectedId.equals(""))
                        candidateDTOList.add(ServicesManager.candidateRepository.getCandidate(Integer.parseInt(selectedId)));
                    else if (!birthplace.equals(""))
                        candidateDTOList.addAll(ServicesManager.candidateRepository.findByProvince(birthplace));
                    else
                        candidateDTOList = ServicesManager.candidateRepository.getListCandidates();

                    // refresh frame
                    dataSource = candidateDTOList;
                    refreshTable(dataSource);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        btnDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            int rowSelected = table.getSelectedRow();
            Integer selectedId = (Integer) table.getValueAt(rowSelected,0);

            // delete in DB
            Candidate candidate = ServicesManager.candidateRepository.find(selectedId);
            ServicesManager.candidateRepository.delete(candidate);

            // refresh frame
            dataSource = ServicesManager.candidateRepository.getListCandidates();
            refreshTable(dataSource);

            // dialog
            JOptionPane.showMessageDialog(null,"Đã xóa dữ liệu thành công");
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

    private void renderTable() {
        defaultTableModel = new DefaultTableModel();
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

        dataSource = ServicesManager.candidateRepository.getListCandidates();
        setDataSource(dataSource);

        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {
                    CandidateDTO candidateDTO = dataSource.get(e.getLastIndex());
                    fillCandidateDetails(candidateDTO);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void setDataSource(List<CandidateDTO> dataSource) {
        dataSource.forEach(c -> {
            defaultTableModel
                    .addRow(new Object[] {
                            c.getId(), c.getName(), c.getProvinceName(), c.getBirthDate(), c.getGender(),
                            c.getMathMark(), c.getPhysicsMark(), c.getChemistryMark(),(double) Math.round(c.getTotalMark() * 10) / 10
                    });
        });
    }

    private void refreshTable(List<CandidateDTO> dataSource) {
        table.clearSelection();
        defaultTableModel.setRowCount(0);
        setDataSource(dataSource);
    }

    private void fillCandidateDetails(CandidateDTO candidateDTO) {
        txtIdOutput.setText(String.valueOf(candidateDTO.getId()));
        txtName.setText(String.valueOf(candidateDTO.getName()));
        txtDate.setText(String.valueOf(candidateDTO.getBirthDate()));
        txtMath.setText(String.valueOf(candidateDTO.getMathMark()));
        txtPhysical.setText(String.valueOf(candidateDTO.getPhysicsMark()));
        txtChemistry.setText(String.valueOf(candidateDTO.getChemistryMark()));
        txtTotal.setText(String.valueOf((double) Math.round(candidateDTO.getTotalMark() * 10) / 10));

        if (candidateDTO.getGender().equals("F"))
            reFemale.setSelected(true);
        else
            rbMale.setSelected(true);

        cmbBirthPlaceOutput.setSelectedItem(candidateDTO.getProvinceName());
    }
}
