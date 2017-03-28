package edu.itechart.contactlist.service;

import edu.itechart.contactlist.entity.Attachment;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

public class AttachmentFileService {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("directories");
    public static final String PATH_TO_ATTACHMENTS = resourceBundle.getString("attachments");
    public static final String PATH_TO_PHOTO = resourceBundle.getString("photos");

    public static void writeAttachments(ArrayList<Attachment> attachments, ArrayList<FileItem> attachmentFiles)
            throws ServiceException {
        Iterator<Attachment> attachmentsIterator = attachments.iterator();
        Iterator<FileItem> attachmentFilesIterator = attachmentFiles.iterator();
        while (attachmentsIterator.hasNext() && attachmentFilesIterator.hasNext()) {
            Attachment attachment = attachmentsIterator.next();
            FileItem fileItem = attachmentFilesIterator.next();
            String path = getDirectory(PATH_TO_ATTACHMENTS, attachment.getContactID());
            path += attachment.getId();
            try {
                writeFileItem(path, fileItem);
            } catch (Exception e) {
                throw new ServiceException("Couldn't write attachment " + fileItem.getName(), e);
            }
        }
    }

    public static void writePhoto(long contactId, FileItem photo) throws ServiceException {
        String path = getDirectory(PATH_TO_PHOTO, contactId);
        path += contactId;
        try {
            writeFileItem(path, photo);
        } catch (Exception e) {
            throw new ServiceException("Couldn't write photo " + photo.getName(), e);
        }
    }

    public static String getPathToPhoto(long contactId) {
        String path = getDirectory(PATH_TO_PHOTO, contactId);
        path += contactId;
        if (new File(path).exists()) {
            return path;
        }
        return StringUtils.EMPTY;
    }

    public static byte[] readFile(String path) throws ServiceException {
        File file = new File(path);
        byte[] content = new byte[0];
        if (file.exists()) {
            try {
                content = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                throw new ServiceException("Couldn't read file " + file.getAbsolutePath(), e);
            }
        }
        return content;
    }

    public static void removeFile(long contactId, long attachmentId, String path) {
        String filePath = path + contactId + File.separator + attachmentId;
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    public static void removeAllFiles(long contactId) {
        String attachmentsPath = PATH_TO_ATTACHMENTS + contactId;
        removeFilesInDirectory(attachmentsPath);
        String photoPath = PATH_TO_PHOTO + contactId;
        removeFilesInDirectory(photoPath);
    }

    private static void removeFilesInDirectory(String path) {
        File dir = new File(path);
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
            dir.delete();
        }
    }

    private static String getDirectory(String path, long id) {
        String dirPath = path + id + File.separator;
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dirPath;
    }

    private static void writeFileItem(String path, FileItem fileItem) throws Exception {
        File file = new File(path);
        fileItem.write(file);
    }
}
