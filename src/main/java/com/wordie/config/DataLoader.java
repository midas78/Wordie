package com.wordie.config;

import com.wordie.model.Word;
import com.wordie.repository.WordRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;

@Component
public class DataLoader implements CommandLineRunner {

    private final WordRepository wordRepository;

    public DataLoader(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (wordRepository.count() > 0) return;

        File file = new File("data/words.xlsx");
        if (!file.exists()) {
            file = new File("../data/words.xlsx");
        }
        if (!file.exists()) return;

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell != null && cell.getCellType() == CellType.STRING) {
                    String word = cell.getStringCellValue().trim().toLowerCase();
                    if (word.length() == 5) {
                        wordRepository.save(new Word(word));
                    }
                }
            }
        }
    }
}
