package com.company.task3;

import com.company.document.Document;
import com.company.supplier.Supplier;
import com.company.ware.Ware;
import com.company.worker.Worker;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        Ware soap = new Ware(1L, "Soap", 10, 30);
        Ware lanterns = new Ware(2L, "Lantern", 20, 1);
        Worker worker = new Worker(1L, "Katya");
        Document document = new Document(Arrays.asList(soap, lanterns), "some company", new Date());

        Supplier supplier = new Supplier();
        supplier.put(document, worker);

        Ware newSoap = new Ware(1L, "Soap", 10, 2);
        Ware newLanterns = new Ware(2L, "Lantern", 20, 1);
        Document newDocument = new Document(Arrays.asList(newSoap, newLanterns), "some company", new Date());

        supplier.get(newDocument, worker);

        Predicate<Ware> warePredicate = (ware) -> ware.getName().equals("Soap");
        System.out.println("Filtered goods:");
        supplier.filterGoods(warePredicate).forEach(System.out::println);

        System.out.println("Average price of products: " + supplier.getAveragePriceOfEveryProducts());

        Comparator<Ware> wareComparator = Comparator.comparingInt(Ware::getPrice);
        System.out.println("Sorted goods:");
        supplier.sortGoods(wareComparator).forEach(System.out::println);

        Date after = new Date();
        after.setHours(after.getHours() - 1);
        Date before = new Date();
        before.setHours(before.getHours() + 1);
        System.out.println("Spends of buyer " + newDocument.getSupplier() + ": "
                + supplier.getSpendsOfCertainBuyer(newDocument.getSupplier(), after, before));

        System.out.println("Every product bought by user " + newDocument.getSupplier());
        supplier.getEveryProductBoughtByUser(newDocument.getSupplier()).forEach(System.out::println);

        System.out.println("The most popular product: " + supplier.findTheMostPopularProduct());

        System.out.println("The biggest value of the day " + new Date().toString() + ": "
                + supplier.getTheBiggestValueForADay(new Date()));
    }
}
