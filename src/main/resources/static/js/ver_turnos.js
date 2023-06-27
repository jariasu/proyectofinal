// Seleccionar el tbody de la tabla de la lista de odontólogos
const tableBody = document.querySelector('#lista-turnos tbody');

// Función para construir una fila de la tabla a partir de un objeto odontólogo
function construirFila(turno) {
  // Crear una fila de la tabla con datos del odontólogo y botones de eliminar y editar
  const row = document.createElement('tr');
  row.innerHTML = `
    <td>${turno.id} - Turno Generado: ${turno.fechaAltaTurno} - Fecha de Turno: ${turno.fechaDeTurno} </td>

    <td>ID: ${turno.pacienteId} - Nombre: ${turno.pacienteNombre} - Apellido: ${turno.pacienteApellido}</td>
    <td>ID: ${turno.odontologoId} - Nombre: ${turno.odontologoNombre} - Apellido: ${turno.odontologoApellido}</td>
  `;
  // Devolver la fila creada
  return row;
}

// Hacer una petición GET al servidor para obtener los datos de los odontólogos y construir las filas de la tabla
fetch('/turnos')
  .then(response => response.json())
  .then(data => data.forEach(turno => tableBody.appendChild(construirFila(turno))))
  .catch(error => console.error(error));