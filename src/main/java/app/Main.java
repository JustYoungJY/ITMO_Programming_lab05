package app;

import app.collection.CollectionManager;
import app.commands.*;
import app.factory.HumanBeingFactory;
import app.model.HumanBeing;
import app.transfer.Request;
import app.transfer.Response;
import app.util.FileManager;
import app.util.InputReader;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


/**
 * Main application class.
 * Reads the FILE environment variable, loads the collection, and starts interactive mode.
 */
public class Main {
    public static void main(String[] args) {

        // Reading an environment variable
        String fileName = System.getenv("FILE");
        if (fileName == null || fileName.isEmpty()) {
            System.err.println("The environment variable is not set. Shutdown...");
            System.exit(1);
        }

        // Initializing Components
        FileManager fileManager = new FileManager(fileName);
        CollectionManager<HumanBeing> collectionManager = new CollectionManager<>();
        InputReader inputReader = new InputReader(new Scanner(System.in));
        HumanBeingFactory factory = new HumanBeingFactory(inputReader);

        // Loading a collection from a file
        try {
            collectionManager.setCollection(fileManager.loadCollection());
            System.out.printf("Collection successfully loaded from file %s%n", fileName);
        } catch (Exception e) {
            System.err.println("Error loading collection: " + e.getMessage());
        }

        // Registering commands
        CommandInvoker invoker = new CommandInvoker() {{
            register(new HelpCommand(this));
            register(new InfoCommand(collectionManager));
            register(new ShowCommand(collectionManager));
            register(new InsertCommand(collectionManager, factory, inputReader));
            register(new UpdateCommand(collectionManager, factory, inputReader));
            register(new RemoveKeyCommand(collectionManager, inputReader));
            register(new ClearCommand(collectionManager));
            register(new SaveCommand(collectionManager, fileManager));
            register(new ExecuteScriptCommand(this, inputReader));
            register(new ExitCommand());
            register(new RemoveLowerCommand(collectionManager, factory));
            register(new ReplaceIfGreaterCommand(collectionManager, factory, inputReader));
            register(new RemoveLowerKeyCommand(collectionManager, inputReader));
            register(new AverageOfImpactSpeedCommand(collectionManager));
            register(new CountLessThanWeaponTypeCommand(collectionManager, inputReader));
            register(new PrintDescendingCommand(collectionManager));
        }};

        // Command loop
        System.out.println("Enter command:");
        while (true) {
            String inputLine = inputReader.prompt("> ");
            if (inputLine == null || inputLine.trim().isEmpty()) continue;

            String[] parts = inputLine.trim().split("\\s+", 2);
            Request request = new Request(parts[0],
                    parts.length > 1
                            ? Arrays.asList(parts[1].split("\\s+"))
                            : Collections.emptyList()
                    , List.of());

            Response response = invoker.executeCommand(request);

            System.out.println(response.message());
            // todo а где остальные поля?
        }
    }
}