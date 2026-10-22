package ua.poltava.energy.client;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ua.poltava.energy.client.api.ApiClient;
import ua.poltava.energy.client.importcsv.CsvImporter;
import ua.poltava.energy.client.model.StanZakhodu;
import ua.poltava.energy.client.model.Zakhid;

import java.io.File;

public class MainApp extends Application {
    private final ApiClient apiClient = new ApiClient();
    private final ObservableList<Zakhid> zakhodyList = FXCollections.observableArrayList();
    private TableView<Zakhid> table = new TableView<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Інформаційна система планування заходів енергоаудиту");

        TableColumn<Zakhid, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Zakhid, String> nazvaCol = new TableColumn<>("Назва заходу");
        nazvaCol.setCellValueFactory(new PropertyValueFactory<>("nazva"));

        TableColumn<Zakhid, Double> vartistCol = new TableColumn<>("Вартість");
        vartistCol.setCellValueFactory(new PropertyValueFactory<>("vartist"));

        TableColumn<Zakhid, String> stanCol = new TableColumn<>("Стан");
        stanCol.setCellValueFactory(new PropertyValueFactory<>("stan"));

        TableColumn<Zakhid, Double> integralCol = new TableColumn<>("Інтегральний показник");
        integralCol.setCellValueFactory(new PropertyValueFactory<>("integralnyiPokaznyk"));

        table.getColumns().addAll(idCol, nazvaCol, vartistCol, stanCol, integralCol);
        table.setItems(zakhodyList);

        Button refreshBtn = new Button("Оновити дані");
        refreshBtn.setOnAction(e -> loadData());

        Button nextStateBtn = new Button("Наступний етап");
        nextStateBtn.setOnAction(e -> changeState());

        Button importBtn = new Button("Імпорт CSV");
        importBtn.setOnAction(e -> handleImport(primaryStage));

        Button addBtn = new Button("Додати тестовий захід");
        addBtn.setOnAction(e -> addTestZakhid());

        HBox topPanel = new HBox(10, refreshBtn, nextStateBtn, importBtn, addBtn);
        topPanel.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(topPanel);
        root.setCenter(table);

        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        loadData();
    }

    private void addTestZakhid() {
        Zakhid z = new Zakhid();
        z.setEnerhoaudytId(1);
        z.setNazva("Новий захід від клієнта");
        z.setVartist(15000.0);
        z.setPotentsialEkonomii(60.0);
        z.setTerminOkupnosti(12.0);
        z.setPotentsialSkorochenniaVykydiv(50.0);
        z.setStan(StanZakhodu.ZAPROPONOVANO);

        if (z.getNazva() == null || z.getNazva().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Назва не може бути порожньою").show();
            return;
        }

        try {
            apiClient.createZakhid(z);
            loadData();
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Помилка створення: " + ex.getMessage()).show();
        }
    }

    private void loadData() {
        try {
            zakhodyList.clear();
            zakhodyList.addAll(apiClient.getZakhody());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не вдалося завантажити дані: " + e.getMessage());
            alert.show();
        }
    }

    private void changeState() {
        Zakhid selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Оберіть захід у таблиці!").show();
            return;
        }

        String current = selected.getStan().name();
        String next = "";
        if (current.equals("ZAPROPONOVANO")) next = "SKHVALENO";
        else if (current.equals("SKHVALENO")) next = "VPROVADZHENNIA";
        else if (current.equals("VPROVADZHENNIA")) next = "ZAVERSHENO";
        else {
            new Alert(Alert.AlertType.INFORMATION, "Захід вже завершено!").show();
            return;
        }

        try {
            apiClient.changeState(selected.getId(), next);
            loadData();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Помилка зміни стану: " + e.getMessage()).show();
        }
    }

    private void handleImport(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Оберіть CSV файл");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            CsvImporter importer = new CsvImporter(file);

            ProgressBar progressBar = new ProgressBar();
            progressBar.progressProperty().bind(importer.progressProperty());
            progressBar.setPrefWidth(200);

            Stage progressStage = new Stage();
            progressStage.setTitle("Імпорт...");
            progressStage.initOwner(primaryStage);
            progressStage.initModality(Modality.WINDOW_MODAL);
            BorderPane progressPane = new BorderPane();
            progressPane.setCenter(progressBar);
            progressPane.setPadding(new Insets(20));
            progressStage.setScene(new Scene(progressPane, 300, 80));

            importer.setOnSucceeded(event -> {
                progressStage.close();
                new Alert(Alert.AlertType.INFORMATION, "Імпорт завершено успішно!").show();
                loadData();
            });

            importer.setOnFailed(event -> {
                progressStage.close();
                new Alert(Alert.AlertType.ERROR, "Помилка імпорту: " + importer.getException().getMessage()).show();
            });

            new Thread(importer).start();
            progressStage.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}