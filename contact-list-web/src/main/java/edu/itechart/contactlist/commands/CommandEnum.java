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

    CREATE_CONTACT {
        {
            command = new CreateContactCommand();
        }
    },

    UPDATE_CONTACT {
        {
            command = new UpdateContactCommand();
        }
    },

    DELETE_CONTACT {
        {
            command = new DeleteContactCommand();
        }
    },

    GET_PHOTO {
        {
            command = new GetPhotoCommand();
        }
    },

    DOWNLOAD_ATTACHMENT {
        {
            command = new DownloadAttachmentCommand();
        }
    },

    SHOW_CONTACT_SEARCH_PAGE {
        {
            command = new ShowContactSearchPageCommand();
        }
    },

    SEARCH_CONTACTS {
        {
            command = new SearchContactsCommand();
        }
    },

    SHOW_EMAIL_PAGE {
        {
            command = new ShowEmailPageCommand();
        }
    },

    SEND_EMAIL {
        {
            command = new SendEmailCommand();
        }
    };

    protected Command command;

    public Command getCommand() {
        return command;
    }
}
