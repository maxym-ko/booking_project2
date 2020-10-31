package com.maxym.booking.web.command;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {
    private static final Map<String, Command> commands = new TreeMap<>();

    static {
        commands.put("invalid", new InvalidCommand());
        commands.put("forbidden", new ForbiddenCommand());
        commands.put("home", new HomeCommand());
        commands.put("about", new AboutCommand());
        commands.put("show_login", new ShowLoginCommand());
        commands.put("login", new LoginCommand());
        commands.put("show_registration", new ShowRegistrationCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("show_applications", new ShowApplicationsCommand());
        commands.put("show_reservations", new ShowReservationsCommand());
        commands.put("add_room", new AddRoomCommand());
        commands.put("change_room", new ChangeRoomCommand());
        commands.put("remove_room", new RemoveRoomCommand());
        commands.put("book_room", new BookRoomCommand());
        commands.put("select_room", new SelectRoomCommand());
        commands.put("find_room", new FindRoomCommand());
        commands.put("create_application", new CreateApplicationCommand());
        commands.put("accept_application", new AcceptApplicationCommand());
        commands.put("reject_application", new RejectApplicationCommand());
        commands.put("remove_application", new RemoveApplicationCommand());
        commands.put("confirm_payment", new ConfirmPaymentCommand());
    }

    private CommandContainer(){}

    public static Command get(String commandName) {
        if (commandName == null) return commands.get("home");
        if (!commands.containsKey(commandName)) return commands.get("invalid");

        return commands.get(commandName);
    }
}
