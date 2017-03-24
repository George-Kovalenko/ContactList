package edu.itechart.contactlist.commands;

public enum CommandEnum {
    SHOW_CONTACT_LIST {
        {
            command = new ShowContactListCommand();
        }
    },

    SHOW_CONTACT {
        {
            command = new ShowContactCommand();
        }
    },

    UPDATE_CONTACT {
        {
            command = new UpdateContactCommand();
        }
    };

    protected Command command;

    public Command getCommand() {
        return command;
    }
}
