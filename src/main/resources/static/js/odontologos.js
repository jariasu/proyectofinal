
const tableBody = document.querySelector('#lista-odontologos tbody');

function construirFila(odontologo) {

  const row = document.createElement('tr');
  row.innerHTML = `
    <td>${odontologo.id}</td>
    <td>${odontologo.nombre}</td>
    <td>${odontologo.apellido}</td>
    <td>${odontologo.matricula}</td>
    <td><button class="eliminar btn btn-danger" data-id="${odontologo.id}">Eliminar</button></td>
    <td><button class="editar btn btn-warning" data-id="${odontologo.id}">Editar</button></td>
  `;

  return row;
}


fetch('/odontologos')
  .then(response => response.json())
  .then(data => data.forEach(odontologo => tableBody.appendChild(construirFila(odontologo))))
  .catch(error => console.error(error));


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


function mostrarFormularioModificar(id) {

  const [nombre, apellido, matricula] = document.querySelector(`[data-id="${id}"]`).closest('tr').querySelectorAll('td:not(:first-child)');

 
  document.getElementById('odontologo_id').value = id;
  document.getElementById('nombre').value = nombre.textContent;
  document.getElementById('apellido').value = apellido.textContent;
  document.getElementById('matricula').value = matricula.textContent;


  document.getElementById('div_odontologo_updating').style.display = 'block';
  document.getElementById('h4_modificar').style.display = 'block';
  }


document.getElementById('update_odontologo_form').addEventListener('submit', async (event) => {
  event.preventDefault();

  
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


document.getElementById('recargar_tabla_btn').addEventListener('click', () => {
 
    document.getElementById('buscar_odontologo_input').value = '';
  fetch('/odontologos')
    .then(response => response.json())
    .then(data => {
     
      tableBody.innerHTML = '';
      
      data.forEach(odontologo => tableBody.appendChild(construirFila(odontologo)));
    })
    .catch(error => console.error(error));
});


document.getElementById('buscar_odontologo_btn').addEventListener('click', async () => {
  
  const id = document.getElementById('buscar_odontologo_input').value;
  document.getElementById('buscar_odontologo_input').value = '';

  try {
   
    const response = await fetch(`/odontologos/${id}`);
    const odontologo = await response.json();
    tableBody.innerHTML = '';

  
    const row = construirFila(odontologo);
    tableBody.appendChild(row);
  } catch (error) {
    
    alert('No se ha encontrado ningún odontólogo con el ID proporcionado.');
  }
});



document.getElementById('boton_odontologo_matricula').addEventListener('click', async () => {

  const matricula = document.getElementById('buscar_odontologo_matricula_input').value;
  document.getElementById('buscar_odontologo_matricula_input').value = '';

  try {

    const response = await fetch(`/odontologos/matricula/${matricula}`);
    const odontologo = await response.json();
    tableBody.innerHTML = '';

   
    const row = construirFila(odontologo);
    tableBody.appendChild(row);
  } catch (error) {
   
    alert('No se ha encontrado ningún odontólogo con la Matricula proporcionado.');
  }
});
