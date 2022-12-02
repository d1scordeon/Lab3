package com.company.document;

import com.company.ware.Ware;
import com.company.worker.Worker;

import java.util.Date;
import java.util.List;

public class Document {
    private List<Ware> goods;
    private String supplier;
    private Date dealDate;
    private Worker worker;
    private String operationType;

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public Document(List<Ware> goods, String supplier, Date dealDate) {
        this.goods = goods;
        this.supplier = supplier;
        this.dealDate = dealDate;
    }

    public List<Ware> getGoods() {
        return goods;
    }

    public void setGoods(List<Ware> goods) {
        this.goods = goods;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Date getDealDate() {
        return dealDate;
    }

    public void setDealDate(Date dealDate) {
        this.dealDate = dealDate;
    }
}
