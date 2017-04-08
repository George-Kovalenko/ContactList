var contactListForm = document.getElementById('contact-list-form');
var deleteContactButton = document.getElementById('delete-contact-button');

deleteContactButton.onclick = function () {
    if (getCheckedItems('check-contact').length === 0) {
        addErrorMessage('Контакты для удаления не выбраны.');
        openModalWindow(errorMessagePopup);
        return;
    }
    contactListForm.action = 'controller?command=delete_contact';
    contactListForm.submit();
};

var sendMailButton = document.getElementById('send-mail-button');

sendMailButton.onclick = function () {
    if (getCheckedItems('check-contact').length === 0) {
        addErrorMessage('Контакты для отправки email не выбраны.');
        openModalWindow(errorMessagePopup);
        return;
    }
    contactListForm.action = 'controller?command=show_email_page';
    contactListForm.submit();
};

function openModalWindow(modalWindow) {
    modalWindow.style.display = 'block';
}

function closeModalWindow(modalWindow) {
    modalWindow.style.display = 'none';
}

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