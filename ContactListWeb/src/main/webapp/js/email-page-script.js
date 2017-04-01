var emailTemplateSelect = document.getElementById('email-template');
var emailTextArea = document.getElementById('email-text');
var sendEmailButton = document.getElementById('send-email-button');
var sendEmailForm = document.getElementById('send-email');

emailTemplateSelect.onchange = function () {
    var selectedIndex = emailTemplateSelect.selectedIndex;
    emailTextArea.value = emailTemplateSelect[selectedIndex].value;
    emailTextArea.disabled = selectedIndex > 0;
};


sendEmailButton.onclick = function () {
    var input = document.createElement('input');
    input.type = 'hidden';
    input.name = 'template-index';
    input.value = emailTemplateSelect.selectedIndex;
    sendEmailForm.appendChild(input);
    sendEmailForm.submit();
};
