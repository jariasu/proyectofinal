// Seleccionar el tbody de la tabla de la lista de odontólogos
const tableBody = document.querySelector('#lista-pacientes tbody');

// Función para construir una fila de la tabla a partir de un objeto odontólogo
function construirFila(paciente) {
  // Crear una fila de la tabla con datos del odontólogo y botones de eliminar y editar
  const row = document.createElement('tr');
   row.innerHTML = `
      <td>${paciente.id}</td>
      <td>${paciente.nombre}</td>
      <td>${paciente.apellido}</td>
      <td>${paciente.dni}</td>
      <td>${paciente.fechaIngreso}</td>
      <td>${paciente.domicilio.calle}</td>
      <td>${paciente.domicilio.numero}</td>
      <td>${paciente.domicilio.localidad}</td>
      <td>${paciente.domicilio.provincia}</td>
  `;
  // Devolver la fila creada
  return row;
}

// Hacer una petición GET al servidor para obtener los datos de los odontólogos y construir las filas de la tabla
fetch('/pacientes')
  .then(response => response.json())
  .then(data => data.forEach(paciente => tableBody.appendChild(construirFila(paciente))))
  .catch(error => console.error(error));

