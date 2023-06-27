formulario.addEventListener('submit', (event) => {
  event.preventDefault(); // Prevenir el envío por defecto del formulario


  const odontologo = {
    nombre: nombre.value,
    apellido: apellido.value,
    matricula: matricula.value,
  };

  const settings = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(odontologo)
  };


  fetch('/odontologos', settings)
    .then(response => {
      if (!response.ok) {
        throw new Error('Error al enviar los datos');
      }

      alert('Los datos se han guardado correctamente');
      window.location.href='/odontologos.html'
    })
    .catch(error => {
      console.error(error);
      alert('Ha ocurrido un error al guardar los datos');
      location.reload();
    });
});
