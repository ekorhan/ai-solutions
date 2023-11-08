package com.ekorhan.aisolutions.kmeans;

import com.ekorhan.aisolutions.kmeans.model.Input;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelDataProcessor implements IDataProcessor {
    @Override
    public List<Input> getData() throws IOException {
        FileInputStream file = new FileInputStream(new File("/Users/mehmeteneskorhan/Desktop/sample1.xlsx"));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);

        List<Input> data = new ArrayList<>();
        int i = 0;
        for (Row row : sheet) {
            Input input = new Input(i + 1, String.valueOf(i + 1));
            for (Cell cell : row) {
                input.addValue(cell.getNumericCellValue());
            }
            data.add(input);
            i++;
        }
        workbook.close();
        file.close();
        return data;
    }
}
