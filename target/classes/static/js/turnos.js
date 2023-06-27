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
    <td><button class="eliminar btn btn-danger" data-id="${turno.id}">Eliminar</button></td>
  `;
  // Devolver la fila creada
  return row;
}

// Hacer una petición GET al servidor para obtener los datos de los odontólogos y construir las filas de la tabla
fetch('/turnos')
  .then(response => response.json())
  .then(data => data.forEach(turno => tableBody.appendChild(construirFila(turno))))
  .catch(error => console.error(error));

// Eventos de clic para los botones de eliminar y editar odontólogos
document.addEventListener('click', (event) => {
  if (event.target.classList.contains('eliminar')) {
    const id = event.target.dataset.id;
    fetch(`/turnos/${id}`, { method: 'DELETE' })
      .then(() => {
        alert('Turno eliminado correctamente');
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
    document.getElementById('div_turno_updating').style.display = 'none';
  }
});

// Función para mostrar el formulario de edición y rellenar los campos con los datos del odontólogo
function mostrarFormularioModificar(id) {
  // Obtener los datos del odontólogo de la fila correspondiente en la tabla
  const [fechaAltaTurno, fechaDeTurno, pacienteNombre, pacienteApellido, pacienteId, odontologoNombre, odontologoApellido, odontologoId] = document.querySelector(`[data-id="${id}"]`).closest('tr').querySelectorAll('td:not(:first-child)');

  // Rellenar los campos del formulario con los datos del odontólogo
  document.getElementById('turno_id').value = id;
  document.getElementById('fechaAltaTurno').value = fechaAltaTurno.textContent;
  document.getElementById('fechaDeTurno').value = fechaDeTurno.textContent;
  document.getElementById('pacienteNombre').value = pacienteNombre.textContent;
  document.getElementById('pacienteApellido').value = pacienteApellido.textContent;
  document.getElementById('pacienteId').value = pacienteId.textContent;
  document.getElementById('odontologoNombre').value = odontologoNombre.textContent;
  document.getElementById('odontologoApellido').value = odontologoApellido.textContent;
  document.getElementById('odontologoId').value = odontologoId.textContent;

  // Mostrar el formulario de modificación
  document.getElementById('div_turno_updating').style.display = 'block';
  document.getElementById('h4_modificar').style.display = 'block';
  }

//// Evento para enviar los datos del formulario de modificación al servidor cuando se hace clic en el botón de guardar
//document.getElementById('update_turno_form').addEventListener('submit', async (event) => {
//  event.preventDefault();

//  // Obtener los datos del formulario
//
//  const turnoForm = {
//    id: document.querySelector('#turno_id').value,
//    nombre: document.querySelector('#nombre').value,
//    apellido: document.querySelector('#apellido').value,
//    dni: document.querySelector('#dni').value,
//  };
//
//
//const response = await fetch('/turnos', {
//  method: 'PUT',
//  headers: { 'Content-Type': 'application/json' },
//  body: JSON.stringify(turnoForm)
//});

//if (response.ok) {
//  const responseData = await response.json();
//  alert('El turno ha sido actualizado exitosamente con los siguientes datos: ' + JSON.stringify(responseData));
//  location.reload();
//} else if (response.status === 400) {
//  try {
//    const errorResponse = await response.json();
//    alert('Ha ocurrido un error en el servidor: ' + errorResponse.message);
//  } catch (error) {
//    alert('Ha ocurrido un error en el servidor: ' + response.statusText);
//  }
//  throw new Error('Error en el servidor');
//} else {
//  throw new Error('Error en el servidor');
//}
//
//});

// Evento click para recargar la tabla
document.getElementById('recargar_tabla_btn').addEventListener('click', () => {
  // Hacer una petición GET al servidor para obtener los datos de los odontólogos y construir las filas de la tabla
    document.getElementById('buscar_turno_input').value = '';
  fetch('/turnos')
    .then(response => response.json())
    .then(data => {
      // Eliminar las filas actuales de la tabla
      tableBody.innerHTML = '';
      // Agregar las filas de la tabla con los nuevos datos obtenidos del servidor
      data.forEach(turno => tableBody.appendChild(construirFila(turno)));
    })
    .catch(error => console.error(error));
});

// Agregar un evento click al botón "Buscar" para realizar la solicitud GET
document.getElementById('buscar_turno_btn').addEventListener('click', async () => {
  // Obtener el valor del input que contiene el ID
  const id = document.getElementById('buscar_turno_input').value;
  document.getElementById('buscar_turno_input').value = '';

  try {
    // Realizar la solicitud GET al servidor con el valor del ID en la URL
    const response = await fetch(`/turnos/${id}`);
    const turno = await response.json();
    tableBody.innerHTML = '';

    // Si se encuentra un odontólogo con el ID proporcionado, construir una fila en la tabla con los datos del odontólogo encontrado
    const row = construirFila(turno);
    tableBody.appendChild(row);
  } catch (error) {
    // Si no se encuentra un odontólogo con el ID proporcionado, mostrar un mensaje de error
    alert('No se ha encontrado ningún odontólogo con el ID proporcionado.');
  }
});

// Evento click para recargar la tabla
document.getElementById('recargar_tabla_btn_paciente').addEventListener('click', () => {
  // Hacer una petición GET al servidor para obtener los datos de los odontólogos y construir las filas de la tabla
    document.getElementById('buscar_turno_input_paciente').value = '';
  fetch('/turnos')
    .then(response => response.json())
    .then(data => {
      // Eliminar las filas actuales de la tabla
      tableBody.innerHTML = '';
      // Agregar las filas de la tabla con los nuevos datos obtenidos del servidor
      data.forEach(turno => tableBody.appendChild(construirFila(turno)));
    })
    .catch(error => console.error(error));
});

// Agregar un evento click al botón "Buscar" para realizar la solicitud GET
document.getElementById('buscar_turno_btn_paciente').addEventListener('click', async () => {
  // Obtener el valor del input que contiene el ID
  const id = document.getElementById('buscar_turno_input_paciente').value;
  document.getElementById('buscar_turno_input_paciente').value = '';

  try {
    // Realizar la solicitud GET al servidor con el valor del ID en la URL
    const response = await fetch(`/turnos/pa/${id}`);
    const turnospa = await response.json();
    tableBody.innerHTML = '';

    // Si se encuentra un odontólogo con el ID proporcionado
    if (Array.isArray(turnospa) && turnospa.length > 0) {
      // Construir una fila en la tabla por cada objeto en el array
      turnospa.forEach(turno => {
        const row = construirFila(turno);
        tableBody.appendChild(row);
      });
    } else {
      // Si no se encuentra ningún odontólogo con el ID proporcionado, mostrar un mensaje de error
      alert('No se ha encontrado turno para el Paciente con el ID proporcionado.');
    }
  } catch (error) {
    // Capturar errores de red u otros errores en la solicitud
    console.error(error);
  }
});


//OD

// Agregar un evento click al botón "Buscar" odra realizar la solicitud GET
document.getElementById('buscar_turno_btn_odontologo').addEventListener('click', async () => {
  // Obtener el valor del input que contiene el ID
  const id = document.getElementById('buscar_turno_input_odontologo').value;
  document.getElementById('buscar_turno_input_odontologo').value = '';

  try {
    // Realizar la solicitud GET al servidor con el valor del ID en la URL
    const response = await fetch(`/turnos/od/${id}`);
    const turnosod = await response.json();
    tableBody.innerHTML = '';

    // Si se encuentra un odontólogo con el ID proporcionado
    if (Array.isArray(turnosod) && turnosod.length > 0) {
      // Construir una fila en la tabla por cada objeto en el array
      turnosod.forEach(turno => {
        const row = construirFila(turno);
        tableBody.appendChild(row);
      });
    } else {
      // Si no se encuentra ningún odontólogo con el ID proporcionado, mostrar un mensaje de error
      alert('No se ha encontrado turno odra el Odontologo con el ID proporcionado.');
    }
  } catch (error) {
    // Capturar errores de red u otros errores en la solicitud
    console.error(error);
  }
});

