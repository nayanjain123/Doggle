package com.nayan.doggle;
import android.content.Context;
import android.content.res.AssetManager;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CSVFileReader {
    private static final char CSV_DELIMITER = ',';

    public static List<String[]> readCSV(Context context, String csvFileName) {
        List<String[]> data = new ArrayList<>();
        AssetManager assetManager = context.getAssets();

        try {
            InputStream inputStream = assetManager.open(csvFileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

            CSVReader csvReader = new CSVReaderBuilder(inputStreamReader)
                    .withSkipLines(0)
                    .withCSVParser(new com.opencsv.CSVParserBuilder().withSeparator(CSV_DELIMITER).build())
                    .build();

            String[] line;
            while ((line = csvReader.readNext()) != null) {
                data.add(line);
            }

            csvReader.close();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return data;
    }
}
