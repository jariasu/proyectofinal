
const tableBody = document.querySelector('#lista-turnos tbody');


function construirFila(turno) {

  const row = document.createElement('tr');
  row.innerHTML = `
    <td>${turno.id} - Turno Generado: ${turno.fechaAltaTurno} - Fecha de Turno: ${turno.fechaDeTurno} </td>

    <td>ID: ${turno.pacienteId} - Nombre: ${turno.pacienteNombre} - Apellido: ${turno.pacienteApellido}</td>
    <td>ID: ${turno.odontologoId} - Nombre: ${turno.odontologoNombre} - Apellido: ${turno.odontologoApellido}</td>
    <td><button class="eliminar btn btn-danger" data-id="${turno.id}">Eliminar</button></td>
  `;

  return row;
}

fetch('/turnos')
  .then(response => response.json())
  .then(data => data.forEach(turno => tableBody.appendChild(construirFila(turno))))
  .catch(error => console.error(error));


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

function mostrarFormularioModificar(id) {

  const [fechaAltaTurno, fechaDeTurno, pacienteNombre, pacienteApellido, pacienteId, odontologoNombre, odontologoApellido, odontologoId] = document.querySelector(`[data-id="${id}"]`).closest('tr').querySelectorAll('td:not(:first-child)');

  document.getElementById('turno_id').value = id;
  document.getElementById('fechaAltaTurno').value = fechaAltaTurno.textContent;
  document.getElementById('fechaDeTurno').value = fechaDeTurno.textContent;
  document.getElementById('pacienteNombre').value = pacienteNombre.textContent;
  document.getElementById('pacienteApellido').value = pacienteApellido.textContent;
  document.getElementById('pacienteId').value = pacienteId.textContent;
  document.getElementById('odontologoNombre').value = odontologoNombre.textContent;
  document.getElementById('odontologoApellido').value = odontologoApellido.textContent;
  document.getElementById('odontologoId').value = odontologoId.textContent;


  document.getElementById('div_turno_updating').style.display = 'block';
  document.getElementById('h4_modificar').style.display = 'block';
  }




document.getElementById('recargar_tabla_btn').addEventListener('click', () => {

    document.getElementById('buscar_turno_input').value = '';
  fetch('/turnos')
    .then(response => response.json())
    .then(data => {
     
      tableBody.innerHTML = '';
     
      data.forEach(turno => tableBody.appendChild(construirFila(turno)));
    })
    .catch(error => console.error(error));
});


document.getElementById('buscar_turno_btn').addEventListener('click', async () => {

  const id = document.getElementById('buscar_turno_input').value;
  document.getElementById('buscar_turno_input').value = '';

  try {
    
    const response = await fetch(`/turnos/${id}`);
    const turno = await response.json();
    tableBody.innerHTML = '';

    const row = construirFila(turno);
    tableBody.appendChild(row);
  } catch (error) {

    alert('No se ha encontrado ningún odontólogo con el ID proporcionado.');
  }
});


document.getElementById('recargar_tabla_btn_paciente').addEventListener('click', () => {

    document.getElementById('buscar_turno_input_paciente').value = '';
  fetch('/turnos')
    .then(response => response.json())
    .then(data => {
  
      tableBody.innerHTML = '';

      data.forEach(turno => tableBody.appendChild(construirFila(turno)));
    })
    .catch(error => console.error(error));
});


document.getElementById('buscar_turno_btn_paciente').addEventListener('click', async () => {

  const id = document.getElementById('buscar_turno_input_paciente').value;
  document.getElementById('buscar_turno_input_paciente').value = '';

  try {
   
    const response = await fetch(`/turnos/pa/${id}`);
    const turnospa = await response.json();
    tableBody.innerHTML = '';

 
    if (Array.isArray(turnospa) && turnospa.length > 0) {
  
      turnospa.forEach(turno => {
        const row = construirFila(turno);
        tableBody.appendChild(row);
      });
    } else {
   
      alert('No se ha encontrado turno para el Paciente con el ID proporcionado.');
    }
  } catch (error) {
 
    console.error(error);
  }
});



document.getElementById('buscar_turno_btn_odontologo').addEventListener('click', async () => {

  const id = document.getElementById('buscar_turno_input_odontologo').value;
  document.getElementById('buscar_turno_input_odontologo').value = '';

  try {
 
    const response = await fetch(`/turnos/od/${id}`);
    const turnosod = await response.json();
    tableBody.innerHTML = '';

    if (Array.isArray(turnosod) && turnosod.length > 0) {
   
      turnosod.forEach(turno => {
        const row = construirFila(turno);
        tableBody.appendChild(row);
      });
    } else {
 
      alert('No se ha encontrado turno odra el Odontologo con el ID proporcionado.');
    }
  } catch (error) {

    console.error(error);
  }
});

