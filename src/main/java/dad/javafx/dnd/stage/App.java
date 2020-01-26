package dad.javafx.dnd.stage;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {
	
	private double x;
	private double y;

	@Override
	public void start(Stage primaryStage) throws Exception {

		Label titleLabel = new Label();
		titleLabel.textProperty().bind(primaryStage.titleProperty());
		
		Button closeButton = new Button("X");
		closeButton.setOnAction(e -> primaryStage.close());
		closeButton.getStyleClass().add("close-button");

		BorderPane titlePane = new BorderPane();
		titlePane.getStyleClass().add("top-pane");
		titlePane.setCenter(titleLabel);
		titlePane.setRight(closeButton);
		
		Label infoLabel = new Label("Stage arrastrable con\ndecoración personalizada.");
		infoLabel.setAlignment(Pos.CENTER);
		
		BorderPane root = new BorderPane();
		root.setTop(titlePane);
		root.setCenter(infoLabel);
		
		root.setOnMouseClicked(e -> {
			// doble clic con bot�n primario -> maximimizar/restaurar ventana
			if (e.getClickCount() == 2 && e.getButton().equals(MouseButton.PRIMARY)) {
				primaryStage.setMaximized(!primaryStage.isMaximized());
			}
			// clic con bot�n secundario -> cambia fondo a color aleatorio
			if (e.getClickCount() == 1 && e.getButton().equals(MouseButton.SECONDARY)) {
				root.setBackground(new Background(new BackgroundFill(Color.color(Math.random(), Math.random(), Math.random()), null, null)));
			}
		});
		root.setOnMousePressed(e -> { 
			// clic con bot�n primario -> guarda coordenadas del clic
			if (e.getButton().equals(MouseButton.PRIMARY)) {
			    x = primaryStage.getX() - e.getScreenX();
			    y = primaryStage.getY() - e.getScreenY();
			}
		});
		root.setOnMouseDragged(e -> {
			// arrastra panel con bot�n primario -> se mueve ventana a nuevas coordenadas
			if (e.getButton().equals(MouseButton.PRIMARY)) {
				primaryStage.setX(e.getScreenX() + x);
				primaryStage.setY(e.getScreenY() + y);
			}
		});
		
		Scene scene = new Scene(root, 320, 200);
		scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
		
		primaryStage.initStyle(StageStyle.UNDECORATED); // stage sin borde ni barra de t�tulo
		primaryStage.setTitle("DnDStage");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
