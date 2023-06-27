
const tableBody = document.querySelector('#lista-turnos tbody');


function construirFila(turno) {
 
  const row = document.createElement('tr');
  row.innerHTML = `
    <td>${turno.id} - Turno Generado: ${turno.fechaAltaTurno} - Fecha de Turno: ${turno.fechaDeTurno} </td>

    <td>ID: ${turno.pacienteId} - Nombre: ${turno.pacienteNombre} - Apellido: ${turno.pacienteApellido}</td>
    <td>ID: ${turno.odontologoId} - Nombre: ${turno.odontologoNombre} - Apellido: ${turno.odontologoApellido}</td>
  `;

  return row;
}


fetch('/turnos')
  .then(response => response.json())
  .then(data => data.forEach(turno => tableBody.appendChild(construirFila(turno))))
  .catch(error => console.error(error));