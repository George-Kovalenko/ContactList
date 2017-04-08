var Phone = function () {
    this.id = 0;
    this.countryCode = '';
    this.operatorCode = '';
    this.number = '';
    this.type = '';
    this.comment = '';
    this.contactId = 0;
};

var contactForm = document.getElementById('contact-form');
var saveContactButton = document.getElementById('save-contact-button');

var addPhoneButton = document.getElementById('add-phone-button');
var editPhoneButton = document.getElementById('edit-phone-button');
var deletePhoneButton = document.getElementById('delete-phone-button');
var phonePopup = document.getElementById('phone-popup');
var submitPhoneButton = document.getElementById('submit-phone-button');
var cancelPhoneButton = document.getElementById('cancel-phone-button');

var countryCodeField = document.getElementById('country-code');
var operatorCodeField = document.getElementById('operator-code');
var phoneNumberField = document.getElementById('phone-number');
var phoneTypeSelect = document.getElementById('phone-type-select');
var phoneComment = document.getElementById('phone-comment');

var isPhoneEdit = false;
var isNewPhoneEdit = false;
var newPhoneId = 0;
var editPhoneId = 0;

function openModalWindow(modalWindow) {
    modalWindow.style.display = 'block';
}

function closeModalWindow(modalWindow) {
    modalWindow.style.display = 'none';
}

addPhoneButton.onclick = function () {
    isPhoneEdit = false;
    countryCodeField.value = '';
    operatorCodeField.value = '';
    phoneNumberField.value = '';
    phoneTypeSelect.value = phoneTypeSelect[0].value;
    phoneComment.value = '';
    openModalWindow(phonePopup);
};

editPhoneButton.onclick = function () {
    isPhoneEdit = true;
    var checkedPhones = getCheckedItems('check-phone');
    var checkedNewPhones = getCheckedItems('check-new-phone');
    if (checkedPhones.length === 0 && checkedNewPhones.length === 0) {
        addErrorMessage('Телефон для редактирования не выбран.');
        openModalWindow(errorMessagePopup);
        return;
    }
    if (checkedPhones.length === 1 && checkedNewPhones.length === 0) {
        editPhoneId = checkedPhones[0];
        appendPhonePopupFields(editPhoneId, '');
        openModalWindow(phonePopup);
    } else if (checkedPhones.length === 0 && checkedNewPhones.length === 1) {
        isNewPhoneEdit = true;
        editPhoneId = checkedNewPhones[0];
        appendPhonePopupFields(editPhoneId, 'new-');
        openModalWindow(phonePopup);
    } else {
        addErrorMessage('Необходимо выбрать только один телефон для редактирования.');
        openModalWindow(errorMessagePopup);
    }
};

function appendPhonePopupFields(phoneId, prefix) {
    var number = document.getElementById(prefix + 'phone-number-' + phoneId).innerHTML.trim();
    countryCodeField.value = number.split(' ', 3)[0];
    operatorCodeField.value = number.split(' ', 3)[1];
    phoneNumberField.value = number.split(' ', 3)[2];
    phoneTypeSelect.value = document.getElementById(prefix + 'phone-type-' + phoneId).innerHTML.trim();
    phoneComment.value = document.getElementById(prefix + 'phone-comment-' + phoneId).innerHTML.trim();
}

deletePhoneButton.onclick = function () {
    var checkedPhones = getCheckedItems('check-phone');
    var checkedNewPhones = getCheckedItems('check-new-phone');
    if (checkedPhones.length === 0 && checkedNewPhones.length === 0) {
        addErrorMessage('Телефоны для удаления не выбраны.');
        openModalWindow(errorMessagePopup);
        return;
    }
    deleteItemsFromPage(checkedPhones, 'phone-');
    deleteItemsFromPage(checkedNewPhones, 'new-phone-');
};

cancelPhoneButton.onclick = function () {
    closeModalWindow(phonePopup);
};

submitPhoneButton.onclick = function () {
    if (checkPhoneBeforeSubmit()) {
        openModalWindow(errorMessagePopup);
        return;
    }
    if (!isPhoneEdit) {
        createNewPhone();
    } else {
        editPhone();
    }
    closeModalWindow(phonePopup);
};

saveContactButton.onclick = function () {
    if (checkInputFieldsBeforeSubmit()) {
        openModalWindow(errorMessagePopup);
        return;
    }
    fillPhoneList('');
    fillPhoneList('new-');
    fillAttachmentList('');
    fillAttachmentList('new-');
    contactForm.submit();
};

function fillPhoneList(prefix) {
    var phones = document.getElementsByClassName(prefix + 'phone');
    var phoneList = [];
    for (var i = 0; i < phones.length; i++) {
        var phone = new Phone();
        var id = prefix ? phones[i].id.split('-')[2] : phones[i].id.split('-')[1];
        var fullNumber = document.getElementById(prefix + 'phone-number-' + id).innerHTML.trim();
        phone.countryCode = fullNumber.split(' ', 3)[0];
        phone.operatorCode = fullNumber.split(' ', 3)[1];
        phone.number = fullNumber.split(' ', 3)[2];
        var type = document.getElementById(prefix + 'phone-type-' + id).innerHTML.trim();
        if (type) {
            phone.type = document.getElementById(type).index.toString();
        }
        phone.comment = document.getElementById(prefix + 'phone-comment-' + id).innerHTML.trim();
        phone.id = prefix ? 0 : id;
        phone.contactId = contactForm.action.split('=').length == 2 ? 0 : contactForm.action.split('=')[2];
        phoneList.push(phone);
    }
    var newChild = addItemsInHiddenInput(prefix + 'phones', JSON.stringify(phoneList));
    contactForm.appendChild(newChild);
}

function addItemsInHiddenInput(name, items) {
    var input = document.createElement('input');
    input.type = 'hidden';
    input.name = name;
    input.value = items;
    return input;
}

function createNewPhone() {
    newPhoneId++;
    var phone = new Phone();
    phone.id = newPhoneId;
    phone.countryCode = countryCodeField.value;
    phone.operatorCode = operatorCodeField.value;
    phone.number = phoneNumberField.value;
    phone.type = phoneTypeSelect.value;
    phone.comment = phoneComment.value;
    var phoneBody = document.getElementById('phone-body');
    var tableRow = document.createElement('tr');
    tableRow.id = 'new-phone-' + phone.id;
    tableRow.className = 'new-phone';
    var checkBoxCell = document.createElement('td');
    var input = document.createElement('input');
    input.type = 'checkbox';
    input.name = 'check-new-phone';
    input.value = phone.id;
    checkBoxCell.appendChild(input);
    var numberCell = document.createElement('td');
    numberCell.appendChild(document.createTextNode(phone.countryCode + ' ' + phone.operatorCode + ' ' + phone.number));
    numberCell.id = 'new-phone-number-' + phone.id;
    var phoneTypeCell = document.createElement('td');
    if (phone.type) {
        phoneTypeCell.appendChild(document.createTextNode(phone.type));
    }
    phoneTypeCell.value = phone.type;
    phoneTypeCell.id = 'new-phone-type-' + phone.id;
    var phoneCommentCell = document.createElement('td');
    phoneCommentCell.appendChild(document.createTextNode(phone.comment));
    phoneCommentCell.id = 'new-phone-comment-' + phone.id;
    tableRow.appendChild(checkBoxCell);
    tableRow.appendChild(numberCell);
    tableRow.appendChild(phoneTypeCell);
    tableRow.appendChild(phoneCommentCell);
    phoneBody.appendChild(tableRow);
}

function editPhone() {
    var prefix = '';
    if (isNewPhoneEdit) {
        prefix = 'new-';
        isNewPhoneEdit = false;
    }
    var numberCell = document.getElementById(prefix + 'phone-number-' + editPhoneId);
    numberCell.innerHTML = countryCodeField.value + ' ' + operatorCodeField.value + ' ' + phoneNumberField.value;
    var phoneTypeCell = document.getElementById(prefix + 'phone-type-' + editPhoneId);
    if (phoneTypeSelect.value) {
        phoneTypeCell.innerHTML = phoneTypeSelect.value;
    }
    var phoneCommentCell = document.getElementById(prefix + 'phone-comment-' + editPhoneId);
    phoneCommentCell.innerHTML = phoneComment.value;
}

function checkPhoneBeforeSubmit() {
    var errorMessages = [];
    var message;
    if ((message = checkTextInputField(countryCodeField, 5, false, containsDigits.name)) != '') {
        errorMessages.push(message);
    }
    if ((message = checkTextInputField(operatorCodeField, 5, false, containsDigits.name)) != '') {
        errorMessages.push(message);
    }
    if ((message = checkTextInputField(phoneNumberField, 7, false, containsDigits.name)) != '') {
        errorMessages.push(message);
    }
    if ((message = checkTextInputField(phoneComment, 255, false))) {
        errorMessages.push(message);
    }
    errorMessages.forEach(function (item) {
        addErrorMessage(item);
    });
    return errorMessages.length != 0;
}

countryCodeField.onkeyup = function () {
    if (checkTextInputField(this, 5, false, containsDigits.name) === '') {
        highlightInput(this, true);
    } else {
        highlightInput(this, false);
    }
};

operatorCodeField.onkeyup = function () {
    if (checkTextInputField(this, 5, false, containsDigits.name) === '') {
        highlightInput(this, true);
    } else {
        highlightInput(this, false);
    }
};

phoneNumberField.onkeyup = function () {
    if (checkTextInputField(this, 7, false, containsDigits.name) === '') {
        highlightInput(this, true);
    } else {
        highlightInput(this, false);
    }
};

phoneComment.onkeyup = function () {
    if (checkTextInputField(this, 255, false) === '') {
        highlightInput(this, true);
    } else {
        highlightInput(this, false);
    }
};

function getCheckedItems(checkName) {
    var checkBoxes = document.getElementsByName(checkName);
    var checked = [];
    for (var i = 0; i < checkBoxes.length; i++) {
        if (checkBoxes[i].checked) {
            checked.push(checkBoxes[i].value);
        }
    }
    return checked;
}

function deleteItemsFromPage(items, checkedValue) {
    for (var i = 0; i < items.length; i++) {
        var item = document.getElementById(checkedValue + items[i]);
        item.parentNode.removeChild(item);
    }
}


var Attachment = function () {
    this.id = 0;
    this.fileName = '';
    this.uploadDate = '';
    this.comment = '';
    this.contactId = '';
};

var addAttachmentButton = document.getElementById('add-attachment-button');
var editAttachmentButton = document.getElementById('edit-attachment-button');
var deleteAttachmentButton = document.getElementById('delete-attachment-button');
var attachmentPopup = document.getElementById('attachment-popup');
var submitAttachmentButton = document.getElementById('submit-attachment-button');
var cancelAttachmentButton = document.getElementById('cancel-attachment-button');

var attachmentComment = document.getElementById('attachment-comment');
var editFileExtension = '';

var isAttachmentEdit = false;
var isNewAttachmentEdit = false;
var newAttachmentId = 0;
var editAttachmentId = 0;

addAttachmentButton.onclick = function () {
    isAttachmentEdit = false;
    document.getElementById('file-path').type = 'file';
    document.getElementById('file-path').value = '';
    attachmentComment.value = '';
    openModalWindow(attachmentPopup);
};

deleteAttachmentButton.onclick = function () {
    var checkedAttachments = getCheckedItems('check-attachment');
    var checkedNewAttachments = getCheckedItems('check-new-attachment');
    if (checkedAttachments.length === 0 && checkedNewAttachments.length === 0) {
        addErrorMessage('Файлы для удаления не выбраны.');
        openModalWindow(errorMessagePopup);
        return;
    }
    deleteItemsFromPage(checkedAttachments, 'attachment-');
    deleteItemsFromPage(checkedNewAttachments, 'new-attachment-');
    deleteItemsFromPage(checkedNewAttachments, 'new-attachment-input-');
};

editAttachmentButton.onclick = function () {
    isAttachmentEdit = true;
    document.getElementById('file-path').value = '';
    var checkedAttachments = getCheckedItems('check-attachment');
    var checkedNewAttachments = getCheckedItems('check-new-attachment');
    if (checkedAttachments.length === 0 && checkedNewAttachments.length === 0) {
        addErrorMessage('Файл для редактирования не выбран.');
        openModalWindow(errorMessagePopup);
        return;
    }
    if (checkedAttachments.length === 1 && checkedNewAttachments.length === 0) {
        editAttachmentId = checkedAttachments[0];
        appendAttachmentPopupFields(editAttachmentId, '');
        openModalWindow(attachmentPopup);
    } else if (checkedAttachments.length === 0 && checkedNewAttachments.length === 1) {
        isNewAttachmentEdit = true;
        editAttachmentId = checkedNewAttachments[0];
        appendAttachmentPopupFields(editAttachmentId, 'new-');
        openModalWindow(attachmentPopup);
    } else {
        addErrorMessage('Необходимо выбрать только один файл для редактирования.');
        openModalWindow(errorMessagePopup);
    }
};

submitAttachmentButton.onclick = function () {
    if (checkAttachmentBeforeSubmit()) {
        openModalWindow(errorMessagePopup);
        return;
    }
    if (!isAttachmentEdit) {
        createNewAttachment();
    } else {
        editAttachment();
    }
    closeModalWindow(attachmentPopup);
};

cancelAttachmentButton.onclick = function () {
    closeModalWindow(attachmentPopup);
};

function createNewAttachment() {
    newAttachmentId++;
    var attachment = new Attachment();
    attachment.id = newAttachmentId;
    var fileName = document.getElementById('file-path').value.split('\\');
    attachment.fileName = fileName[fileName.length - 1];
    attachment.comment = attachmentComment.value;
    attachment.uploadDate = getDateTime();
    var attachmentBody = document.getElementById('attachment-body');
    var tableRow = document.createElement('tr');
    tableRow.id = 'new-attachment-' + attachment.id;
    tableRow.className = 'new-attachment';
    var checkBoxCell = document.createElement('td');
    var input = document.createElement('input');
    input.type = 'checkbox';
    input.name = 'check-new-attachment';
    input.value = attachment.id;
    checkBoxCell.appendChild(input);
    var fileNameCell = document.createElement('td');
    fileNameCell.id = 'new-attachment-file-name-' + attachment.id;
    fileNameCell.appendChild(document.createTextNode(attachment.fileName));
    var uploadDateCell = document.createElement('td');
    uploadDateCell.id = 'new-attachment-upload-date-' + attachment.id;
    uploadDateCell.appendChild(document.createTextNode(attachment.uploadDate));
    var commentCell = document.createElement('td');
    commentCell.id = 'new-attachment-comment-' + attachment.id;
    commentCell.appendChild(document.createTextNode(attachment.comment));
    var downloadCell = document.createElement('td');
    downloadCell.id = 'new-attachment-download-link-' + attachment.id;
    tableRow.appendChild(checkBoxCell);
    tableRow.appendChild(fileNameCell);
    tableRow.appendChild(uploadDateCell);
    tableRow.appendChild(commentCell);
    tableRow.appendChild(downloadCell);
    attachmentBody.appendChild(tableRow);
    var attachmentInput = document.getElementById('file-path');
    addFilePathInHiddenField(attachmentInput);
}

function addFilePathInHiddenField(attachmentInput) {
    var newAttachmentFileInput = attachmentInput;
    newAttachmentFileInput.id = 'new-attachment-input-' + newAttachmentId;
    newAttachmentFileInput.name = 'new-attachment-input-' + newAttachmentId;
    newAttachmentFileInput.style.display = 'none';
    var attachmentList = document.getElementById('attachment-input-fields');
    attachmentList.appendChild(newAttachmentFileInput);
    var fileInput = document.createElement('input');
    fileInput.type = 'file';
    fileInput.id = 'file-path';
    document.getElementById('path-to-file').appendChild(fileInput);
}

function appendAttachmentPopupFields(attachmentId, prefix) {
    document.getElementById('file-path').type = 'text';
    var fileName = document.getElementById(prefix + 'attachment-file-name-' + attachmentId).innerHTML.trim();
    var name = fileName.split('.');
    editFileExtension = '.' + name[name.length - 1];
    document.getElementById('file-path').value = name.length == 1 ? fileName :
        fileName.substring(0, fileName.lastIndexOf(editFileExtension));
    attachmentComment.value = document.getElementById(prefix + 'attachment-comment-' + attachmentId).innerHTML.trim();
}

function editAttachment() {
    var prefix = '';
    if (isNewAttachmentEdit) {
        prefix = 'new-';
        isNewAttachmentEdit = false;
    }
    var attachmentNameCell = document.getElementById(prefix + 'attachment-file-name-' + editAttachmentId);
    attachmentNameCell.innerHTML = document.getElementById('file-path').value + editFileExtension;
    var attachmentCommentCell = document.getElementById(prefix + 'attachment-comment-' + editAttachmentId);
    attachmentCommentCell.innerHTML = attachmentComment.value;
}

function getDateTime() {
    var date = new Date();
    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
}


function fillAttachmentList(prefix) {
    var attachments = document.getElementsByClassName(prefix + 'attachment');
    var attachmentList = [];
    for (var i = 0; i < attachments.length; i++) {
        var attachment = new Attachment();
        var id = prefix ? attachments[i].id.split('-')[2] : attachments[i].id.split('-')[1];
        attachment.fileName = document.getElementById(prefix + 'attachment-file-name-' + id).innerHTML.trim();
        attachment.uploadDate = document.getElementById(prefix + 'attachment-upload-date-' + id).innerHTML.trim();
        attachment.comment = document.getElementById(prefix + 'attachment-comment-' + id).innerHTML.trim();
        attachment.id = prefix ? 0 : id;
        attachment.contactId = contactForm.action.split('=').length == 2 ? 0 : contactForm.action.split('=')[2];
        attachmentList.push(attachment);
    }
    var newChild = addItemsInHiddenInput(prefix + 'attachments', JSON.stringify(attachmentList));
    contactForm.appendChild(newChild);
}

function checkAttachmentBeforeSubmit() {
    var errorMessages = [];
    var message;
    var filePath = document.getElementById('file-path');
    if (!isAttachmentEdit) {
        var maxSize = 20;
        var sizeMB = 1024 * 1024;
        var file = filePath.files[0];
        if (file === null || file === undefined) {
            errorMessages.push('Файл для присоединения не выбран.');
        } else if (file.size > maxSize * sizeMB) {
            errorMessages.push('Размер файла должен быть меньше ' + maxSize + ' МБ.');
        }

    } else if ((message = checkTextInputField(filePath, 100, false)) != '') {
        errorMessages.push(message);
    }
    if ((message = checkTextInputField(phoneComment, 255, false)) != '') {
        errorMessages.push(message);
    }
    errorMessages.forEach(function (item) {
        addErrorMessage(item);
    });
    return errorMessages.length != 0;
}

document.getElementById('file-path').onkeyup = function () {
    if (isAttachmentEdit) {
        if (checkTextInputField(this, 100, false) === '') {
            highlightInput(this, true);
        } else {
            highlightInput(this, false);
        }
    }
};

attachmentComment.onkeyup = function () {
    if (checkTextInputField(this, 255, false) === '') {
        highlightInput(this, true);
    } else {
        highlightInput(this, false);
    }
};

var contactPhoto = document.getElementById('contact-photo-image');
var photoPath = document.getElementById('photo-path');
var photoPopup = document.getElementById('photo-popup');
var submitPhotoButton = document.getElementById('submit-photo-button');
var cancelPhotoButton = document.getElementById('cancel-photo-button');
var deletePhotoButton = document.getElementById('delete-photo-button');

contactPhoto.onclick = function () {
    photoPath.value = '';
    openModalWindow(photoPopup);
};

cancelPhotoButton.onclick = function () {
    closeModalWindow(photoPopup);
};

submitPhotoButton.onclick = function () {
    var image = photoPath.files[0];
    if (image) {
        var maxSize = 3;
        var sizeMB = 1024 * 1024;
        if (image.size > maxSize * sizeMB) {
            addErrorMessage('Размер фотографии должен быть не больше ' + maxSize + ' МБ.');
            openModalWindow(errorMessagePopup);
            return;
        }
        var fileReader = new FileReader;
        fileReader.onload = function () {
            contactPhoto.src = this.result;
        };
        fileReader.readAsDataURL(image);
        var photoInputField = document.getElementById('photo-input-field');
        photoInputField.parentNode.removeChild(photoInputField);
        photoPath.name = 'photo-field';
        photoPath.id = 'photo-input-field';
        photoPath.style.display = 'none';
        contactPhoto.appendChild(photoPath);
        photoPath = document.createElement('input');
        photoPath.type = 'file';
        photoPath.id = 'photo-path';
        photoPath.accept = 'image/jpeg, image/png';
        document.getElementById('photo-path-field').appendChild(photoPath);
    } else {
        addErrorMessage('Файл не выбран.');
        openModalWindow(errorMessagePopup);
        return;
    }
    closeModalWindow(photoPopup);
};

deletePhotoButton.onclick = function () {
    console.log(contactPhoto.src);
    if (contactPhoto.src.indexOf('icons/default_contact_icon.jpeg') != -1) {
        addErrorMessage('Фотография для контакта не загружена.');
        openModalWindow(errorMessagePopup);
        return;
    }
    var photoInputField = document.getElementById('photo-input-field');
    photoInputField.name = 'photo-field-delete';
    contactPhoto.src = 'icons/default_contact_icon.jpeg';
    closeModalWindow(photoPopup);
};

var errorMessagePopup = document.getElementById('error-message-popup');
var errorMessage = document.getElementById('error-message');
var submitErrorMessageButton = document.getElementById('submit-error-message-button');

submitErrorMessageButton.onclick = function () {
    closeModalWindow(errorMessagePopup);
    errorMessage.innerHTML = null;
};

function addErrorMessage(message) {
    var newError = document.createElement('p');
    newError.innerHTML = message;
    errorMessage.appendChild(newError);
}

var firstName = document.getElementById('first-name');
firstName.onkeyup = function () {
    if (checkTextInputField(this, 30, true, containsLettersHyphen.name) === '') {
        highlightInput(this, true);
    } else {
        highlightInput(this, false);
    }
};

var lastName = document.getElementById('last-name');
lastName.onkeyup = function () {
    if (checkTextInputField(this, 30, true, containsLettersHyphen.name) === '') {
        highlightInput(this, true);
    } else {
        highlightInput(this, false);
    }
};

var middleName = document.getElementById('middle-name');
middleName.onkeyup = function () {
    if (checkTextInputField(this, 30, false, containsLettersHyphen.name) === '') {
        highlightInput(this, true);
    } else {
        highlightInput(this, false);
    }
};

var day = document.getElementById('day');
day.onkeyup = function () {
    checkDateInput();
};

var month = document.getElementById('month');
month.onkeyup = function () {
    checkDateInput();
};

var year = document.getElementById('year');
year.onkeyup = function () {
    checkDateInput();
};

var nationality = document.getElementById('nationality');
nationality.onkeyup = function () {
    if (checkTextInputField(this, 45, false, containsLettersHyphenSpace.name) === '') {
        highlightInput(this, true);
    } else {
        highlightInput(this, false);
    }
};

var website = document.getElementById('website');
website.onkeyup = function () {
    if (checkTextInputField(this, 45, false, checkWebsite.name) === '') {
        highlightInput(this, true);
    } else {
        highlightInput(this, false);
    }
};

var email = document.getElementById('email');
email.onkeyup = function () {
    if (checkTextInputField(this, 45, false, checkEmail.name) === '') {
        highlightInput(this, true);
    } else {
        highlightInput(this, false);
    }
};

var job = document.getElementById('job');
job.onkeyup = function () {
    if (checkTextInputField(this, 100, false) === '') {
        highlightInput(this, true);
    } else {
        highlightInput(this, false);
    }
};

var country = document.getElementById('country');
country.onkeyup = function () {
    if (checkTextInputField(this, 45, false, containsLettersHyphenSpace.name) === '') {
        highlightInput(this, true);
    } else {
        highlightInput(this, false);
    }
};

var city = document.getElementById('city');
city.onkeyup = function () {
    if (checkTextInputField(this, 45, false, containsLettersHyphenSpace.name) === '') {
        highlightInput(this, true);
    } else {
        highlightInput(this, false);
    }
};

var street = document.getElementById('street');
street.onkeyup = function () {
    if (checkTextInputField(this, 45, false, containsLettersDigitsHyphenSpace.name) === '') {
        highlightInput(this, true);
    } else {
        highlightInput(this, false);
    }
};

var house = document.getElementById('house');
house.onkeyup = function () {
    if (checkTextInputField(this, 10, false, containsDigits.name) === '') {
        highlightInput(this, true);
    } else {
        highlightInput(this, false);
    }
};

var flat = document.getElementById('flat');
flat.onkeyup = function () {
    if (checkTextInputField(this, 10, false, containsDigits.name) === '') {
        highlightInput(this, true);
    } else {
        highlightInput(this, false);
    }
};

var postcode = document.getElementById('postcode');
postcode.onkeyup = function () {
    if (checkTextInputField(this, 10, false, containsDigits.name) === '') {
        highlightInput(this, true);
    } else {
        highlightInput(this, false);
    }
};

function checkInputFieldsBeforeSubmit() {
    var errorMessages = [];
    var message;
    if ((message = checkTextInputField(firstName, 30, true, containsLettersHyphen.name)) != '') {
        errorMessages.push(message);
    }
    if ((message = checkTextInputField(lastName, 30, true, containsLettersHyphen.name)) != '') {
        errorMessages.push(message);
    }
    if ((message = checkTextInputField(middleName, 30, false, containsLettersHyphen.name)) != '') {
        errorMessages.push(message);
    }
    if ((message = checkDateInput()) != '') {
        errorMessages.push(message);
    }
    if ((message = checkTextInputField(nationality, 45, false, containsLettersHyphenSpace.name)) != '') {
        errorMessages.push(message);
    }
    if ((message = checkTextInputField(job, 100, false)) != '') {
        errorMessages.push(message);
    }
    if ((message = checkTextInputField(website, 45, false, checkWebsite.name))) {
        errorMessages.push(message);
    }
    if ((message = checkTextInputField(email, 45, false, checkEmail.name))) {
        errorMessages.push(message);
    }
    if ((message = checkTextInputField(country, 45, false, containsLettersHyphenSpace.name)) != '') {
        errorMessages.push(message);
    }
    if ((message = checkTextInputField(city, 45, false, containsLettersHyphenSpace.name)) != '') {
        errorMessages.push(message);
    }
    if ((message = checkTextInputField(street, 45, false, containsLettersDigitsHyphenSpace.name)) != '') {
        errorMessages.push(message);
    }
    if ((message = checkTextInputField(house, 10, false, containsDigits.name)) != '') {
        errorMessages.push(message);
    }
    if ((message = checkTextInputField(flat, 10, false, containsDigits.name)) != '') {
        errorMessages.push(message);
    }
    if ((message = checkTextInputField(postcode, 10, false, containsDigits.name)) != '') {
        errorMessages.push(message);
    }
    errorMessages.forEach(function (item) {
        addErrorMessage(item);
    });
    return errorMessages.length != 0;
}

function checkTextInputField(inputField, maxLength, required, checkFunction) {
    var text = inputField.value.trim();
    var displayFieldName = inputField.parentNode.textContent.trim();
    if (required) {
        var minLength = 2;
        if (text.length === 0) {
            return 'Поле "' + displayFieldName + '" должно быть заполнено.';
        } else if (text.length < minLength) {
            return 'Длина поля "' + displayFieldName + '" должна быть не меньше ' + minLength + ' символов.';
        }
    }
    if (text.length > maxLength) {
        return 'Длина поля "' + displayFieldName + '" должна быть не больше ' + maxLength + ' символов.';
    } else if (text.length === 0) {
        return '';
    } else {
        switch (checkFunction) {
            case containsLettersHyphen.name:
                if (!containsLettersHyphen(text)) {
                    return 'Поле "' + displayFieldName + '" может содержать только буквы.';
                }
                break;
            case containsLettersHyphenSpace.name:
                if (!containsLettersHyphenSpace(text)) {
                    return 'Поле "' + displayFieldName + '" может содержать только буквы.';
                }
                break;
            case containsLettersDigitsHyphenSpace.name:
                if (!containsLettersDigitsHyphenSpace(text)) {
                    return 'Поле "' + displayFieldName + '" может содержать только буквы и цифры.';
                }
                break;
            case containsDigits.name:
                if (!containsDigits(text)) {
                    return 'Поле "' + displayFieldName + '" может содержать только цифры.';
                }
                break;
            case checkEmail.name:
                if (!checkEmail(inputField)) {
                    return 'Поле "' + displayFieldName + '" некорректно заполнено.';
                }
                break;
            case checkWebsite.name:
                if (!checkWebsite(inputField)) {
                    return 'Поле "' + displayFieldName + '" некорректно заполнено.';
                }
                break;
            default:
                break;
        }

    }
    return '';
}

function checkDateInput() {
    var dayCorrect = true;
    var monthCorrect = true;
    var yearCorrect = true;
    var message = '';
    if (day.value.length > 0 || month.value.length > 0 || year.value.length > 0) {
        var date = new Date(year.value, month.value - 1, day.value);
        dayCorrect = false;
        monthCorrect = false;
        yearCorrect = false;
        if (date == 'Invalid Date') {
            message += 'Дата введена неправильно.<br>';
        } else if (date > new Date()) {
            message += 'Введеная дата больше текущей.<br>';
        } else {
            dayCorrect = date.getDate() == day.value;
            monthCorrect = date.getMonth() == month.value - 1;
            yearCorrect = date.getFullYear() == year.value && year.value >= 1917;
            if (!dayCorrect) {
                message += 'Поле "День" некорректно заполнено.<br>';
            }
            if (!monthCorrect) {
                message += 'Поле "Месяц" некорректно заполнено.<br>';
            }
            if (!yearCorrect) {
                message += 'Поле "Год" некорректно заполнено.<br>';
            }
        }
    }
    highlightInput(day, dayCorrect);
    highlightInput(month, monthCorrect);
    highlightInput(year, yearCorrect);
    return message;
}

function checkEmail(inputElement) {
    var regex = /^[\w]{1}[\w\.]*@[\w]+\.[a-z]{2,4}$/i;
    return regex.test(inputElement.value.trim());
}

function checkWebsite(inputElement) {
    var regex = /^[\w]*\.[a-z]{2,4}$/i;
    return regex.test(inputElement.value.trim());
}

var alphabet_ru = 'abcdefghijklmnopqrstuvwxyz';
var alphabet_en = 'абвгдеёжзийклмнопрстуфхцчшщъыьэюя';
var digits = '0123456789';
var hyphen = '-';
var space = ' ';

function containsDigits(value) {
    return containsOnlyCharsIgnoreCase(value, digits);
}

function containsLettersHyphen(value) {
    return containsOnlyCharsIgnoreCase(value, alphabet_en + alphabet_ru + hyphen);
}

function containsLettersHyphenSpace(value) {
    return containsOnlyCharsIgnoreCase(value, alphabet_en + alphabet_ru + hyphen + space);
}

function containsLettersDigitsHyphenSpace(value) {
    return containsOnlyCharsIgnoreCase(value, alphabet_en + alphabet_ru + digits + hyphen + space);
}

function containsOnlyCharsIgnoreCase(value, chars) {
    var lowerCaseValue = value.toLowerCase();
    var lowerCaseChars = chars.toLowerCase();
    for (var i = 0; i < value.length; i++) {
        if (lowerCaseChars.indexOf(lowerCaseValue.charAt(i)) === -1) {
            return false;
        }
    }
    return true;
}

function highlightInput(inputElement, isCorrect) {
    if (isCorrect) {
        inputElement.style.borderColor = 'initial';
    } else {
        inputElement.style.borderColor = 'red';
    }
}

document.getElementById('back-button').onclick = function () {
    window.location.href = 'controller?command=show_contact_list&page=1';
};