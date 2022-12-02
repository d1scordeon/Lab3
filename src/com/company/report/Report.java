package com.company.report;

import com.company.history.History;
import com.company.ware.Ware;
import com.company.worker.Worker;

import java.util.List;
import java.util.stream.Collectors;

public class Report {
    public int getReportsByUser(Worker worker) {
        return (int) History.history.stream().filter(document -> document.getWorker().getId().equals(worker.getId())).count();
    }

    public int getReceivedGoods(Ware ware) {
        List<Integer> allGoodsQuantities = History.history.stream().filter(document -> document.getOperationType().equals("PUT")).flatMap(document -> document.getGoods().stream())
                .filter(ware1 -> ware1.getId().equals(ware.getId())).map(Ware::getQuantity).collect(Collectors.toList());
        return allGoodsQuantities.stream().mapToInt(Integer::intValue).sum();
    }
}
