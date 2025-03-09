package app.commands;

import app.collection.CollectionManager;
import app.factory.HumanBeingFactory;
import app.model.HumanBeing;
import app.transfer.Request;
import app.transfer.Response;
import app.util.InputReader;

import java.util.List;

/**
 * insert command: adds a new element with the given key.
 * After entering the command, the user sequentially enters the fields of the new object.
 */
public class InsertCommand implements Command {
    private final CollectionManager<HumanBeing> collectionManager;
    private final HumanBeingFactory factory;
    private final InputReader reader;

    public InsertCommand(CollectionManager<HumanBeing> collectionManager, HumanBeingFactory factory, InputReader reader) {
        this.collectionManager = collectionManager;
        this.factory = factory;
        this.reader = reader;
    }

    @Override
    public Response execute(Request request) {
        String keyStr = request.args().isEmpty()
                ? reader.prompt("Enter key: ")
                : request.args().get(0);

        try {
            Long key = Long.parseLong(keyStr);

            HumanBeing human = factory.createHumanBeing();
            // todo потом тяжело будет клиент-сервер воткнуть
            // todo из-за того, что именно команда вызывает получение элемента с консоли
            // todo а не внешний обработчик, который придет к команде уже с элементов для вставки

            collectionManager.insert(key, human);
            return new Response("Item added");
        } catch (NumberFormatException e) {
            return new Response("The key must be a number");
        }
    }

    @Override
    public String getName() {
        return "insert";
    }

    @Override
    public String getDescription() {
        return "Add a new element with a given key";
    }

}