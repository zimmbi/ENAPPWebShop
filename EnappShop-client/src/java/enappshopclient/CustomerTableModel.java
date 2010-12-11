/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enappshopclient;

import ch.hslu.enappwebshop.ejb.remote.AccountRemote;
import ch.hslu.enappwebshop.entities.Customer;

import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author berdir
 */
public class CustomerTableModel extends DefaultTableModel {

    private AccountRemote accountRemoteBean;
    private List<Customer> customers;
    public static final int COLUMN_CID = 0;
    public static final int COLUMN_USER = 1;
    public static final int COLUMN_NAME = 2;
    public static final int COLUMN_NAVCUSTID = 3;

    public CustomerTableModel(AccountRemote accountRemoteBean) {
        super();
        this.accountRemoteBean = accountRemoteBean;
        customers = accountRemoteBean.list();

        String[] columns = new String[]{"ID", "Username", "Name", "Nav Cust ID"};
        setColumnIdentifiers(columns);

    }

    @Override
    public int getRowCount() {
        if (customers != null) {
            return customers.size();
        }
        return 0;
    }

    @Override
    public Object getValueAt(int row, int column) {

        Customer c = customers.get(row);
        switch (column) {
            case COLUMN_CID:
                return c.getId();
            case COLUMN_USER:
                return c.getUsername();
            case COLUMN_NAME:
                return c.getName();
            case COLUMN_NAVCUSTID:
                return c.getDynnavid();
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == COLUMN_NAVCUSTID;
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        if (column == COLUMN_NAVCUSTID) {
            customers.get(row).setDynnavid((String)value);
            Customer updated = accountRemoteBean.update(customers.get(row));
            customers.set(row, updated);
            fireTableDataChanged();
        }
    }
}
