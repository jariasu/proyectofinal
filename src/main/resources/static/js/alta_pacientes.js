const btnEnviar = document.getElementById('btnEnviar');


formulario.addEventListener('submit', (event) => {
  event.preventDefault(); 
    const domicilio = {
      calle: calle.value,
      localidad: localidad.value,
      numero: numero.value,
      provincia: provincia.value
    };

  const paciente = {
    nombre: nombre.value,
    apellido: apellido.value,
    dni: dni.value,
    domicilio: domicilio,
  };

const settings = {method: 'POST',
                     headers: {
                       'Content-Type': 'application/json'
                     },
                     body: JSON.stringify(paciente)}



  fetch('/pacientes', settings  )
  .then(response => {
    if (!response.ok) {
      throw new Error('Error al enviar los datos');
    }

    alert('Los datos se han guardado correctamente');
window.location.href = "/pacientes.html";
  })
  .catch(error => {
    console.error(error);
    alert('Ha ocurrido un error al guardar los datos');
    location.reload();
  });
});
