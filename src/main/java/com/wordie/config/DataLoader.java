package com.wordie.config;

import com.wordie.model.Word;
import com.wordie.repository.WordRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class DataLoader implements CommandLineRunner {

    private final WordRepository wordRepository;

    @Value("${wordie.xls-file:words.xls}")
    private String xlsFile;

    public DataLoader(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (wordRepository.count() > 0) {
            return;
        }

        ClassPathResource resource = new ClassPathResource(xlsFile);
        if (!resource.exists()) {
            return;
        }

        try (InputStream is = resource.getInputStream();
             Workbook workbook = new HSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell == null) continue;

                String word = getCellValue(cell).trim().toUpperCase();
                if (word.length() == 5 && word.matches("[A-Z]+")) {
                    wordRepository.save(new Word(word));
                }
            }
        }
    }

    private String getCellValue(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
            default -> "";
        };
    }
}
