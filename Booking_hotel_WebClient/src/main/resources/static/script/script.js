// form validation
(function () {
    'use strict'
  
    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.querySelectorAll('.needs-validation')
  
    // Loop over them and prevent submission
    Array.prototype.slice.call(forms)
      .forEach(function (form) {
        form.addEventListener('submit', function (event) {
          if (!form.checkValidity()) {
            event.preventDefault()
            event.stopPropagation()
          }
  
          form.classList.add('was-validated')
        }, false)
      })
  })()

// payment-hide.js
function initPaymentMethodToggle() {
  const creditCardInfo = document.getElementById('credit-card-info');
  const creditRadio = document.getElementById('credit');
  const debitRadio = document.getElementById('debit');

  function toggleCreditCardInfo() {
      if (creditRadio.checked) {
          creditCardInfo.style.display = 'block';
      } else {
          creditCardInfo.style.display = 'none';
      }
  }

  toggleCreditCardInfo();

  creditRadio.addEventListener('change', toggleCreditCardInfo);
  debitRadio.addEventListener('change', toggleCreditCardInfo);
}

// เรียกใช้ฟังก์ชันเมื่อหน้าเว็บโหลดเสร็จ
document.addEventListener('DOMContentLoaded', initPaymentMethodToggle);
