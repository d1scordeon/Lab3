package com.company.task2;

import com.company.document.Document;
import com.company.supplier.Supplier;
import com.company.ware.Ware;
import com.company.worker.Worker;

import java.util.Arrays;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Ware soap = new Ware(1L, "Soap", 10, 20);
        Ware lanterns = new Ware(2L, "Lantern", 20, 20);
        Worker worker = new Worker(1L, "Katya");
        Document document = new Document(Arrays.asList(soap, lanterns), "some company", new Date());
        Supplier supplier = new Supplier();
        try {
            supplier.get(document, worker);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
