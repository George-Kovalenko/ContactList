package edu.itechart.contactlist.service;

import edu.itechart.contactlist.entity.Attachment;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

public class AttachmentFileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentFileService.class);
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
            LOGGER.debug("Write attachment with contactId = {}, id = {} and full path = {}",
                    attachment.getContactID(), attachment.getId(), path);
            try {
                writeFileItem(path, fileItem);
            } catch (Exception e) {
                throw new ServiceException(String.format("Can't write attachment with id = %d to %s",
                        attachment.getId(), path) , e);
            }
        }
    }

    public static void writePhoto(long contactId, FileItem photo) throws ServiceException {
        String path = getDirectory(PATH_TO_PHOTO, contactId);
        path += contactId;
        LOGGER.debug("Write attachment with contactId = {} and full path = {}",
                contactId, path);
        try {
            writeFileItem(path, photo);
        } catch (Exception e) {
            throw new ServiceException(String.format("Can't write photo with contactId = %d to %s",
                    contactId, photo.getName()) , e);
        }
    }

    public static String getPathToPhoto(long contactId) {
        String path = PATH_TO_PHOTO + contactId + File.separator + contactId;
        if (new File(path).exists()) {
            return path;
        }
        return StringUtils.EMPTY;
    }

    public static String getPathToAttachment(long contactId, long attachmentId) {
        String path = PATH_TO_ATTACHMENTS + contactId + File.separator + attachmentId;
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
                throw new ServiceException(String.format("Can't read file %s", file.getAbsolutePath()) , e);
            }
        }
        return content;
    }

    public static void removeFile(long contactId, long attachmentId, String path) throws ServiceException {
        String filePath = path + contactId + File.separator + attachmentId;
        LOGGER.debug("Remove file {}", filePath);
        File file = new File(filePath);
        if (file.exists()) {
            if (!file.delete()) {
                throw new ServiceException(String.format("Can't delete file %s", filePath));
            }
        }
    }

    public static void removeAllFiles(long contactId) throws ServiceException {
        String attachmentsPath = PATH_TO_ATTACHMENTS + contactId;
        removeFilesInDirectory(attachmentsPath);
        String photoPath = PATH_TO_PHOTO + contactId;
        removeFilesInDirectory(photoPath);
    }

    private static void removeFilesInDirectory(String path) throws ServiceException {
        File dir = new File(path);
        LOGGER.debug("Remove directory with files {}", dir);
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!file.delete()) {
                        throw new ServiceException(String.format("Can't remove file %s", file.getAbsolutePath()));
                    }
                }
            }
            if (!dir.delete()) {
                throw new ServiceException(String.format("Can't remove directory %s", dir.getAbsolutePath()));
            }
        }
    }

    private static String getDirectory(String path, long id) throws ServiceException {
        String dirPath = path + id + File.separator;
        File dir = new File(dirPath);
        if (!dir.exists()) {
            if (!dir.mkdir()) {
                throw new ServiceException(String.format("Can't create directory %s", dir.getAbsolutePath()));
            }
        }
        return dirPath;
    }

    private static void writeFileItem(String path, FileItem fileItem) throws Exception {
        File file = new File(path);
        fileItem.write(file);
    }
}
