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
    <td><button class="eliminar btn btn-danger" data-id="${odontologo.id}">Eliminar</button></td>
    <td><button class="editar btn btn-warning" data-id="${odontologo.id}">Editar</button></td>
  `;
  // Devolver la fila creada
  return row;
}

// Hacer una petición GET al servidor para obtener los datos de los odontólogos y construir las filas de la tabla
fetch('/odontologos')
  .then(response => response.json())
  .then(data => data.forEach(odontologo => tableBody.appendChild(construirFila(odontologo))))
  .catch(error => console.error(error));

// Eventos de clic para los botones de eliminar y editar odontólogos
document.addEventListener('click', (event) => {
  if (event.target.classList.contains('eliminar')) {
    const id = event.target.dataset.id;
    fetch(`/odontologos/${id}`, { method: 'DELETE' })
      .then(() => {
        alert('Odontólogo eliminado correctamente');
        event.target.closest('tr').remove();
      })
      .catch(error => console.error(error));
  } else if (event.target.classList.contains('editar')) {
    const id = event.target.dataset.id;
setTimeout(() => {
  document.getElementById('btnModificar').scrollIntoView({ behavior: 'smooth' });
}, 100);



    mostrarFormularioModificar(id);
  } else if (event.target.id === 'cancelar_modificacion') {
    document.getElementById('div_odontologo_updating').style.display = 'none';
  }
});

// Función para mostrar el formulario de edición y rellenar los campos con los datos del odontólogo
function mostrarFormularioModificar(id) {
  // Obtener los datos del odontólogo de la fila correspondiente en la tabla
  const [nombre, apellido, matricula] = document.querySelector(`[data-id="${id}"]`).closest('tr').querySelectorAll('td:not(:first-child)');

  // Rellenar los campos del formulario con los datos del odontólogo
  document.getElementById('odontologo_id').value = id;
  document.getElementById('nombre').value = nombre.textContent;
  document.getElementById('apellido').value = apellido.textContent;
  document.getElementById('matricula').value = matricula.textContent;

  // Mostrar el formulario de modificación
  document.getElementById('div_odontologo_updating').style.display = 'block';
  document.getElementById('h4_modificar').style.display = 'block';
  }

// Evento para enviar los datos del formulario de modificación al servidor cuando se hace clic en el botón de guardar
document.getElementById('update_odontologo_form').addEventListener('submit', async (event) => {
  event.preventDefault();

  // Obtener los datos del formulario
  const formData = {
    id: document.querySelector('#odontologo_id').value,
    nombre: document.querySelector('#nombre').value,
    apellido: document.querySelector('#apellido').value,
    matricula: document.querySelector('#matricula').value,
  };

const response = await fetch('/odontologos', {
  method: 'PUT',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify(formData)
});

if (response.ok) {
  const responseData = await response.json();
  alert('El odontólogo ha sido actualizado exitosamente con los siguientes datos: ' + JSON.stringify(responseData));
  location.reload();
} else if (response.status === 400) {
  try {
    const errorResponse = await response.json();
    alert('Ha ocurrido un error en el servidor: ' + errorResponse.message);
  } catch (error) {
    alert('Ha ocurrido un error en el servidor: ' + response.statusText);
  }
  throw new Error('Error en el servidor');
} else {
  throw new Error('Error en el servidor');
}

});

// Evento click para recargar la tabla
document.getElementById('recargar_tabla_btn').addEventListener('click', () => {
  // Hacer una petición GET al servidor para obtener los datos de los odontólogos y construir las filas de la tabla
    document.getElementById('buscar_odontologo_input').value = '';
  fetch('/odontologos')
    .then(response => response.json())
    .then(data => {
      // Eliminar las filas actuales de la tabla
      tableBody.innerHTML = '';
      // Agregar las filas de la tabla con los nuevos datos obtenidos del servidor
      data.forEach(odontologo => tableBody.appendChild(construirFila(odontologo)));
    })
    .catch(error => console.error(error));
});

// Agregar un evento click al botón "Buscar" para realizar la solicitud GET
document.getElementById('buscar_odontologo_btn').addEventListener('click', async () => {
  // Obtener el valor del input que contiene el ID
  const id = document.getElementById('buscar_odontologo_input').value;
  document.getElementById('buscar_odontologo_input').value = '';

  try {
    // Realizar la solicitud GET al servidor con el valor del ID en la URL
    const response = await fetch(`/odontologos/${id}`);
    const odontologo = await response.json();
    tableBody.innerHTML = '';

    // Si se encuentra un odontólogo con el ID proporcionado, construir una fila en la tabla con los datos del odontólogo encontrado
    const row = construirFila(odontologo);
    tableBody.appendChild(row);
  } catch (error) {
    // Si no se encuentra un odontólogo con el ID proporcionado, mostrar un mensaje de error
    alert('No se ha encontrado ningún odontólogo con el ID proporcionado.');
  }
});


// Agregar un evento click al botón "Buscar" para realizar la solicitud GET
document.getElementById('boton_odontologo_matricula').addEventListener('click', async () => {
  // Obtener el valor del input que contiene el ID
  const matricula = document.getElementById('buscar_odontologo_matricula_input').value;
  document.getElementById('buscar_odontologo_matricula_input').value = '';

  try {
    // Realizar la solicitud GET al servidor con el valor del ID en la URL
    const response = await fetch(`/odontologos/matricula/${matricula}`);
    const odontologo = await response.json();
    tableBody.innerHTML = '';

    // Si se encuentra un odontólogo con el ID proporcionado, construir una fila en la tabla con los datos del odontólogo encontrado
    const row = construirFila(odontologo);
    tableBody.appendChild(row);
  } catch (error) {
    // Si no se encuentra un odontólogo con el ID proporcionado, mostrar un mensaje de error
    alert('No se ha encontrado ningún odontólogo con la Matricula proporcionado.');
  }
});
