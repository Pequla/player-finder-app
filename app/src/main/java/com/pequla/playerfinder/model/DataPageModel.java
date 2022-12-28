package com.pequla.playerfinder.model;

import java.util.ArrayList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DataPageModel {
    private ArrayList<DataModel> content;
    private boolean first;
    private boolean last;
    private int pageNumber;
    private int totalPages;
}
