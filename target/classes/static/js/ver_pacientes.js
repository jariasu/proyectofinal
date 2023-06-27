
const tableBody = document.querySelector('#lista-pacientes tbody');

function construirFila(paciente) {

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

  return row;
}

fetch('/pacientes')
  .then(response => response.json())
  .then(data => data.forEach(paciente => tableBody.appendChild(construirFila(paciente))))
  .catch(error => console.error(error));

