package edu.itechart.contactlist.commands;

import edu.itechart.contactlist.entity.Attachment;
import edu.itechart.contactlist.service.AttachmentFileService;
import edu.itechart.contactlist.service.AttachmentService;
import edu.itechart.contactlist.service.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DownloadAttachmentCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadAttachmentCommand.class);
    private static final String REQUEST_PARAM_NAME = "id";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        long id = Long.parseLong(request.getParameter(REQUEST_PARAM_NAME));
        try {
            Attachment attachment = AttachmentService.findById(id);
            String path = AttachmentFileService.getPathToAttachment(attachment.getContactId(), attachment.getId());
            LOGGER.info("Download attachment with id = {}, path = {}", id, path);
            if (StringUtils.isNotEmpty(path)) {
                File file = new File(path);
                FileInputStream fileInputStream = null;
                OutputStream outputStream = null;
                try {
                    fileInputStream = new FileInputStream((path));
                    outputStream = response.getOutputStream();
                    response.setContentType("application/download");
                    response.setHeader("Content-disposition", String.format("attachment;filename=%s",
                            attachment.getFileName()));
                    byte[] buffer = new byte[(int) file.length()];
                    while (fileInputStream.read(buffer) != -1) {
                        outputStream.write(buffer);
                    }
                } catch (IOException e) {
                    throw new CommandException(e);
                } finally {
                    try {
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    } catch (IOException e) {
                        LOGGER.error("Can't close stream");
                    }
                }
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return StringUtils.EMPTY;
    }
}
