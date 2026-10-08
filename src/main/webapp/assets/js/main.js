// Show employer-only fields on the registration form
document.addEventListener('DOMContentLoaded', function () {
  var role = document.getElementById('role');
  if (role) {
    var toggle = function () {
      document.querySelectorAll('.employer-only').forEach(function (el) { el.style.display = role.value === 'EMPLOYER' ? '' : 'none'; });
      document.querySelectorAll('.candidate-only').forEach(function (el) { el.style.display = role.value === 'EMPLOYER' ? 'none' : ''; });
    };
    role.addEventListener('change', toggle);
    toggle();
  }
  // confirm dangerous actions
  document.querySelectorAll('[data-confirm]').forEach(function (el) {
    el.addEventListener('submit', function (e) { if (!confirm(el.getAttribute('data-confirm'))) e.preventDefault(); });
  });
});
