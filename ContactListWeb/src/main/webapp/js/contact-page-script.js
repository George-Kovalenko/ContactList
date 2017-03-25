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
    if (checkedPhones.length > 0) {
        editPhoneId = checkedPhones[0];
        appendPhonePopupFields(editPhoneId, '');
        openModalWindow(phonePopup);
    } else {
        var checkedNewPhones = getCheckedItems('check-new-phone');
        if (checkedNewPhones.length > 0) {
            isNewPhoneEdit = true;
            editPhoneId = checkedNewPhones[0];
            appendPhonePopupFields(editPhoneId, 'new-');
            openModalWindow(phonePopup);
        }
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
    deleteItemsFromPage(checkedPhones, 'phone-');
    var checkedNewPhones = getCheckedItems('check-new-phone');
    deleteItemsFromPage(checkedNewPhones, 'new-phone-');
};

cancelPhoneButton.onclick = function () {
    closeModalWindow(phonePopup);
};

submitPhoneButton.onclick = function () {
    if (!isPhoneEdit) {
        createNewPhone();
    } else {
        editPhone();
    }
    closeModalWindow(phonePopup);
};

saveContactButton.onclick = function () {
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
        phone.contactId = contactForm.action.split('=', 3)[2];
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

var filePath = document.getElementById('file-path');
var attachmentComment = document.getElementById('attachment-comment');

var isAttachmentEdit = false;
var isNewAttachmentEdit = false;
var newAttachmentId = 0;
var editAttachmentId = 0;

addAttachmentButton.onclick = function () {
    isAttachmentEdit = false;
    filePath.value = filePath.defaultValue;
    filePath.style.display = 'block';
    attachmentComment.value = '';
    openModalWindow(attachmentPopup);
};

deleteAttachmentButton.onclick = function () {
    var checkedAttachments = getCheckedItems('check-attachment');
    deleteItemsFromPage(checkedAttachments, 'attachment-');
    var newCheckedAttachments = getCheckedItems('check-new-attachment');
    deleteItemsFromPage(newCheckedAttachments, 'new-attachment-');
};

editAttachmentButton.onclick = function () {
    isAttachmentEdit = true;
    var checkedAttachments = getCheckedItems('check-attachment');
    if (checkedAttachments.length > 0) {
        editAttachmentId = checkedAttachments[0];
        console.log(editAttachmentId);
        appendAttachmentPopupFields(editAttachmentId, '');
        openModalWindow(attachmentPopup);
    } else {
        var checkedNewAttachments = getCheckedItems('check-new-attachment');
        if (checkedAttachments.length > 0) {
            isNewAttachmentEdit = true;
            editAttachmentId = checkedNewAttachments[0];
            appendAttachmentPopupFields(editAttachmentId, 'new-');
            openModalWindow(attachmentPopup);
        }
    }
};

submitAttachmentButton.onclick = function () {
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
    var fileName = filePath.value.split('\\');
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
    tableRow.appendChild(checkBoxCell);
    tableRow.appendChild(fileNameCell);
    tableRow.appendChild(uploadDateCell);
    tableRow.appendChild(commentCell);
    attachmentBody.appendChild(tableRow);
    var attachmentInput = document.getElementById('file-path');
    addFilePathInHiddenField(attachmentInput);
}

function addFilePathInHiddenField(attachmentInput) {
    var newAttachmentFileInput = attachmentInput;
    newAttachmentFileInput.id = 'new-attachment-input-' + newAttachmentId;
    newAttachmentFileInput.name = 'new-attachment-input-' + newAttachmentId;
    newAttachmentFileInput.style.display = 'none';
    console.log(newAttachmentFileInput.value);
    var attachmentList = document.getElementById('attachment-input-fields');
    attachmentList.appendChild(newAttachmentFileInput);
    var fileInput = document.createElement('input');
    fileInput.type = 'file';
    fileInput.id = 'file-path';
    document.getElementById('path-to-file').appendChild(fileInput);
}

function appendAttachmentPopupFields(attachmentId, prefix) {
    document.getElementById('file-path').style.display = 'none';
    attachmentComment.value = document.getElementById(prefix + 'attachment-comment-' + attachmentId).innerHTML.trim();
}

function editAttachment() {
    var prefix = '';
    if (isNewAttachmentEdit) {
        prefix = 'new-';
        isNewAttachmentEdit = false;
    }
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
        attachment.fileName = document.getElementById(prefix + "attachment-file-name-" + id).innerHTML.trim();
        attachment.uploadDate = document.getElementById(prefix + 'attachment-upload-date-' + id).innerHTML.trim();
        attachment.comment = document.getElementById(prefix + 'attachment-comment-' + id).innerHTML.trim();
        attachment.id = prefix ? 0 : id;
        attachment.contactId = contactForm.action.split('=', 3)[2];
        attachmentList.push(attachment);
    }
    var newChild = addItemsInHiddenInput(prefix + 'attachments', JSON.stringify(attachmentList));
    contactForm.appendChild(newChild);
}