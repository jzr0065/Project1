package Mainframe;

import java.util.HashMap;
import java.util.Map;

public class CachedDataAdapter implements IDataAccess {
    Map<Integer, ProductModel> cachedProducts = new HashMap<>();
    Map<Integer, CustomerModel> cachedCustomers = new HashMap<>();
    IDataAccess adapter;

    public CachedDataAdapter(IDataAccess adapter) {
        this.adapter = adapter;
    }


    @Override
    public boolean connect(String path) {
        return this.adapter.connect(path);
    }

    @Override
    public ProductModel loadProduct(int id) {
        if (cachedProducts.containsKey(id))
            return cachedProducts.get(id);
        else {
            ProductModel product = adapter.loadProduct(id);
            cachedProducts.put(id, product);
            return product;
        }
    }

    @Override
    public boolean saveProduct(ProductModel product) {
        adapter.saveProduct(product);
        cachedProducts.put(product.mProductID, product);
        return true;
    }

    public CustomerModel loadCustomer(int id) {
        if (cachedProducts.containsKey(id))
            return cachedCustomers.get(id);
        else {
            CustomerModel customer = adapter.loadCustomer(id);
            cachedCustomers.put(id, customer);
            return customer;
        }
    }

    public boolean saveCustomer(CustomerModel customer) {
        adapter.saveCustomer(customer);
        cachedCustomers.put(customer.mCustomerID, customer);
        return true;
    }

    @Override
    public boolean disconnect() {
        return true;
    }

    @Override
    public int getErrorCode() {
        return this.adapter.getErrorCode();
    }

    @Override
    public String getErrorMessage() {
        switch (this.adapter.getErrorCode()) {
            case CONNECTION_OPEN_FAILED: return "Connection is not opened!";
            case PRODUCT_LOAD_FAILED: return "Cannot load the product!";
        };
        return "OK";
    }
}
