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


    <td><button class="eliminar btn btn-danger" data-id="${paciente.id}">Eliminar</button></td>
    <td><button class="editar btn btn-warning" data-id="${paciente.id}">Editar</button></td>
  `;
  // Devolver la fila creada
  return row;
}

// Hacer una petición GET al servidor para obtener los datos de los odontólogos y construir las filas de la tabla
fetch('/pacientes')
  .then(response => response.json())
  .then(data => data.forEach(paciente => tableBody.appendChild(construirFila(paciente))))
  .catch(error => console.error(error));

// Eventos de clic para los botones de eliminar y editar odontólogos
document.addEventListener('click', (event) => {
  if (event.target.classList.contains('eliminar')) {
    const id = event.target.dataset.id;
    fetch(`/pacientes/${id}`, { method: 'DELETE' })
      .then(() => {
        alert('Paciente eliminado correctamente');
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
    document.getElementById('div_paciente_updating').style.display = 'none';
  }
});

// Función para mostrar el formulario de edición y rellenar los campos con los datos del odontólogo
function mostrarFormularioModificar(id) {
  // Obtener los datos del odontólogo de la fila correspondiente en la tabla
  const [nombre, apellido, dni, fechaIngreso, calle, numero, localidad, provincia] = document.querySelector(`[data-id="${id}"]`).closest('tr').querySelectorAll('td:not(:first-child)');

  // Rellenar los campos del formulario con los datos del odontólogo
  document.getElementById('paciente_id').value = id;
  document.getElementById('nombre').value = nombre.textContent;
  document.getElementById('apellido').value = apellido.textContent;
  document.getElementById('dni').value = dni.textContent;
  document.getElementById('fechaAlta').value = fechaIngreso.textContent;
  document.getElementById('calle').value = calle.textContent;
  document.getElementById('numero').value = numero.textContent;
  document.getElementById('localidad').value = localidad.textContent;
  document.getElementById('provincia').value = localidad.textContent;

  // Mostrar el formulario de modificación
  document.getElementById('div_paciente_updating').style.display = 'block';
  document.getElementById('h4_modificar').style.display = 'block';
  }

// Evento para enviar los datos del formulario de modificación al servidor cuando se hace clic en el botón de guardar
document.getElementById('update_paciente_form').addEventListener('submit', async (event) => {
  event.preventDefault();

  // Obtener los datos del formulario
    const domicilio = {
      calle: document.querySelector('#calle').value,
      numero: document.querySelector('#numero').value,
      localidad: document.querySelector('#localidad').value,
      provincia: document.querySelector('#provincia').value,
    };
  const pacienteData = {
    id: document.querySelector('#paciente_id').value,
    fechaIngreso: document.querySelector('#fechaAlta').value,
        id: document.querySelector('#paciente_id').value,
    nombre: document.querySelector('#nombre').value,
    apellido: document.querySelector('#apellido').value,
    dni: document.querySelector('#dni').value,
    domicilio : domicilio,
  };


const response = await fetch('/pacientes', {
  method: 'PUT',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify(pacienteData)
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
    document.getElementById('buscar_paciente_input').value = '';
  fetch('/pacientes')
    .then(response => response.json())
    .then(data => {
      // Eliminar las filas actuales de la tabla
      tableBody.innerHTML = '';
      // Agregar las filas de la tabla con los nuevos datos obtenidos del servidor
      data.forEach(paciente => tableBody.appendChild(construirFila(paciente)));
    })
    .catch(error => console.error(error));
});

// Agregar un evento click al botón "Buscar" para realizar la solicitud GET
document.getElementById('buscar_paciente_btn').addEventListener('click', async () => {
  // Obtener el valor del input que contiene el ID
  const id = document.getElementById('buscar_paciente_input').value;
  document.getElementById('buscar_paciente_input').value = '';

  try {
    // Realizar la solicitud GET al servidor con el valor del ID en la URL
    const response = await fetch(`/pacientes/${id}`);
    const paciente = await response.json();
    tableBody.innerHTML = '';

    // Si se encuentra un odontólogo con el ID proporcionado, construir una fila en la tabla con los datos del odontólogo encontrado
    const row = construirFila(paciente);
    tableBody.appendChild(row);
  } catch (error) {
    // Si no se encuentra un odontólogo con el ID proporcionado, mostrar un mensaje de error
    alert('No se ha encontrado ningún paciente con el ID proporcionado.');
  }
});

// Agregar un evento click al botón "Buscar" para realizar la solicitud GET
document.getElementById('boton_paciente_dni').addEventListener('click', async () => {
  // Obtener el valor del input que contiene el ID
  const dni = document.getElementById('buscar_paciente_dni_input').value;
  document.getElementById('buscar_paciente_dni_input').value = '';

  try {
    // Realizar la solicitud GET al servidor con el valor del ID en la URL
    const response = await fetch(`/pacientes/dni/${dni}`);
    const paciente = await response.json();
    tableBody.innerHTML = '';

    // Si se encuentra un odontólogo con el ID proporcionado, construir una fila en la tabla con los datos del odontólogo encontrado
    const row = construirFila(paciente);
    tableBody.appendChild(row);
  } catch (error) {
    // Si no se encuentra un odontólogo con el ID proporcionado, mostrar un mensaje de error
    alert('No se ha encontrado ningún paciente con el DNI proporcionado');
  }
});