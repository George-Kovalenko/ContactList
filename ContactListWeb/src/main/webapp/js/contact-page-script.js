function Phone() {
    this.id = 0;
    this.countryCode = '';
    this.operatorCode = '';
    this.number = '';
    this.type = '';
    this.comment = '';
}

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

addPhoneButton.onclick = function() {
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

cancelPhoneButton.onclick = function() {
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
    contactForm.submit();
};

function fillPhoneList(prefix) {
    var phones = document.getElementsByClassName(prefix + 'phone');
    var phoneList = [];
    for (var i = 0; i < phones.length; i++) {
        var phone = new Phone();
        phone.id = prefix ? 0 : phones[i].id.split('-')[1];
        console.log(phone.id);
        var fullNumber = document.getElementById(prefix + 'phone-number-' + phone.id).innerHTML.trim();
        phone.countryCode = fullNumber.split(' ')[0];
        phone.operatorCode = fullNumber.split(' ')[1];
        phone.number = fullNumber.split(' ')[2];
        var type = document.getElementById(prefix + 'phone-type-' + phone.id).innerHTML.trim();
        if (type) {
            phone.type = document.getElementById(type).index.toString();
        }
        phone.comment = document.getElementById(prefix + 'phone-comment-' + phone.id).innerHTML.trim();
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
    for(var i = 0; i < items.length; i++) {
        var item = document.getElementById(checkedValue + items[i]);
        item.parentNode.removeChild(item);
    }
}

var addAttachmentButton = document.getElementById('add-attachment-button');
var attachmentPopup = document.getElementById('attachment-popup');
var cancelAttachmentButton = document.getElementById('cancel-attachment-button');

addAttachmentButton.onclick = function () {
    openModalWindow(attachmentPopup);
};

cancelAttachmentButton.onclick = function () {
    closeModalWindow(attachmentPopup);
};
