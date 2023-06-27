// Seleccionar el tbody de la tabla de la lista de odontólogos
const tableBody = document.querySelector('#lista-odontologos tbody');

// Función para construir una fila de la tabla a partir de un objeto odontólogo
function construirFila(odontologo) {
  // Crear una fila de la tabla con datos del odontólogo y botones de eliminar y editar
  const row = document.createElement('tr');
  row.innerHTML = `
    <td>${odontologo.id}</td>
    <td>${odontologo.nombre}</td>
    <td>${odontologo.apellido}</td>
    <td>${odontologo.matricula}</td>
  `;
  // Devolver la fila creada
  return row;
}

// Hacer una petición GET al servidor para obtener los datos de los odontólogos y construir las filas de la tabla
fetch('/odontologos')
  .then(response => response.json())
  .then(data => data.forEach(odontologo => tableBody.appendChild(construirFila(odontologo))))
  .catch(error => console.error(error));

