package edu.itechart.contactlist.commands;

import edu.itechart.contactlist.service.AttachmentFileService;
import edu.itechart.contactlist.service.ServiceException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetPhotoCommand implements Command {
    private static final String REQUEST_PARAM_NAME = "path";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String fileName = request.getParameter(REQUEST_PARAM_NAME);
        try {
            byte[] content = AttachmentFileService.readFile(fileName);
            response.getOutputStream().write(content);
        } catch (ServiceException e) {
            throw new CommandException(e);
        } catch (IOException e) {
            throw new CommandException(String.format("Can't write content of file %s to response", fileName), e);
        }
        return StringUtils.EMPTY;
    }
}
