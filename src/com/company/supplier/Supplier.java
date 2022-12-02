package com.company.supplier;

import com.company.document.Document;
import com.company.history.History;
import com.company.ware.Ware;
import com.company.ware.storage.WareStorage;
import com.company.worker.Worker;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Supplier {

    private final static String OPERATION_TYPE_PUT = "PUT";
    private final static String OPERATION_TYPE_GET = "GET";

    public void put(Document document, Worker worker) {
        for (Ware ware : document.getGoods()) {
            Optional<Ware> currentWare = WareStorage.goods.stream().filter(ware1 -> ware1.getId().equals(ware.getId())).findFirst();
            if (currentWare.isPresent()) {
                Ware w = currentWare.get();
                w.setQuantity(w.getQuantity() + ware.getQuantity());
            } else {
                WareStorage.goods.add(ware);
            }
        }
        document.setOperationType(OPERATION_TYPE_PUT);
        document.setWorker(worker);
        History.history.add(document);
    }

    public void get(Document document, Worker worker) {
        for (Ware ware : document.getGoods()) {
            Optional<Ware> currentWare = WareStorage.goods.stream().filter(ware1 -> ware1.getId().equals(ware.getId())).findFirst();
            if (currentWare.isPresent()) {
                Ware w = currentWare.get();
                if (w.getQuantity() - ware.getQuantity() < 0) {
                    throw new IllegalArgumentException(String.format("There is no enough ware with id %s in the storage", w.getId()));
                }
                w.setQuantity(w.getQuantity() - ware.getQuantity());
            } else {
                throw new IllegalArgumentException(String.format("There is no ware with id %s in the storage", ware.getId()));
            }
        }
        document.setOperationType(OPERATION_TYPE_GET);
        document.setWorker(worker);
        History.history.add(document);
    }

    public double getAveragePriceOfEveryProducts() {
        return WareStorage.goods.stream().map(Ware::getPrice).mapToInt(Integer::intValue).average().orElse(0);
    }

    public List<Ware> filterGoods(Predicate<Ware> warePredicate) {
        return WareStorage.goods.stream().filter(warePredicate).collect(Collectors.toList());
    }

    public List<Ware> sortGoods(Comparator<Ware> wareComparator) {
        return WareStorage.goods.stream().sorted(wareComparator).collect(Collectors.toList());
    }

    public int getSpendsOfCertainBuyer(String buyer, Date after, Date before) {
        return History.history.stream()
                .filter(document -> document.getOperationType().equals("GET"))
                .filter(document -> document.getSupplier().equals(buyer))
                .filter(document -> document.getDealDate().after(after))
                .filter(document -> document.getDealDate().before(before))
                .flatMap(document -> document.getGoods().stream())
                .map(ware -> ware.getQuantity() * ware.getPrice())
                .mapToInt(Integer::intValue).sum();
    }

    public List<Ware> getEveryProductBoughtByUser(String buyer) {
        return History.history.stream()
                .filter(document -> document.getSupplier().equals(buyer))
                .filter(document -> document.getOperationType().equals("GET"))
                .flatMap(document -> document.getGoods().stream())
                .collect(Collectors.toList());
    }

    public String findTheMostPopularProduct() {
        Map<String, Integer> allProductsToQuantity = new HashMap<>();
        History.history.stream().filter(document -> document.getOperationType().equals("GET"))
                .flatMap(document -> document.getGoods().stream())
                .forEach(ware -> {
                    if (allProductsToQuantity.containsKey(ware.getName())) {
                        allProductsToQuantity.put(ware.getName(), allProductsToQuantity.get(ware.getName()) + ware.getQuantity());
                    } else {
                        allProductsToQuantity.put(ware.getName(), ware.getQuantity());
                    }
                });
        String mostPopularProduct = "";
        int quantity = 0;
        for (Map.Entry<String, Integer> entry : allProductsToQuantity.entrySet()) {
            if (entry.getValue() > quantity) {
                quantity = entry.getValue();
                mostPopularProduct = entry.getKey();
            }
        }
        return mostPopularProduct;
    }

    public int getTheBiggestValueForADay(Date date) {
        return History.history.stream()
                .filter(document -> document.getOperationType().equals("GET"))
                .filter(document -> document.getDealDate().getDay() == date.getDay())
                .flatMap(document -> document.getGoods().stream())
                .map(ware -> ware.getQuantity() * ware.getPrice())
                .mapToInt(Integer::intValue)
                .sum();
    }
}
