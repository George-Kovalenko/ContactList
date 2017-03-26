package edu.itechart.contactlist.service;

import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.util.ArrayList;

public class AttachmentWriterService {
    private static final String PATH_TO_ATTACHMENTS = "E:" + File.separator + "server" + File.separator +
            "attachments" + File.separator + "contact-";

    public static void writeAttachments(long id, ArrayList<FileItem> attachments) throws ServiceException {
        for (FileItem fileItem : attachments) {
            try {
                writeFileItem(fileItem, id);
            } catch (Exception e) {
                throw new ServiceException("Couldn't write attachment " + fileItem.getName(), e);
            }
        }
    }

    private static void writeFileItem(FileItem fileItem, long id) throws Exception {
        String filePath = PATH_TO_ATTACHMENTS + Long.toString(id) + File.separator;
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(filePath + fileItem.getName());
        fileItem.write(file);
    }
}
