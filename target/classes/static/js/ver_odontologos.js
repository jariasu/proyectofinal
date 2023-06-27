
const tableBody = document.querySelector('#lista-odontologos tbody');

function construirFila(odontologo) {

  const row = document.createElement('tr');
  row.innerHTML = `
    <td>${odontologo.id}</td>
    <td>${odontologo.nombre}</td>
    <td>${odontologo.apellido}</td>
    <td>${odontologo.matricula}</td>
  `;

  return row;
}


fetch('/odontologos')
  .then(response => response.json())
  .then(data => data.forEach(odontologo => tableBody.appendChild(construirFila(odontologo))))
  .catch(error => console.error(error));

