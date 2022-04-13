/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 *
 * @author Petra
 */
public class GenreTransferable implements Transferable {

    public static final DataFlavor GENRE_FLAVOR = new DataFlavor(Genre.class, "Genre");
    public static final DataFlavor[] SUPPORTED_FLAVORS = {GENRE_FLAVOR};

    private final Genre data;

    public GenreTransferable(Genre data) {
        this.data = data;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return SUPPORTED_FLAVORS;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(GENRE_FLAVOR);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (isDataFlavorSupported(flavor)) {
            return data;
        }
        throw new UnsupportedFlavorException(flavor); //To change body of generated methods, choose Tools | Templates.
    }
}
