/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra;

import hr.algebra.dal.Repository;
import hr.algebra.dal.RepositoryFactory;
import hr.algebra.model.Genre;
import hr.algebra.model.GenreTableModel;
import hr.algebra.utils.MessageUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Petra
 */
public class GenresPanel extends javax.swing.JPanel {

    private List<JTextComponent> validationFields;
    private List<JLabel> errorLables;
    private Repository repository;

    private GenreTableModel genreTableModel;

    private Genre selectedGenre;

    /**
     * Creates new form GenresPanel
     */
    public GenresPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tfName = new javax.swing.JTextField();
        lblNameError = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbGenres = new javax.swing.JTable();
        lblName = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1192, 689));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        lblNameError.setForeground(java.awt.Color.red);

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        tbGenres.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbGenres.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbGenresMouseClicked(evt);
            }
        });
        tbGenres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbGenresKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tbGenres);

        lblName.setText("Name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 967, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(264, 264, 264)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(371, 371, 371)
                        .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblNameError, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(123, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNameError, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (selectedGenre == null) {
            MessageUtils.showInformationMessage("Wrong", "Select genre");
            return;
        }
        if (formValid()) {
            try {
                selectedGenre.setName(tfName.getText().trim());

                repository.updateGenre(selectedGenre.getId(), selectedGenre);
                genreTableModel.setGenres(new ArrayList<>(repository.selectGenres()));

                clearForm();

            } catch (Exception ex) {
                Logger.getLogger(MoviesPanel.class.getName()).log(Level.SEVERE, null, ex);
                MessageUtils.showErrorMessage("Error", "Unable to update genre");
            }
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (selectedGenre == null) {
            MessageUtils.showInformationMessage("Wrong", "Select genre");
            return;
        }
        if (MessageUtils.showConfirmDialog("Delete", "Really?") == JOptionPane.YES_OPTION) {
            try {
                repository.deletePerson(selectedGenre.getId());
                genreTableModel.setGenres(new ArrayList<>(repository.selectGenres()));

                clearForm();
            } catch (Exception ex) {
                Logger.getLogger(MoviesPanel.class.getName()).log(Level.SEVERE, null, ex);
                
                MessageUtils.showErrorMessage("Error", "Unable to delete genre!");
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (formValid()) {
            try {
                Genre genre = new Genre(
                        tfName.getText().trim());

                repository.getOrCreateGenreId(genre);
                genreTableModel.setGenres(new ArrayList<>(repository.selectGenres()));

                clearForm();

            } catch (Exception ex) {
                Logger.getLogger(MoviesPanel.class.getName()).log(Level.SEVERE, null, ex);
                MessageUtils.showErrorMessage("Error", "Unable to add genre");
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void tbGenresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbGenresMouseClicked
        showGenre();
    }//GEN-LAST:event_tbGenresMouseClicked

    private void tbGenresKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbGenresKeyReleased
        showGenre();
    }//GEN-LAST:event_tbGenresKeyReleased

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        init();
    }//GEN-LAST:event_formComponentShown


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblNameError;
    private javax.swing.JTable tbGenres;
    private javax.swing.JTextField tfName;
    // End of variables declaration//GEN-END:variables

    private void init() {
        try {
            initValidation();
            initRepository();
            initTable();

        } catch (Exception ex) {
            Logger.getLogger(PeoplePanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Error", "Unable to load people");
            System.exit(1);
        }
    }

    private void initValidation() {
        validationFields = Arrays.asList(tfName);
        errorLables = Arrays.asList(lblNameError);
    }

    private void initRepository() throws Exception {
        repository = RepositoryFactory.getRepository();
    }

    private void initTable() throws Exception {
        tbGenres.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbGenres.setAutoCreateRowSorter(true);
        tbGenres.setRowHeight(25);
        List<Genre> genres = new ArrayList<>(repository.selectGenres());
        genreTableModel = new GenreTableModel(genres);
        tbGenres.setModel(genreTableModel);

    }

    private boolean formValid() {
        boolean ok = true;

        for (int i = 0; i < validationFields.size(); i++) {
            ok &= !validationFields.get(i).getText().trim().isEmpty();
            errorLables.get(i).setText(validationFields.get(i).getText().trim().isEmpty() ? "X" : "");
        }
        return ok;
    }

    private void clearForm() {
        validationFields.forEach(e -> e.setText(""));
        errorLables.forEach(e -> e.setText(""));

        selectedGenre = null;
    }

    private void showGenre() {
        clearForm();
        int selectedRow = tbGenres.getSelectedRow();
        int rowIndex = tbGenres.convertRowIndexToModel(selectedRow);

        int id = (int) genreTableModel.getValueAt(rowIndex, 0);

        try {
            Optional<Genre> optGenre = repository.selectGenre(id);
            if (optGenre.isPresent()) {
                selectedGenre = optGenre.get();
                fillForm(selectedGenre);
            }
        } catch (Exception ex) {
            Logger.getLogger(MoviesPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fillForm(Genre genre) throws Exception {
        tfName.setText(genre.getName());
    }
}