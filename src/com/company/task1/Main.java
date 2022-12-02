package com.company.task1;

import com.company.document.Document;
import com.company.report.Report;
import com.company.supplier.Supplier;
import com.company.ware.Ware;
import com.company.worker.Worker;

import java.util.Arrays;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        Ware soap = new Ware(1L, "Soap", 10, 10);
        Ware lanterns = new Ware(2L, "Lantern", 20, 2);
        Worker worker = new Worker(1L, "Katya");
        Document document = new Document(Arrays.asList(soap, lanterns), "some company", new Date());

        Supplier supplier = new Supplier();
        supplier.put(document, worker);

        Ware newSoap = new Ware(1L, "Soap", 10, 4);
        Ware newLanterns = new Ware(2L, "Lantern", 20, 1);
        Document newDocument = new Document(Arrays.asList(newSoap, newLanterns), "some company", new Date());

        supplier.get(newDocument, worker);
        Report report = new Report();

        System.out.println(String.format("Total quantity of ware %s: %d", soap.getName(), report.getReceivedGoods(soap)));
    }
}
