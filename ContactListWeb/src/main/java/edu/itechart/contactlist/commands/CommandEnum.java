package edu.itechart.contactlist.commands;

public enum CommandEnum {
    SHOW_CONTACT_LIST {
        {
            command = new ShowContactListCommand();
        }
    };

    protected Command command;

    public Command getCommand() {
        return command;
    }
}
