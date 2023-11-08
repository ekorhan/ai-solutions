package com.ekorhan.aisolutions.api.apifootball;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@EnableAsync
public class ApiFootballProvider {
    @Scheduled(fixedDelay = 1000)
    public void scheduleFixedDelayTask() {
        System.out.println(
                "Fixed delay task - " + System.currentTimeMillis() / 1000);
    }
    @Async
    @Scheduled(fixedRate = 1000)
    public void scheduleFixedRateTaskAsync() throws InterruptedException {
        System.out.println(
                "Fixed rate task async - " + System.currentTimeMillis() / 1000);
        Thread.sleep(2000);
    }
    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    public void scheduleFixedRateWithInitialDelayTask() {

        long now = System.currentTimeMillis() / 1000;
        System.out.println(
                "Fixed rate task with one second initial delay - " + now);
    }
    @Scheduled(cron = "*/5 * * * * *")
    public void scheduleTaskUsingCronExpression() {

        long now = System.currentTimeMillis() / 1000;
        System.out.println(
                "schedule tasks using cron jobs - " + now);
    }
    public static ApiFootballPlayersResponse getPlayers(int page) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api-football-v1.p.rapidapi.com/v3/players?league=39&season=2020&page=" + page))
                .header("X-RapidAPI-Key", "bf7877b402mshc77f9e8afb8c776p1fc269jsn08f693a75c91")
                .header("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        return gson.fromJson(response.body(), ApiFootballPlayersResponse.class);
    }

    public static void saveBulkPlayer(int count) throws IOException, InterruptedException {
        for (int i = 1; i <= count; i++) {
            savePlayers();
        }
    }

    public static void savePlayers() throws IOException, InterruptedException {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "players.xlsx";
        File file = new File(fileLocation);
        Workbook workbook;
        try {
            FileInputStream inputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(inputStream);
        } catch (FileNotFoundException fileNotFoundException) {
            workbook = new XSSFWorkbook();
        }

        int page = -1;
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        List<Integer> willBeRemovedSheets = new ArrayList<>();
        int sheetIndex = 0;
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            String sheetName = sheet.getSheetName();
            try {
                int sheetNameAsInt = Integer.parseInt(sheetName);
                if (sheetNameAsInt > page) {
                    page = sheetNameAsInt;
                }
            } catch (Exception e) {
                log.error("Sheet name is not page. name: " + sheetName);
                willBeRemovedSheets.add(sheetIndex);
            }
            sheetIndex++;
        }
        for (int i : willBeRemovedSheets) {
            workbook.removeSheetAt(i);
        }
        if (page == -1) {
            page = 1;
        } else {
            page += 1;
        }

        ApiFootballPlayersResponse response = getPlayers(page);

        Sheet sheet = workbook.createSheet(String.valueOf(page));
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setAlignment(HorizontalAlignment.LEFT);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Verdana");
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        headerStyle.setFont(font);

        Row header = sheet.createRow(0);

        int index = 0;

        //Player
        index = prepareHeaderCell(header, headerStyle, index, "name");
        index = prepareHeaderCell(header, headerStyle, index, "age");
        index = prepareHeaderCell(header, headerStyle, index, "lastname");
        index = prepareHeaderCell(header, headerStyle, index, "age");
        index = prepareHeaderCell(header, headerStyle, index, "height");
        index = prepareHeaderCell(header, headerStyle, index, "weight");
        index = prepareHeaderCell(header, headerStyle, index, "nationality");
        index = prepareHeaderCell(header, headerStyle, index, "birth country");
        index = prepareHeaderCell(header, headerStyle, index, "birth date");
        index = prepareHeaderCell(header, headerStyle, index, "birth place");
        index = prepareHeaderCell(header, headerStyle, index, "injured");

        //Games
        index = prepareHeaderCell(header, headerStyle, index, "appearences");
        index = prepareHeaderCell(header, headerStyle, index, "lineups");
        index = prepareHeaderCell(header, headerStyle, index, "minutes");
        index = prepareHeaderCell(header, headerStyle, index, "position");
        index = prepareHeaderCell(header, headerStyle, index, "rating");
        index = prepareHeaderCell(header, headerStyle, index, "captain");

        //Substitutes
        index = prepareHeaderCell(header, headerStyle, index, "in");
        index = prepareHeaderCell(header, headerStyle, index, "out");
        index = prepareHeaderCell(header, headerStyle, index, "bench");

        //Shots
        index = prepareHeaderCell(header, headerStyle, index, "total");
        index = prepareHeaderCell(header, headerStyle, index, "on");

        //Goals
        index = prepareHeaderCell(header, headerStyle, index, "total");
        index = prepareHeaderCell(header, headerStyle, index, "conceded");
        index = prepareHeaderCell(header, headerStyle, index, "assists");
        index = prepareHeaderCell(header, headerStyle, index, "saves");

        //Passes
        index = prepareHeaderCell(header, headerStyle, index, "total");
        index = prepareHeaderCell(header, headerStyle, index, "key");
        index = prepareHeaderCell(header, headerStyle, index, "accuracy");

        //Tackles
        index = prepareHeaderCell(header, headerStyle, index, "total");
        index = prepareHeaderCell(header, headerStyle, index, "blocks");
        index = prepareHeaderCell(header, headerStyle, index, "interceptions");

        //Duels
        index = prepareHeaderCell(header, headerStyle, index, "total");
        index = prepareHeaderCell(header, headerStyle, index, "won");

        //Dribbles
        index = prepareHeaderCell(header, headerStyle, index, "attempts");
        index = prepareHeaderCell(header, headerStyle, index, "success");
        //index = prepareHeaderCell(header, headerStyle, index, "past");

        //Fouls
        index = prepareHeaderCell(header, headerStyle, index, "drawn");
        index = prepareHeaderCell(header, headerStyle, index, "committed");

        //Cards
        index = prepareHeaderCell(header, headerStyle, index, "yellow");
        index = prepareHeaderCell(header, headerStyle, index, "yellowred");
        index = prepareHeaderCell(header, headerStyle, index, "red");

        //Penalty
        index = prepareHeaderCell(header, headerStyle, index, "won");
        index = prepareHeaderCell(header, headerStyle, index, "commited");
        index = prepareHeaderCell(header, headerStyle, index, "scored");
        index = prepareHeaderCell(header, headerStyle, index, "missed");
        prepareHeaderCell(header, headerStyle, index, "saved");

        for (int i = 0; i < response.getResponse().size(); i++) {
            ApiFootballPlayersResponse.Response r = response.getResponse().get(i);
            ApiFootballPlayersResponse.Statistic stat = r.statistics.get(0);
            ApiFootballPlayersResponse.Player player = r.player;

            CellStyle style = workbook.createCellStyle();
            style.setWrapText(true);
            style.setAlignment(HorizontalAlignment.LEFT);
            int cellIndex = 0;

            Row row = sheet.createRow(i + 1);

            //Player
            cellIndex = prepareCell(row, style, cellIndex, player.name);
            cellIndex = prepareCell(row, style, cellIndex, player.firstname);
            cellIndex = prepareCell(row, style, cellIndex, player.lastname);
            cellIndex = prepareCell(row, style, cellIndex, player.age);
            cellIndex = prepareCell(row, style, cellIndex, player.height);
            cellIndex = prepareCell(row, style, cellIndex, player.weight);
            cellIndex = prepareCell(row, style, cellIndex, player.nationality);
            cellIndex = prepareCell(row, style, cellIndex, player.birth.country);
            cellIndex = prepareCell(row, style, cellIndex, player.birth.date);
            cellIndex = prepareCell(row, style, cellIndex, player.birth.place);
            cellIndex = prepareCell(row, style, cellIndex, player.injured);

            //Games
            cellIndex = prepareCell(row, style, cellIndex, stat.games.appearences);
            cellIndex = prepareCell(row, style, cellIndex, stat.games.lineups);
            cellIndex = prepareCell(row, style, cellIndex, stat.games.minutes);
            cellIndex = prepareCell(row, style, cellIndex, stat.games.position);
            cellIndex = prepareCell(row, style, cellIndex, stat.games.rating);
            cellIndex = prepareCell(row, style, cellIndex, stat.games.captain);

            //Substitutes
            cellIndex = prepareCell(row, style, cellIndex, stat.substitutes.in);
            cellIndex = prepareCell(row, style, cellIndex, stat.substitutes.out);
            cellIndex = prepareCell(row, style, cellIndex, stat.substitutes.bench);

            //Shots
            cellIndex = prepareCell(row, style, cellIndex, stat.shots.total);
            cellIndex = prepareCell(row, style, cellIndex, stat.shots.on);

            //Goals
            cellIndex = prepareCell(row, style, cellIndex, stat.goals.total);
            cellIndex = prepareCell(row, style, cellIndex, stat.goals.conceded);
            cellIndex = prepareCell(row, style, cellIndex, stat.goals.assists);
            cellIndex = prepareCell(row, style, cellIndex, stat.goals.saves);

            //Passes
            cellIndex = prepareCell(row, style, cellIndex, stat.passes.total);
            cellIndex = prepareCell(row, style, cellIndex, stat.passes.key);
            cellIndex = prepareCell(row, style, cellIndex, stat.passes.accuracy);

            //Tackles
            cellIndex = prepareCell(row, style, cellIndex, stat.tackles.total);
            cellIndex = prepareCell(row, style, cellIndex, stat.tackles.blocks);
            cellIndex = prepareCell(row, style, cellIndex, stat.tackles.interceptions);

            //Duels
            cellIndex = prepareCell(row, style, cellIndex, stat.duels.total);
            cellIndex = prepareCell(row, style, cellIndex, stat.duels.won);

            //Dribbles
            cellIndex = prepareCell(row, style, cellIndex, stat.dribbles.attempts);
            cellIndex = prepareCell(row, style, cellIndex, stat.dribbles.success);
            //cellIndex = prepareCell(row, style, cellIndex, stat.dribbles.past);//TODO

            //Fouls
            cellIndex = prepareCell(row, style, cellIndex, stat.fouls.drawn);
            cellIndex = prepareCell(row, style, cellIndex, stat.fouls.committed);

            //Cards
            cellIndex = prepareCell(row, style, cellIndex, stat.cards.yellow);
            cellIndex = prepareCell(row, style, cellIndex, stat.cards.yellowred);
            cellIndex = prepareCell(row, style, cellIndex, stat.cards.red);

            //Penalty
            cellIndex = prepareCell(row, style, cellIndex, stat.penalty.won);
            cellIndex = prepareCell(row, style, cellIndex, stat.penalty.commited);
            cellIndex = prepareCell(row, style, cellIndex, stat.penalty.scored);
            cellIndex = prepareCell(row, style, cellIndex, stat.penalty.missed);
            prepareCell(row, style, cellIndex, stat.penalty.saved);
        }

        OutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        workbook.close();
    }

    private static int prepareCell(Row row, CellStyle style, int cellIndex, Integer val) {
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(val);
        cell.setCellStyle(style);
        return ++cellIndex;
    }

    private static int prepareCell(Row row, CellStyle style, int cellIndex, Double val) {
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(val);
        cell.setCellStyle(style);
        return ++cellIndex;
    }

    private static int prepareCell(Row row, CellStyle style, int cellIndex, String val) {
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(val);
        cell.setCellStyle(style);
        return ++cellIndex;
    }

    private static int prepareCell(Row row, CellStyle style, int cellIndex, Boolean val) {
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(val);
        cell.setCellStyle(style);
        return ++cellIndex;
    }

    private static int prepareHeaderCell(Row header, CellStyle headerStyle, int index, String val) {
        Cell headerCell = header.createCell(index);
        headerCell.setCellValue(val);
        headerCell.setCellStyle(headerStyle);
        return ++index;
    }
}
