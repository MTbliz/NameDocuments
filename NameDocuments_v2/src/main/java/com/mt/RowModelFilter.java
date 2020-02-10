package com.mt;

import java.util.List;
import java.util.stream.Collectors;

public class RowModelFilter {


    public RowModel getRowModel(List<RowModel> rowModels,String classification, String document){

        List<RowModel> rowModelList = rowModels.stream()
                .filter(row -> row.getClassification().equals(classification))
                .filter(row -> row.getDocument().equals(document)).collect(Collectors.toList());
        return rowModelList.get(0);
        }
    }
