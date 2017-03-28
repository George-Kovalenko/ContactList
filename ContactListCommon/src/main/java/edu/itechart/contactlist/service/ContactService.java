package edu.itechart.contactlist.service;

import edu.itechart.contactlist.connectionpool.ConnectionPool;
import edu.itechart.contactlist.connectionpool.ConnectionPoolException;
import edu.itechart.contactlist.dao.*;
import edu.itechart.contactlist.entity.*;
import org.apache.commons.fileupload.FileItem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContactService {
    public static Contact findById(long id) throws ServiceException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            Contact contact = new ContactDAO(connection).findById(id);
            Address address = new AddressDAO(connection).findById(id);
            contact.setAddress(address);
            ArrayList<Phone> phones = new PhoneDAO(connection).findByContactId(id);
            contact.setPhones(phones);
            ArrayList<Attachment> attachments = new AttachmentDAO(connection).findByContactId(id);
            contact.setAttachments(attachments);
            return contact;
        } catch (SQLException | ConnectionPoolException | DAOException e) {
            throw new ServiceException(e);
        }
    }

    public static ArrayList<Contact> findAll() throws ServiceException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            ArrayList<Contact> contacts = new ContactDAO(connection).findAll();
            AddressDAO addressDAO = new AddressDAO(connection);
            PhoneDAO phoneDAO = new PhoneDAO(connection);
            AttachmentDAO attachmentDAO = new AttachmentDAO(connection);
            for (Contact contact : contacts) {
                long id = contact.getId();
                contact.setAddress(addressDAO.findById(id));
                contact.setPhones(phoneDAO.findByContactId(id));
                contact.setAttachments(attachmentDAO.findByContactId(id));
            }
            return contacts;
        } catch (SQLException | ConnectionPoolException | DAOException e) {
            throw new ServiceException(e);
        }
    }

    public static void insert(Contact contact, ArrayList<FileItem> fileItems) throws ServiceException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            try {
                ContactDAO contactDAO = new ContactDAO(connection);
                contactDAO.insert(contact);
                long id = contactDAO.getLastId();
                AddressDAO addressDAO = new AddressDAO(connection);
                contact.getAddress().setId(id);
                addressDAO.insert(contact.getAddress());
                contact.getPhones().forEach(item -> item.setContactID(id));
                PhoneDAO phoneDAO = new PhoneDAO(connection);
                insertEntityList(contact.getPhones(), phoneDAO);
                contact.getAttachments().forEach(item -> item.setContactID(id));
                AttachmentDAO attachmentDAO = new AttachmentDAO(connection);
                insertEntityList(contact.getAttachments(), attachmentDAO);
                AttachmentFileService.writeAttachments(contact.getAttachments(), fileItems);
                connection.commit();
            } catch (DAOException | ServiceException e) {
                connection.rollback();
                throw new ServiceException(e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new ServiceException(e);
        }
    }

    public static void update(long id, Contact contact, ArrayList<FileItem> fileItems) throws ServiceException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            try {
                ContactDAO contactDAO = new ContactDAO(connection);
                contactDAO.update(id, contact);
                AddressDAO addressDAO = new AddressDAO(connection);
                addressDAO.update(id, contact.getAddress());
                PhoneDAO phoneDAO = new PhoneDAO(connection);
                updateEntityList(contact.getPhones(), phoneDAO.findByContactId(id), phoneDAO);
                AttachmentDAO attachmentDAO = new AttachmentDAO(connection);
                ArrayList<Attachment> attachmentsForInsert = updateEntityList(contact.getAttachments(),
                        attachmentDAO.findByContactId(id), attachmentDAO);
                AttachmentFileService.writeAttachments(attachmentsForInsert, fileItems);
                connection.commit();
            } catch (DAOException | ServiceException e) {
                connection.rollback();
                throw new ServiceException(e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new ServiceException(e);
        }
    }

    public static void delete(long id) throws ServiceException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            try {
                AddressDAO addressDAO = new AddressDAO(connection);
                addressDAO.delete(id);
                PhoneDAO phoneDAO = new PhoneDAO(connection);
                phoneDAO.deleteByContactId(id);
                AttachmentDAO attachmentDAO = new AttachmentDAO(connection);
                attachmentDAO.deleteByContactId(id);
                ContactDAO contactDAO = new ContactDAO(connection);
                contactDAO.delete(id);
                connection.commit();
            } catch (DAOException e) {
                connection.rollback();
                throw new ServiceException(e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new ServiceException(e);
        }
    }

    private static <T extends Entity> void insertEntityList(ArrayList<T> list, AbstractDAO<T> dao)
            throws DAOException {
        for (T entity : list) {
            dao.insert(entity);
        }
    }


    private static <T extends Entity> ArrayList<T> updateEntityList(ArrayList<T> list, ArrayList<T> oldList, AbstractDAO<T> dao)
            throws DAOException {
        ArrayList<T> entitiesForInsert = new ArrayList<>();
        ArrayList<Long> entitiesIndexes = new ArrayList<>();
        for (T entity : list) {
            if (entity.getId() == 0) {
                entitiesForInsert.add(entity);
            } else {
                entitiesIndexes.add(entity.getId());
            }
        }
        list.removeAll(entitiesForInsert);
        ArrayList<T> entitiesForUpdate = new ArrayList<>(list);
        entitiesForUpdate.removeAll(oldList);
        ArrayList<Long> indexesForDelete = new ArrayList<>();
        for (T entity : oldList) {
            indexesForDelete.add(entity.getId());
        }
        indexesForDelete.removeIf(entitiesIndexes::contains);
        for (T entity : entitiesForInsert) {
            dao.insert(entity);
        }
        for (T entity : entitiesForUpdate) {
            dao.update(entity.getId(), entity);
        }
        for (Long id : indexesForDelete) {
            dao.delete(id);
        }
        return entitiesForInsert;
    }
}
