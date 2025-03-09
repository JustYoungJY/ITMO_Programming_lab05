package app.commands;

import app.collection.CollectionManager;
import app.model.HumanBeing;
import app.transfer.Request;
import app.transfer.Response;
import app.util.InputReader;

/**
 * remove_key command: removes an element from a collection based on the given key.
 */
public class RemoveKeyCommand implements Command {
    private final CollectionManager<HumanBeing> collectionManager;
    private final InputReader reader;

    public RemoveKeyCommand(CollectionManager<HumanBeing> collectionManager, InputReader reader) {
        this.collectionManager = collectionManager;
        this.reader = reader;
    }

    @Override
    public Response execute(Request request) {
        String keyStr = request.args().isEmpty()
                ? reader.prompt("Enter key to remove: ")
                : request.args().get(0);

        try {
            Long key = Long.parseLong(keyStr);
            HumanBeing removed = collectionManager.removeKey(key);
            if (removed != null) {
                return new Response("Item removed");
            } else {
                return new Response("Element with this key not found");
            }
        } catch (NumberFormatException e) {
            return new Response("The key must be a number");
        }
    }

    @Override
    public String getName() {
        return "remove_key";
    }

    @Override
    public String getDescription() {
        return "remove an element from a collection by its key";
    }
}
