package org.utc.k59.it3.forms;

import org.utc.k59.it3.dto.CandidateDTO;
import org.utc.k59.it3.models.Candidate;
import org.utc.k59.it3.models.Province;
import org.utc.k59.it3.utils.DbSeeder;
import org.utc.k59.it3.utils.Gender;
import org.utc.k59.it3.utils.ServicesManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class MainForm {
    private JPanel mainPanel;
    private JButton btnInsert;
    private JButton btnDelete;
    private JButton btnAboutus;
    private JButton btnEdit;
    private JButton btnRefresh;
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
//        txtDate.setText("dd/mm/yyyy");

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
                    // get data from text field
                    String dateString = txtDate.getText();
                    int[] dateArray = Arrays.stream(dateString.split("/")).mapToInt(Integer::valueOf).toArray();
                    LocalDate date = LocalDate.of(dateArray[2], dateArray[1], dateArray[0] + 1);

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
                    refreshTable(ServicesManager.candidateRepository.getListCandidates());

                    //Dialog
                    JOptionPane.showMessageDialog(null,"Đã thêm dữ liệu thành công");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,"Hãy nhập thông tin thí sinh cần thêm vào dữ liệu.");
                }
            }
        });

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 int rowSelected = table.getSelectedRow();
                 if(rowSelected == -1) {
                     JOptionPane.showMessageDialog(null, "Mời chọn thí sinh cần sửa thông tin");
                 }
                 
                 else{
                         Integer confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn chỉnh sửa thông tin thí sinh ?", "Xác thực", JOptionPane.ERROR_MESSAGE);
                         if (confirm == JOptionPane.YES_OPTION) {
                             // get data from text field
                             String dateString = txtDate.getText();
                             int[] dateArray = Arrays.stream(dateString.split("/")).mapToInt(Integer::valueOf).toArray();
                             LocalDate date = LocalDate.of(dateArray[2], dateArray[1], dateArray[0] + 1);
                             Integer id = (Integer) table.getValueAt(rowSelected, 0);
                             String name = txtName.getText();
                             String provinceName = String.valueOf(cmbBirthPlaceOutput.getSelectedItem());
                             Integer provinceId = ServicesManager.provinceRepository.findByName(provinceName).getId();
                             Double chemistry = Double.valueOf(txtChemistry.getText());
                             Double physics = Double.valueOf(txtPhysical.getText());
                             Double math = Double.valueOf(txtMath.getText());
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
                             refreshTable(ServicesManager.candidateRepository.getListCandidates());
                             CandidateDTO candidateDTO = new CandidateDTO();
                             candidateDTO.setId(candidate.getId());
                             candidateDTO.setName(candidate.getName());
                             candidateDTO.setBirthDate(dateString);
                             candidateDTO.setProvinceName(provinceName);
                             candidateDTO.setGender(candidate.getGender());
                             candidateDTO.setMathMark(candidate.getMathMark());
                             candidateDTO.setPhysicsMark(candidate.getPhysicsMark());
                             candidateDTO.setChemistryMark(candidate.getChemistryMark());
                             candidateDTO.setTotalMark(candidate.getMathMark() + candidate.getPhysicsMark() + candidate.getChemistryMark());
                             fillCandidateDetails(candidateDTO);

                              //Dialog
                              JOptionPane.showMessageDialog(null,"Đã chỉnh sửa thành công");
                         }
                     }
                 }
        });

        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<CandidateDTO>  candidateDTOList= ServicesManager.candidateRepository.getListCandidates() ;
                refreshTable(candidateDTOList);


            }
        });

        btnAboutus.addActionListener(new ActionListener() {
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
                    refreshTable(candidateDTOList);
                } catch (Exception ex) {
                    ex.printStackTrace();                       
                }
            }
        });

        btnDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int rowSelected = table.getSelectedRow();
                if(rowSelected == -1)     {
                    JOptionPane.showMessageDialog(null, " Hãy chọn thí sinh cần xóa .");

                } else {
                     Integer confirm= JOptionPane.showConfirmDialog(null,"Bạn có chắc chắn muốn xóa ?","Xác thực",JOptionPane.ERROR_MESSAGE)  ;
                     if (confirm == JOptionPane.YES_OPTION)
                     {
                          Integer selectedId = (Integer) table.getValueAt(rowSelected,0);

                          // delete in DB
                          Candidate candidate = ServicesManager.candidateRepository.find(selectedId);
                          ServicesManager.candidateRepository.delete(candidate);

                          // refresh frame
                          refreshTable(ServicesManager.candidateRepository.getListCandidates());
                          fillCandidateDetails(null);

                          // dialog
                          JOptionPane.showMessageDialog(null,"Đã xóa dữ liệu thành công");
                     }
                }
            }
        });

        btnAboutus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jFrame = new JFrame("About us");
                jFrame.setContentPane(new AboutForm().Mainframe1);
                jFrame.pack();
                jFrame.setSize(450,220);
                jFrame.setVisible(true);
                jFrame.setLocationRelativeTo(null);
            }
        });
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.setProperty("user.timezone", "Asia/Ho_Chi_Minh");
        Logger.getGlobal().info(LocalDateTime.now().toString());

        DbSeeder.seedAll();

        JFrame frame= new JFrame("Quản lí thí sinh thi Đại học UTC 2020 v1.5");
        frame.setContentPane(new MainForm().mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1200,768);
        frame.setVisible(true);
    }

    private void renderTable() {
        defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
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
        this.dataSource = dataSource;
        dataSource.forEach(c -> {
            defaultTableModel
                    .addRow(new Object[] {
                            c.getId(), c.getName(), c.getProvinceName(), c.getBirthDate(), c.getGender(),
                            c.getMathMark(), c.getPhysicsMark(), c.getChemistryMark(),(double) Math.round(c.getTotalMark() * 10) / 10
                    });
        });
    }

    private void refreshTable(List<CandidateDTO> dataSource) {
        defaultTableModel.setRowCount(0);
        setDataSource(dataSource);
    }

    private void fillCandidateDetails(CandidateDTO candidateDTO) {
        txtIdOutput.setText(candidateDTO == null ? "" : String.valueOf(candidateDTO.getId()));
        txtName.setText(candidateDTO == null ? "" : String.valueOf(candidateDTO.getName()));
        txtDate.setText(candidateDTO == null ? "" : String.valueOf(candidateDTO.getBirthDate()));
        txtMath.setText(candidateDTO == null ? "" : String.valueOf(candidateDTO.getMathMark()));
        txtPhysical.setText(candidateDTO == null ? "" : String.valueOf(candidateDTO.getPhysicsMark()));
        txtChemistry.setText(candidateDTO == null ? "" : String.valueOf(candidateDTO.getChemistryMark()));
        txtTotal.setText(candidateDTO == null ? "" : String.valueOf((double) Math.round(candidateDTO.getTotalMark() * 10) / 10));
        if (candidateDTO == null)  {}
        else if (candidateDTO.getGender().equals("F"))
            reFemale.setSelected(true);
        else
            rbMale.setSelected(true);

        cmbBirthPlaceOutput.setSelectedItem(candidateDTO == null ? cmbBirthPlaceOutput.getItemAt(0) : candidateDTO.getProvinceName());
    }
}
