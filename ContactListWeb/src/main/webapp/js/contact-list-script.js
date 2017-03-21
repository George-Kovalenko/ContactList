var addPhoneButton = document.getElementById('add-phone-button');
var phonePopup = document.getElementById('phone-popup');
var cancelPhoneButton = document.getElementById('cancel-phone-button');
var addAttachmentButton = document.getElementById('add-attachment-button');
var attachmentPopup = document.getElementById('attachment-popup');
var cancelAttachmentButton = document.getElementById('cancel-attachment-button');

addPhoneButton.onclick = function() {
    phonePopup.style.display = 'block';
};

cancelPhoneButton.onclick = function() {
    phonePopup.style.display = 'none';
};

addAttachmentButton.onclick = function () {
    attachmentPopup.style.display = 'block';
};

cancelAttachmentButton.onclick = function () {
    attachmentPopup.style.display = 'none';
};
