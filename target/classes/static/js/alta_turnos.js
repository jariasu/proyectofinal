window.addEventListener('load', function () {
    const formulario = document.querySelector('#add_new_turno');
    formulario.addEventListener('submit', async (e) => {
        e.preventDefault();

        const odontologo = {
            id: document.querySelector('#odontologoId').value,
        };

        const paciente = {
            id: document.querySelector('#pacienteId').value,
        };

        const formData = {
            odontologo: odontologo,
            paciente: paciente,
            fechaDeTurno: document.querySelector('#fecha').value
        };

        const url = '/turnos';
        const settings = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        };

        try {
            const response = await fetch(url, settings);
            if (response.ok) {
                // Si la respuesta es exitosa (código de estado 200-299)
                alert('Turno agregado exitosamente.');
                window.location.href = '/turnos.html';
            } else {
                // Si la respuesta no es exitosa
                alert('Ocurrió un error al agregar el turno. Por favor, intenta nuevamente.');
            }
        } catch (error) {
            console.error(error);
            alert('Ocurrió un error al agregar el turno. Por favor, intenta nuevamente.');
        }
    });
});
