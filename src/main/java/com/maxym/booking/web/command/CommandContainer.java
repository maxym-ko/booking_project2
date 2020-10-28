package com.maxym.booking.web.command;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {
    private static final Map<String, Command> commands = new TreeMap<>();

    static {
        commands.put("invalid", new InvalidCommand());
        commands.put("forbidden", new ForbiddenCommand());
        commands.put("main", new MainCommand());
        commands.put("about", new AboutCommand());
        commands.put("show_login", new ShowLoginCommand());
        commands.put("login", new LoginCommand());
        commands.put("show_registration", new ShowRegistrationCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("show_applications", new ShowApplicationsCommand());
        commands.put("show_applications_admin", new ShowApplicationsAdminCommand());
        commands.put("show_reservations", new ShowReservationsCommand());
        commands.put("book_room", new BookRoomCommand());
        commands.put("create_application", new CreateApplicationCommand());
        commands.put("remove_application", new RemoveApplicationCommand());
    }

    private CommandContainer(){}

    public static Command get(String commandName) {
        if (commandName == null) return commands.get("main");
        if (!commands.containsKey(commandName)) return commands.get("invalid");

        return commands.get(commandName);
    }
}
