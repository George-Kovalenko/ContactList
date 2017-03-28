package edu.itechart.contactlist.service;

import edu.itechart.contactlist.entity.Attachment;
import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

public class AttachmentFileService {
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("directories");
    private static final String PATH_TO_ATTACHMENTS = resourceBundle.getString("attachments");

    public static void writeAttachments(ArrayList<Attachment> attachments, ArrayList<FileItem> attachmentFiles)
            throws ServiceException {
        Iterator<Attachment> attachmentsIterator = attachments.iterator();
        Iterator<FileItem> attachmentFilesIterator = attachmentFiles.iterator();
        while (attachmentsIterator.hasNext() && attachmentFilesIterator.hasNext()) {
            Attachment attachment = attachmentsIterator.next();
            FileItem fileItem = attachmentFilesIterator.next();
            try {
                writeFileItem(attachment, fileItem);
            } catch (Exception e) {
                throw new ServiceException("Couldn't write attachment " + fileItem.getName(), e);
            }
        }
    }

    private static void writeFileItem(Attachment attachment, FileItem fileItem) throws Exception {
        String filePath = PATH_TO_ATTACHMENTS + Long.toString(attachment.getContactID()) + File.separator;
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(filePath + attachment.getId());
        fileItem.write(file);
    }
}
