package com.ekorhan.aisolutions.kmeans;

import com.ekorhan.aisolutions.kmeans.model.Input;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IDataProcessor {
    List<Input> getData() throws IOException;
}
